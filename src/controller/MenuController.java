package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import Exceptions.NoComponentsExceptions;
import Exceptions.UncompleteField;
import Model.Component;
import Model.Dish;
import Utils.DishType;
import Utils.Gender;
import application.Main;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	//------------------------

	// Video background
	@FXML
	private MediaView media;
	
	private MediaPlayer mediaPlayer;

	
	// backgroundMusic
	@FXML
	private MediaView mediaAudio;
	private MediaPlayer mediaPlayerAudio;



	@FXML
	private ImageView imgMute;
	private boolean music;


	@FXML 
	private ImageView imgUser;
	@FXML
	private Label labelHello, quantityDishes;
	
	//Lists of the menu
	@FXML
	private JFXListView<Dish> listStarters, listMain, listDessert;
	
	// details of dish
	@FXML
	private Label nameLabel, priceLabel, compsLabel, removeCompLabel;
	
	@FXML
	private ImageView image;
	
	@FXML 
	private JFXListView<Component> listComps;
	
	@FXML
	private Spinner<Integer> qunatitySpinner;
	
	@FXML
	private JFXButton addDishButton, removeCompButton;

	private static ArrayList<Dish> dishesInNewOrder;
	public static ArrayList<Dish> getDishesInNewOrder() {
		return dishesInNewOrder;
	}
	
	// watchCart
	@FXML
	private JFXListView<Dish> listDishes;
	@FXML
	private  JFXButton checkOutButton;
	@FXML
	private AnchorPane cartPane;

	private Dish selectionDish;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		 Video background
		try 
		{ 
			 //Playing the video we added	
			mediaPlayer = new MediaPlayer(new Media(this.getClass().getResource("/Media/videoKittle.mp4").toExternalForm()));
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
			return;
		}
		
		
		// backgroundMusic
		String songName = "/Media/backgroundMusic.mp3";
		mediaPlayerAudio = new MediaPlayer(new Media(this.getClass().getResource(songName).toExternalForm()));
		mediaPlayerAudio.setAutoPlay(true);
		mediaAudio.setMediaPlayer(mediaPlayerAudio);
		mediaPlayerAudio.setCycleCount(MediaPlayer.INDEFINITE); // NonStop
		
		music = true;	

		// Blessing message
		// Image of user
		String imageName = "c"+LoginController.getCurrentCust().getId();
		File file = new File("src/Media/"+imageName+".png");
		if(file.length() == 0) {
			if(LoginController.getCurrentCust().getGender().equals(Gender.Male))
				imgUser.setImage(new Image(getClass().getResourceAsStream("/Media/defaultM.png")));
			else if(LoginController.getCurrentCust().getGender().equals(Gender.Female))
				imgUser.setImage(new Image(getClass().getResourceAsStream("/Media/defaultW.png")));
			else 
				imgUser.setImage(new Image(getClass().getResourceAsStream("/Media/unknownGender.png")));
		}
		else {
			Image img = new Image(file.toURI().toString());
			imgUser.setImage(img);
		}

		String bless = "";
		int hour = LocalTime.now().getHour();
		if (hour >= 6 &&  hour <= 11) 
			bless = "good morning";
		else if (hour >= 12 && hour <= 16) 
			bless = "good afternoon";
		else if (hour >= 17 && hour <= 20) 
			bless = "good evening";
		else 
			bless = "good night";

		labelHello.setText("Hello " + LoginController.getCurrentCust().getFirstName() +", " + bless);
		
		
		if (dishesInNewOrder == null)
			dishesInNewOrder = new ArrayList<>();
		quantityDishes.setText(Integer.toString(dishesInNewOrder.size()));
		
		//Lists of the menu
		HashSet<Dish> starters = new HashSet<>();
		HashSet<Dish> main = new HashSet<>();
		HashSet<Dish> desserts = new HashSet<>();
		
		for(Dish d: Main.getRest().getDishes().values()) {
			if(d.getType().equals(DishType.Starter)) 
				starters.add(d);
			else if(d.getType().equals(DishType.Main))
				main.add(d);
			else if(d.getType().equals(DishType.Dessert))
			desserts.add(d);
		}
		
		listStarters.getItems().addAll(starters);
		listMain.getItems().addAll(main);
		listDessert.getItems().addAll(desserts);
		
		// details of dish
		nameLabel.setVisible(false);
		priceLabel.setVisible(false);
		//image.setVisible(false);
		compsLabel.setVisible(false);
		listComps.setVisible(false);
		removeCompLabel.setVisible(false);
		qunatitySpinner.setVisible(false);
		addDishButton.setVisible(false);
		removeCompButton.setVisible(false);
		
		// qunatitySpinner
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
		valueFactory.setValue(1);
		qunatitySpinner.setValueFactory(valueFactory);
		
		listStarters.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				selectionDish = listStarters.getSelectionModel().getSelectedItem();
				showDetails(null);
			}
		});
		listMain.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				selectionDish = listMain.getSelectionModel().getSelectedItem();
				showDetails(null);
			}
		});
		listDessert.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				selectionDish = listDessert.getSelectionModel().getSelectedItem();
				showDetails(null);
			}
		});
		
		
		// watchCart
		listDishes.setVisible(false);
		checkOutButton.setVisible(false);
				
	}
	
	
	// Show details dish
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

		
		compsLabel.setText("The dish components:");
		compsLabel.setVisible(true);
		
		listComps.getItems().clear();
		listComps.getItems().addAll(selectionDish.getComponenets());
		listComps.setVisible(true);
		
		removeCompLabel.setText("To remove component, mark it and press");
		removeCompLabel.setVisible(true);
		
		qunatitySpinner.setVisible(true);
		addDishButton.setVisible(true);
		removeCompButton.setVisible(true);
		
		listStarters.refresh();
		listMain.refresh();
		listDessert.refresh();
		
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

	}
	
	private boolean isDishChanged = false;
	public void removeComp(ActionEvent event) {
		Component selectionComp = listComps.getSelectionModel().getSelectedItem();
		
		try {
			if (selectionComp == null)
				throw new UncompleteField();
		}catch (UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("Please choose component for removing");
			alert.show();
			return;
		}
		
		// Not possible remove the last component
		try {
			if (listComps.getItems().size() == 1) 
				throw new NoComponentsExceptions(selectionDish.getDishName());
		} catch (NoComponentsExceptions e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Remove component error");
			alert.setHeaderText(e.getMessage());
			alert.show();
			return;
		}
		
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove component");
		alert.setHeaderText("Are you sure that you want to remove "
		+ selectionComp.getComponentName() + " from " + selectionDish.getDishName() + "?");
		if (alert.showAndWait().get() == ButtonType.OK) {
			listComps.getItems().remove(selectionComp);
			isDishChanged = true;
			listComps.refresh();
		}
	}
	
	
	// Add to shopping cart
	public void addNewDishToCart(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Dish");
		alert.setHeaderText("Are you sure that you want to add\n" + qunatitySpinner.getValue() +" of this '" + selectionDish.getDishName() +"' to shopping cart?");
		if (alert.showAndWait().get() == ButtonType.OK) {
			for (int i=0; i<qunatitySpinner.getValue(); i++) {
				if (!isDishChanged) // original dish
					dishesInNewOrder.add(selectionDish);
				else {
					ArrayList<Component> comps = new ArrayList<>();
					comps.addAll(listComps.getItems());
					Dish newDish = new Dish(selectionDish.getDishName(), selectionDish.getType(), comps, selectionDish.getTimeToMake());
					newDish.setPrice(selectionDish.getPrice());
					dishesInNewOrder.add(newDish);
					
				}
			}
			quantityDishes.setText(Integer.toString(dishesInNewOrder.size()));
		}
		
		// reset qunatitySpinner
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
		valueFactory.setValue(1);
		qunatitySpinner.setValueFactory(valueFactory);
		
		
		refresh(event);
	}
	
	
	int counter = 1;
	public void watchCart( ActionEvent event) {
		listDishes.getItems().clear();
		listDishes.getItems().addAll(dishesInNewOrder);
		
    	if(counter%2 !=0) {
    		cartPane.setTranslateX(1243);
        	TranslateTransition slide = new TranslateTransition();
        	slide.setDuration(Duration.seconds(1));
        	slide.setNode(cartPane);
        	
        	slide.setToX(0);
        	slide.play(); 	
        	
    		checkOutButton.setVisible(true);
    		listDishes.setVisible(true);
    		
    		counter++;
    	}
    	else{
     		checkOutButton.setVisible(false);
    		listDishes.setVisible(false);
    		
    		counter++;
    	}

    }

	
	// Mute music 
	public void muteMusic(ActionEvent event) {		
		if (music) {
			mediaPlayerAudio.setMute(true);
			imgMute.setImage(new Image(getClass().getResourceAsStream("/Media/mute.png")));
			music = false;
		}
		else {
			mediaPlayerAudio.setMute(false);
			imgMute.setImage(new Image(getClass().getResourceAsStream("/Media/music.png")));
			music = true;
		}
	}


	
	public void refresh(ActionEvent event) throws IOException {
		if (mediaPlayer != null || mediaPlayerAudio != null) {
			mediaPlayer.stop();
			mediaPlayerAudio.stop();
		}

		 root = FXMLLoader.load(getClass().getResource("/View/RestaurantMenu.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Restaurant Menu");
		 stage.show();

	}
	
	public void switchTo_ShoppingCart(ActionEvent event) throws IOException{
		if (mediaPlayer != null || mediaPlayerAudio != null) {
			mediaPlayer.stop();
			mediaPlayerAudio.stop();
		}

		 root = FXMLLoader.load(getClass().getResource("/View/ShoppingCart.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Shopping Cart");
		 stage.show();
	}

	
	public void switchTo_UserProfile(ActionEvent event) throws IOException{
		if (mediaPlayer != null || mediaPlayerAudio != null) {
			mediaPlayer.stop();
			mediaPlayerAudio.stop();
		}

		 root = FXMLLoader.load(getClass().getResource("/View/UserProfile.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - UserProfile");
		 stage.show();
	}
	
	public void switchToUserQueries(ActionEvent event) throws IOException{
		if (mediaPlayer != null || mediaPlayerAudio != null) {
			mediaPlayer.stop();
			mediaPlayerAudio.stop();
		}

		 root = FXMLLoader.load(getClass().getResource("/View/UserQueries.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - User Queries");
		 stage.show();
	}
	
	
	public void switchToLogin(ActionEvent event) throws IOException{
		if (mediaPlayer != null || mediaPlayerAudio != null) {
			mediaPlayer.stop();
			mediaPlayerAudio.stop();
		}

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

	

}
