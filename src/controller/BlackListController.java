package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import Exceptions.UncompleteField;
import Model.Customer;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BlackListController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	//------------------------

    @FXML
    private TableView<Customer> table;

    @FXML
    private TableColumn<Customer, Integer> idCol;

    @FXML
    private TableColumn<Customer, String> firstNameCol;

    @FXML
    private TableColumn<Customer, String> lastNameCol;

    @FXML
    private TableColumn<Customer, String> reasonCol;
    
    
    // Add to BlackList
    
    @FXML
    private JFXComboBox<Customer> chooseCust;

    private Collection<Customer> relevantCusts;
    
    @FXML
    private JFXTextArea reason;
    
    @FXML
    private JFXButton submitAdd;
    
    
    // Edit reason for black list
    @FXML
    private JFXButton searchCustEdit, submitEdit;
    
    // Remove from blackList
    @FXML
    private JFXButton searchCustRemove;

    @FXML
  	private JFXButton AddBL, EditBL, RemoveBL;
  			
  	@FXML	   
  	private AnchorPane pane;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		reasonCol.setCellValueFactory(new PropertyValueFactory<>("reasonBlackList"));
		
		ObservableList<Customer> custBlackList = FXCollections.observableArrayList(Main.getRest().getBlackList());
		
		table.setItems(custBlackList);
		
		// Add BL
		chooseCust.setVisible(false);
		reason.setVisible(false);
		submitAdd.setVisible(false);
		
		// Edit BL
		searchCustEdit.setVisible(false);
		submitEdit.setVisible(false);
		
		// remove BL
		searchCustRemove.setVisible(false);
		
		//Design
		AddBL.setVisible(false);
		EditBL.setVisible(false);
		RemoveBL.setVisible(false);	
	}
	
	// Add to BlackList
	public void showAddBL(ActionEvent event) {
		chooseCust.getItems().clear();
		relevantCusts = FXCollections.observableArrayList(Main.getRest().getCustomers().values());
		relevantCusts.removeAll(Main.getRest().getBlackList());
		chooseCust.getItems().addAll(relevantCusts);
		
		chooseCust.setVisible(true);
		reason.clear();
		reason.setVisible(true);
		submitAdd.setVisible(true);
		
		searchCustEdit.setVisible(false);
		submitEdit.setVisible(false);

		// remove BL
		searchCustRemove.setVisible(false);	
	}
	
	 public void ShowMenu(ActionEvent event) {
    	pane.setTranslateX(-500);
    	
    	TranslateTransition slide = new TranslateTransition();
    	slide.setDuration(Duration.seconds(1));
    	slide.setNode(pane);

    	slide.setToX(0);
    	slide.play();

		AddBL.setVisible(true);
		EditBL.setVisible(true);
		RemoveBL.setVisible(true);
	    	
	    }
	
	private Customer tempCust; // External
	public void submitAddBL(ActionEvent event) throws IOException {

		tempCust = chooseCust.getValue();
		
		try {
			if (tempCust == null)
				throw new UncompleteField();
		} catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("Please choose customer");
			alert.show();
			return;
		}
		
		String reas = reason.getText();
		
		try {
			if (reas == null || reas.equals(""))
				throw new UncompleteField();
		} catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("Please enter a reason");
			alert.show();
			return;
		}
		
		tempCust.setReasonBlackList(reas);
		
		Main.getRest().addCustomerToBlackList(tempCust);
			
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("New customer was added to BlackList");
		alert.setHeaderText("The customer: " + tempCust.getFirstName() + " " + tempCust.getLastName() + " was added successfully to BlackList");
		if (alert.showAndWait().get() == ButtonType.OK) {
			refresh(event);
		}
		
	}
	
	
	// Edit reason for customer in black list
	public void showEditBL(ActionEvent event) {
		chooseCust.getItems().clear();
		chooseCust.getItems().addAll(Main.getRest().getBlackList());
		chooseCust.setVisible(true);
		
		searchCustEdit.setVisible(true);
		
		reason.setVisible(false);
		submitAdd.setVisible(false);
		
		// remove BL
		searchCustRemove.setVisible(false);
	}
	public void searchCustForEdit(ActionEvent event) {
		tempCust = chooseCust.getValue();
		
		try {
			if (tempCust == null)
				throw new UncompleteField();
		} catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("Please choose customer");
			alert.show();
			return;
		}
		
		reason.setVisible(true);
		reason.setText(tempCust.getReasonBlackList());
		
		submitEdit.setVisible(true);
	}
	
	public void submitEditBL(ActionEvent event) throws IOException {
		String reas = reason.getText();
		
		try {
			if (reas == null || reas.equals(""))
				throw new UncompleteField();
		} catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("Please enter a reason");
			alert.show();
			return;
		}
		
		tempCust.setReasonBlackList(reas);
		
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Edit successfully");
		alert.setHeaderText("The reason was updated");
		if (alert.showAndWait().get() == ButtonType.OK)
			refresh(event);


	}
	
	
	// Remove BlackList
	public void showRemoveBL(ActionEvent event) {
		chooseCust.getItems().clear();
		chooseCust.getItems().addAll(Main.getRest().getBlackList());
		chooseCust.setVisible(true);

		searchCustRemove.setVisible(true);
		
		// Add BL
		reason.setVisible(false);
		submitAdd.setVisible(false);
		
		// Edit BL
		searchCustEdit.setVisible(false);
		submitEdit.setVisible(false);
	}
	
	public void removeCust(ActionEvent event) throws IOException {
		tempCust = chooseCust.getValue();
		
		try {
			if (tempCust == null)
				throw new UncompleteField();
		} catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("Please choose customer");
			alert.show();
			return;
		}
		
		tempCust.setReasonBlackList("");
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove customer from black list");
		alert.setHeaderText("Are you sure you want to remove this customer from black list:\n" 
		+ tempCust.getFirstName() + " " + tempCust.getLastName());
		if (alert.showAndWait().get() == ButtonType.OK) {
			Main.getRest().getBlackList().remove(tempCust);		
			refresh(event);
		}


	}
	
	
	public void refresh(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/BlackList.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - BlackList");
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
