package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeSet;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TableRowAlign;
import org.apache.poi.xwpf.usermodel.TableWidthType;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

import Exceptions.UncompleteField;
import Model.Component;
import Model.Cook;
import Model.Customer;
import Model.Delivery;
import Model.DeliveryPerson;
import Model.Dish;
import Utils.Expertise;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;




public class QueriesController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	//------------------------
	
	// getReleventDishList
	@FXML
	private JFXComboBox<Customer> chooseCustomerForRelevent;
	
	@FXML
	private Label labelReleventDish;
	
	@FXML
	private JFXListView<Dish> listReleventDish;
	
	
	// GetCooksByExpertise
	@FXML
	private JFXComboBox<Expertise> chooseExpertise;
	
	@FXML
	private Label labelExpertise;
	
	@FXML
	private JFXListView<Cook> listExpertise;
	
	
	// getDeliveriesByPerson
	@FXML
	private ComboBox<DeliveryPerson> chooseDP;
	@FXML
	private JFXComboBox<Integer> chooseMonth;
	
	@FXML
	private JFXButton submitDeliveriesByDP;
	
	@FXML
	private JFXListView<Delivery> listDeliveriesByDP;
	
	// getNumberOfDeliveriesPerType
	@FXML 
	private Label labelNumDeliveries;
	
	@FXML 
	private PieChart pieChartDeliveries; 
	
	// revenueFromExpressDeliveries
	@FXML
	private Label labelRevenueExpress;
	
	// getPopularComponents
	@FXML
	private Label labelPopularComps;
//	@FXML
//	private PieChart pieChartComps;
	@FXML
	private BarChart<String, Integer>  barChartComps;
	@FXML
	private CategoryAxis x;
	@FXML
	private NumberAxis y;
	
	@FXML
	private JFXListView<Component> listPopularComps;
	
	
	// getProfitRelation
	@FXML
	private Label labelProfitRelation;
	
	@FXML
	private JFXListView<Dish> listProfitRelation;
	
	@FXML
	private JFXButton wordButton;
	
	

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// getReleventDishList
		chooseCustomerForRelevent.setVisible(false);
		labelReleventDish.setVisible(false);
		listReleventDish.setVisible(false);
		
		// GetCooksByExpertise
		chooseExpertise.setVisible(false);
		labelExpertise.setVisible(false);
		listExpertise.setVisible(false);
		
		// getDeliveriesByPerson
		chooseDP.setVisible(false);
		chooseMonth.setVisible(false);
		submitDeliveriesByDP.setVisible(false);
		listDeliveriesByDP.setVisible(false);
		
		// getNumberOfDeliveriesPerType
		labelNumDeliveries.setVisible(false);
		pieChartDeliveries.setVisible(false);
		
		// revenueFromExpressDeliveries
		labelRevenueExpress.setVisible(false);
		
		// getPopularComponents
		labelPopularComps.setVisible(false);
		listPopularComps.setVisible(false);
		barChartComps.setVisible(false);
		
		// getProfitRelation
		labelProfitRelation.setVisible(false);
		listProfitRelation.setVisible(false);
		wordButton.setVisible(false);
		
	}
	
	public void getReleventDishList(ActionEvent event) {
		chooseCustomerForRelevent.getItems().clear();
		chooseCustomerForRelevent.getItems().addAll(Main.getRest().getCustomers().values());
		chooseCustomerForRelevent.setVisible(true);

		// getReleventDishList
		labelReleventDish.setVisible(false);
		listReleventDish.setVisible(false);
		
		// GetCooksByExpertise
		chooseExpertise.setVisible(false);
		labelExpertise.setVisible(false);
		listExpertise.setVisible(false);
		
		// getDeliveriesByPerson
		chooseDP.setVisible(false);
		chooseMonth.setVisible(false);
		submitDeliveriesByDP.setVisible(false);
		listDeliveriesByDP.setVisible(false);

		// getNumberOfDeliveriesPerType
		labelNumDeliveries.setVisible(false);
		pieChartDeliveries.setVisible(false);

		
		// revenueFromExpressDeliveries
		labelRevenueExpress.setVisible(false);

		// getPopularComponents
		labelPopularComps.setVisible(false);
		listPopularComps.setVisible(false);
		barChartComps.setVisible(false);



		// getProfitRelation
		labelProfitRelation.setVisible(false);
		listProfitRelation.setVisible(false);
		wordButton.setVisible(false);
	}
	
	public void showReleventDishes(ActionEvent event) {
		Customer tempCustRelevent;
		
		tempCustRelevent = chooseCustomerForRelevent.getValue();
		
		if (tempCustRelevent != null) {
			labelReleventDish.setVisible(true);
			labelReleventDish.setText("Relevent Dishes for: " + tempCustRelevent.getFirstName() + " " + tempCustRelevent.getLastName());
			
			listReleventDish.setVisible(true);
			listReleventDish.getItems().clear();
			listReleventDish.getItems().addAll(Main.getRest().getReleventDishList(tempCustRelevent));
		}

	}
	
	// GetCooksByExpertise
	public void getCooksByExpertise(ActionEvent event) {
		chooseExpertise.getItems().clear();
		chooseExpertise.getItems().addAll(Expertise.values());
		chooseExpertise.setVisible(true);
		
		// getReleventDishList
		chooseCustomerForRelevent.setVisible(false);
		labelReleventDish.setVisible(false);
		listReleventDish.setVisible(false);
		
		// GetCooksByExpertise
		labelExpertise.setVisible(false);
		listExpertise.setVisible(false);
		
		// getDeliveriesByPerson
		chooseDP.setVisible(false);
		chooseMonth.setVisible(false);
		submitDeliveriesByDP.setVisible(false);
		listDeliveriesByDP.setVisible(false);
		
		// getNumberOfDeliveriesPerType
		labelNumDeliveries.setVisible(false);
		pieChartDeliveries.setVisible(false);

		
		// revenueFromExpressDeliveries
		labelRevenueExpress.setVisible(false);

		// getPopularComponents
		labelPopularComps.setVisible(false);
		listPopularComps.setVisible(false);
		barChartComps.setVisible(false);


		// getProfitRelation
		labelProfitRelation.setVisible(false);
		listProfitRelation.setVisible(false);
		wordButton.setVisible(false);


	}
	public void showCooksExpertise(ActionEvent event) {
		Expertise tempExpertise = chooseExpertise.getValue();
		
		labelExpertise.setVisible(true);
		labelExpertise.setText("The cooks for '" + tempExpertise + "' expertise:");
		
		listExpertise.getItems().clear();
		listExpertise.getItems().addAll(Main.getRest().GetCooksByExpertise(tempExpertise));
		listExpertise.setVisible(true);
		
	}
	
	public void getDeliveriesByPerson(ActionEvent event) {
		chooseDP.getItems().clear();
		chooseDP.getItems().addAll(Main.getRest().getDeliveryPersons().values());
		chooseDP.setVisible(true);
		
		chooseMonth.getItems().clear();
		ArrayList<Integer> arrMonths = new ArrayList<>();
		for (int i=0; i<12; i++)
			arrMonths.add(i + 1);
		chooseMonth.getItems().addAll(arrMonths);
		chooseMonth.setVisible(true);
		
		submitDeliveriesByDP.setVisible(true);
		
		// getReleventDishList
		chooseCustomerForRelevent.setVisible(false);
		labelReleventDish.setVisible(false);
		listReleventDish.setVisible(false);
		
		// GetCooksByExpertise
		chooseExpertise.setVisible(false);
		labelExpertise.setVisible(false);
		listExpertise.setVisible(false);
		
		// getDeliveriesByPerson
		listDeliveriesByDP.setVisible(false);
		
		// getNumberOfDeliveriesPerType
		labelNumDeliveries.setVisible(false);
		pieChartDeliveries.setVisible(false);

		
		// revenueFromExpressDeliveries
		labelRevenueExpress.setVisible(false);

		// getPopularComponents
		labelPopularComps.setVisible(false);
		listPopularComps.setVisible(false);
		barChartComps.setVisible(false);


		// getProfitRelation
		labelProfitRelation.setVisible(false);
		listProfitRelation.setVisible(false);
		wordButton.setVisible(false);

	}
	
	public void showDeliveriesByDP(ActionEvent event) {
		DeliveryPerson tempDP = chooseDP.getValue();
		Integer month = chooseMonth.getValue();
		
		try {
			if (tempDP == null || month == null)
				throw new UncompleteField();
		} catch(UncompleteField e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(e.getMessage());
			alert.setHeaderText("You didn't finish complete all the fields\nPlease try again :)");
			alert.show();
			return;
		}
		
		listDeliveriesByDP.getItems().clear();
		listDeliveriesByDP.getItems().addAll(Main.getRest().getDeliveriesByPerson(tempDP, month));
		listDeliveriesByDP.setVisible(true);
	}
	
	
	// getNumberOfDeliveriesPerType
	public void getNumberOfDeliveriesPerType(ActionEvent event) {
		// getReleventDishList
		chooseCustomerForRelevent.setVisible(false);
		labelReleventDish.setVisible(false);
		listReleventDish.setVisible(false);
		
		// GetCooksByExpertise
		chooseExpertise.setVisible(false);
		labelExpertise.setVisible(false);
		listExpertise.setVisible(false);
		
		// getDeliveriesByPerson
		chooseDP.setVisible(false);
		chooseMonth.setVisible(false);
		submitDeliveriesByDP.setVisible(false);
		listDeliveriesByDP.setVisible(false);
		
		// revenueFromExpressDeliveries
		labelRevenueExpress.setVisible(false);

		// getPopularComponents
		labelPopularComps.setVisible(false);
		listPopularComps.setVisible(false);
		barChartComps.setVisible(false);


		// getProfitRelation
		labelProfitRelation.setVisible(false);
		listProfitRelation.setVisible(false);
		wordButton.setVisible(false);


		
		// Start Function
		String content = "";
		HashMap<String, Integer> mapNumDeliveries = Main.getRest().getNumberOfDeliveriesPerType();
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		
		for (String type : mapNumDeliveries.keySet()) {
			content += (type + ": ");
			content += (mapNumDeliveries.get(type) + "\n");
			
			pieChartData.add(new PieChart.Data(type, mapNumDeliveries.get(type)));
		}
		
		labelNumDeliveries.setText(content);
		labelNumDeliveries.setVisible(true);
		
		pieChartDeliveries.setData(pieChartData);
		pieChartDeliveries.setVisible(true);
		
	}
	
	// revenueFromExpressDeliveries
	public void revenueFromExpressDeliveries(ActionEvent event) {
		// getReleventDishList
		chooseCustomerForRelevent.setVisible(false);
		labelReleventDish.setVisible(false);
		listReleventDish.setVisible(false);
		
		// GetCooksByExpertise
		chooseExpertise.setVisible(false);
		labelExpertise.setVisible(false);
		listExpertise.setVisible(false);
		
		// getDeliveriesByPerson
		chooseDP.setVisible(false);
		chooseMonth.setVisible(false);
		submitDeliveriesByDP.setVisible(false);
		listDeliveriesByDP.setVisible(false);
		
		// getNumberOfDeliveriesPerType
		labelNumDeliveries.setVisible(false);
		pieChartDeliveries.setVisible(false);
		
		// getPopularComponents
		labelPopularComps.setVisible(false);
		listPopularComps.setVisible(false);
		barChartComps.setVisible(false);

		
		// getProfitRelation
		labelProfitRelation.setVisible(false);
		listProfitRelation.setVisible(false);
		wordButton.setVisible(false);


		
		// Start function
		String content = "The revenue from \nexpress deliveries is: "
		+ Main.getRest().revenueFromExpressDeliveries() + "$";
		
		labelRevenueExpress.setText(content);
		labelRevenueExpress.setVisible(true);

	}
	
	public void getPopularComponents(ActionEvent event) {
		// getReleventDishList
		chooseCustomerForRelevent.setVisible(false);
		labelReleventDish.setVisible(false);
		listReleventDish.setVisible(false);
		
		// GetCooksByExpertise
		chooseExpertise.setVisible(false);
		labelExpertise.setVisible(false);
		listExpertise.setVisible(false);
		
		// getDeliveriesByPerson
		chooseDP.setVisible(false);
		chooseMonth.setVisible(false);
		submitDeliveriesByDP.setVisible(false);
		listDeliveriesByDP.setVisible(false);
		
		// getNumberOfDeliveriesPerType
		labelNumDeliveries.setVisible(false);
		pieChartDeliveries.setVisible(false);
		
		// revenueFromExpressDeliveries
		labelRevenueExpress.setVisible(false);

		// getProfitRelation
		labelProfitRelation.setVisible(false);
		listProfitRelation.setVisible(false);
		wordButton.setVisible(false);

		
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
	
	public void getProfitRelation(ActionEvent event) throws IOException {
		// getReleventDishList
		chooseCustomerForRelevent.setVisible(false);
		labelReleventDish.setVisible(false);
		listReleventDish.setVisible(false);
		
		// GetCooksByExpertise
		chooseExpertise.setVisible(false);
		labelExpertise.setVisible(false);
		listExpertise.setVisible(false);
		
		// getDeliveriesByPerson
		chooseDP.setVisible(false);
		chooseMonth.setVisible(false);
		submitDeliveriesByDP.setVisible(false);
		listDeliveriesByDP.setVisible(false);
		
		// getNumberOfDeliveriesPerType
		labelNumDeliveries.setVisible(false);
		pieChartDeliveries.setVisible(false);
		
		// revenueFromExpressDeliveries
		labelRevenueExpress.setVisible(false);
		
		// getPopularComponents
		labelPopularComps.setVisible(false);
		listPopularComps.setVisible(false);
		barChartComps.setVisible(false);

		

		// Start function
		labelProfitRelation.setText("The disehs with the best relation of profit are:");
		labelProfitRelation.setVisible(true);
		
		listProfitRelation.getItems().clear();
		listProfitRelation.getItems().addAll(Main.getRest().getProfitRelation());
		listProfitRelation.setVisible(true);
		
		wordButton.setVisible(true);

	}
	
	
	public void createWordDocument(ActionEvent event) throws IOException {
        String fileName = "Output1.docx";

        try (XWPFDocument document = new XWPFDocument()) {

            // create a paragraph
            XWPFParagraph header1 = document.createParagraph();
            header1.setAlignment(ParagraphAlignment.CENTER);

            // set font - header
            XWPFRun font1 = header1.createRun();
            font1.setBold(true);
            font1.setItalic(true);
            font1.setUnderline(UnderlinePatterns.DASH_LONG);
            font1.setFontSize(34);
            font1.setFontFamily("New Roman");
            font1.setColor("3E2CD8");
            font1.setText("Dishes by relation of profit");
            
            // Description
            XWPFParagraph description2 = document.createParagraph();
            description2.setAlignment(ParagraphAlignment.RIGHT);
            // Set font - description
            XWPFRun font2 = description2.createRun();
            font2.setBold(true);
            font2.setItalic(true);
            font2.setFontSize(20);
            font2.setFontFamily("Ink free");
            font2.setColor("82DCE8");
            font2.addBreak();
            font2.setText("We collected data on the profits from the restaurant's dishes.");
            font2.addBreak();
            font2.addBreak();
            font2.setText("The relation is calculated by the price and the making time of the dishes.");
            font2.addBreak();
            font2.addBreak();
            
           
            // Table
            XWPFTable table = document.createTable();
            table.setTableAlignment(TableRowAlign.LEFT);
            table.setInsideHBorder(XWPFBorderType.DASH_DOT_STROKED, 3, 2, "82DCE8");
            table.setInsideVBorder(XWPFBorderType.DASH_DOT_STROKED, 3, 2, "82DCE8");
            table.setTopBorder(XWPFBorderType.INSET, 3, 2, "3E2CD8");
            table.setBottomBorder(XWPFBorderType.INSET, 3, 2, "3E2CD8");
            table.setLeftBorder(XWPFBorderType.INSET, 3, 2, "3E2CD8");
            table.setRightBorder(XWPFBorderType.INSET, 3, 2, "3E2CD8");

            TreeSet<Dish> dishesProfit = Main.getRest().getProfitRelation();
            
            if (dishesProfit != null && !dishesProfit.isEmpty()) {
    			XWPFTableRow firstRow = table.getRow(0);
    			firstRow.getCell(0).setWidthType(TableWidthType.AUTO);
    			
    			firstRow.getCell(0).setText("");
    			setTextInCell(firstRow.getCell(0), "Dish name", true);
    			
    			firstRow.addNewTableCell().setText("");
    			setTextInCell(firstRow.getCell(1), "Price", true);

    			firstRow.addNewTableCell().setText("");
    			setTextInCell(firstRow.getCell(2), "Making time", true);
    			
    			firstRow.addNewTableCell().setText("");
    			setTextInCell(firstRow.getCell(3), "Relation", true);


            	for (Dish tempDish : dishesProfit) {
            			XWPFTableRow newRow = table.createRow();
            			
            			newRow.getCell(0).setText(""); // Reset the cell
            			setTextInCell(newRow.getCell(0), tempDish.getDishName(), false);
            			
            			newRow.getCell(1).setText("");
            			setTextInCell(newRow.getCell(1), ((Double)tempDish.getPrice()).toString(), false);
            			
            			newRow.getCell(2).setText("");
            			setTextInCell(newRow.getCell(2), ((Integer)tempDish.getTimeToMake()).toString(), false);
            			
            			newRow.getCell(3).setText("");
            			setTextInCell(newRow.getCell(3), ((Double)(tempDish.getPrice()/tempDish.getTimeToMake())).toString(), false);
            			
            	}
            }
            
            // save it to .docx file
            try (FileOutputStream out = new FileOutputStream(fileName)) {
            	document.write(out);
            }

        } catch (Exception e) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Word error");
        	alert.setHeaderText("There is some error, plase try again later");
        	alert.setContentText("Check that the word file isn't open");
        	alert.show();
        	e.printStackTrace();
        	return;
        }
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Create word document");
        alert.setHeaderText("The file was generated successfully.\nDo you want to open it?");
        if (alert.showAndWait().get() == ButtonType.OK) {
	        // Open new file
	        File file = new File(fileName);
	        try {
	        	Desktop.getDesktop().open(file);
	        } catch(IOException e) {
	        	Alert alert1 = new Alert(AlertType.ERROR);
	        	alert1.setTitle("Word error");
	        	alert1.setHeaderText("There is some error, plase try again later");
	        	alert1.setContentText("Check that the word file isn't open");
	        	alert1.show();
	        }
        }

	}
	
    private static void setTextInCell(XWPFTableCell cell, String text, boolean firstLine) {
        XWPFParagraph tempParagraph = cell.getParagraphs().get(0);
        tempParagraph.setIndentationLeft(100);
        tempParagraph.setIndentationRight(100);
        tempParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun tempRun = tempParagraph.createRun();
        if (firstLine) {
        	tempRun.setFontSize(22);
        	tempRun.setBold(true);
        	tempRun.setUnderline(UnderlinePatterns.WAVE);
        }
        else
        	tempRun.setFontSize(14);
        tempRun.setText(text);
        cell.setVerticalAlignment(XWPFVertAlign.CENTER);
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
