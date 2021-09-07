package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import Exceptions.NameAlreadyExistsExcepction;
import Exceptions.UncompleteField;
import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DeliveryAreaController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	//------------------------


	@FXML 
	private TableView<DeliveryArea> table;
	
	@FXML
	private TableColumn<DeliveryArea, Integer> idCol;
	
	@FXML 
	TableColumn<DeliveryArea, String> areaNameCol;
	
	@FXML
	TableColumn<DeliveryArea, Integer> deliveryTimeCol;
	
	private ObservableList<DeliveryArea> listDA = FXCollections.observableArrayList(Main.getRest().getAreas().values());
	
	
	// Show details
	@FXML
	private JFXTextField nameForDetails;
	
	@FXML
	private JFXButton searchForDetails;
	
	@FXML
	private Label headerForDetails, labelDP, labelDelivery, labelNeigh;
	
	@FXML
	private JFXListView<DeliveryPerson> listDP;
	
	@FXML
	private JFXListView<Delivery> listDelivery;
	
	@FXML
	private JFXListView<Neighberhood> listNeigh;
	
	
	// Add DA
    @FXML
    private JFXTextField areaName;
    
    @FXML
    private JFXTextField deliveryTime;

    @FXML
    private JFXComboBox<Neighberhood> neighberhood;
    private ObservableList<Neighberhood> availableNeighberhoods = FXCollections.observableArrayList(Neighberhood.values());
    
    @FXML
    private JFXButton addNeighNew;
    @FXML
    private JFXButton resetNeighs;


    @FXML
    private JFXListView<Neighberhood> listNeighAdd;

    @FXML
    private JFXButton submitAdd;
    
    
    // Edit DA
    @FXML
    private JFXTextField nameForEdit;
    @FXML private JFXButton searchEdit, submitEdit, transfetTitle;
    
    @FXML 
    private JFXListView<Neighberhood> listNeighEdit;
    
    @FXML
    private JFXComboBox<DeliveryArea> chooseNewArea;
    
    @FXML
    private Label labelMoveNeigh;
    
    @FXML
    private JFXButton moveNeighButton;
    
    // Remove DA
    @FXML 
    private JFXButton searchRemove;
    @FXML
    private Label removeLabel;
    @FXML 
    private JFXComboBox<DeliveryArea> selectAreaForRemove;
    @FXML
    private JFXButton submitTransferRemove;
    
  		
  	@FXML
  	private JFXButton AddDA, EditDA, RemoveDA;
  			
  	@FXML	   
  	private AnchorPane pane;
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		idCol.setCellValueFactory(new PropertyValueFactory<DeliveryArea, Integer>("id"));
		areaNameCol.setCellValueFactory(new PropertyValueFactory<>("areaName"));
		deliveryTimeCol.setCellValueFactory(new PropertyValueFactory<>("deliverTime"));
		
		table.setItems(listDA);
		
		headerForDetails.setVisible(false);
		labelDelivery.setVisible(false);
		labelDP.setVisible(false);
		labelNeigh.setVisible(false);
		listDelivery.setVisible(false);
		listDP.setVisible(false);
		listNeigh.setVisible(false);
		
		// Add DA
		areaName.setVisible(false);
		deliveryTime.setVisible(false);
		
		
		availableNeighberhoods = FXCollections.observableArrayList(Neighberhood.values());
		if (Main.getRest().getNeighsInRest() == null)
			Main.getRest().resetNeighsInRest();
		availableNeighberhoods.removeAll(Main.getRest().getNeighsInRest()); //-- New because area
		neighberhood.getItems().addAll(availableNeighberhoods);
		neighberhood.setVisible(false);
		addNeighNew.setVisible(false);
		resetNeighs.setVisible(false);
		listNeighAdd.setVisible(false);
		
		submitAdd.setVisible(false);
		
		
		// Edit DA
		nameForEdit.setVisible(false);
		searchEdit.setVisible(false);
		submitEdit.setVisible(false);
		
		
		// All newEdit
		transfetTitle.setVisible(false);
		listNeighEdit.setVisible(false);
		listNeighEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				showNewArea(null);
			}
		});
		chooseNewArea.setVisible(false);
		labelMoveNeigh.setVisible(false);
		moveNeighButton.setVisible(false);
		
		// Remove DA
		searchRemove.setVisible(false);
		removeLabel.setVisible(false);
		selectAreaForRemove.setVisible(false);
		submitTransferRemove.setVisible(false);
		
		//Design
		AddDA.setVisible(false);
		EditDA.setVisible(false);
		RemoveDA.setVisible(false);

		
	}
	public void ShowMenu(ActionEvent event) {
    	pane.setTranslateX(-479);
    	
    	TranslateTransition slide = new TranslateTransition();
    	slide.setDuration(Duration.seconds(1));
    	slide.setNode(pane);

    	slide.setToX(0);
    	slide.play();
    	
		AddDA.setVisible(true);
		EditDA.setVisible(true);
		RemoveDA.setVisible(true);
    	
    }
	
	// Show details
	private DeliveryArea tempDA;
	public void showDetails(ActionEvent event) {
		
		String nameDA = nameForDetails.getText();
		
		tempDA = Main.getRest().getRealDeliveryArea(nameDA);
		
		if (tempDA == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("We couldn't find this delivery area");
			alert.show();
			return;
		}
		
		
		// show Hidden components
		headerForDetails.setVisible(true);
		headerForDetails.setText("Details of " + tempDA.getAreaName());
		
		labelDP.setVisible(true);
		listDP.getItems().clear();
		listDP.getItems().addAll(FXCollections.observableArrayList(tempDA.getDelPersons()));
		listDP.setVisible(true);
		
		labelDelivery.setVisible(true);
		listDelivery.getItems().clear();
		listDelivery.getItems().addAll(FXCollections.observableArrayList(tempDA.getDelivers()));
		listDelivery.setVisible(true);
		
		labelNeigh.setVisible(true);
		listNeigh.getItems().clear();
		listNeigh.getItems().addAll(FXCollections.observableArrayList(tempDA.getNeighberhoods()));
		listNeigh.setVisible(true);
		
	}
	
	
    // Add DA
    public void showAddDA(ActionEvent event) {
		// Add DA
    	areaName.clear();
		areaName.setVisible(true);
		deliveryTime.clear();
		deliveryTime.setVisible(true);
				
		neighberhood.setVisible(true);
		addNeighNew.setVisible(true);
		resetNeighs.setVisible(true);
		listNeighAdd.getItems().clear();
		listNeighAdd.setVisible(true);
		
		submitAdd.setVisible(true);
		
		// EditDA
		nameForEdit.setVisible(false);
		searchEdit.setVisible(false);
		submitEdit.setVisible(false);
		
		transfetTitle.setVisible(false);
		listNeighEdit.setVisible(false);
		chooseNewArea.setVisible(false);
		labelMoveNeigh.setVisible(false);
		moveNeighButton.setVisible(false);
		
		// RemoveDA
		searchRemove.setVisible(false);
		removeLabel.setVisible(false);
		selectAreaForRemove.setVisible(false);
		submitTransferRemove.setVisible(false);

    }
    
    private HashSet<Neighberhood> neighsInNewDA; // External
    public void submitAddDA(ActionEvent event) throws IOException {
    	String name, deliverTimeStr;
    	int delierTimeNum;
    	
    	name = areaName.getText();
    	deliverTimeStr = deliveryTime.getText();
    	
		try {
			if (name == null || name.equals(""))
				throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("You didn't finish complete all the fields\nPlease try again :)");
			alert.show();
			return;
		}
		
		// check if name exist
		try {
		if (Main.getRest().getAreas().containsKey(name))
			throw new NameAlreadyExistsExcepction();
		} catch (NameAlreadyExistsExcepction e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Delivery area error");
			alert.setHeaderText(e.getMessage());
			alert.show();
			return;
		}

		
		try { // get delivery time
			delierTimeNum = Integer.parseInt(deliverTimeStr);
			if (delierTimeNum <= 0) //-- Maybe change exception
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Illegal input");
			alert.setHeaderText("The delivery time must be positive number");
			alert.show();
			return;
		}
		
		// Check that exists at least one component
		try {
			if (neighsInNewDA == null || neighsInNewDA.isEmpty()) 
				throw new UncompleteField();
		} catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("You must choose at least one neighberhood");
			alert.show();
			return;
		}
		
		
		Main.getRest().addDeliveryArea(new DeliveryArea(name, neighsInNewDA, delierTimeNum));
		
		availableNeighberhoods.clear();
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("New area was added");
		alert.setHeaderText("The area: " + name + " was added successfully");
		if (alert.showAndWait().get() == ButtonType.OK) {
			refresh(event);
		}

    }
    
    public void addNeighForNewDA(ActionEvent event) {
    	Neighberhood tempNeigh = neighberhood.getValue();
    	
    	try {
    		if (tempNeigh == null)
    			throw new UncompleteField();
    	} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("You must choose at least one neighberhood");
			alert.show();
			return;
    	}
    	
    	if (neighsInNewDA == null)
    		neighsInNewDA = new HashSet<>();
    	
    	neighsInNewDA.add(tempNeigh);
    	listNeighAdd.getItems().clear();
    	listNeighAdd.getItems().addAll(neighsInNewDA);
    	listNeighAdd.refresh();
    }
    public void resetNewNeighs(ActionEvent event) {
    	if (neighsInNewDA != null) {
    		availableNeighberhoods.addAll(neighsInNewDA);
    		neighberhood.getItems().clear();
    		neighberhood.getItems().addAll(availableNeighberhoods);
    		
    		neighsInNewDA.clear();
    		listNeighAdd.getItems().clear();
    		listNeighAdd.refresh();
    	}
    	listNeighAdd.refresh();
    }
    
    
    // Edit DA
    public void showEditDA(ActionEvent event) {
    	
		// Edit DA
		nameForEdit.setVisible(true);
		searchEdit.setVisible(true);

		transfetTitle.setVisible(false);
		listNeighEdit.setVisible(false);
		labelMoveNeigh.setVisible(false);
		chooseNewArea.setVisible(false);
		moveNeighButton.setVisible(false);

    	
		// Add DA
		areaName.setVisible(false);
		deliveryTime.setVisible(false);
				
		neighberhood.setVisible(false);
		addNeighNew.setVisible(false);
		resetNeighs.setVisible(false);
		listNeighAdd.setVisible(false);
		
		submitAdd.setVisible(false);
		
		// RemoveDA
		searchRemove.setVisible(false);
		removeLabel.setVisible(false);
		selectAreaForRemove.setVisible(false);
		submitTransferRemove.setVisible(false);



    }
    
    private DeliveryArea tempDA_Edit;
    public void searchForEdit(ActionEvent event) {
    	String daName = nameForEdit.getText();
    	
    	tempDA_Edit = Main.getRest().getRealDeliveryArea(daName);
    	
		if (tempDA_Edit == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Delivery area error");
			alert.setHeaderText("We couldn't find this delivery area");
			alert.show();
			return;
		}
		
		areaName.setVisible(true);
		areaName.setText(tempDA_Edit.getAreaName());
		areaName.setTooltip(new Tooltip("Area name"));
		
		deliveryTime.setVisible(true);
		deliveryTime.setText(Integer.toString(tempDA_Edit.getDeliverTime()));
		deliveryTime.setTooltip(new Tooltip("Delivery time"));
				
		//-- New Edit
		transfetTitle.setVisible(true);
		labelMoveNeigh.setText("If you want move neighberhood to different area, Press the neighberhood");
		labelMoveNeigh.setVisible(true);

		listNeighEdit.getItems().clear();
		listNeighEdit.getItems().addAll(tempDA_Edit.getNeighberhoods());
		listNeighEdit.setVisible(true);
		
		
		submitEdit.setVisible(true);

    }
    
    public void showNewArea(ActionEvent event) {
		try {
			if (listNeighEdit.getSelectionModel().getSelectedItem() == null)
				throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("Please choose neighberhood");
			alert.show();
			return;
		}
		
		// Last neighberhood in area 
    	if (listNeighEdit.getItems().size() == 1) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Edit area error");
    		alert.setHeaderText("In " + tempDA_Edit.getAreaName() 
    				+ " There is just one neighberhood " + listNeighEdit.getItems().get(0)
    				+"\nYou can't transfer this neighberhood");
    		alert.show();
    		return;
    	}

    	chooseNewArea.getItems().clear();
    	chooseNewArea.getItems().addAll(Main.getRest().getAreas().values());
    	chooseNewArea.getItems().remove(tempDA_Edit);
    	chooseNewArea.setVisible(true);
    	
    	moveNeighButton.setVisible(true);
    }
    
    public void moveNeighberhood(ActionEvent event) throws IOException {
    	DeliveryArea newArea = chooseNewArea.getValue();
    	Neighberhood moveNeigh = listNeighEdit.getSelectionModel().getSelectedItem();
    	
		try {
			if (newArea == null)
				throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("Please choose new area");
			alert.show();
			return;
		}
		
		tempDA_Edit.removeNeighberhood(moveNeigh);
		newArea.addNeighberhood(moveNeigh);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Transfer was successful");
		alert.setHeaderText("The neighberhood '" + moveNeigh + "' now in " + newArea.getAreaName());
		alert.show();
		
		refresh(event);
    }
    
    
    
    public void submitEditDA(ActionEvent event) throws IOException {
    	//-- Same code from up
    	String name; 
    	
    	int delierTimeNum;
    	
    	name = areaName.getText();
    	String deliverTimeStr = deliveryTime.getText();

    	
		try {
			if (name == null || name.equals(""))
				throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("You didn't finish complete all the fields\nPlease try again :)");
			alert.show();
			return;
		}
				
		// check if name exist
		try {
		if (Main.getRest().getAreas().containsKey(name) && !name.equals(tempDA_Edit.getAreaName()))
			throw new NameAlreadyExistsExcepction();
		} catch (NameAlreadyExistsExcepction e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Delivery area error");
			alert.setHeaderText(e.getMessage());
			alert.show();
			return;
		}


		try { // get delivery time
			delierTimeNum = Integer.parseInt(deliverTimeStr);
			if (delierTimeNum <= 0) //-- Maybe change exception
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Illegal input");
			alert.setHeaderText("The delivery time must be positive number");
			alert.show();
			return;
		}

				
		// Check that exists at least one component
		try {
			if (neighsInNewDA == null || neighsInNewDA.isEmpty()) 
				throw new UncompleteField();
		} catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("You must choose at least one neighberhood");
			alert.show();
			return;
		}
		//-- End of old code
		
		// Change key in rest map
		if (!name.equals(tempDA_Edit.getAreaName())) {
			Main.getRest().removeDeliveryArea(tempDA_Edit, tempDA_Edit);
			tempDA_Edit.setAreaName(name);
			Main.getRest().addDeliveryArea(tempDA_Edit);
		}
		// Neighberhoods are pointers
		tempDA_Edit.setDelivertTime(delierTimeNum);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Edit successfully");
		alert.setHeaderText("The details of the delivery are were updated");
		if (alert.showAndWait().get() == ButtonType.OK);
			refresh(event);
    }
    
    
    // Remove DA
    public void showRemoveDA(ActionEvent event) {
    	nameForEdit.setVisible(true);
    	searchRemove.setVisible(true);
    	
		removeLabel.setVisible(false);
		selectAreaForRemove.setVisible(false);
		submitTransferRemove.setVisible(false);

    			
		// Edit DA
		searchEdit.setVisible(false);
		submitEdit.setVisible(false);	
		// All newEdit
		transfetTitle.setVisible(false);
		listNeighEdit.setVisible(false);
		chooseNewArea.setVisible(false);
		labelMoveNeigh.setVisible(false);
		moveNeighButton.setVisible(false);

    	
		// Add DA
		areaName.setVisible(false);
		deliveryTime.setVisible(false);
				
		neighberhood.setVisible(false);
		addNeighNew.setVisible(false);
		resetNeighs.setVisible(false);
		listNeighAdd.setVisible(false);
		
		submitAdd.setVisible(false);

    }
    
    public void removeDA(ActionEvent event) throws IOException {
    	String daName = nameForEdit.getText();
    	
    	tempDA_Edit = Main.getRest().getRealDeliveryArea(daName);
    	
		if (tempDA_Edit == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Delivery area error");
			alert.setHeaderText("We couldn't find this delivery area");
			alert.show();
			return;
		}
		
		
		if (Main.getRest().getAreas().size() == 1) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Remove delivery area");
			alert.setHeaderText("You can't remove the last area");
			alert.show();
			refresh(event);
			return;
		}
		else {
			removeLabel.setVisible(true);
			removeLabel.setText("Please select delivery area in ordet to \ntransfer the details from " + tempDA_Edit.getAreaName());
			
			selectAreaForRemove.setVisible(true);
			ObservableList<DeliveryArea> listRemoveDA = FXCollections.observableArrayList(Main.getRest().getAreas().values());
			listRemoveDA.remove(tempDA_Edit);
			selectAreaForRemove.getItems().addAll(listRemoveDA);
			
			submitTransferRemove.setVisible(true);
		}

    }
    
    public void transferDetailsForRemove(ActionEvent event) throws IOException {
    	DeliveryArea newArea = selectAreaForRemove.getValue();
    	
    	if (tempDA_Edit == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Delivery area error");
			alert.setHeaderText("You must select area");
			alert.show();
			return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove delivery area");
		alert.setHeaderText("Are you sure you want to remove the delivery area:\n" 
		+ tempDA_Edit.getAreaName());
		if (alert.showAndWait().get() == ButtonType.OK) {
			Main.getRest().removeDeliveryArea(tempDA_Edit, newArea);
			refresh(event);
		}
    }
    
    
    
    
	public void refresh(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/DeliveryAreas.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Delivery areas");
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
