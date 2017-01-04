package ss_classes;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.SwingUtilities;

public class ScreenSaverRunner {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	GraphicsDevice device = GraphicsEnvironment
        		        .getLocalGraphicsEnvironment().getScreenDevices()[0];
            	ScreenFrame window = new ScreenFrame();
            	if (device.isFullScreenSupported())
            		device.setFullScreenWindow(window); //Makes window full-screen
            }
        });
	}
}
