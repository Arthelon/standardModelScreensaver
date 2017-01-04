package ss_classes;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ScreenFrame extends JFrame implements MouseListener, KeyListener, MouseMotionListener {
	
	//Cursor
	private BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	private Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
	
	private static TableCell selected = null; //Indicates the selected TableCell object
	
	private BgPanel bg = new BgPanel();
	private TableCell previous = null; //Stores the previous selected object
	private boolean infoState = false;
	private URL[] links;
	
	/*
	 * Default constructor for ScreenFrame objects
	 */
	public ScreenFrame() {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setUndecorated(true); //Removes Window title & buttons
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);//Makes window size as large as possible
		setVisible(true);
		add(bg);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
//		loadFonts();
		try { //Loads all particle URLs and stores them in the "links" array
			links = new URL[]{new URL("https://en.wikipedia.org/wiki/Up_quark"), new URL("https://en.wikipedia.org/wiki/Charm_quark"),
					new URL("https://en.wikipedia.org/wiki/Top_quark"), new URL("https://en.wikipedia.org/wiki/Gluon"),
					new URL("https://en.wikipedia.org/wiki/Down_quark"), new URL("https://en.wikipedia.org/wiki/Strange_quark"),
					new URL("https://en.wikipedia.org/wiki/Bottom_quark"), new URL("https://en.wikipedia.org/wiki/Photon"),
					new URL("https://en.wikipedia.org/wiki/Electron"), new URL("https://en.wikipedia.org/wiki/Muon"),
					new URL("https://en.wikipedia.org/wiki/Tau_(particle)"), new URL("https://en.wikipedia.org/wiki/W_and_Z_bosons"),
					new URL("https://en.wikipedia.org/wiki/Electron_neutrino"), new URL("https://en.wikipedia.org/wiki/Muon_neutrino"),
					new URL("https://en.wikipedia.org/wiki/Tau_neutrino"), new URL("https://en.wikipedia.org/wiki/W_and_Z_bosons"),
					new URL("https://en.wikipedia.org/wiki/Higgs_boson")};
		} catch (Exception e) {
			e.printStackTrace();
		}
		setCursor(blankCursor); //Turns cursor invisible by setting it to a blank image
	}
	
//	public void loadFonts() {
//		try {
//			InputStream is = getClass().getResourceAsStream("res/SourceCodePro-Regular.otf");
//			Font CodePro = Font.createFont(Font.TRUETYPE_FONT, is);
//			is = getClass().getResourceAsStream("/Golden-sun.tff");
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    } catch (FontFormatException e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!infoState && (e.getKeyCode() == KeyEvent.VK_UP || //If any of the arrow keys are selected
				e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_RIGHT)) {
			previous = selected; 
			//selected is assigned to the Higgs Boson TableCell object if no TableCells are currently selected
			if (selected == null) { 
				selected = TableCell.getById(16); 
				selected.select();
				return; 
			} else if((selected.getID() == 4 || selected.getID() == 8 && 
					e.getKeyCode() == KeyEvent.VK_LEFT) || 
					(selected.getID() == 7 || selected.getID() == 11 && 
					e.getKeyCode() == KeyEvent.VK_RIGHT)) {
				selected = TableCell.getById(16); 	
			//Otherwise assigns selected to the TableCell object that is in a specified direction relative to the current selected TableCell
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				selected = TableCell.getDir(selected, "up");
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				selected = TableCell.getDir(selected, "down");
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				selected = TableCell.getDir(selected, "left");
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				selected = TableCell.getDir(selected, "right");
			}
		//If enter key is pressed while a TableCell is selected
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER && selected != null) { 
			tableOut(); //Fade-outs all table components
			//Sets ID of information state components and fades them in
			BgPanel.getInfoList()[0].setID(selected.getID()); 
			BgPanel.getInfoList()[1].setID(selected.getID());
			BgPanel.getInfoList()[0].fadeIn(0.5f);
			BgPanel.getInfoList()[1].fadeIn(0.8f);
			infoState = true;
			selected = null;
			return;
		//If enter key is pressed while in the information state
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER && infoState) {
			infoState = false;
			//Fades out information state components
			BgPanel.getInfoList()[0].fadeOut(0f);
			BgPanel.getInfoList()[1].fadeOut(0f);
			tableIn(); //Fades in all Table Components
			return;
		//If "L" key is pressed while in the information state
		} else if(infoState && e.getKeyCode() == KeyEvent.VK_L) {
			//Opens page regarding the displayed particle on the device's default browser. 
			openWebpage(links[BgPanel.getInfoList()[0].getID()]);
			close(); //Exits screen-saver
		//If they key pressed violates all of the above cases
		} else {
			close(); //Exits screen-saver
		}
		selected.select();
		previous.fadeOut(0.5f);
	}
	/*
	 * Fades out all TableComponents until they're invisible
	 */
	public void tableOut() {
		for (TableCell c: TableCell.getList()) {
			c.vanish();
//			c.fadeOut(0);
		}
		for (TableHeader h: TableHeader.getList()) {
			h.vanish();
//			h.fadeOut(0);
		}
	}
	/*
	 * Fades in all Table components
	 */
	public void tableIn() {
		for (TableCell c: TableCell.getList()) {
			c.fadeIn(0.5f);
		}
		for (TableHeader h: TableHeader.getList()) {
			h.fadeIn(0.7f);
		}
	}
	
	/*
	 * Assigns a null value to selected, indicating that no TableCell is currently selected
	 */
	public static void deselect() {
		selected = null;
	}
	/*
	 * Closes window running the screensaver
	 */
	public void close() {
		this.processWindowEvent(
                new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		System.exit(0); //Terminates the JVM normally.
	}
	/*
	 * Exits the screen-saver if mouse is moved or clicked
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		close();	
	}
	@Override
	public void mousePressed(MouseEvent e) {
		close();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		close();
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		close();
	}
	/*
	 * Unused Listener Methods
	 */
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	
	//The following methods are taken from stackoverflow user 'Vulcan'
	public void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
}
