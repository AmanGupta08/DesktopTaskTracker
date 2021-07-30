package application;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class StartActivity extends ViewActivity {

	@FXML
	private Button p;

	@FXML
	private Button r;

	@FXML
	private Button s;
	
	

	@FXML
	void pause(ActionEvent event) {
		r.setVisible(true);
		p.setVisible(false);
		
		Home.takeScreenShot.interrupt();
	}

	@FXML
	void resume(ActionEvent event) {
		p.setVisible(true);
		r.setVisible(false);
		 Home.takeScreenShot=new TakeScreenShot();
		 Home.takeScreenShot.start();
	}

	@FXML
	void stop(ActionEvent event) {

		Home.takeScreenShot.interrupt();
		
		//single screenshot
		try {
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			File f = new File("screenshot.png");
			ImageIO.write(capture, "bmp", f);
            
			// data base code
			InputStream i = new FileInputStream(f);
			DbConnect.insertScreenShot(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// webcam shot
		class TakeWebCamShot extends Thread {

			@Override
			public void run() {
				System.out.println(" webcam ");
				Webcam webcam = Webcam.getDefault();
				if (webcam != null) {
					try {
						webcam.open();
						File f = new File("webcamshot.png");
						ImageIO.write(webcam.getImage(), "PNG", f);
						webcam.close();

						// data base code
						InputStream i = new FileInputStream(f);
						DbConnect.insertWebcamShotStop(i);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					System.out.println("No webcam detected");
				}
			}

		}
		TakeWebCamShot w = new TakeWebCamShot();
		w.start();

		s.getScene().getWindow().hide();
		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Home");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
