package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import Exceptions.SensitiveException;
import Model.Customer;
import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Dish;
import Model.ExpressDelivery;
import Model.Order;
import application.Main;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;

public class OrderController implements Initializable{

	private Stage stage;
	private Scene scene;
	private Parent root;
	//------------------------

	
	@FXML 
	private TableView<Order> table;
	
	@FXML
	private TableColumn<Order, Integer> idCol;
	
	@FXML 
	TableColumn<Order, Customer> customerCol;
	
	@FXML 
	TableColumn<Order, Delivery> deliveryCol;
	
	private ObservableList<Order> orderList = FXCollections.observableArrayList(Main.getRest().getOrders().values());
	
	// Show details
	@FXML
	private JFXTextField nameForDetails;
	
	@FXML
	private JFXButton searchForDetails;
	
	@FXML
	private Label headerForDetails, labelDishes;
	
	@FXML
	private JFXListView<Dish> listDishes;
	
	//Add order
	
	@FXML
    private JFXComboBox<Customer> customer;
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList(Main.getRest().getCustomers().values());
    
    @FXML
    private JFXComboBox<Dish> dish;
    private ObservableList<Dish> allDishes = FXCollections.observableArrayList(Main.getRest().getDishes().values());
    
   
    @FXML
    private JFXButton addDishNew;
    @FXML
    private JFXButton resetDishes;
	
    @FXML
    private JFXListView<Dish> listDishesAdd;
	
    @FXML
    private JFXButton submitAdd;
    
    // Edit dish
    @FXML
    private JFXTextField idForEdit;
    
    @FXML
    private JFXButton searchIdEdit, submitEdit;
    
    // Remove Order
 	@FXML
 	private JFXButton searchIdRemove;
 	
 	//Design
  		
  	@FXML
  	private JFXButton AddOrder, EditOrder, RemoveOrder;
  			
  	@FXML	   
  	private AnchorPane pane;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));	
		customerCol.setCellValueFactory(new PropertyValueFactory<Order, Customer>("customer"));	
		deliveryCol.setCellValueFactory(new PropertyValueFactory<Order, Delivery>("delivery"));	
		table.setItems(orderList);
		
		//More details
		
		headerForDetails.setVisible(false);
		labelDishes.setVisible(false);
		listDishes.setVisible(false);
		
		
		//Add order
		customer.setVisible(false);
		allCustomers.removeAll(Main.getRest().getBlackList());
		customer.getItems().addAll(allCustomers);
		dish.setVisible(false);
		dish.getItems().addAll(allDishes);
		addDishNew.setVisible(false);
		resetDishes.setVisible(false);
		listDishesAdd.setVisible(false);
		submitAdd.setVisible(false);
		
		//Edit Order
		idForEdit.setVisible(false);
		searchIdEdit.setVisible(false);
		submitEdit.setVisible(false);
		
		//Remove
		searchIdRemove.setVisible(false);
		
		//Design
		//menuTittle.setVisible(false);
		AddOrder.setVisible(false);
		EditOrder.setVisible(false);
		RemoveOrder.setVisible(false);
						
		

	}
	
	 public void ShowMenu(ActionEvent event) {
	    	pane.setTranslateX(-350);
	    	
	    	TranslateTransition slide = new TranslateTransition();
	    	slide.setDuration(Duration.seconds(1));
	    	slide.setNode(pane);

	    	slide.setToX(0);
	    	slide.play();
	    	
			AddOrder.setVisible(true);
			EditOrder.setVisible(true);
			RemoveOrder.setVisible(true);
	    	
	    }
	
	private Order tempOrder;
	
	public void showDetails(ActionEvent event) {
		
		String getID = nameForDetails.getText();
		int orderID;
		
		try {
			orderID = Integer.parseInt(getID);
		} catch(NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("Illegal id");
			alert.show();
			return;
		}
		
		tempOrder = Main.getRest().getRealOrder(orderID);
		
		if (tempOrder == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("We couldn't find this Order");
			alert.show();
			return;
		}
		
		
		headerForDetails.setVisible(true);
		headerForDetails.setText("Details of order " + tempOrder.getId());
		labelDishes.setVisible(true);
		listDishes.getItems().clear();
		listDishes.getItems().addAll(FXCollections.observableArrayList(tempOrder.getDishes()));
		listDishes.setVisible(true);
		
	}
	
	public void showAddOrder(ActionEvent event) {
		
		customer.setValue(null);
		customer.setVisible(true);
		dish.setValue(null);
		dish.setVisible(true);
		addDishNew.setVisible(true);
		resetDishes.setVisible(true);
		listDishesAdd.getItems().clear();
		listDishesAdd.setVisible(true);
		submitAdd.setVisible(true);
		
		
		//Edit
		idForEdit.setVisible(false);
		searchIdEdit.setVisible(false);
		submitEdit.setVisible(false);
		
		//Remove
		searchIdRemove.setVisible(false);

		
	}
	
	ArrayList<Dish> addDishes; // External
	//--
	public void submitAddOrder(ActionEvent event) throws IOException {
		
		Customer c = customer.getValue();
		if (addDishes == null || addDishes.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Uncomplete field");
			alert.setHeaderText("You must choose at least one Dish");
			alert.show();
			return;
		}
		
		boolean isSensitive = false;
		Order newOrder = new Order(c, addDishes, null);
		try {
		Main.getRest().addOrder(newOrder);
		} catch(SensitiveException e) {
			isSensitive = true;
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Sensitive exception");
			alert.setHeaderText("There is some sensitive component in your order.\n"
					+"Are you sure that you want continue the order?");
			if (!(alert.showAndWait().get() == ButtonType.OK))
				return;
		}

		if (isSensitive) {
			if (newOrder.getCustomer().getOrdersHistory() == null)
				newOrder.getCustomer().resetOrderHistory();
			newOrder.getCustomer().getOrdersHistory().add(newOrder); // New For GUI
			
			Main.getRest().getOrders().put(newOrder.getId(), newOrder);
		}
		
		allDishes.clear(); // Prevent duplicates in 'Choose components'
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("New Order was added");
		alert.setHeaderText("The Order was added successfully");
		if (alert.showAndWait().get() == ButtonType.OK) {
			refresh(event);
		}
		
		// AI Machine
		DeliveryArea tempDA = null;
		for (DeliveryArea da : Main.getRest().getAreas().values())
			if (da.getNeighberhoods().contains(customer.getValue().getNeighberhood()))
				tempDA = da;
		
		ArrayList<DeliveryPerson> dPersons = new ArrayList<>();
		dPersons.addAll(tempDA.getDelPersons());

		ExpressDelivery newExpress = new ExpressDelivery(dPersons.get(ShoppingCartController.getDpDeliveryOrder()), tempDA, false, newOrder, LocalDate.now());
		Main.getRest().addDelivery(newExpress);
		
		if (Main.getRest().getNotDeliveredYet() == null)
			Main.getRest().resetNotDeliveredYet();
		Main.getRest().getNotDeliveredYet().add(newExpress); // add to notDelivered
				
	}
	
	public void addDishForNewOrder(ActionEvent event) {
		Dish tempDish = dish.getValue();
		
		if (tempDish == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Uncomplete field");
			alert.setHeaderText("You must choose at least one component");
			alert.show();
			return;
		}
		if (addDishes == null)
			addDishes = new ArrayList<>();
		
		addDishes.add(tempDish); // External
		listDishesAdd.getItems().clear();
		listDishesAdd.getItems().addAll(addDishes);
		listDishesAdd.refresh();
	}
	
	public void resetNewDishList(ActionEvent event) {
		if (addDishes != null) {
			addDishes.clear();
			listDishesAdd.getItems().clear();
			listDishesAdd.getItems().addAll(addDishes);
			listDishesAdd.refresh();
		}
		listDishesAdd.refresh();
	}
	
	public void showEditOrder(ActionEvent event) {
		idForEdit.setVisible(true);
		searchIdEdit.setVisible(true);
				
		// Add Order
		customer.setVisible(false);
		dish.setVisible(false);
		addDishNew.setVisible(false);
		resetDishes.setVisible(false);
		listDishesAdd.setVisible(false);
		submitAdd.setVisible(false);
		
		//Remove
		searchIdRemove.setVisible(false);

		

	}
	
	private Order tempOrderEdit;
	public void searchForEdit(ActionEvent event) {
		String orderId = idForEdit.getText();
		int idNum;
		
		try {
			idNum = Integer.parseInt(orderId);
		} catch(NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("Illegal id");
			alert.show();
			return;
		}
		
		tempOrderEdit = Main.getRest().getRealOrder(idNum);
		
		if(tempOrderEdit.getDelivery() != null &&  tempOrderEdit.getDelivery().isDelivered()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Order error");
			alert.setHeaderText("Sorry, you can not edit an order that is already delivered");
			alert.show();
			return;	
		}

		if (tempOrderEdit == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Order error");
			alert.setHeaderText("We couldn't find this Order");
			alert.show();
			return;
		}
		
		customer.setVisible(true);
		customer.setValue(tempOrderEdit.getCustomer());
		customer.setEditable(false);
		
		dish.setVisible(true);
		listDishesAdd.getItems().clear();
		listDishesAdd.getItems().addAll(tempOrderEdit.getDishes());
		addDishes = (ArrayList<Dish>) tempOrderEdit.getDishes();
		listDishesAdd.setVisible(true);
		addDishNew.setVisible(true);
		resetDishes.setVisible(true);
		listDishesAdd.setVisible(true);
		submitEdit.setVisible(true);

	}
	
	public void submitEditOrder(ActionEvent event) throws IOException {
		
		if (addDishes == null || addDishes.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Uncomplete field");
			alert.setHeaderText("You must choose at least one Dish");
			alert.show();
			return;
		}

		allDishes.clear(); // Prevent duplicates in 'Choose components'
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("New Order was added");
		alert.setHeaderText("The Order was added successfully");
		if (alert.showAndWait().get() == ButtonType.OK) {
			refresh(event);
		}
		
	}
	
	public void showRemoveOrder(ActionEvent event) {		
		// Add Order
		customer.setVisible(false);
		dish.setVisible(false);
		addDishNew.setVisible(false);
		resetDishes.setVisible(false);
		listDishesAdd.setVisible(false);
		submitAdd.setVisible(false);
		//Edit Order
		searchIdEdit.setVisible(false);
		//remove Order
		idForEdit.setVisible(true);
		searchIdRemove.setVisible(true);
		
	}
	
	public void removeOrder(ActionEvent event) throws IOException {
		
		String orderId = idForEdit.getText();
		int idNum;
		
		try {
			idNum = Integer.parseInt(orderId);
		} catch(NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("Illegal id");
			alert.show();
			return;
		}

		
		tempOrder = Main.getRest().getRealOrder(idNum);
		
		if (tempOrder == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Order error");
			alert.setHeaderText("We couldn't find this Order");
			alert.show();
			return;
		}
		
		if(tempOrder.getDelivery() != null &&  tempOrder.getDelivery().isDelivered()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Order error");
			alert.setHeaderText("Sorry, you can not edit an order that is already delivered");
			alert.show();
			return;	
		}

		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove Order");
		alert.setHeaderText("Are you sure you want to remove Order:\n" 
		+ tempOrder.getId());
		if (alert.showAndWait().get() == ButtonType.OK) {
			Main.getRest().removeOrder(tempOrder);
			refresh(event);
		}
	
		
	}

	
	
	public void refresh(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/Orders.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Dishes");
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
