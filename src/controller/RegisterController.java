package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import Exceptions.InvalidPassword;
import Exceptions.InvalidUsername;
import Exceptions.UncompleteField;
import Model.Customer;
import Utils.Gender;
import Utils.Neighberhood;
import application.Main;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class RegisterController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;

	// Fields
	
	@FXML
	private JFXTextField firstName, lastName, userName;
	
	@FXML
	private JFXPasswordField password, passwordAgain;

	@FXML
	private JFXRadioButton male, female, unknown;
	
	@FXML
	private DatePicker birthDate;
	
	@FXML
	private JFXCheckBox lactose, gluten, isRandomPassword;
	
	@FXML
	private JFXComboBox<Neighberhood> neighborhood;
	
	@FXML
	private ImageView image; //---new
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (Main.getRest().getNeighsInRest() == null)
			Main.getRest().resetNeighsInRest();
			
		neighborhood.getItems().addAll(Main.getRest().getNeighsInRest());		
	}
	

	
	String pass, passAgain; // Define here for using in isRandomPassword()
	boolean randomPasswordFlag = false;
	Customer tempCustomer;// for Image
	int id = 0;
	public void addCustomer(ActionEvent event) throws IOException {
		String first, last, user;
		Gender gender = null;
		LocalDate date;
		boolean lactose1 = false, gluten1 = false;
		Neighberhood neigh;
		
		first = firstName.getText();
		last = lastName.getText();
		user = userName.getText();
		if (!randomPasswordFlag) {
			pass = password.getText();
			passAgain = passwordAgain.getText();
		}
		date = birthDate.getValue();
		neigh = neighborhood.getValue();
		
		
		try {
			if (first == null || first.equals("") || last == null || last.equals("") || date == null || neigh == null) 
				throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("You didn't finish complete all the fields\nPlease try again :)");
			alert.show();
			return;
		}
						
		
		if (male.isSelected())
			gender = Gender.Male;
		else if (female.isSelected())
			gender = Gender.Female;
		else if (unknown.isSelected())
			gender = Gender.Unknown;
		
		
		if (lactose.isSelected())
			lactose1 = true;
		if (gluten.isSelected())
			gluten1 = true;
		
		
		int age = Period.between(date, LocalDate.now()).getYears();
		if (age < 16) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Illegal age");
			alert.setHeaderText("You are too young, go to Mcdonald's");
			alert.show();
			return;
		}
		
		
		try {
			if (!isValidUsername(user)) 
				throw new InvalidUsername();
			if (!isValidPassword(pass))
				throw new InvalidPassword();
		} catch (InvalidUsername | InvalidPassword e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect field");
			alert.setHeaderText(e.getMessage());
			alert.show();
			return;
		}
		
		if (!randomPasswordFlag)
			pass = password.getText();
		
		if (!pass.equals(passAgain)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Unmatch password");
			alert.setHeaderText("The passwords you entered are not identical");
			alert.show();
			return;
		}
		Customer addCustomer = new Customer(first, last, date, gender, user, pass, neigh, lactose1, gluten1);
		Main.getRest().addCustomer(addCustomer);

		if(image.getImage() != null) {
			String imageName = "c"+addCustomer.getId();
			File fileOutput = new File("src/Media/"+imageName+".png");
			BufferedImage bi = SwingFXUtils.fromFXImage(image.getImage(),null);
			ImageIO.write(bi, "png", fileOutput);	
		}

	
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Register completed successfully");
		alert.setHeaderText("Welcomt to JavaEat!\nHope you are hungry");
		if (alert.showAndWait().get() == ButtonType.OK)
			switchToLogin(event);
		
	}

	//---new for Image
	public void handleButtonAction (DragEvent event) {
		if(event.getDragboard().hasFiles()) {
			event.acceptTransferModes(TransferMode.ANY);
		}
	}

	public void handleDrop(DragEvent event) throws IOException{
		List<File> files = event.getDragboard().getFiles();
		Image img = new Image(new FileInputStream(files.get(0)));
		image.setImage(img);
	}
	
	public void refreshImage(ActionEvent event) {
		image.setImage(null);
	}
	
	public void isRandomPassword(ActionEvent event) {
		if (isRandomPassword.isSelected()) {
			password.clear();
			passwordAgain.clear();
			password.setPromptText("Not available");
			passwordAgain.setPromptText("Not available");
			password.setEditable(false);
			passwordAgain.setEditable(false);
			
			do {
				pass = makeRamdomizePassword();
			}while(!isValidPassword(pass));
			passAgain = pass;
			
			randomPasswordFlag = true;
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("New randomize password");
			alert.setHeaderText("Your password:\n" + pass);
			alert.setContentText("Please copy your password and keep it as a secret");
			alert.show();
		}
		else {
			randomPasswordFlag = false;
			pass = password.getText();
			passAgain = passwordAgain.getText();
			password.setEditable(true);
			passwordAgain.setEditable(true);
			password.setPromptText("Enter password");
			passwordAgain.setPromptText("Enter password again");
		}
	}
	
	
	public static String makeRamdomizePassword() {
			
		String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
		+ "!\\\"#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}~" + "abcdefghijklmnopqrstuvwxyz";
		String newPassword = "";
		int randIndex;
		Random rand = new Random();
		for (int i=0; i<10; i++) { // Length of new password - 10;
				randIndex = rand.nextInt(allChars.length());
				newPassword += allChars.charAt(randIndex);
		}
		
		return newPassword;
	}
	
	public static boolean isValidPassword(String pass) {
		if (pass == null || pass.length()<=5)
			return false;
		
		boolean containCapital = false;
		boolean containDigit = false;
		boolean containPunctuations = false; 
		String punctuation = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
		
				
		for (char c :  pass.toCharArray()){

			if (c >= 'A' && c <= 'Z')
				containCapital = true;
			
			if (c >= '0' && c <='9')
				containDigit = true;
			
			for (char punc : punctuation.toCharArray()) // VeryNew - I added 
				if (c == punc)
					containPunctuations = true;
		}
		
		if (!containCapital || !containDigit || !containPunctuations)
			return false;
		
		return true;
	}
	
	public static boolean isValidUsername(String user) {
		if (Main.getRest().getUserNames() == null)
			Main.getRest().resetUserNames();
//		System.out.println(Main.getRest().getUserNames());
		if (user.equals("") || user == null)
			return false;
		if (Main.getRest().getUserNames().contains(user))
			return false;
		return true;
	}



	public void switchToLogin(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/LoginScene.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Login");
		 stage.show();
		 
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
	}
	

	

}
