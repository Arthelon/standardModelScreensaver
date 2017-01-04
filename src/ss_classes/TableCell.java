package ss_classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import javax.swing.Timer;

@SuppressWarnings("serial")
public class TableCell extends ScreenComponent  {
	
	private static final Color[] cellColor= {Color.WHITE, Color.BLACK, new Color(0x727272), new Color(0x0000cc)};
	private static TableCell[] list = new TableCell[17]; //Array containing all TableCell objects that are displayed on-screen
	
	//Fonts
	private static final Font font = new Font("Source Sans Pro", Font.PLAIN, 16);
	private static final Font[] fonts = {font, font.deriveFont(18),
			font.deriveFont(Font.BOLD, 64)};
	private static final int size = 170; //Length / Width of all TableCells
	private static final float textX = 5;
	
	//Instance Variables
	private FontMetrics metrics;
	private Timer waitTimer = new Timer(0, null); //Timer for delayed fadeOut
	private int colorIndex; 
	/*
	 * Initialization Constructor of TableCell objects
	 */
	public TableCell(int id) {
		setSize(new Dimension(size, size));
		setAlpha(0.5f);
		setOpaque(false);
		list[id] = this;
		this.id = id;
		
		if (id == 16) {
			colorIndex = 3;
		} else if (id < 8 && id%4 != 3) {
			colorIndex = 0;
		} else if (id%4 != 3) {
			colorIndex = 1;
		} else {
			colorIndex = 2;
		}
	}
	/*
	 * Paints TableCell Object onto the swing component holding it
	 * @see Screensaver.ScreenComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(cellColor[colorIndex]);
		g2.fillRect(0, 0, size, size);
		g2.setColor(Color.WHITE);
		if (id < 8 && id%4 != 3)  g2.setColor(Color.BLACK);
		g2.setFont(fonts[1]);
		metrics = g2.getFontMetrics();
		g2.drawString(cellText[id][0], (size-metrics.stringWidth(cellText[id][0]))/2, 150); //Draws particle name
		g2.setFont(fonts[2]);
		metrics = g2.getFontMetrics();
		//Draws first character of particle symbol
		g2.drawString(cellText[id][4].substring(0,1), 70, (size-20-metrics.getHeight())/2+metrics.getAscent()); 
		if (cellText[id][4].length() > 1) { //Subscripts second character if present
			AttributedString as = new AttributedString(cellText[id][4].substring(1, 2));
			as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT, 0, 1);
			g2.drawString(as.getIterator(), 70 + metrics.stringWidth(cellText[id][4].substring(0,1)), 
					(size-20-metrics.getHeight())/2+metrics.getAscent());
		}
		g2.setFont(fonts[0]);
		//Draws particle properties
		g2.drawString(cellText[id][1], textX, 20);
		g2.drawString(cellText[id][2], textX, 60);
		g2.drawString(cellText[id][3], textX, 100);
	}
	/*
	 * Fades the object into max opacity. 
	 * After 5 seconds, the object will fade out to normal opacity (0.5f). 
	 */
	public void select() {
		fadeIn(1f);
		waitTimer = new Timer(40, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				fadeOut(0.5f);
				ScreenFrame.deselect(); 
				waitTimer.stop();
			}
		});
		waitTimer.setInitialDelay(5000);
		waitTimer.start();
	}
	/*
	 * Stops the delayed fadeOut 
	 * @see select()
	 * @see Screensaver.ScreenComponent#fadeOut(float)
	 */
	public void fadeOut(float alpha) {
		waitTimer.stop();
		super.fadeOut(alpha);
	}
	/*
	 * Stops the delayed fadeOut
	 * @see select()
	 * @see Screensaver.ScreenComponent#fadeIn(float)
	 */
	public void fadeIn(float alpha) {
		waitTimer.stop();
		super.fadeIn(alpha);
	}
	
	/*
	 * Returns Color object used to paint the background of this TableCell
	 */
	public Color getColor() {
		return cellColor[colorIndex];
	}
	
	public static TableCell getById(int id) {
		return list[id];
	}
	/*
	 * Returns an array containing all TableCell objects that are displayed on the screen
	 */
	public static TableCell[] getList() {
		return list;
	}
	
	/*
	 * Returns a TableCell object that is displayed on the screen in a direction relative to another TableCell object
	 * First parameter specifies the TableCell object to pivot around
	 * Second parameter specifies the direction
	 */
	public static TableCell getDir(TableCell selected, String dir) {
		int id = selected.getID();
		switch (dir) {
			case "up":
				if (id != 16) id -= 4;
				if (id < 0) id += 16;
				break;
			case "down":
				if (id != 16) id += 4;
				if (id > 16) id -= 16;
				break;
			case "left":
				if (id == 16) id = 11;
				else if (id % 4 != 0) id -= 1;
				else id += 3;
				break;
			case "right":
				if (id == 16) id = 8;
				else if (id % 4 != 3) id += 1;
				else id -= 3;
				break;
		}
		return list[id]; 
	}
	
	/*
	 * Returns True if the alpha values of all TableCells is equal to the value of the parameter
	 */
	public static boolean reached(float toAlpha) {
		for (TableCell c: list) {
			if (c.getAlpha() != toAlpha) return false;
		}
		return true;
	}
 }
