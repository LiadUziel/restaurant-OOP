package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class HomePageController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private MediaView media;
	
	private MediaPlayer mediaPlayer;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try 
		{ 
			// Playing the video we added	
			mediaPlayer = new MediaPlayer(new Media(this.getClass().getResource("/Media/videoDishes.mp4").toExternalForm()));
			mediaPlayer.setAutoPlay(true);
			mediaPlayer.setMute(true); // Mute
			media.setMediaPlayer(mediaPlayer);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // NonStop
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("We have some troubles, Please try again later");
			alert.show();
		}
		
	}
	
	public void switchTo_Cooks(ActionEvent event) throws IOException{
		if (mediaPlayer != null)
			mediaPlayer.stop();
		
		 root = FXMLLoader.load(getClass().getResource("/View/Cooks.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Cooks");
		 stage.show();
	}
	
	public void switchTo_Customers(ActionEvent event) throws IOException{
		if (mediaPlayer != null)
			mediaPlayer.stop();

		 root = FXMLLoader.load(getClass().getResource("/View/Customers.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Customers");
		 stage.show();
	}
	
	public void switchTo_DeliveryPersons(ActionEvent event) throws IOException{
		if (mediaPlayer != null)
			mediaPlayer.stop();

		 root = FXMLLoader.load(getClass().getResource("/View/DeliveryPersons.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - DeliveryPersons");
		 stage.show();
	}
	
	public void switchTo_DeliveryAreas(ActionEvent event) throws IOException{
		if (mediaPlayer != null)
			mediaPlayer.stop();

		 root = FXMLLoader.load(getClass().getResource("/View/DeliveryAreas.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - DeliveryAreas");
		 stage.show();
	}
	
	public void switchTo_Dishes(ActionEvent event) throws IOException{
		if (mediaPlayer != null)
			mediaPlayer.stop();

		 root = FXMLLoader.load(getClass().getResource("/View/Dishes.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Dishes");
		 stage.show();
	}
	
	public void switchTo_Components(ActionEvent event) throws IOException{
		if (mediaPlayer != null)
			mediaPlayer.stop();

		 root = FXMLLoader.load(getClass().getResource("/View/Components.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Components");
		 stage.show();
	}
	
	public void switchTo_BlackList(ActionEvent event) throws IOException{
		if (mediaPlayer != null)
			mediaPlayer.stop();

		 root = FXMLLoader.load(getClass().getResource("/View/BlackList.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - BlackList");
		 stage.show();
	}
	
	public void switchTo_Deliveries(ActionEvent event) throws IOException{
		if (mediaPlayer != null)
			mediaPlayer.stop();

		 root = FXMLLoader.load(getClass().getResource("/View/Deliveries.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Deliveries");
		 stage.show();
	}
	
	
	public void switchTo_Queries(ActionEvent event) throws IOException{
		if (mediaPlayer != null)
			mediaPlayer.stop();

		 root = FXMLLoader.load(getClass().getResource("/View/Queries.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Queries");
		 stage.show();
	}
	public void switchToLogin(ActionEvent event) throws IOException{
		if (mediaPlayer != null)
			mediaPlayer.stop();

		 root = FXMLLoader.load(getClass().getResource("/View/LoginScene.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Login");
		 stage.show();
		 
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

	}
	
	
	public void switchTo_Orders(ActionEvent event) throws IOException{
		if (mediaPlayer != null)
			mediaPlayer.stop();

		 root = FXMLLoader.load(getClass().getResource("/View/Orders.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Orders");
		 stage.show();
	}


}
