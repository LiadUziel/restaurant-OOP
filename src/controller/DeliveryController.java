package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.TreeSet;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import Exceptions.ConvertToExpressException;
import Exceptions.UncompleteField;
import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.ExpressDelivery;
import Model.Order;
import Model.RegularDelivery;
import Utils.DeliveryType;
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
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DeliveryController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	//------------------------

    @FXML
    private TableView<Delivery> table;

    @FXML
    private TableColumn<Delivery, Integer> idCol;

    @FXML
    private TableColumn<Delivery, DeliveryPerson> dpCol;

    @FXML
    private TableColumn<Delivery, DeliveryArea> daCol;

    @FXML
    private TableColumn<Delivery, Boolean> isDeliverCol;

    @FXML
    private TableColumn<Delivery, LocalDate> dateCol;

    @FXML
    private TableColumn<Delivery, DeliveryType> typeCol;
    
    
    private ObservableList<Delivery> listDelivery = FXCollections.observableArrayList(Main.getRest().getDeliveries().values());
    
    
    // Show details
    @FXML
    private JFXTextField idForDetails;    

    @FXML
    private JFXButton searchForDetails;
    
    
    @FXML
    private Label labelPostageExpress, labelOrderExpress, labelsOrdersRegular;
    
    @FXML
    private JFXListView<Order> listOrdersRegular;
    
    
    // Remove Delivery
    @FXML
    private Label labelRemoveEdit;
    
    @FXML
    private JFXComboBox<Delivery> chooseDelivery;
    
    @FXML
    private JFXButton confirmRemove;
    
    // Edit Delivery
    @FXML
    private JFXButton searchEdit, removeOrderInEdit, submitEditButton;
    
    @FXML
    private JFXComboBox<DeliveryPerson> changeDP;
    
    @FXML
    private Label labelOrdersEdit, labelChangeDP;
    
    @FXML 
    private JFXListView<Order> listOrdersEdit;
    
  //Design
  		
  	@FXML
  	private JFXButton RemoveDelivery, EditDelivery,showDetails;
  			
  	@FXML	   
  	private AnchorPane pane;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		dpCol.setCellValueFactory(new PropertyValueFactory<>("deliveryPerson"));
		daCol.setCellValueFactory(new PropertyValueFactory<>("area"));
		isDeliverCol.setCellValueFactory(new PropertyValueFactory<>("isDelivered"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("deliveredDate"));
		typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
		
		table.setItems(listDelivery);
		
		// Show details
		idForDetails.setVisible(false);
		searchForDetails.setVisible(false);
		
		labelPostageExpress.setVisible(false);
		labelOrderExpress.setVisible(false);
		
		labelsOrdersRegular.setVisible(false);
		listOrdersRegular.setVisible(false);
		
		// Remove
		labelRemoveEdit.setVisible(false);
		chooseDelivery.setVisible(false);
		confirmRemove.setVisible(false);
		
	    // Edit Delivery
		searchEdit.setVisible(false);
		changeDP.setVisible(false);
		labelOrdersEdit.setVisible(false);
		labelChangeDP.setVisible(false);
		listOrdersEdit.setVisible(false);
		removeOrderInEdit.setVisible(false);
		submitEditButton.setVisible(false);
		
		//Design
		EditDelivery.setVisible(false);
		RemoveDelivery.setVisible(false);
		showDetails.setVisible(false);
		
	}
	
	public void ShowMenu(ActionEvent event) {
    	pane.setTranslateX(-382);
    	
    	TranslateTransition slide = new TranslateTransition();
    	slide.setDuration(Duration.seconds(1));
    	slide.setNode(pane);

    	slide.setToX(10);
    	slide.play();
    	
    	EditDelivery.setVisible(true);
		RemoveDelivery.setVisible(true);
		showDetails.setVisible(true);
    	
    }
	
	// Show Details
	public void showDetails(ActionEvent event) {
		idForDetails.setVisible(true);
		searchForDetails.setVisible(true);
		
		// Remove
		labelRemoveEdit.setVisible(false);
		chooseDelivery.setVisible(false);
		confirmRemove.setVisible(false);
		
	    // Edit Delivery
		searchEdit.setVisible(false);
		changeDP.setVisible(false);
		labelOrdersEdit.setVisible(false);
		labelChangeDP.setVisible(false);
		listOrdersEdit.setVisible(false);
		removeOrderInEdit.setVisible(false);
		submitEditButton.setVisible(false);
	}

	Delivery tempDelivery;
    public void findDetails(ActionEvent event) {
		labelsOrdersRegular.setVisible(false);
		listOrdersRegular.setVisible(false);
		labelPostageExpress.setVisible(false);
		labelOrderExpress.setVisible(false);
		
		String id = idForDetails.getText();
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
		
		tempDelivery = Main.getRest().getRealDelivery(idNum);
		
		if (tempDelivery == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("We couldn't find this delivery");
			alert.show();
			return;
		}
		
		if (tempDelivery instanceof ExpressDelivery) {			
			labelPostageExpress.setVisible(true);
			labelPostageExpress.setText("The postage is: " + ((ExpressDelivery) tempDelivery).getPostage());
			
			labelOrderExpress.setVisible(true);
			labelOrderExpress.setText("The order is:\n" + ((ExpressDelivery) tempDelivery).getOrder());
		}
		else {
			labelsOrdersRegular.setVisible(true);
			labelsOrdersRegular.setText("The orders are:");
			
			listOrdersRegular.getItems().clear();
			listOrdersRegular.getItems().addAll(((RegularDelivery) tempDelivery).getOrders());
			listOrdersRegular.setVisible(true);
		}

    }
    
    // Remove Delivery
    public void showRemoveDelivery(ActionEvent event) {
    	labelRemoveEdit.setVisible(true);
    	    	
    	chooseDelivery.setVisible(true);
    	HashSet<Delivery> notDelivered = new HashSet<>();
    	for (Delivery tempDel : Main.getRest().getDeliveries().values())
    		if (!tempDel.isDelivered())
    			notDelivered.add(tempDel);
    	chooseDelivery.getItems().clear();
    	chooseDelivery.getItems().addAll(notDelivered);
    	
    	confirmRemove.setVisible(true);
    	
		// Show details
		idForDetails.setVisible(false);
		searchForDetails.setVisible(false);
		
		labelPostageExpress.setVisible(false);
		labelOrderExpress.setVisible(false);
		
		labelsOrdersRegular.setVisible(false);
		listOrdersRegular.setVisible(false);
		
	    // Edit Delivery
		searchEdit.setVisible(false);
		changeDP.setVisible(false);
		labelOrdersEdit.setVisible(false);
		labelChangeDP.setVisible(false);
		listOrdersEdit.setVisible(false);
		removeOrderInEdit.setVisible(false);
		submitEditButton.setVisible(false);


    }
    
    Delivery tempDeliveryRemove;
    public void removeDelivery(ActionEvent event) throws IOException {
    	tempDeliveryRemove = chooseDelivery.getValue();
    	
    	try {
    		if (tempDeliveryRemove == null)
    			throw new UncompleteField();
    	} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Uncomplete field");
			alert.setHeaderText("Please choose delivery");
			alert.show();
			return;
    	}
    	

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove delivery");
		alert.setHeaderText("Are you sure you want to remove this delivery?");
		if (alert.showAndWait().get() == ButtonType.OK) {
	    	Main.getRest().removeDelivery(tempDeliveryRemove);
			refresh(event);
		}

    }
    
    // Edit delivery
    public void showEditDelivery(ActionEvent event) {
		// Edit:
    	labelRemoveEdit.setVisible(true);
    	
    	chooseDelivery.setVisible(true);
    	HashSet<Delivery> notDelivered = new HashSet<>();
    	for (Delivery tempDel : Main.getRest().getDeliveries().values())
    		if (!tempDel.isDelivered())
    			notDelivered.add(tempDel);
    	chooseDelivery.getItems().clear();
    	chooseDelivery.getItems().addAll(notDelivered);
    	
    	searchEdit.setVisible(true);

    	
		// Show details
		idForDetails.setVisible(false);
		searchForDetails.setVisible(false);
		
		labelPostageExpress.setVisible(false);
		labelOrderExpress.setVisible(false);
		
		labelsOrdersRegular.setVisible(false);
		listOrdersRegular.setVisible(false);
		
		// Remove
		labelRemoveEdit.setVisible(false);
		confirmRemove.setVisible(false);
    }
    
    Delivery deliveryEdit;
    public void expandEditDelivery(ActionEvent event) {
    	deliveryEdit = chooseDelivery.getValue();
    	
    	try {
    		if (deliveryEdit == null)
    			throw new UncompleteField();
    	} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("Please choose delivery");
			alert.show();
			return;
    	}

    	labelChangeDP.setText("Change Delivery person:");
    	labelChangeDP.setVisible(true);
    	
    	changeDP.getItems().addAll(deliveryEdit.getArea().getDelPersons());
    	changeDP.setValue(deliveryEdit.getDeliveryPerson());
    	changeDP.setVisible(true);
    	
    	labelOrdersEdit.setVisible(true);
		listOrdersEdit.setVisible(true);

    	if (deliveryEdit instanceof RegularDelivery) {
    		labelOrdersEdit.setText("Orders (Choose the order that you want remove and press the button)");
    		listOrdersEdit.getItems().clear();
    		listOrdersEdit.getItems().addAll(((RegularDelivery) deliveryEdit).getOrders());
    	}
    	else {
    		labelOrdersEdit.setText("Order (if you want remove this order all the delivery will be deleted)");
    		listOrdersEdit.getItems().clear();
    		listOrdersEdit.getItems().add(((ExpressDelivery) deliveryEdit).getOrder());
    	}
    	
    	removeOrderInEdit.setVisible(true);
    	submitEditButton.setVisible(true);
    		
    }
    
    public void removeThisOrder(ActionEvent event) throws IOException {
    	Order thisOrderRemove = listOrdersEdit.getSelectionModel().getSelectedItem();
    	try {
    		if (thisOrderRemove == null)
    			throw new UncompleteField();
    	} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("Please choose delivery");
			alert.show();
			return;
    	}
    	
    	Main.getRest().removeOrder(thisOrderRemove);
    	
    	if (listOrdersEdit.getItems().size() == 1) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Last order");
			alert.setHeaderText("This is the last order\nIf you remove this all the delivery will be deleted\nAre you sure?");
			if (alert.showAndWait().get() == ButtonType.OK) {
				Main.getRest().removeDelivery(deliveryEdit);
				refresh(event);
			}
			else
				return;
    	}
    	else { // orders >= 2
    		listOrdersEdit.getItems().remove(thisOrderRemove);
    		TreeSet<Order> newOrders = new TreeSet<>();
    		newOrders.addAll(listOrdersEdit.getItems());
    		((RegularDelivery) deliveryEdit).setOrders(newOrders);
    		listOrdersEdit.refresh();
    	}
    	

    }
    
    public void submitEdit(ActionEvent event) throws IOException {
    	DeliveryPerson dpAfterChange = changeDP.getValue();
    	try {
    		if (dpAfterChange == null)
    			throw new UncompleteField();
    	} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("You didn't change delivery person");
			alert.show();
			return;
    	}
    	
    	deliveryEdit.setDeliveryPerson(dpAfterChange);
    	
    	boolean necessaryCasting = false;
    	// Casting from RegularDelivery to ExpressDelivery
    	try {
    		if (deliveryEdit instanceof RegularDelivery && listOrdersEdit.getItems().size() == 1) {
    			necessaryCasting = true;
    			throw new ConvertToExpressException();
    		}
    	} catch(ConvertToExpressException e) {
    		Main.getRest().removeDelivery(deliveryEdit);
    		ExpressDelivery newExpress = new ExpressDelivery(dpAfterChange, deliveryEdit.getArea(), false, listOrdersEdit.getItems().get(0), 0, LocalDate.now());
    		Main.getRest().addDelivery(newExpress);
    		
    		if (Main.getRest().getNotDeliveredYet() == null)
    			Main.getRest().resetNotDeliveredYet();
    		Main.getRest().getNotDeliveredYet().add(newExpress);
    	}
    	
    	if (!necessaryCasting) {// Edit RegularDelivery without casting to ExpressDelivery
	    	TreeSet<Order> newOrders = new TreeSet<>();
	    	newOrders.addAll(listOrdersEdit.getItems());
			((RegularDelivery) deliveryEdit).setOrders(newOrders);
    	}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Edit successfully");
		if (!necessaryCasting)
			alert.setHeaderText("The delivery was updated");
		else
			alert.setHeaderText("The delivery was updated\nThe id was changed from " + deliveryEdit.getId() + " to " + (Delivery.getIdCounter()-1));
		if (alert.showAndWait().get() == ButtonType.OK)
			refresh(event);


    }

	
	public void refresh(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/Deliveries.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Deliveries");
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
