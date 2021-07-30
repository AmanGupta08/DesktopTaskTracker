package application;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewActivity  {
	 @FXML
	    private DatePicker d;

	    @FXML
	    private Button s;

	    @FXML
	    private Label l;
	    
	                
	    
	    @FXML
	    void date(ActionEvent event) {
	    	
	    }
	   

	    
	    @FXML
	    void label(MouseEvent event) {

	    }
	    

	    @FXML
	    void submit(ActionEvent event) {
	    	
    	   LocalDate f=d.getValue();
    	   l.setText(f.toString());
    	   
    	   
    	    Stage primaryStage = new Stage(); 
		    Pane pane=new HBox();
		     
		    Label l3=new Label("Date:");
		    l3.setFont(new Font("Arial", 30));
		    pane.getChildren().add(l3);
		    Label l4=new Label();
		    l4.setText(f.toString());
		    l4.setFont(new Font("Arial", 30));
		    pane.getChildren().add(l4);
		    
		    
		    Label l1=new Label("WebCam Shots:"); 
		    pane.getChildren().add(l1);
		    
		    try {
		    	ArrayList<InputStream> images =DbConnect.getWebcamShot(f);
		    	for(InputStream i:images) {
		    		Image image=new Image(i);
		    		ImageView img=new ImageView();
		    		img.setImage(image);
					img.setFitHeight(200);
					img.setFitWidth(200);
					pane.getChildren().add(img);
		    	}
				
		    }catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			Label l2=new Label("ScreenShots:");
			pane.getChildren().add(l2);
			
			try {
		    	ArrayList<InputStream> images =DbConnect.getScreenShot(f);
		    	for(InputStream i:images) {
		    		Image image=new Image(i);
		    		ImageView img=new ImageView();
		    		img.setImage(image);
					img.setFitHeight(200);
					img.setFitWidth(200);
					pane.getChildren().add(img);
		    	}
				
		    }catch (Exception e) {
				e.printStackTrace();
			}
			
			Scene scene=new Scene(pane,600,600);
			primaryStage.setTitle("Images");
			primaryStage.setScene(scene);
			primaryStage.show();
			
	
   		
    	   
	    }



		

}
