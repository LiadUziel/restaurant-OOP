package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

import Model.Component;
import Model.Cook;
import Model.Customer;
import Model.Dish;
import Utils.Expertise;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserQueriesController implements Initializable{

	private Stage stage;
	private Scene scene;
	private Parent root;
	//------------------------

	// getReleventDishList
	@FXML
	private Label labelReleventDish;

	@FXML
	private JFXListView<Dish> listReleventDish;
	
	// getCooksByExpertise
	@FXML
	private JFXComboBox<Expertise> chooseExpertise;
	
	@FXML
	private Label labelExpertise;
	
	@FXML
	private JFXListView<Cook> listExpertise;

	
	// getPopularComponents
	@FXML
	private Label labelPopularComps;
	
	@FXML
	private BarChart<String, Integer>  barChartComps;
	@FXML
	private CategoryAxis x;
	@FXML
	private NumberAxis y;

	
	@FXML
	private JFXListView<Component> listPopularComps;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// getReleventDishList
		labelReleventDish.setVisible(false);
		listReleventDish.setVisible(false);
		
		// GetCooksByExpertise
		chooseExpertise.setVisible(false);
		labelExpertise.setVisible(false);
		listExpertise.setVisible(false);
		
		// getPopularComponents
		labelPopularComps.setVisible(false);
		listPopularComps.setVisible(false);
		barChartComps.setVisible(false);
	}
	
	public void getReleventDishList(ActionEvent event) {
		// GetCooksByExpertise
		chooseExpertise.setVisible(false);
		labelExpertise.setVisible(false);
		listExpertise.setVisible(false);
		
		// getPopularComponents
		labelPopularComps.setVisible(false);
		listPopularComps.setVisible(false);
		barChartComps.setVisible(false);

		
		Customer tempCustRelevent = LoginController.getCurrentCust();
		
		labelReleventDish.setVisible(true);
		labelReleventDish.setText("Relevent Dishes for: " + tempCustRelevent.getFirstName() + " " + tempCustRelevent.getLastName());
		
		listReleventDish.setVisible(true);
		listReleventDish.getItems().clear();
		listReleventDish.getItems().addAll(Main.getRest().getReleventDishList(tempCustRelevent));
	}
	
	// getCooksByExpertise
	public void getCooksByExpertise(ActionEvent event) {
		chooseExpertise.getItems().clear();
		chooseExpertise.getItems().addAll(Expertise.values());
		chooseExpertise.setVisible(true);
		
		// getReleventDishList
		labelReleventDish.setVisible(false);
		listReleventDish.setVisible(false);
		
		// GetCooksByExpertise
		labelExpertise.setVisible(false);
		listExpertise.setVisible(false);
		
		// getPopularComponents
		labelPopularComps.setVisible(false);
		listPopularComps.setVisible(false);
		barChartComps.setVisible(false);
	}
	public void showCooksExpertise(ActionEvent event) {
		Expertise tempExpertise = chooseExpertise.getValue();
		
		labelExpertise.setVisible(true);
		labelExpertise.setText("The cooks for '" + tempExpertise + "' expertise:");
		
		listExpertise.getItems().clear();
		listExpertise.getItems().addAll(Main.getRest().GetCooksByExpertise(tempExpertise));
		listExpertise.setVisible(true);
	}
	
	// getPopularComponents
	
	public void getPopularComponents(ActionEvent event) throws IOException {		
		// getReleventDishList
		labelReleventDish.setVisible(false);
		listReleventDish.setVisible(false);
		
		// GetCooksByExpertise
		chooseExpertise.setVisible(false);
		labelExpertise.setVisible(false);
		listExpertise.setVisible(false);
		
		
		// Start function:
		labelPopularComps.setText("The popular components are:");
		labelPopularComps.setVisible(true);
		
		listPopularComps.getItems().clear();
		listPopularComps.getItems().addAll(Main.getRest().getPopularComponents());
		listPopularComps.setVisible(true);
		
		// BarChart
		XYChart.Series<String, Integer> set1 = new XYChart.Series<>();
		if (Main.getRest().getComponentsandAmount() == null)
			Main.getRest().resetCompsAmount();
		for (Component tempComp : Main.getRest().getComponentsandAmount().keySet())
			set1.getData().add(new XYChart.Data<>(tempComp.toString(), Main.getRest().getComponentsandAmount().get(tempComp)));
				
		x.setAnimated(false);
		barChartComps.getData().add(set1);
		barChartComps.setVisible(true);

	}

	
	public void switchToHomeUser(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/RestaurantMenu.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Restaurant Menu");
		 stage.show();
	}


}
