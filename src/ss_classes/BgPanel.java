
package ss_classes;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class BgPanel extends JPanel {
	//List of Information State objects that are displayed on the screen
	private static ScreenComponent[] infoList = {new InfoParticle(), new InfoText()}; 
	
	//Instance Variables
	private BufferedImage[] imageArr = new BufferedImage[3]; //List of Background Images
	private int cycle = 0;
	private Timer timer;
	/*
	 * Default Constructor for BgPanel objects
	 */
	public BgPanel() {
		setOpaque(false);
		bgRun();
		setLayout(new TableLayout());
		populateComponents();
	}
	/*
	 * Paints the object onto the swing component holding it
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
        refreshList(); //Updates the bgArray list in BgImage
        BgImage.get(0).paint(g); //Paints the first BgImage object in
        if (BgImage.get(1).getAlpha() > 0) BgImage.get(1).paint(g);
    }
	/*
	 * Updates the static bgArray list in BgImage with all BgImage objects on the panel
	 */
	public void refreshList() {
		for (int i = 0; i < getComponentCount(); i++) {
			if (getComponents()[i] instanceof BgImage) BgImage.add(((BgImage)getComponents()[i]));
		}
	}
	/*
	 * Returns the index of the next image to be displayed in imageArr
	 */
	public int nextCycle() {
		if (cycle >= 2) return 0;
		return cycle + 1;
	}
	/*
	 * Adds all Table components onto the panel
	 */
	public void populateComponents() {
		for (int i = 0; i < 17; i++) {
			add(new TableCell(i));
		}
		for (TableHeader h: TableHeader.getList()) {
			add(h);
		}
		add(infoList[1]);
		add(infoList[0]);
	}
	
	public void bgRun() {
		try {
			//Initializes background images
			imageArr[0] = ImageIO.read(Class.class.getResourceAsStream("/res/bg1.jpg"));
			imageArr[1] = ImageIO.read(Class.class.getResourceAsStream("/res/bg2.jpeg"));
			imageArr[2] = ImageIO.read(Class.class.getResourceAsStream("/res/bg3.jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Adds BgImage objects onto the screen
		add(new BgImage(imageArr[cycle], 1f)); //The visible BgImage
		add(new BgImage(imageArr[nextCycle()], 0f)); //BgImage object that will fade in after 1 cycle

		timer = new Timer(40, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	BgImage.get(1).fadeIn(); 
            	BgImage.get(0).fadeOut();
                if (BgImage.get(0).getAlpha() <= 0.05) {
                	cycle = nextCycle();
                	remove(BgImage.get(0));
                	add(new BgImage(imageArr[nextCycle()], 0f));
                	timer.restart();
                }
                revalidate();
                repaint();
            }
        });
		timer.setInitialDelay(10000); //10 second delay before the next BgImage fade-ins
        timer.start();
	}
	/*
	 * Returns array containing all Information state Objects that appear on the screen
	 */
	public static ScreenComponent[] getInfoList() {
		return infoList;
	}
}
