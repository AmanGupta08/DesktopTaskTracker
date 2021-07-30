package application;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import com.github.sarxos.webcam.Webcam;

public class Home {
	
	@FXML
    private Button s;

    @FXML
    private Button v;
	
	 @FXML
	    private Button log_out;

	    @FXML
	    void logout(ActionEvent event) {
	    log_out.getScene().getWindow().hide();
	    try {
	    	Stage primaryStage=new Stage();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	      
	
	static TakeScreenShot takeScreenShot=new TakeScreenShot();
    @FXML
    void start(ActionEvent event) {
    	//screen shot
    	takeScreenShot.start();
    	
    	//webcam shot
    	class TakeWebCamShot extends Thread{

    		@Override
    		public void run() {
    			System.out.println(" webcam ");
    	    	Webcam webcam = Webcam.getDefault();
    	        if (webcam != null) {
    	            try {
    	                webcam.open();
    	                File f=new File("webcamshot.png");
    	                ImageIO.write(webcam.getImage(), "PNG", f);
    	                webcam.close();
    	                
    	                //data base code
    	                InputStream i=new FileInputStream(f);
    	                DbConnect.insertWebcamShotStart(i);
    	            } catch (Exception ex) {
    	                ex.printStackTrace();
    	            }
    	        } else {
    	          System.out.println("No webcam detected");
    	        }
    		}
    		
    	}
    	TakeWebCamShot w=new TakeWebCamShot();
    	w.start();
    	
    	//open StartActivity screen
    	s.getScene().getWindow().hide();
    	try {
    		Stage primaryStage=new Stage();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("StartActivity.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("StartActivity");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	
    }

    @FXML
    void view(ActionEvent event) {
    	s.getScene().getWindow().hide();
    	try {
    		Stage primaryStage=new Stage();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("ViewActivity.fxml"));
			Scene scene = new Scene(root,623,172 );
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("ViewActivity");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

}
