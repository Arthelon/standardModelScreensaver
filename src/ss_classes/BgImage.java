package ss_classes;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JComponent;


@SuppressWarnings("serial")
public class BgImage extends JComponent {
	
	private static BgImage[] bgArray = new BgImage[2]; //List of BgImage objects that are displayed on-screen
	private static int count = 0;
	
	//Instance Variables
    private Image image;
    private int width;
    private int height;
    private final float DELTA = 0.05f;
    private float alpha;
    /*
     * Initialization Constructor of BgImage
     */
    public BgImage(Image image, float alpha) {
    	setOpaque(false); 
        this.image = image;
        this.alpha = alpha;
        scaleImage();
    }
    /*
     * Paints BgImage object onto the component holding it
     */
    public void paint(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g;
    	g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON );
    	g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha));
        g2.drawImage(image, 0, 0, width, height, null);
    }
    /*
     * Resets the image size to fit the screen (1280x800px)
     */
    public void scaleImage() {
       	int tempX = image.getWidth(null);
    	int tempY = image.getHeight(null);
    	float ratio = (float) 1280 / tempY;
    	if (tempY < tempX) ratio = (float) 1280 / tempX;
    	
    	width = (int) (tempX * ratio);
    	height = (int) (tempY * ratio); 	
    }
    /*
     * Reduces alpha value of BgImage
     */
    public void fadeOut() {
    	if (alpha - DELTA >= 0) alpha -= DELTA;
	}
	/*
	 * Increases alpha value of BgImage
	 */
    public void fadeIn() {
    	if (alpha + DELTA <= 0.95) alpha += DELTA;
    }
    /*
     * Accessor Method of instance variable alpha
     */
    public float getAlpha() {
    	return alpha;
    }
    
    public static void add(BgImage bg) {
    	bgArray[count] = bg;
    	count++;
    	if (count > 1) count = 0;
    }
    /*
     * Replaces the first element of bgArray with the second element.
     */
    public static void pop() {
    	bgArray[0] = bgArray[1];
    	bgArray[1] = null;
    }
    /*
     * Returns BgImage object from bgArray based on the index specified in the parameters
     */
    public static BgImage get(int i) {
    	return bgArray[i];
    }
    
}
