package application;
	
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Model.Component;
import Model.Cook;
import Model.Customer;
import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Dish;
import Model.Order;
import Model.Restaurant;
import controller.HomePageController;
import controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;


public class Main extends Application {
	
	private static Restaurant rest = Restaurant.getInstance();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			new FXMLLoader();
//			Parent root = FXMLLoader.load(getClass().getResource("/View/Ppp.fxml"));
			Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScene.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("JavaEat - Login");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(event -> {
				event.consume();
				logout(primaryStage);	
			});
	
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
			primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			readFile();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("File not found exception");
			alert.setHeaderText("There is a problem, try again later");
			primaryStage.close();
			alert.show();
		}
		

	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static Restaurant getRest() {
		return rest;
	}
	
	// Serialization
	public static void writeToFile() {
		try {
			FileOutputStream fileOut = new FileOutputStream("Rest.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			rest = Main.getRest();
//			System.out.println(Customer.getIdCounter());
			out.writeObject(rest);
//			System.out.println("Writing... " + rest.getBlackList() + "\n");
//			out.flush();
			out.close();
			fileOut.close();
			System.out.println("writeToFile succeeded");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("writeToFile not succeeded");
		}
	}
	
	// DeSerialization
	public static void readFile() throws IOException, ClassNotFoundException{
			FileInputStream fileIn = new FileInputStream("Rest.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			rest = (Restaurant) in.readObject();
			System.out.println("readFile succeeded");
			
			setIdCounters();
			
			in.close();
			fileIn.close();

	}
	
	public static void setIdCounters() {
		int maxID = 0;
		for (Customer temp : rest.getCustomers().values())
			if (temp.getId() > maxID)
				maxID = temp.getId();
		Customer.setIdCounter(maxID + 1);
		maxID = 0;
		
		for (Cook temp : rest.getCooks().values())
			if (temp.getId() > maxID)
				maxID = temp.getId();
		Cook.setIdCounter(maxID + 1);
		maxID = 0;
		
		for (Component temp : rest.getComponenets().values())
			if (temp.getId() > maxID)
				maxID = temp.getId();
		Component.setIdCounter(maxID + 1);
		maxID = 0;
		
		for (DeliveryArea temp : rest.getAreas().values())
			if (temp.getId() > maxID)
				maxID = temp.getId();
		DeliveryArea.setIdCounter(maxID + 1);
		maxID = 0;

		for (DeliveryPerson temp : rest.getDeliveryPersons().values())
			if (temp.getId() > maxID)
				maxID = temp.getId();
		DeliveryPerson.setIdCounter(maxID + 1);
		maxID = 0;

		for (Dish temp : rest.getDishes().values())
			if (temp.getId() > maxID)
				maxID = temp.getId();
		Dish.setIdCounter(maxID + 1);
		maxID = 0;

		for (Order temp : rest.getOrders().values())
			if (temp.getId() > maxID)
				maxID = temp.getId();
		Order.setIdCounter(maxID + 1);
		maxID = 0;

		for (Delivery temp : rest.getDeliveries().values())
			if (temp.getId() > maxID)
				maxID = temp.getId();
		Delivery.setIdCounter(maxID + 1);
	}
	
	
	public void logout(Stage stage){	
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("You're about to exit!");
		alert.setContentText("Do you want to save before exiting?");
		
		if (alert.showAndWait().get() == ButtonType.OK){
			writeToFile();
			stage.close();
//			System.exit(1);
		} 
	}


	
	
	
}
