package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import Exceptions.UncompleteField;
import Model.Cook;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Utils.Gender;
import Utils.Vehicle;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DeliveryPersonController implements Initializable {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	//------------------------
	
	//table
	
	@FXML
   private TableView<DeliveryPerson> table;

    @FXML
    private TableColumn<DeliveryPerson, Integer> idCol;

    @FXML
    private TableColumn<DeliveryPerson, String> firstNameCol;

    @FXML
    private TableColumn<DeliveryPerson, String> lastNameCol;

    @FXML
    private TableColumn<DeliveryPerson, LocalDate> birthDateCol;

    @FXML
    private TableColumn<DeliveryPerson, Gender> genderCol;

    @FXML
    private TableColumn<DeliveryPerson, Vehicle> vehicleCol;

    @FXML
    private TableColumn<DeliveryPerson, DeliveryArea> deliveryAreaCol;	// --maybe DeliveryArea and not String --
    
   ObservableList<DeliveryPerson> list = FXCollections.observableArrayList(Main.getRest().getDeliveryPersons().values());
   
   //Add delivery person
   
   @FXML
   private JFXTextField firstName, lastName;
   
   @FXML
   private DatePicker birthday;
   
   @FXML
   private JFXRadioButton male, female, unknown;
   
   @FXML
   private JFXComboBox<Vehicle> vehicle;
   private Vehicle[] vehicles = Vehicle.values();
   
   @FXML
   private JFXComboBox<DeliveryArea> deliveryArea;
   private Collection<DeliveryArea> deliveryAreas = Main.getRest().getAreas().values();

	@FXML
	private JFXButton submitAdd;
   
   
   //Edit delivery person
   
	@FXML
	private JFXTextField idForEditDP;
	
	@FXML
	private JFXButton searchId, submitEdit;
   
   // remove delivery person
   
	@FXML
	private JFXButton searchIdRemoveDP;
	
	
	//Design
  		
  	@FXML
  	private JFXButton AddDP, EditDP, RemoveDP;
  			
  	@FXML	   
  	private AnchorPane pane;
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	  	
    	idCol.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, Integer>("id"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, String>("lastName"));
		birthDateCol.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, LocalDate>("birthDay"));
		genderCol.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, Gender>("gender"));
		vehicleCol.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, Vehicle>("vehicle"));
		deliveryAreaCol.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, DeliveryArea>("area"));
		
		table.setItems(list);
		
		//addDeliveryPerson
		firstName.setVisible(false);
		lastName.setVisible(false);
		birthday.setVisible(false);
		male.setVisible(false);
		female.setVisible(false);
		unknown.setVisible(false);
		deliveryArea.getItems().addAll(deliveryAreas);
		vehicle.getItems().addAll(vehicles);
		vehicle.setVisible(false);
		deliveryArea.setVisible(false);
		submitAdd.setVisible(false);
		
		// Edit DeliveryPerson
		idForEditDP.setVisible(false);
		searchId.setVisible(false);
		submitEdit.setVisible(false);
				
		// Remove DeliveryPerson
		searchIdRemoveDP.setVisible(false);
		
		//Design
		AddDP.setVisible(false);
		EditDP.setVisible(false);
		RemoveDP.setVisible(false);
	}
    
    public void ShowMenu(ActionEvent event) {
    	pane.setTranslateX(-300);
    	
    	TranslateTransition slide = new TranslateTransition();
    	slide.setDuration(Duration.seconds(1));
    	slide.setNode(pane);

    	slide.setToX(0);
    	slide.play();
    	
		AddDP.setVisible(true);
		EditDP.setVisible(true);
		RemoveDP.setVisible(true);
    	
    }


    
    public void showAddDeliveryPerson(ActionEvent event) {
		searchIdRemoveDP.setVisible(false);
		
		idForEditDP.setVisible(false);
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
		vehicle.setValue(null);
		vehicle.setVisible(true);
		deliveryArea.setValue(null);
		deliveryArea.setVisible(true);
		submitAdd.setVisible(true);
	}
    
    
    public void submitNewDeliveryPerson(ActionEvent event) throws IOException {
		String first, last, userName, password;
		LocalDate birth;
		Gender gender;
		Vehicle vehicle_1;
		DeliveryArea dArea;
		
		first = firstName.getText();
		last = lastName.getText();
		birth = birthday.getValue();
		
		if(male.isSelected())
			gender = Gender.Male;
		else if(female.isSelected())
			gender = Gender.Female;
		else
			gender = Gender.Unknown;
		
		vehicle_1 = vehicle.getValue();
		dArea = deliveryArea.getValue();
		
		
	
		// Check for null fields
		try {
			if (first == null || first.equals("") || last == null || last.equals("") || birth == null || vehicle_1 == null || dArea == null) 
				throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Uncomplete field");
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

		Main.getRest().addDeliveryPerson(new DeliveryPerson(first, last, birth, gender, userName, password, vehicle_1, dArea), dArea);
		
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("New DeliveryPerson was added");
//		alert.setHeaderText("The username is : " + userName +  "\nThe password is: " + password);
		alert.setHeaderText("The delivery person " + first + " " + last + " was added successfully");
//		alert.setContentText("Please copy the details");
		if (alert.showAndWait().get() == ButtonType.OK)
			refresh(event);		
	}
	

    
    public void showEditDeliveryPerson(ActionEvent event) {
		searchIdRemoveDP.setVisible(false);
		
		firstName.setVisible(false);
		lastName.setVisible(false);
		birthday.setVisible(false);
		male.setVisible(false);
		female.setVisible(false);
		unknown.setVisible(false);
		vehicle.setVisible(false);
		deliveryArea.setVisible(false);
		submitAdd.setVisible(false);

		idForEditDP.setVisible(true);
		searchId.setVisible(true);
	}
	
    DeliveryPerson tempDP; // External - for use in 2 functions
    
	public void searchForEdit(ActionEvent event) {
		
		String id = idForEditDP.getText();
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
		
		tempDP = Main.getRest().getRealDeliveryPerson(idNum);
		
		if (tempDP == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("We couldn't find this DeliveryPerson");
			alert.show();
			return;
		}
		
		firstName.setVisible(true);
		firstName.setText(tempDP.getFirstName());
		
		lastName.setVisible(true);
		lastName.setText(tempDP.getLastName());
		
		birthday.setVisible(true);
		birthday.setValue(tempDP.getBirthDay());
		
		male.setVisible(true);
		female.setVisible(true);
		unknown.setVisible(true);
		
		if (tempDP.getGender().equals(Gender.Male))
			male.setSelected(true);
		else if (tempDP.getGender().equals(Gender.Female))
			female.setSelected(true);
		
		vehicle.setVisible(true);
		vehicle.setValue(tempDP.getVehicle());
		
		//---deliveryArea
		deliveryArea.setVisible(true);
		deliveryArea.setValue(tempDP.getArea());
		
		submitEdit.setVisible(true);
	
	}
	
	public void submitEditDeliveryPerson(ActionEvent event) throws IOException {
		
		// Same code from up
		String first, last;
		LocalDate birth;
		Gender gender;
		Vehicle vehicle_1;
		//---deliveryArea
		DeliveryArea dArea;
		
		
		first = firstName.getText();
		last = lastName.getText();
		birth = birthday.getValue();
		if(male.isSelected())
			gender = Gender.Male;
		else if(female.isSelected())
			gender = Gender.Female;
		else
			gender = Gender.Unknown;
		vehicle_1 = vehicle.getValue();
		dArea = deliveryArea.getValue();
		
		//check for null fields
		try {
			if (first == null || first.equals("") || last == null || last.equals("") || birth == null || vehicle_1 == null|| dArea == null) 
				throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Uncomplete field");
			alert.setHeaderText("You didn't finish complete all the fields\nPlease try again :)");
			alert.show();
			return;
		}

		

		int age = Period.between(birth, LocalDate.now()).getYears();
		if (age < 16) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Illegal age");
			alert.setHeaderText("The DeliveryPerson is too young!");
			alert.show();
			return;
		}
		// End of old code
		
		tempDP.setFirstName(first);
		tempDP.setLastName(last);
		tempDP.setBirthDay(birth);
		tempDP.setGender(gender);
		tempDP.setVehicle(vehicle_1);
		tempDP.setArea(dArea);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Edit successfully");
		alert.setHeaderText("The details of the DeliveryPerson were updated");
		if (alert.showAndWait().get() == ButtonType.OK)
			refresh(event);

	}
    
	// Remove DeliveryPerson
		
	public void showRemoveDeliveryPerson(ActionEvent event) {
			idForEditDP.setVisible(true);
			searchIdRemoveDP.setVisible(true);
			
			firstName.setVisible(false);
			lastName.setVisible(false);
			birthday.setVisible(false);
			male.setVisible(false);
			female.setVisible(false);
			unknown.setVisible(false);
			vehicle.setVisible(false);
			deliveryArea.setVisible(false);
			submitAdd.setVisible(false);
			
			searchId.setVisible(false);
			submitEdit.setVisible(false);
		}
		
	public void removeDeliveryPerson(ActionEvent event) throws IOException {
		String id = idForEditDP.getText();
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
		tempDP = Main.getRest().getRealDeliveryPerson(idNum);
		
		if (tempDP == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("We couldn't find this delivery person");
			alert.show();
			return;
		}
		
		// not possible remove last DP from area
		if (tempDP.getArea().getDelPersons().size() == 1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Remove DP error");
			alert.setHeaderText("You can't remove the last delivery person");
			alert.show();
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove Cook");
		alert.setHeaderText("Are you sure you want to remove the DeliveryPerson:\n" 
		+ tempDP.getFirstName() + " " + tempDP.getLastName());
		if (alert.showAndWait().get() == ButtonType.OK) {
			Main.getRest().removeDeliveryPerson(tempDP);
			refresh(event);
		}
		
	}
    
     
    
    public void refresh(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/DeliveryPersons.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - DeliveryPersons");
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
