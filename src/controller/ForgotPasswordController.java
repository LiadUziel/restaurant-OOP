package controller;


import java.io.IOException;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;

public class ForgotPasswordController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private JFXTextField userName;
	
	@FXML
	private DatePicker dateBirth;
	
	@FXML
	private Button submitButton, backLoginButton;
	
	public void getMyPassword(ActionEvent event) throws IOException{
		// Alex must be contain (until the admin)
//		Main.getRest().addCustomer(new Customer("akes", "fdvdfs", LocalDate.now(), Gender.Female, "Alex", "A1234!", Neighberhood.Bat_Galim, false, false));
				
		if (userName.getText() == null || userName.getText().equals("") || dateBirth.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Forgot my password");
			alert.setHeaderText("You must complete the fields");
			alert.show();
			return;
		}
		
		String user = userName.getText();
		String date = dateBirth.getValue().toString();
			
		String pass = application.Main.getRest().getForgotMyPassWord().get(user + date);
		
		if (pass == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Forgot my password");
			alert.setHeaderText("Sorry, we couldn't find your password");
			alert.show();
			return;
		}

		// In case the fields are correct - The user gets the password
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Forgot my password");
		alert.setHeaderText("Your password is:\n" + pass);
		alert.setContentText("Please copy the password and try not to forget it again");
		if (alert.showAndWait().get() == ButtonType.OK)
			switchToLogin(event);
		return;
	
	}
	
	
	public void switchToLogin(ActionEvent event) throws IOException{
		 root = FXMLLoader.load(getClass().getResource("/View/LoginScene.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("JavaEat - Login");
		 stage.show();
	}

}
