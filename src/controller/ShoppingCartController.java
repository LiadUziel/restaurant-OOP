package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TreeSet;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;

import Exceptions.SensitiveException;
import Exceptions.UncompleteField;
import Model.Component;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Dish;
import Model.ExpressDelivery;
import Model.Order;
import Model.RegularDelivery;
import application.Main;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShoppingCartController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	//------------------
	
	Dish selectionDish;
	
	@FXML
	private JFXListView<Dish> listDishes;
	
	@FXML
	private Label totalPriceLabel;
	
	@FXML
	private JFXCheckBox expressPress;
	
	
	// details of dish
	@FXML
	private Label nameLabel, priceLabel, compsLabel; // , removeCompLabel
	
	@FXML
	private ImageView image;
	
	@FXML 
	private JFXListView<Component> listComps;
		
	@FXML
	private JFXButton removeDishButton; // removeCompButton, , updateDishButton





	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// isDeliver Timer
		Timer timer2 = new Timer();
		timer2.schedule(new IsDeliver(), 0, 120000);


		listDishes.getItems().addAll(MenuController.getDishesInNewOrder());
		
		listDishes.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				selectionDish = listDishes.getSelectionModel().getSelectedItem();
				showDetails(null);
			}
		});
		
		double totalPrice = 0;
		for (Dish d : listDishes.getItems())
			totalPrice += d.getPrice();
		if (expressPress.isSelected())
			totalPrice += 5;
		totalPriceLabel.setText("Total price: " + Double.toString(totalPrice) + "$");
		
		// details of dish
		nameLabel.setVisible(false);
		priceLabel.setVisible(false);
		image.setVisible(false);
		compsLabel.setVisible(false);
		listComps.setVisible(false);
		removeDishButton.setVisible(false);
	}
	
	// Show details of dish
	public void showDetails(ActionEvent event) {
		try {
			if (selectionDish == null) 
				throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("Please choose dish");
			alert.show();
			return;
		}
		
		nameLabel.setText(selectionDish.getDishName());
		nameLabel.setVisible(true);
		
		priceLabel.setText(selectionDish.getPrice() + "$");
		priceLabel.setVisible(true);
		
		
		compsLabel.setText("The components are:");
		compsLabel.setVisible(true);
		
		listComps.getItems().clear();
		listComps.getItems().addAll(selectionDish.getComponenets());
		listComps.setVisible(true);
		listComps.setDisable(true);
		
		
		// Image
		String imageName = "d"+selectionDish.getId();
		File file = new File("src/Media/"+imageName+".png");
		if(file.length() == 0) {
			image.setImage(new Image(getClass().getResourceAsStream("/Media/defaultDish.png")));
		}
		else {
			Image img = new Image(file.toURI().toString());
			image.setImage(img);
		}
		image.setVisible(true);

		
		removeDishButton.setVisible(true);

	}
	
	// Remove Dish
	public void removeDish(ActionEvent event) throws IOException {
		selectionDish = listDishes.getSelectionModel().getSelectedItem();
		
		try {
			if (selectionDish == null)
				throw new UncompleteField();
		}catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("Please choose dish for removing");
			alert.show();
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove dish");
		alert.setHeaderText("Are you sure that you want to remove " + selectionDish.getDishName() + "?");
		if (alert.showAndWait().get() == ButtonType.OK) {
			MenuController.getDishesInNewOrder().remove(selectionDish);
			refresh(event);
		}


	}
	
	private static int dpDeliveryOrder = 0;
	public static int getDpDeliveryOrder() {
		return dpDeliveryOrder;
	}
	public static void setDpDeliveryOrder(int dpDeliveryOrder) {
		ShoppingCartController.dpDeliveryOrder = dpDeliveryOrder;
	}

	
	// expressAdding
	public void expressAdding(ActionEvent event) {
		String totalPriceStr;
		Double totalPriceNum;
		if (expressPress.isSelected()) {
			totalPriceStr = totalPriceLabel.getText().substring(13, totalPriceLabel.getText().length()-1);
			totalPriceNum = (Double.parseDouble(totalPriceStr)) + 5.0;
			totalPriceLabel.setText("Total price: " + totalPriceNum.toString() + "$");
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Express delivery");
			alert.setHeaderText("You chose express delivery\nPlease notice an additional cost was added to your cart");
			alert.show();
		} else {
			totalPriceStr = totalPriceLabel.getText().substring(13, totalPriceLabel.getText().length()-1);
			totalPriceNum = (Double.parseDouble(totalPriceStr)) - 5.0;
			totalPriceLabel.setText("Total price: " + totalPriceNum.toString() + "$");
		}
	}
	// confirmOrder
	public void confirmOrder(ActionEvent event) throws IOException {
		boolean isSensitive = false;
		
		try {
			if (listDishes.getItems().isEmpty()) 
				throw new UncompleteField();
		} catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("It's not possible make order without dishes");
			alert.show();
			return;
		}
				
		ArrayList<Dish> dishes = new ArrayList<>();
		dishes.addAll(listDishes.getItems());
		Order newOrder = null;
		try {
		newOrder = new Order(LoginController.getCurrentCust(), dishes, null);
		Main.getRest().addOrder(newOrder);
		} catch(SensitiveException e) {
			isSensitive = true;
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Sensitive exception");
			alert.setHeaderText("There is some sensitive component in your order.\n"
					+"Are you sure that you want continue the order?");
			if (!(alert.showAndWait().get() == ButtonType.OK)) {
				return;
			}
		}
		if (isSensitive) {
			if (newOrder.getCustomer().getOrdersHistory() == null)
				newOrder.getCustomer().resetOrderHistory();
			newOrder.getCustomer().getOrdersHistory().add(newOrder); // New For GUI
			
			Main.getRest().getOrders().put(newOrder.getId(), newOrder);
		}
					
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirm order");
		alert.setHeaderText("You order was added successfully\nThank you!");
		alert.show();


		// reset Shopping cart
		MenuController.getDishesInNewOrder().clear();
		
		// AI Machine
		DeliveryArea tempDA = null;
		for (DeliveryArea da : Main.getRest().getAreas().values())
			if (da.getNeighberhoods().contains(LoginController.getCurrentCust().getNeighberhood()))
				tempDA = da;
		
		if (dpDeliveryOrder == tempDA.getDelPersons().size()) // Reset DP if his is the last
			dpDeliveryOrder = 0;
		ArrayList<DeliveryPerson> dPersons = new ArrayList<>();
		dPersons.addAll(tempDA.getDelPersons());



		if (isSensitive || expressPress.isSelected()) {
			ExpressDelivery newExpress;
			if (expressPress.isSelected())
				newExpress = new ExpressDelivery(dPersons.get(dpDeliveryOrder++), tempDA, false, newOrder, LocalDate.now());
			else
				newExpress = new ExpressDelivery(dPersons.get(dpDeliveryOrder++), tempDA, false, newOrder, 0, LocalDate.now());
						
			Main.getRest().addDelivery(newExpress);
			
    		if (Main.getRest().getNotDeliveredYet() == null)
    			Main.getRest().resetNotDeliveredYet();
			Main.getRest().getNotDeliveredYet().add(newExpress); // add to notDelivered
			

			switchTo_RestMenu(event);
			return;
		}
		
		// Collect orders to hashmap of DA 
		TreeSet<Order> ordersOfDA = Main.getRest().getAI_machine().get(tempDA);
		
		if (ordersOfDA == null) {
			ordersOfDA = new TreeSet<>();
			Main.getRest().getAI_machine().put(tempDA, ordersOfDA);
		}
		ordersOfDA.add(newOrder);
		
		if (ordersOfDA.size() == 3) {
			RegularDelivery newRegular = new RegularDelivery(ordersOfDA, dPersons.get(dpDeliveryOrder++), tempDA, false, LocalDate.now());
			Main.getRest().addDelivery(newRegular);
			
			
    		if (Main.getRest().getNotDeliveredYet() == null)
    			Main.getRest().resetNotDeliveredYet();
			Main.getRest().getNotDeliveredYet().add(newRegular);
			TreeSet<Order> resetOrdersAI = new TreeSet<>();
			Main.getRest().getAI_machine().put(tempDA, resetOrdersAI);
		}



		switchTo_RestMenu(event);
	}
	
	
	public void refresh(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/ShoppingCart.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Shopping Cart");
		 stage.show();
	}
	
	public void switchTo_RestMenu(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/RestaurantMenu.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - RestaurantMenu");
		 stage.show();
	}
}
