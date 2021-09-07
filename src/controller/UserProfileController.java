package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import Exceptions.ConvertToExpressException;
import Exceptions.OrderHistoryException;
import Exceptions.UncompleteField;
import Model.Component;
import Model.Customer;
import Model.Dish;
import Model.ExpressDelivery;
import Model.Order;
import Model.RegularDelivery;
import Utils.Gender;
import Utils.Neighberhood;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class UserProfileController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	//------------------------
	
	// Show Details
	@FXML
	private JFXTextField firstName, lastName, gender, neighberhood;
	
	@FXML 
	private DatePicker birthDay;
	
	@FXML 
	private Label labelLactose, labelGluten;
	
	
	// Edit Details
	@FXML 
	private Label labelEdit;
	
	@FXML
	private JFXComboBox<Gender> genderEdit;
	@FXML
	private JFXComboBox<Neighberhood> neighEdit;
	
	@FXML
	private JFXButton editButton, submitEdit;
	
	@FXML
	private JFXCheckBox lactoseEdit, glutenEdit;
	
	
	// Order history
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
	
	
	// remove Order - not delivered
	@FXML
	private AnchorPane paneRemove;
	@FXML
	private Label labelRemoveOrder;
	
	@FXML 
	private JFXComboBox<Order> chooseRemoveOrder;
	
	@FXML
	private JFXButton submitRemoveOrder;

	@FXML
	private ImageView image;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Show details
		firstName.setText(LoginController.getCurrentCust().getFirstName());
		firstName.setEditable(false);
		
		lastName.setText(LoginController.getCurrentCust().getLastName());
		lastName.setEditable(false);
		
		birthDay.setValue(LoginController.getCurrentCust().getBirthDay());
		birthDay.setDisable(true);
		
		gender.setText(LoginController.getCurrentCust().getGender().toString());
		gender.setEditable(false);
		
		neighberhood.setText(LoginController.getCurrentCust().getNeighberhood().toString());
		neighberhood.setEditable(false);
		
		if (LoginController.getCurrentCust().getIsSensitiveToLactose())
			labelLactose.setText("Sensitive to lactose");
		else
			labelLactose.setText("Not sensitive to lactose");
		if (LoginController.getCurrentCust().getIsSensitiveToGluten())
			labelGluten.setText("Sensitive to gluten");
		else
			labelGluten.setText("Not sensitive to gluten");
		
		String imageName = "c"+LoginController.getCurrentCust().getId();
		File file = new File("src/Media/"+imageName+".png");
		if(file.length() == 0) {
			if(LoginController.getCurrentCust().getGender().equals(Gender.Male))
				image.setImage(new Image(getClass().getResourceAsStream("/Media/defaultM.png")));
			else if(LoginController.getCurrentCust().getGender().equals(Gender.Female))
				image.setImage(new Image(getClass().getResourceAsStream("/Media/defaultW.png")));
			else {
					image.setImage(new Image(getClass().getResourceAsStream("/Media/unknownGender.png")));
			
			}
		}
		else {
			Image img = new Image(file.toURI().toString());
			image.setImage(img);
		}


		// Edit
		labelEdit.setVisible(false);
		genderEdit.setVisible(false);
		neighEdit.setVisible(false);
		lactoseEdit.setVisible(false);
		glutenEdit.setVisible(false);
		submitEdit.setVisible(false);
		
		// Order history
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

		// Remove Order - not delivered
		paneRemove.setVisible(false);
		chooseRemoveOrder.getItems().clear();
		chooseRemoveOrder.setVisible(false);
		labelRemoveOrder.setVisible(false);
		submitRemoveOrder.setVisible(false);
		
		boolean notDeliveredExists = false;
		for (Order o : Main.getRest().getOrders().values())
			if (o.getDelivery() == null && o.getCustomer().equals(LoginController.getCurrentCust())
			|| (o.getDelivery() != null && o.getCustomer().equals(LoginController.getCurrentCust()) && !o.getDelivery().getIsDelivered())) {
				notDeliveredExists = true;
				chooseRemoveOrder.getItems().add(o);
			}
		if (notDeliveredExists) {
			paneRemove.setVisible(true);
			labelRemoveOrder.setText("Choose order for remove\nyou can only remove orders that not delivered");
			labelRemoveOrder.setVisible(true);
			chooseRemoveOrder.setVisible(true);
			submitRemoveOrder.setVisible(true);
		}
	}
	
	public void showEdit(ActionEvent event) {
		labelEdit.setText("Please edit the details");
		labelEdit.setVisible(true);
		
		firstName.setEditable(true);
		lastName.setEditable(true);
		birthDay.setDisable(false);
		
		gender.setVisible(false);
		genderEdit.getItems().addAll(Gender.values());
		genderEdit.setValue(LoginController.getCurrentCust().getGender());
		genderEdit.setVisible(true);
		
		neighberhood.setVisible(false);
		neighEdit.getItems().addAll(Main.getRest().getNeighsInRest());
		neighEdit.setValue(LoginController.getCurrentCust().getNeighberhood());
		neighEdit.setVisible(true);
		
		labelLactose.setText("Are you sensitive to lactose?");
		labelGluten.setText("Are you sensitive to gluten?");
		if (LoginController.getCurrentCust().getIsSensitiveToLactose())
			lactoseEdit.setSelected(true);
		if (LoginController.getCurrentCust().getIsSensitiveToGluten())
			lactoseEdit.setSelected(true);
		lactoseEdit.setVisible(true);
		glutenEdit.setVisible(true);
		
		submitEdit.setVisible(true);
		editButton.setVisible(false);
	}
	
	public void submitEdit(ActionEvent event) throws IOException {
		String first, last;
		LocalDate birth;
		Gender gender;
		Neighberhood neigh;
		boolean lactose = false;
		boolean gluten = false;
		
		first = firstName.getText();
		last = lastName.getText();
		birth = birthDay.getValue();
		
		gender = genderEdit.getValue();
		neigh = neighEdit.getValue();
		
		if(lactoseEdit.isSelected())
			lactose = true;
		if (glutenEdit.isSelected())
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
		
		Customer tempCust = LoginController.getCurrentCust();
		
		tempCust.setFirstName(first);
		tempCust.setLastName(last);
		tempCust.setBirthDay(birth);
		tempCust.setGender(gender);
		tempCust.setNeighberhood(neigh);
		tempCust.setSensitiveToLactose(lactose);
		tempCust.setSensitiveToGluten(gluten);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Edit successfully");
		alert.setHeaderText("Your details were updated");
		if (alert.showAndWait().get() == ButtonType.OK)
			refresh(event);
	}
	
	
	// Order history
	public void showOrderHistory(ActionEvent event) {
		
		try {
			if (LoginController.getCurrentCust().getOrdersHistory() == null || LoginController.getCurrentCust().getOrdersHistory().isEmpty())
				throw new OrderHistoryException();
		}catch(OrderHistoryException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Order History Exception");
			alert.setHeaderText(e.getMessage());
			alert.show();
			return;
		}

		listOrderHistory.getItems().clear();
		listOrderHistory.getItems().addAll(LoginController.getCurrentCust().getOrdersHistory());
		listOrderHistory.setVisible(true);
	}
	public void showOrderDishes(ActionEvent event) {
		listDishesOrder.getItems().clear();
		listDishesOrder.setVisible(false);
		listComps.getItems().clear();
		listComps.setVisible(false);

		Order selectionOrder = listOrderHistory.getSelectionModel().getSelectedItem();
		if (selectionOrder == null)
			return;
		
		labelDishesOrder.setText("The dishes of " + selectionOrder + ":");
		labelDishesOrder.setVisible(true);
		
		listDishesOrder.getItems().clear();
		listDishesOrder.getItems().addAll(selectionOrder.getDishes());
		listDishesOrder.setVisible(true);
	}
	public void showCompsOfDish(ActionEvent event) {
		Dish selectionDish = listDishesOrder.getSelectionModel().getSelectedItem();
		if (selectionDish == null)
			return;
		
		labelComps.setText("The component of selection '" + selectionDish.getDishName() + "':");
		labelComps.setVisible(true);
		
		listComps.getItems().clear();
		listComps.getItems().addAll(selectionDish.getComponenets());
		listComps.setVisible(true);
	}
	
	// Remove Order - not delivered
	public void removeOrder(ActionEvent event) throws IOException {
		Order selectionOrder = chooseRemoveOrder.getValue();
		
		try {
			if (selectionOrder == null) 
				throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("You didn't finish complete all the fields\nPlease try again :)");
			alert.show();
			return;
		}
		
		Main.getRest().getOrders().remove(selectionOrder.getId());

		if (selectionOrder.getDelivery() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Remove successfully");
			alert.setHeaderText("The order was deleted");
			if (alert.showAndWait().get() == ButtonType.OK) 
				refresh(event);
			return;
		}
		try {
			// Express delivery - reomve order and delivery
			if (selectionOrder.getDelivery() instanceof ExpressDelivery) {
				Main.getRest().getNotDeliveredYet().remove(selectionOrder.getDelivery());
				Main.getRest().getOrders().remove(selectionOrder.getId());
				Main.getRest().removeDelivery(selectionOrder.getDelivery());
			}
			
			// 2 orders before remove in this delivery - casting to Express
			else if (((RegularDelivery) selectionOrder.getDelivery()).getOrders().size() == 2) 
					throw new ConvertToExpressException();	
			else // 3 orders before remove - no casting 
				((RegularDelivery) selectionOrder.getDelivery()).removeOrder(selectionOrder);
		} catch(ConvertToExpressException e) {
			ExpressDelivery newExpress = new ExpressDelivery(selectionOrder.getDelivery().getDeliveryPerson(), selectionOrder.getDelivery().getArea(), false, selectionOrder, 0, LocalDate.now());
			
			Main.getRest().getNotDeliveredYet().remove(selectionOrder.getDelivery());
			Main.getRest().removeDelivery(selectionOrder.getDelivery());

			Main.getRest().addDelivery(newExpress);
			Main.getRest().getNotDeliveredYet().add(newExpress);
		}

		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Remove successfully");
		alert.setHeaderText("The order was deleted");
		if (alert.showAndWait().get() == ButtonType.OK) 
			refresh(event);

	}
	
	public void refresh(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/UserProfile.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - UserProfile");
		 stage.show();
	}

	public void switchTo_RestMenu(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/RestaurantMenu.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - RestaurantMenu");
		 stage.show();
		 
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
	}


}
