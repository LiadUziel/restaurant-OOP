package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import Exceptions.IllegalCustomerException;
import Exceptions.UncompleteField;
import Model.Customer;
import Model.Restaurant;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginController implements Initializable{
	
	public Restaurant rest = Main.getRest();
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
		
	// Fields
	@FXML
	private JFXTextField userName, passwordShow;
	@FXML
	private JFXPasswordField password;
	@FXML
	private JFXCheckBox showPass;
	
	private static Customer currentCust;
	public static Customer getCurrentCust() {
		return currentCust;
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		password.setVisible(true);
		passwordShow.setVisible(false);
	}

	public void switchToRegisterScene(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/RegisterScene.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Register");
		 stage.show();
		 
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

	}
	
	public void switchToForgotPass(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/ForgotPasswordScene.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Forgot my password");
		 stage.show();

	}
	
	// Show password if the user wants
	public void isShowPassword(ActionEvent event) {
		if (showPass.isSelected()) {
			password.setVisible(false);
			passwordShow.setVisible(true);
			passwordShow.setText(password.getText());
		}
		else {
			password.setVisible(true);
			passwordShow.setVisible(false);
			password.setText(passwordShow.getText());
		}
	}
	
	// Login checks
	public void login(ActionEvent event) throws IOException {		
//		 Every 5 seconds
//		Timer timer = new Timer();
//		timer.schedule(new AI_Machine_Timer(), 0, 5000);
//		Timer timer = new Timer();
//		timer.schedule(new AI_Machine_Timer(), 0, 120000);
//		Timer timer2 = new Timer();
//		timer2.schedule(new IsDeliver(), 0, 120000);

		// AI Machine Timer
		Timer timer = new Timer();
		timer.schedule(new AI_Machine_Timer(), 0, 120000);



		String passInput = "";
		
		if (!showPass.isSelected()) // Password is not shown
			passInput = password.getText();
		else // Password is shown
			passInput = passwordShow.getText();
		

		// Login - manager
		if (userName.getText().equals("manager") && passInput.equals("manager")) {
			switchToHomePage(event);
			return;
		}
		else if (userName.getText().equals("manager") && !passInput.equals("manager")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Password error");
			alert.setHeaderText("This password is not correct");
			alert.show();
			return;
		}
		
		// Login - Customer
		Integer idCust = Main.getRest().getGetIdByUserCust().get(userName.getText());
		if (idCust == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Usernamer error");
			alert.setHeaderText("This username is not exists");
			alert.show();
			return;
		}
		
		// Black list
		try {
			if (Main.getRest().getBlackList().contains(Main.getRest().getRealCustomer(idCust)))
				throw new IllegalCustomerException();
		} catch (IllegalCustomerException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Illegal customer exception");
			alert.setHeaderText("You are in the blacklist.\nYou can't order from this restaurant.");
			alert.show();
			return;
		}
		
		try { // Uncomplete password
		if (passInput == null || passInput.equals(""))
			throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("Please enter password");
			alert.show();
			return;
		}


		// Password correct
		if (passInput.equals(Main.getRest().getRealCustomer(idCust).getPassword())) {
			currentCust = Main.getRest().getRealCustomer(idCust);
			switchTo_RestMenu(event);
		}
		else { // Password isn't correct
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Password error");
			alert.setHeaderText("This password is not correct");
			alert.show();
			return;
		}

			
	}
		
	public void switchToHomePage(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/HomePageManager.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - HomePageManager");
		 stage.show();
		 
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

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
