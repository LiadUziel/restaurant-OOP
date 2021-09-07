package controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import Exceptions.NameAlreadyExistsExcepction;
import Exceptions.UncompleteField;
import Model.Component;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ComponentController implements Initializable {
	
	
	private Stage stage;
	private Scene scene;
	private Parent root;

		
	 // table
	
	@FXML
	private TableView<Component> table;

    @FXML
    private TableColumn<Component, Integer> idCol;

    @FXML
    private TableColumn<Component, String> componentNameCol;

    @FXML
    private TableColumn<Component, Boolean> containLactoseCol;

    @FXML
    private TableColumn<Component, Boolean> containGlutenCol;

    @FXML
    private TableColumn<Component, Double> priceCol;   
   // End table
   
   // Add component
   
   @FXML
   private JFXTextField componentName, componentPrice;
   
   @FXML
	private JFXCheckBox containLactose, containGluten;
   @FXML
	private JFXButton submitAdd;

   
   // Edit Component
	@FXML 
	private JFXTextField nameForEdit;
	
	@FXML 
	private JFXButton searchId;
	
	@FXML
	private JFXButton submitEdit;
	
	
	// Remove component
	@FXML
	private JFXButton searchIdRemove;
	
	//Design
	
	@FXML
	private JFXButton RemoveComponent, EditComponent, AddComponent;
	
	@FXML	   
	private AnchorPane pane;
	
   
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {

	idCol.setCellValueFactory(new PropertyValueFactory<Component, Integer>("id"));
	componentNameCol.setCellValueFactory(new PropertyValueFactory<Component, String>("componentName"));
	containLactoseCol.setCellValueFactory(new PropertyValueFactory<Component, Boolean>("hasLactose"));
	containGlutenCol.setCellValueFactory(new PropertyValueFactory<Component, Boolean>("hasGluten"));
	priceCol.setCellValueFactory(new PropertyValueFactory<Component, Double>("price"));
	
	ObservableList<Component> allComps = FXCollections.observableArrayList(Main.getRest().getComponenets().values());
	table.setItems(allComps);

	//add component
	componentName.setVisible(false);
	componentPrice.setVisible(false);
	containLactose.setVisible(false);
	containGluten.setVisible(false);
	submitAdd.setVisible(false);
	
	// Edit customer
	nameForEdit.setVisible(false);
	searchId.setVisible(false);
	submitEdit.setVisible(false);
	
	//table.setVisible(false);
	

	// Remove component
	searchIdRemove.setVisible(false);
	
	//Design
	AddComponent.setVisible(false);
	EditComponent.setVisible(false);
	RemoveComponent.setVisible(false);
	
	}
    
    public void ShowMenu(ActionEvent event) {
    	pane.setTranslateX(-300);
    	
    	TranslateTransition slide = new TranslateTransition();
    	slide.setDuration(Duration.seconds(1));
    	slide.setNode(pane);

    	slide.setToX(0);
    	slide.play();
    	
    	AddComponent.setVisible(true);
    	EditComponent.setVisible(true);
    	RemoveComponent.setVisible(true);
    	
    }

    public void showAddComponent(ActionEvent event) {
 
    	componentName.clear();
    	componentName.setVisible(true);
    	componentPrice.clear();
    	componentPrice.setVisible(true);
    	containLactose.setVisible(true);
    	containGluten.setVisible(true);
    	submitAdd.setVisible(true);
    	
    	// Edit customer
    	nameForEdit.setVisible(false);
    	searchId.setVisible(false);
    	submitEdit.setVisible(false);
    	
    	// Remove component
    	searchIdRemove.setVisible(false);
	}

    public void submitAddComponent(ActionEvent event) throws IOException {
    	String name, price;
    	boolean lactose = false;
		boolean gluten = false;
		
		name = componentName.getText();
		price = componentPrice.getText();
		if(containLactose.isSelected())
			lactose = true;
		if (containGluten.isSelected())
			gluten = true;
		

		// Check for null fields
		try {
			if (name == null || name.equals("") || price == null || price.equals(""))
				throw new UncompleteField();
		} catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Uncomplete field");
			alert.setHeaderText("You didn't finish complete all the fields\nPlease try again :)");
			alert.show();
			return;
		}
		
		// check if name exist
		try {
		if (Main.getRest().getComponenets().containsKey(name))
			throw new NameAlreadyExistsExcepction();
		} catch (NameAlreadyExistsExcepction e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Component error");
			alert.setHeaderText(e.getMessage());
			alert.show();
			return;
		}

		
		// check price isvalid
		Double isValidPrice;
		try {
			isValidPrice = Double.parseDouble(price);
		}catch(NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Price error");
			alert.setHeaderText("Illegal price");
			alert.show();
			return;
    }
		
		Main.getRest().addComponent(new Component(name, lactose, gluten, isValidPrice));
		
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("New component was added");
		alert.setHeaderText("The component: " + name +  " was added succssfully  ");
		if (alert.showAndWait().get() == ButtonType.OK)
			refresh(event);	
    }
    
    public void showEditComponent(ActionEvent event) {

    	nameForEdit.setVisible(true);
		searchId.setVisible(true);
		
		//Add component
		componentName.setVisible(false);
		componentPrice.setVisible(false);
		containLactose.setVisible(false);
		containGluten.setVisible(false);
		submitAdd.setVisible(false);
		
		// Remove component
		searchIdRemove.setVisible(false);
    }
    
    Component tempComp; // External
    
    public void searchForEdit(ActionEvent event) {
    	
    	String compName = nameForEdit.getText();
		
		tempComp = Main.getRest().getRealComponent(compName);
		
		if (tempComp == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Component error");
			alert.setHeaderText("We couldn't find this component");
			alert.show();
			return;
		}
		
		componentName.setVisible(true);
		componentName.setText(tempComp.getComponentName());
		componentName.setTooltip(new Tooltip("Component name"));
		
		containLactose.setVisible(true);
		if (tempComp.isHasLactose())
			containLactose.setSelected(true);
		
		containGluten.setVisible(true);
		if (tempComp.isHasGluten())
			containGluten.setSelected(true);
		
		componentPrice.setVisible(true);
		componentPrice.setText(String.valueOf(tempComp.getPrice()));
		componentPrice.setTooltip(new Tooltip("Component price"));

		
		containLactose.setVisible(true);
		if (tempComp.isHasLactose())
			containLactose.setSelected(true);
		
		containGluten.setVisible(true);
		if (tempComp.isHasGluten())
			containGluten.setSelected(true);
		
		submitEdit.setVisible(true);
		

    }
    
    public void submitEditComponent(ActionEvent event) throws IOException {
    	
    	String name, price;
    	boolean lactose = false;
		boolean gluten = false;
		
		name = componentName.getText();
		price = componentPrice.getText();
		if(containLactose.isSelected())
			lactose = true;
		if (containGluten.isSelected())
			gluten = true;
		

		// Check for null fields
		try {
			if (name == null || name.equals("") || price == null || price.equals(""))
				throw new UncompleteField();
		} catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Uncomplete field");
			alert.setHeaderText("You didn't finish complete all the fields\nPlease try again :)");
			alert.show();
			return;
		}
		
		// check if name exist
		try {
		if (Main.getRest().getComponenets().containsKey(name) && !name.equals(tempComp.getComponentName()))
			throw new NameAlreadyExistsExcepction();
		} catch (NameAlreadyExistsExcepction e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Component error");
			alert.setHeaderText(e.getMessage());
			alert.show();
			return;
		}


		// Change key in rest map
		if (!name.equals(tempComp.getComponentName())) {
			Main.getRest().removeComponent(tempComp);
			tempComp.setComponentName(name);
			Main.getRest().addComponent(tempComp);
		}
	
		tempComp.setComponentName(name);
		tempComp.setHasLactose(lactose);
		tempComp.setHasGluten(gluten);
		tempComp.setPrice(Double.parseDouble(price));
		
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Edit successfully");
		alert.setHeaderText("The details of the component were updated");
		if (alert.showAndWait().get() == ButtonType.OK)
			refresh(event);
    }
    
    public void showRemoveComponent(ActionEvent event) {
    	nameForEdit.setVisible(true);
    	searchIdRemove.setVisible(true);

    	//add component
    	componentName.setVisible(false);
    	componentPrice.setVisible(false);
    	containLactose.setVisible(false);
    	containGluten.setVisible(false);
    	submitAdd.setVisible(false);
    	
    	// Edit customer	
    	searchId.setVisible(false);
    	submitEdit.setVisible(false);
    
	}
    
    public void removeComponent(ActionEvent event) throws IOException {

		String compName = nameForEdit.getText();
		
		// tempCust is external
		tempComp = Main.getRest().getRealComponent(compName);
		
		if (tempComp == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Component error");
			alert.setHeaderText("We couldn't find this component");
			alert.show();
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove component");
		alert.setHeaderText("Are you sure you want to remove the component:\n" 
		+ tempComp.getComponentName());
		if (alert.showAndWait().get() == ButtonType.OK) {
			Main.getRest().removeComponent(tempComp);
			refresh(event);
		}
	
	}
    
    
    
    
	public void refresh(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/components.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - components");
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
