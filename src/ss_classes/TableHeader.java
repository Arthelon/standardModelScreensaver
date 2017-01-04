package ss_classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class TableHeader extends ScreenComponent {
	//Array of all TableHeader objects displayed on screen
	private static TableHeader[] list = {new TableHeader("Leptons", 1), new TableHeader("Quarks", 1),
			new TableHeader("Fermions"), new TableHeader("Bosons"), new TableHeader("Force Carriers", 2)};
	private static Font font = new Font("Source Code Pro", Font.PLAIN, 18);
	private static Color headColor = new Color(0xababab);
	
	//Instance Variables
	private String display;
	private int vertical = 0;
	/*
	 * Initialization constructor with 1 parameter
	 */
	public TableHeader(String text) {
		this(text, 0);
	}
	/*
	 * Initialization constructor with 2 parameters
	 * Second parameter specifies whether the object is aligned vertically or horizontally
	 */
	public TableHeader(String text, int vertical) {
		setOpaque(false);
		setAlpha(0.7f);
		display = text;
		this.vertical = vertical;
	}
	/*
	 * Paints TableCell Object onto the swing component holding it
	 * @see Screensaver.ScreenComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		FontMetrics metrics = g2.getFontMetrics();
		Rectangle bounds = getBounds();
		g2.setColor(headColor);
    	g2.fillRect(0, 0, 700, 700);
    	g2.setFont(font);
    	g2.setColor(Color.BLACK);
    	if (vertical == 1) {
    		g2.rotate(Math.toRadians(270), (double)(bounds.width - metrics.stringWidth(display)) / 2+30,
    				(double)(bounds.height - metrics.getHeight()) / 2 + metrics.getAscent());
    	} else if (vertical == 2) {
    		g2.rotate(Math.toRadians(90), (double)(bounds.width - metrics.stringWidth(display)) / 2+38,
    				(double)(bounds.height - metrics.getHeight()) / 2 + metrics.getAscent());
    	}
    	g2.drawString(display, (bounds.width - metrics.stringWidth(display)) / 2, 
    			(bounds.height - metrics.getHeight()) / 2+metrics.getAscent());
	}
	
	/*
	 * Returns array containing all TableHeader objects displayed on the screen
	 */
	public static TableHeader[] getList() {
		return list;
	}
	
	/*
	 * Returns True if the alpha values of all TableCells is equal to the value of the parameter
	 */
	public static boolean reached(float toAlpha) {
		for (TableHeader h: list) {
			if (h.getAlpha() != toAlpha) return false;
		}
		return true;
	}
	
}
