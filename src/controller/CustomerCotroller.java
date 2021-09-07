package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import Exceptions.OrderHistoryException;
import Exceptions.UncompleteField;
import Model.Component;
import Model.Customer;
import Model.Dish;
import Model.Order;
import Utils.Gender;
import Utils.Neighberhood;
import application.Main;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CustomerCotroller implements Initializable{

	private Stage stage;
	private Scene scene;
	private Parent root;

	
    @FXML
    private TableView<Customer> table;

    @FXML
    private TableColumn<Customer, Integer> idCol;

    @FXML
    private TableColumn<Customer, String> firstNameCol;

    @FXML
    private TableColumn<Customer, String> lastNameCol;

    @FXML
    private TableColumn<Customer, LocalDate> birthDateCol;

    @FXML
    private TableColumn<Customer, Gender> genderCol;

    @FXML
    private TableColumn<Customer, Neighberhood> neighCol;

    @FXML
    private TableColumn<Customer, Boolean> lactoseCol;

    @FXML
    private TableColumn<Customer, Boolean> glutenCol;
    
    ObservableList<Customer> list = FXCollections.observableArrayList(Main.getRest().getCustomers().values());
    // End table
	
	
    // Add customer
    @FXML
    private JFXTextField firstName, lastName;
    
    @FXML
    private DatePicker birthday;
    
    @FXML
    private JFXRadioButton male, female, unknown;
    
    @FXML
    private JFXComboBox<Neighberhood> neighberhood;
	
	@FXML
	private JFXCheckBox isLactose, isGluten;
	
	@FXML
	private JFXButton submitAdd;

	
	// Edit Customer
	@FXML 
	private JFXTextField idForEdit;
	
	@FXML 
	private JFXButton searchId;
	
	@FXML
	private JFXButton submitEdit;
	
	// Remove customer
	@FXML
	private JFXButton searchIdRemove;

	
	// Show history
	@FXML
	private JFXComboBox<Customer> chooseCustForHistory;
	@FXML 
	private JFXListView<Order> listOrderHistory;
	
	@FXML
	private Label labelDishesOrder;
	@FXML
	private JFXListView<Dish> listDishesOrder;
	
	@FXML
	private Label labelComps;
	@FXML 
	private JFXListView<Component> listComps;
	
	//Design
		
	@FXML
	private JFXButton RemoveCustomer, EditCustomer, AddCustomer;
		
	@FXML	   
	private AnchorPane pane;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
		genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
		neighCol.setCellValueFactory(new PropertyValueFactory<>("neighberhood"));
		lactoseCol.setCellValueFactory(new PropertyValueFactory<>("isSensitiveToLactose"));
		glutenCol.setCellValueFactory(new PropertyValueFactory<>("isSensitiveToGluten"));
		
		table.setItems(list);
		
		// Add customer
		firstName.setVisible(false);
		lastName.setVisible(false);
		birthday.setVisible(false);
		male.setVisible(false);
		female.setVisible(false);
		unknown.setVisible(false);
		
		if (Main.getRest().getNeighsInRest() == null) //-- New because area
			Main.getRest().resetNeighsInRest();
		neighberhood.getItems().addAll(Main.getRest().getNeighsInRest());

		neighberhood.setVisible(false);
		isLactose.setVisible(false);
		isGluten.setVisible(false);
		submitAdd.setVisible(false);
		
		// Edit customer
		idForEdit.setVisible(false);
		searchId.setVisible(false);
		submitEdit.setVisible(false);
		
		// Remove customer
		searchIdRemove.setVisible(false);
		
		// Order history
		chooseCustForHistory.getItems().clear();
		chooseCustForHistory.getItems().addAll(Main.getRest().getCustomers().values());
		
		listOrderHistory.setVisible(false);
		listOrderHistory.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				showOrderDishes(null);
			}
		});
		
		labelDishesOrder.setVisible(false);
		listDishesOrder.setVisible(false);
		listDishesOrder.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				showCompsOfDish(null);
			}
		});
		
		labelComps.setVisible(false);
		listComps.setVisible(false);
		
		//Design
		//menuTittle.setVisible(false);
		AddCustomer.setVisible(false);
		EditCustomer.setVisible(false);
		RemoveCustomer.setVisible(false);
		
		
	}
	
	 public void ShowMenu(ActionEvent event) {
	    	pane.setTranslateX(-300);
	    	
	    	TranslateTransition slide = new TranslateTransition();
	    	slide.setDuration(Duration.seconds(1));
	    	slide.setNode(pane);

	    	slide.setToX(0);
	    	slide.play();
	    	
	    	AddCustomer.setVisible(true);
			EditCustomer.setVisible(true);
			RemoveCustomer.setVisible(true);
	    	
	    }
	
	public void showAddCustomer(ActionEvent event) {
		firstName.clear();
		firstName.setVisible(true);
		lastName.clear();
		lastName.setVisible(true);
		birthday.setValue(null);
		birthday.setVisible(true);
		male.setVisible(true);
		female.setVisible(true);
		unknown.setVisible(true);
		neighberhood.setValue(null);
		neighberhood.setVisible(true);
		isLactose.setVisible(true);
		isGluten.setVisible(true);
		submitAdd.setVisible(true);
		
		// Edit customer
		idForEdit.setVisible(false);
		searchId.setVisible(false);
		submitEdit.setVisible(false);
		
		// Remove customer
		searchIdRemove.setVisible(false);

	}
	
	public void submitAddCustomer(ActionEvent event) throws IOException {
		
		String first, last, userName, password;
		LocalDate birth;
		Gender gender;
		Neighberhood neigh;
		boolean lactose = false;
		boolean gluten = false;
		
		first = firstName.getText();
		last = lastName.getText();
		birth = birthday.getValue();
		
		if(male.isSelected())
			gender = Gender.Male;
		else if(female.isSelected())
			gender = Gender.Female;
		else
			gender = Gender.Unknown;
		
		neigh = neighberhood.getValue();
		
		if(isLactose.isSelected())
			lactose = true;
		if (isGluten.isSelected())
			gluten = true;
		
		// Check for null fields
		try {
			if (first == null || first.equals("") || last == null || last.equals("") || birth == null || neigh == null) 
				throw new UncompleteField();
		} catch(UncompleteField e) {
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
			alert.setHeaderText("The customer is too young!");
			alert.show();
			return;
		}
		
		// Create new user name
		userName = first + last + birth.getDayOfYear();
		if(!RegisterController.isValidUsername(userName)) {
			userName+=(Customer.getIdCounter()+1); // save unique userName
		}
		
		// Create new password
		do {
			password = RegisterController.makeRamdomizePassword();
		}while(RegisterController.isValidPassword(password));
		
		Main.getRest().addCustomer(new Customer(first, last, birth, gender, userName, password, neigh, lactose, gluten));
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("New customer was added");
		alert.setHeaderText("The username is : " + userName +  "\nThe password is: " + password);
		alert.setContentText("Please copy the details");
		if (alert.showAndWait().get() == ButtonType.OK)
			refresh(event);	

		
	}
	
	
	public void showEditCustomer(ActionEvent event) {

		idForEdit.setVisible(true);
		searchId.setVisible(true);

		searchIdRemove.setVisible(false);
		
		firstName.setVisible(false);
		lastName.setVisible(false);
		birthday.setVisible(false);
		male.setVisible(false);
		female.setVisible(false);
		unknown.setVisible(false);
		neighberhood.setVisible(false);
		isLactose.setVisible(false);
		isGluten.setVisible(false);
		submitAdd.setVisible(false);
	}
	
	Customer tempCust; // External
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
		
		tempCust = Main.getRest().getRealCustomer(idNum);
		
		if (tempCust == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("We couldn't find this customer");
			alert.show();
			return;
		}
		
		firstName.setVisible(true);
		firstName.setText(tempCust.getFirstName());
		
		lastName.setVisible(true);
		lastName.setText(tempCust.getLastName());
		
		birthday.setVisible(true);
		birthday.setValue(tempCust.getBirthDay());
		
		male.setVisible(true);
		female.setVisible(true);
		unknown.setVisible(true);
		
		if (tempCust.getGender().equals(Gender.Male))
			male.setSelected(true);
		else if (tempCust.getGender().equals(Gender.Female))
			female.setSelected(true);
		
		neighberhood.setVisible(true);
		neighberhood.setValue(tempCust.getNeighberhood());
		
		isLactose.setVisible(true);
		if (tempCust.getIsSensitiveToLactose())
			isLactose.setSelected(true);
		
		isGluten.setVisible(true);
		if (tempCust.getIsSensitiveToGluten())
			isGluten.setSelected(true);
		
		submitEdit.setVisible(true);
		
	}
	
	public void submitEditCustomer(ActionEvent event) throws IOException {
		// Same code from up
		String first, last;
		LocalDate birth;
		Gender gender;
		Neighberhood neigh;
		boolean lactose = false;
		boolean gluten = false;
		
		first = firstName.getText();
		last = lastName.getText();
		birth = birthday.getValue();
		
		if(male.isSelected())
			gender = Gender.Male;
		else if(female.isSelected())
			gender = Gender.Female;
		else
			gender = Gender.Unknown;
		
		neigh = neighberhood.getValue();
		
		if(isLactose.isSelected())
			lactose = true;
		if (isGluten.isSelected())
			gluten = true;
		
		// Check for null fields
		try {
			if (first == null || first.equals("") || last == null || last.equals("") || birth == null || neigh == null) 
				throw new UncompleteField();
		} catch(UncompleteField e) {
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
			alert.setHeaderText("The customer is too young!");
			alert.show();
			return;
		}
		// End of old code
		
		tempCust.setFirstName(first);
		tempCust.setLastName(last);
		tempCust.setBirthDay(birth);
		tempCust.setGender(gender);
		tempCust.setNeighberhood(neigh);
		tempCust.setSensitiveToLactose(lactose);
		tempCust.setSensitiveToGluten(gluten);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Edit successfully");
		alert.setHeaderText("The details of the customer were updated");
		if (alert.showAndWait().get() == ButtonType.OK)
			refresh(event);
		
	}
	
	
	public void showRemoveCustomer(ActionEvent event) {
		idForEdit.setVisible(true);
		searchIdRemove.setVisible(true);
		
		firstName.setVisible(false);
		lastName.setVisible(false);
		birthday.setVisible(false);
		male.setVisible(false);
		female.setVisible(false);
		unknown.setVisible(false);
		neighberhood.setVisible(false);
		isLactose.setVisible(false);
		isGluten.setVisible(false);
		submitAdd.setVisible(false);
		
		searchId.setVisible(false);
		submitEdit.setVisible(false);

	}
	
	public void removeCustomer(ActionEvent event) throws IOException {

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
		
		// tempCust is external
		tempCust = Main.getRest().getRealCustomer(idNum);
		
		if (tempCust == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("We couldn't find this customer");
			alert.show();
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove customer");
		alert.setHeaderText("Are you sure you want to remove the customer:\n" 
		+ tempCust.getFirstName() + " " + tempCust.getLastName());
		if (alert.showAndWait().get() == ButtonType.OK) {
			Main.getRest().removeCustomer(tempCust);
			refresh(event);
		}
	
	}
	
	
	// Show history
	public void showOrderHistory(ActionEvent event) {
		listOrderHistory.getItems().clear();
		
		try {
			if (chooseCustForHistory.getValue().getOrdersHistory() == null || chooseCustForHistory.getValue().getOrdersHistory().isEmpty())
				throw new OrderHistoryException();
		}catch(OrderHistoryException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Order History Exception");
			alert.setHeaderText(e.getMessage());
			alert.show();
			return;
		}
		
		listOrderHistory.getItems().addAll(chooseCustForHistory.getValue().getOrdersHistory());
		listOrderHistory.setVisible(true);
	}
	public void showOrderDishes(ActionEvent event) {
		Order selectionOrder = listOrderHistory.getSelectionModel().getSelectedItem();
		labelDishesOrder.setText("The dishes of order " + selectionOrder.getId() + ":");
		labelDishesOrder.setVisible(true);
		
		listDishesOrder.getItems().clear();
		listDishesOrder.getItems().addAll(selectionOrder.getDishes());
		listDishesOrder.setVisible(true);
	}
	public void showCompsOfDish(ActionEvent event) {
		Dish selectionDish = listDishesOrder.getSelectionModel().getSelectedItem();
		labelComps.setText("The component of selection '" + selectionDish.getDishName() + "':");
		labelComps.setVisible(true);
		
		listComps.getItems().clear();
		listComps.getItems().addAll(selectionDish.getComponenets());
		listComps.setVisible(true);
	}
	
	
	
	public void refresh(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/Customers.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Customers");
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
