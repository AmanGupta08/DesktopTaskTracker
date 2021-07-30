package application;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.stage.Stage;


public class Login {
	@FXML
    private TextField userText;

    @FXML
    private TextField passText;
    
    @FXML
    private Button log;
    																											
    @FXML
    void login(ActionEvent event) {
    	
    	
    	
    	if(userText.getText().equals("admin") && passText.getText().equals("admin") ) {
    		log.getScene().getWindow().hide();
    		try {
    			Stage primaryStage=new Stage();
    			Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
    			Scene scene = new Scene(root,400,400);
    			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    			primaryStage.setScene(scene);
    			primaryStage.setTitle("Home");
    			primaryStage.show();
    		} catch(Exception e) {
    			e.printStackTrace();
    		}  
    	}else {
    		
    			Alert errorAlert = new Alert(AlertType.WARNING);
    			errorAlert.setContentText("Wrong Entries!");
    			errorAlert.showAndWait();
    		
    	}
    	userText.setText(null);
		passText.setText(null);
    }

}
