package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import Exceptions.UncompleteField;
import Model.Cook;
import Utils.Expertise;
import Utils.Gender;
import application.Main;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CookController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	//------------------------
	
    @FXML
    private TableView<Cook> table;

    @FXML
    private TableColumn<Cook, Integer> idCol;

    @FXML
    private TableColumn<Cook, String> firstNameCol;

    @FXML
    private TableColumn<Cook, String> lastNameCol;

    @FXML
    private TableColumn<Cook, LocalDate> birthDateCol;

    @FXML
    private TableColumn<Cook, Gender> genderCol;

    @FXML
    private TableColumn<Cook, Expertise> expertCol;

    @FXML
    private TableColumn<Cook, Boolean> isChefCol;	
        
    ObservableList<Cook> list = FXCollections.observableArrayList(Main.getRest().getCooks().values());
    //end table
    
    
    //add cook  
    @FXML
    private JFXTextField firstName, lastName;
    
    @FXML
    private DatePicker birthday;
    
    @FXML
    private JFXRadioButton male, female, unknown;
    
    @FXML
    private JFXComboBox<Expertise> expertise;
	private Expertise[] expertises = Expertise.values();
	
	@FXML
	private JFXCheckBox isChef;
	
	@FXML
	private JFXButton submitAdd;
	
	
	// Edit cook
	@FXML
	private JFXTextField idForEdit;
	
	@FXML
	private JFXButton searchId, submitEdit;
	
	// Remove cook
	@FXML
	private JFXButton searchIdRemove;
	
	//Design
		
	@FXML
	private JFXButton RemoveCook, EditCook, AddCook;
			
	@FXML	   
	private AnchorPane pane;
    
    

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		//-- The String parameter in PropertyValueFactory has to be identical to field's name in Cook
		//-- The Cook must be with getters with classic name 'getField' 
		idCol.setCellValueFactory(new PropertyValueFactory<Cook, Integer>("id"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Cook, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Cook, String>("lastName"));
		birthDateCol.setCellValueFactory(new PropertyValueFactory<Cook, LocalDate>("birthDay"));
		genderCol.setCellValueFactory(new PropertyValueFactory<Cook, Gender>("gender"));
		expertCol.setCellValueFactory(new PropertyValueFactory<Cook, Expertise>("expert"));
		isChefCol.setCellValueFactory(new PropertyValueFactory<Cook, Boolean>("isChef"));
		
		table.setItems(list);
		
		//addCook
		firstName.setVisible(false);
		lastName.setVisible(false);
		birthday.setVisible(false);
		male.setVisible(false);
		female.setVisible(false);
		unknown.setVisible(false);
		expertise.getItems().addAll(expertises);
		expertise.setVisible(false);
		isChef.setVisible(false);
		submitAdd.setVisible(false);
		
		// Edit Cook
		idForEdit.setVisible(false);
		searchId.setVisible(false);
		submitEdit.setVisible(false);
		
		// Remove Cook
		searchIdRemove.setVisible(false);
		

		//Design
		AddCook.setVisible(false);
		EditCook.setVisible(false);
		RemoveCook.setVisible(false);
		
		
	}
	
	public void ShowMenu(ActionEvent event) {
    	pane.setTranslateX(-300);
    	
    	TranslateTransition slide = new TranslateTransition();
    	slide.setDuration(Duration.seconds(1));
    	slide.setNode(pane);

    	slide.setToX(0);
    	slide.play();
    	
		AddCook.setVisible(true);
		EditCook.setVisible(true);
		RemoveCook.setVisible(true);
    	
    }
	  
	
	public void showAddCook(ActionEvent event) {
		searchIdRemove.setVisible(false);
		
		idForEdit.setVisible(false);
		searchId.setVisible(false);
		submitEdit.setVisible(false);
		
		firstName.clear();
		firstName.setVisible(true);
		lastName.clear();
		lastName.setVisible(true);
		birthday.setValue(null);
		birthday.setVisible(true);
		male.setVisible(true);
		female.setVisible(true);
		unknown.setVisible(true);
		expertise.setValue(null);
		expertise.setVisible(true);
		isChef.setVisible(true);
		submitAdd.setVisible(true);
	}
	
	public void submitNewCook(ActionEvent event) throws IOException {
		String first, last, userName, password;
		LocalDate birth;
		Gender gender;
		Expertise experty;
		boolean chef = false;
		
		first = firstName.getText();
		last = lastName.getText();
		birth = birthday.getValue();
		
		if(male.isSelected())
			gender = Gender.Male;
		else if(female.isSelected())
			gender = Gender.Female;
		else
			gender = Gender.Unknown;
		
		experty = expertise.getValue();
		
		if(isChef.isSelected())
			chef = true;
	
		// Check for null fields
		try {
			if (first == null || first.equals("") || last == null || last.equals("") || birth == null || experty == null) 
				throw new UncompleteField();
		} catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("You didn't finish complete all the fields\nPlease try again :)");
			alert.show();
			return;
		}
		
		// Check age
		int age = Period.between(birth, LocalDate.now()).getYears();
		if (age < 16) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Illegal age");
			alert.setHeaderText("The cook is too young!");
			alert.show();
			return;
		}
		
		// Create new user name
		userName = first + last + birth.getDayOfYear();
		if(!RegisterController.isValidUsername(userName)) {
			userName+=(Cook.getIdCounter()+1); // save unique userName
		}
		
		// Create new password
		do {
			password = RegisterController.makeRamdomizePassword();
		}while(RegisterController.isValidPassword(password));
		
		Main.getRest().addCook(new Cook(first, last, birth, gender, userName, password, experty, chef));
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("New cook was added");
//		alert.setHeaderText("The username is : " + userName +  "\nThe password is: " + password);
//		alert.setContentText("Please copy the details");
		alert.setHeaderText("The cook " + first + " " + last + " was added successfully");
		if (alert.showAndWait().get() == ButtonType.OK)
			refresh(event);		
	}
	
	
	public void showEditCook(ActionEvent event) {
		idForEdit.setVisible(true);
		searchId.setVisible(true);

		searchIdRemove.setVisible(false);
		
		firstName.setVisible(false);
		lastName.setVisible(false);
		birthday.setVisible(false);
		male.setVisible(false);
		female.setVisible(false);
		unknown.setVisible(false);
		expertise.setVisible(false);
		isChef.setVisible(false);
		submitAdd.setVisible(false);

	}
	
	Cook tempCook; // External - for use in 2 functions
	public void searchForEdit(ActionEvent event) {
		
		String id = idForEdit.getText();
		int idNum;
		
		try {
			idNum = Integer.parseInt(id);
		} catch(NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("Illegal id");
			alert.show();
			return;
		}
		
		tempCook = Main.getRest().getRealCook(idNum);
		
		if (tempCook == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("We couldn't find this cook");
			alert.show();
			return;
		}
		
		firstName.setVisible(true);
		firstName.setText(tempCook.getFirstName());
		
		lastName.setVisible(true);
		lastName.setText(tempCook.getLastName());
		
		birthday.setVisible(true);
		birthday.setValue(tempCook.getBirthDay());
		
		male.setVisible(true);
		female.setVisible(true);
		unknown.setVisible(true);
		
		if (tempCook.getGender().equals(Gender.Male))
			male.setSelected(true);
		else if (tempCook.getGender().equals(Gender.Female))
			female.setSelected(true);
		
		expertise.setVisible(true);
		expertise.setValue(tempCook.getExpert());
		
		isChef.setVisible(true);
		if (tempCook.getIsChef())
			isChef.setSelected(true);
		
		submitEdit.setVisible(true);
	
	}
	
	public void submitEditCook(ActionEvent event) throws IOException {
		
		// Same code from up
		String first, last;
		LocalDate birth;
		Gender gender;
		Expertise experty;
		boolean chef = false;
				
		first = firstName.getText();
		last = lastName.getText();
		birth = birthday.getValue();
		if(male.isSelected())
			gender = Gender.Male;
		else if(female.isSelected())
			gender = Gender.Female;
		else
			gender = Gender.Unknown;
		experty = expertise.getValue();
		if(isChef.isSelected())
			chef = true;
	
		//check for null fields
		try {
			if (first == null || first.equals("") || last == null || last.equals("") || birth == null || experty == null) 
				throw new UncompleteField();
		} catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("You didn't finish complete all the fields\nPlease try again :)");
			alert.show();
			return;
		}
		

		int age = Period.between(birth, LocalDate.now()).getYears();
		if (age < 16) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Illegal age");
			alert.setHeaderText("The cook is too young!");
			alert.show();
			return;
		}
		// End of old code
		
		tempCook.setFirstName(first);
		tempCook.setLastName(last);
		tempCook.setBirthDay(birth);
		tempCook.setGender(gender);
		tempCook.setExpert(experty);
		tempCook.setChef(chef);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Edit successfully");
		alert.setHeaderText("The details of the cook were updated");
		if (alert.showAndWait().get() == ButtonType.OK)
			refresh(event);

	}
		
	
	
	// Remove cook
	public void showRemoveCook(ActionEvent event) {
		idForEdit.setVisible(true);
		searchIdRemove.setVisible(true);
		
		firstName.setVisible(false);
		lastName.setVisible(false);
		birthday.setVisible(false);
		male.setVisible(false);
		female.setVisible(false);
		unknown.setVisible(false);
		expertise.setVisible(false);
		isChef.setVisible(false);
		submitAdd.setVisible(false);
		
		searchId.setVisible(false);
		submitEdit.setVisible(false);
	}
	
	public void removeCook(ActionEvent event) throws IOException {
		String id = idForEdit.getText();
		int idNum;
		
		try {
			idNum = Integer.parseInt(id);
		} catch(NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("Illegal id");
			alert.show();
			return;
		}
		
		// tempCook is external
		tempCook = Main.getRest().getRealCook(idNum);
		
		if (tempCook == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("We couldn't find this cook");
			alert.show();
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove Cook");
		alert.setHeaderText("Are you sure you want to remove the cook:\n" 
		+ tempCook.getFirstName() + " " + tempCook.getLastName());
		if (alert.showAndWait().get() == ButtonType.OK) {
			Main.getRest().removeCook(tempCook);
			refresh(event);
		}
	}
	
	public void refresh(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/Cooks.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Cooks");
		 stage.show();
	}
	
	public void switchToHomePage(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/HomePageManager.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Home page");
		 stage.show();
	}
}
