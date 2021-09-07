package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import Exceptions.NameAlreadyExistsExcepction;
import Exceptions.UncompleteField;
import Model.Component;
import Model.Dish;
import Utils.DishType;
import application.Main;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DishController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	//------------------------

	
    @FXML
    private TableView<Dish> table;

    @FXML
    private TableColumn<Dish, Integer> idCol;

    @FXML
    private TableColumn<Dish, String> dishNameCol;

    @FXML
    private TableColumn<Dish, DishType> dishTypeCol;

    @FXML
    private TableColumn<Dish, Double> priceCol;

    @FXML
    private TableColumn<Dish, Integer> timeToMakeCol;
    
    private ObservableList<Dish> listDish = FXCollections.observableArrayList(Main.getRest().getDishes().values());
    
    // Show components
    @FXML
    private JFXTextField nameForDetails;
    
    @FXML
    private JFXButton searchForDetails;
    
    @FXML
    private Label headerForDetails;
    
    @FXML
    private JFXListView<Component> listComponents;
    
    
    // Add dish
    @FXML
    private JFXTextField dishName, timeToMake;
    
    @FXML
    private Label labelType;
    
    @FXML 
    private JFXRadioButton starter, main, dessert;
    
    @FXML
    private JFXComboBox<Component> component;
    private Collection<Component> allComponents = Main.getRest().getComponenets().values();
    
    @FXML
    private JFXListView<Component> newDishComps;
    
    @FXML
    private JFXButton addCompButton, resetCompButton, submitAdd;
    
    
    // Edit dish
    @FXML
    private JFXTextField nameForEdit;
    
    @FXML
    private JFXButton searchIdEdit, submitEdit;
        
    
	// Remove dish
	@FXML
	private JFXButton searchIdRemove;
	
	@FXML
	private ImageView image;

	//Design
  		
  	@FXML
  	private JFXButton AddDish, EditDish, RemoveDish;
  			
  	@FXML	   
  	private AnchorPane pane;
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		dishNameCol.setCellValueFactory(new PropertyValueFactory<>("dishName"));
		dishTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		timeToMakeCol.setCellValueFactory(new PropertyValueFactory<>("timeToMake"));
		
		table.setItems(listDish);
		
		// Show components
		headerForDetails.setVisible(false);
		listComponents.setVisible(false);
		
		// Add dish
		dishName.setVisible(false);
		labelType.setVisible(false);
		starter.setVisible(false);
		main.setVisible(false);
		dessert.setVisible(false);
		component.setVisible(false);
		component.getItems().addAll(allComponents);
		addCompButton.setVisible(false);
		resetCompButton.setVisible(false);
		newDishComps.setVisible(false);
		timeToMake.setVisible(false);
		submitAdd.setVisible(false);
		
		// Edit dish
		nameForEdit.setVisible(false);
		searchIdEdit.setVisible(false);
		submitEdit.setVisible(false);
		
		// RemoveDish
		searchIdRemove.setVisible(false);
		
		//Design
		AddDish.setVisible(false);
		EditDish.setVisible(false);
		RemoveDish.setVisible(false);
		
		
	}
	
	 public void ShowMenu(ActionEvent event) {
	    	pane.setTranslateX(-370);
	    	
	    	TranslateTransition slide = new TranslateTransition();
	    	slide.setDuration(Duration.seconds(1));
	    	slide.setNode(pane);

	    	slide.setToX(0);
	    	slide.play();
	    	
			AddDish.setVisible(true);
			EditDish.setVisible(true);
			RemoveDish.setVisible(true);
	    }
	
	
	private Dish tempDish;
	public void showComponents(ActionEvent event) {
		
		String dName = nameForDetails.getText();
		
		tempDish = Main.getRest().getRealDish(dName);
		
		if (tempDish == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Id error");
			alert.setHeaderText("We couldn't find this dish");
			alert.show();
			return;
		}
		
		// Show hidden
		headerForDetails.setVisible(true);
		headerForDetails.setText("The components of '" + tempDish.getDishName() + "':");

		listComponents.getItems().clear();
		listComponents.getItems().addAll(tempDish.getComponenets());
		listComponents.setVisible(true);
	}
	
	
	// Add dish
	public void showAddDish(ActionEvent event) {
		
		dishName.clear();
		dishName.setVisible(true);
		labelType.setVisible(true);
		starter.setVisible(true);
		main.setVisible(true);
		dessert.setVisible(true);
		component.setValue(null);
		component.setVisible(true);
		addCompButton.setVisible(true);
		resetCompButton.setVisible(true);
		newDishComps.getItems().clear();
		newDishComps.setVisible(true);
		timeToMake.clear();
		timeToMake.setVisible(true);
		submitAdd.setVisible(true);
		
		nameForEdit.setVisible(false);
		searchIdEdit.setVisible(false);
		submitEdit.setVisible(false);
		
		searchIdRemove.setVisible(false);
	}
	
	ArrayList<Component> compsInNewDish; // External
	
	public void submitAddDish(ActionEvent event) throws IOException {
		String name, timeMakeStr;
		DishType type = null;
		int timeMakeNum;
		
		name = dishName.getText();
		timeMakeStr = timeToMake.getText();
		
		if (main.isSelected())
			type = DishType.Main;
		else if (dessert.isSelected())
			type = DishType.Dessert;
		else
			type = DishType.Starter;
		
				
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
		if (Main.getRest().getDishes().containsKey(name))
			throw new NameAlreadyExistsExcepction();
		} catch (NameAlreadyExistsExcepction e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dish error");
			alert.setHeaderText(e.getMessage());
			alert.show();
			return;
		}

		
		try { // get Making time
			timeMakeNum = Integer.parseInt(timeMakeStr);
			if (timeMakeNum <= 0) //-- Maybe change exception
				throw new NumberFormatException();
			
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Illegal input");
			alert.setHeaderText("The making time must be number");
			alert.show();
			return;
		}
		
		try {
			if (compsInNewDish == null || compsInNewDish.isEmpty())
				throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("You must choose at least one component");
			alert.show();
			return;
		}
		
		Dish addDish = new Dish(name, type, compsInNewDish, timeMakeNum);
		Main.getRest().addDish(addDish);
		if(image.getImage() != null) {
			String imageName = "d" + addDish.getId();
			File fileOutput = new File("src/Media/"+imageName+".png");/*.JPG*/
			BufferedImage bi = SwingFXUtils.fromFXImage(image.getImage(),null);
			ImageIO.write(bi, "png", fileOutput);			
		}
		
		
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("New dish was added");
		alert.setHeaderText("The dish: " + name + " was added successfully");
		if (alert.showAndWait().get() == ButtonType.OK) {
			refresh(event);
		}
			
	}
	
	public void addComponentForNewDIsh(ActionEvent event) {
		Component tempComp = component.getValue();
		
		try {
			if (tempComp == null)
				throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Uncomplete field");
			alert.setHeaderText("You must choose at least one component");
			alert.show();
			return;
		}
		
		if (compsInNewDish == null)
			compsInNewDish = new ArrayList<>();
		
		compsInNewDish.add(tempComp); // External
		newDishComps.getItems().clear();
		newDishComps.getItems().addAll(compsInNewDish);
		newDishComps.refresh();
	}
	
	public void resetNewCompList(ActionEvent event) {
		if (compsInNewDish != null) {
			compsInNewDish.clear();
			newDishComps.getItems().clear();
			newDishComps.getItems().addAll(compsInNewDish);
			newDishComps.refresh();
		}
		newDishComps.refresh();
	}
	
	
	// Edit dish
	public void showEditDish(ActionEvent event) {
		nameForEdit.setVisible(true);
		searchIdEdit.setVisible(true);
				
		// Add dish
		dishName.setVisible(false);
		labelType.setVisible(false);
		starter.setVisible(false);
		main.setVisible(false);
		dessert.setVisible(false);
		component.setVisible(false);
		addCompButton.setVisible(false);
		resetCompButton.setVisible(false);
		newDishComps.setVisible(false);
		timeToMake.setVisible(false);
		submitAdd.setVisible(false);
		
		// Remove dish
		searchIdRemove.setVisible(false);

	}
	
	private Dish tempDishEdit;
	public void searchForEdit(ActionEvent event) {
		String dName = nameForEdit.getText();
		
		tempDishEdit = Main.getRest().getRealDish(dName);
		
		if (tempDishEdit == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Dish error");
			alert.setHeaderText("We couldn't find this dish");
			alert.show();
			return;
		}
		
		dishName.setVisible(true);
		dishName.setText(tempDishEdit.getDishName());
		dishName.setTooltip(new Tooltip("Dish name"));
		
		labelType.setVisible(true);
		starter.setVisible(true);
		main.setVisible(true);
		dessert.setVisible(true);
		
		if (tempDishEdit.getType().equals(DishType.Main))
			main.setSelected(true);
		else if (tempDishEdit.getType().equals(DishType.Dessert))
			dessert.setSelected(true);
		else
			starter.setVisible(true);
		
		component.setVisible(true);
		newDishComps.getItems().clear();
		newDishComps.getItems().addAll(tempDishEdit.getComponenets());
		
		compsInNewDish = (ArrayList<Component>) tempDishEdit.getComponenets();
		
		newDishComps.setVisible(true);
		addCompButton.setVisible(true);
		resetCompButton.setVisible(true);
		
		timeToMake.setVisible(true);
		timeToMake.setText(Integer.toString(tempDishEdit.getTimeToMake()));
		timeToMake.setTooltip(new Tooltip("Making time"));
		
		submitEdit.setVisible(true);
	
		String imageName = "d"+tempDishEdit.getId();
		File file = new File("src/Media/"+imageName+".png");
		if(file.length() == 0) 
			image.setImage(new Image(getClass().getResourceAsStream("/Media/defaultDish.png")));
		else {
			Image img = new Image(file.toURI().toString());
			image.setImage(img);
		}


	}
	
	public void submitEditDish(ActionEvent event) throws IOException {
		
		// Same code from up
		String name, timeMakeStr;
		DishType type = null;
		int timeMakeNum;
		
		name = dishName.getText();
		timeMakeStr = timeToMake.getText();
		
		if (main.isSelected())
			type = DishType.Main;
		else if (dessert.isSelected())
			type = DishType.Dessert;
		else
			type = DishType.Starter;
		
				
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
		if (Main.getRest().getDishes().containsKey(name) && !name.equals(tempDishEdit.getDishName()))
			throw new NameAlreadyExistsExcepction();
		} catch (NameAlreadyExistsExcepction e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Dish error");
			alert.setHeaderText(e.getMessage());
			alert.show();
			return;
		}


		
		try { // get Making time
			timeMakeNum = Integer.parseInt(timeMakeStr);
			if (timeMakeNum <= 0) //-- Maybe change exception
				throw new NumberFormatException();
			
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Illegal input");
			alert.setHeaderText("The making time must be number");
			alert.show();
			return;
		}
		
		try {
			if (compsInNewDish == null || compsInNewDish.isEmpty())
				throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("You must choose at least one component");
			alert.show();
			return;
		}
		// End of old code
		
		// Change key in rest map
		if (!name.equals(tempDishEdit.getDishName())) {
			Main.getRest().removeDish(tempDishEdit);
			tempDishEdit.setDishName(name);
			Main.getRest().addDish(tempDishEdit);
		}
		tempDishEdit.setType(type);
		tempDishEdit.setTimeToMake(timeMakeNum);
		tempDishEdit.setComponenets(compsInNewDish); //-- Maybe not good;
		tempDishEdit.setPrice(tempDishEdit.calcDishPrice());
		
		
		if(image.getImage() != null) {
			String imageName = "d" + tempDishEdit.getId();
			File fileOutput = new File("src/Media/"+imageName+".png");/*.JPG*/
			BufferedImage bi = SwingFXUtils.fromFXImage(image.getImage(),null);
			ImageIO.write(bi, "png", fileOutput);
		}
		String imageName = "d" + tempDishEdit.getId();
		File fileOutput = new File("src/Media/"+imageName+".png");/*.JPG*/
		BufferedImage bi = SwingFXUtils.fromFXImage(image.getImage(),null);
		ImageIO.write(bi, "png", fileOutput);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Edit successfully");
		alert.setHeaderText("The details of the dish were updated");
		if (alert.showAndWait().get() == ButtonType.OK)
			refresh(event);

	
		
	}
	
	
	// Remove Dish
	public void showRemoveDish(ActionEvent event) {
		nameForEdit.setVisible(true);
		searchIdRemove.setVisible(true);
		
		// Add dish
		dishName.setVisible(false);
		labelType.setVisible(false);
		starter.setVisible(false);
		main.setVisible(false);
		dessert.setVisible(false);
		component.setVisible(false);
		addCompButton.setVisible(false);
		resetCompButton.setVisible(false);
		newDishComps.setVisible(false);
		timeToMake.setVisible(false);
		submitAdd.setVisible(false);
		
		// Edit dish
		searchIdEdit.setVisible(false);
		submitEdit.setVisible(false);

		
	}
	
	public void removeDish(ActionEvent event) throws IOException {
		

		String dName = nameForEdit.getText();
		
		// tempCook is external
		tempDishEdit = Main.getRest().getRealDish(dName);
		
		if (tempDishEdit == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Dish error");
			alert.setHeaderText("We couldn't find this dish");
			alert.show();
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove Dish");
		alert.setHeaderText("Are you sure you want to remove the dish:\n" 
		+ tempDishEdit.getDishName());
		if (alert.showAndWait().get() == ButtonType.OK) {
			Main.getRest().removeDish(tempDishEdit);
			refresh(event);
		}
	
		
	}

	public void handleButtonAction (DragEvent event) {
		if(event.getDragboard().hasFiles()) {
			event.acceptTransferModes(TransferMode.ANY);
		}
	}
	
	public void handleDrop(DragEvent event) throws IOException{
		List<File> files = event.getDragboard().getFiles();
		Image img = new Image(new FileInputStream(files.get(0)));
		image.setImage(img);

	}
	
	public void refreshImage(ActionEvent event) {
		image.setImage(null);
	}
	
	
	
	
	
	
	public void refresh(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/Dishes.fxml"));
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
