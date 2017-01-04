package ss_classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

@SuppressWarnings("serial")
public class InfoParticle extends ScreenComponent {
	
	private static final int size = 400; //Size of InfoParticle object
	private static int sizeY = 210; //String height of particle symbol
	
	//Fonts
	private static final Font font = new Font("Source Sans Pro", Font.PLAIN, 24);
	private static final Font largeFont = font.deriveFont(Font.BOLD, 100);
	private static final Font labelFont = font.deriveFont(50);
	
	//Instance Variables
	private int sizeX; //String width of particle symbol
	private boolean first = true;
	private FontMetrics fm;
	/*
	 * Default constructor for InfoParticle objects
	 */
	public InfoParticle() {
		this(-1);
	}
	/*
	 * Initialization constructor for InfoParticle objects
	 */
	public InfoParticle(int id) {
		this.id = id; 
		setAlpha(0f); //makes object invisible
		setOpaque(false);
	}
	/*
	 * Paints this object onto the panel holding it
	 * @see Screensaver.ScreenComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (first) { //Runs once
			g.setFont(largeFont);
			fm = g.getFontMetrics();
			first = false;
		}
		super.paintComponent(g);
		if (id != -1) { //If the id points to a valid TableCell object
			g.setFont(largeFont);
			g.setColor(TableCell.getById(id).getColor());
			g.fillRect(0, 0, size, size);
			if (g.getColor() == Color.WHITE) g.setColor(Color.BLACK);
			else g.setColor(Color.WHITE);
			g.drawString(cellText[id][4].substring(0, 1), sizeX, sizeY); //Draws first character of particle symbol
			if (cellText[id][4].length() > 1) { //Subscripts second character if present
				AttributedString as = new AttributedString(cellText[id][4].substring(1, 2));
				as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT, 0, 1);
				g.drawString(as.getIterator(), sizeX + fm.stringWidth(cellText[id][4].substring(0,1)), 
						(size-20-fm.getHeight())/2+fm.getAscent());
			}
			g.setFont(font);
			//Draws particle properties
			g.drawString(cellText[id][1], 10, 50);
			g.drawString(cellText[id][2], 10, 140);
			g.drawString(cellText[id][3], 10, 240);
			g.setFont(labelFont);
			FontMetrics metrics = g.getFontMetrics();
			g.drawString(cellText[id][0], (size-metrics.stringWidth(cellText[id][0]))/2, 350); //Draws particle name/label
		}
	}
	/*
	 * Changes the ID value that is used to retrieve particle descriptions
	 * @see Screensaver.ScreenComponent#setID(int)
	 */
	public void setID(int id) {
		super.setID(id);
		sizeX = (size-fm.stringWidth(cellText[id][4]))/2; //Stores the width of the specified particle symbol
	}
}
