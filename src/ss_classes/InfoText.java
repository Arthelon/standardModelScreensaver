package ss_classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SuppressWarnings("serial")
public class InfoText extends ScreenComponent{
	private static Font infoFont = new Font("Source Sans Pro", Font.PLAIN, 20);
	private static Font latinFont = new Font("/res/", Font.PLAIN, 36);
	private static String[] infoStr;
	private static FontMetrics metrics;
	
	private static int width = 700;
	private boolean first = true;
	/*
	 * Default Constructor of InfoText object
	 */
	public InfoText() {
		this(-1);
	}
	/*
	 * Initialization constructor of InfoText object
	 * Parameter specifies the index of the associated TableCell object in the TableCell info list
	 */
	public InfoText(int id) {
		setID(id);
		setAlpha(0f);
		setOpaque(false);
	}
	/*
	 * Paints this object onto the swing component holding it
	 * @see Screensaver.ScreenComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		if (first) { //Runs once
			metrics = g.getFontMetrics(infoFont);
			try {
				InputStreamReader isr = new InputStreamReader(Class.class.getResourceAsStream("/res/particleData.txt"));
				InfoText.setText(new BufferedReader(isr));
			} catch (Exception e) {
				e.printStackTrace();
			}
			first = false;
		}
		super.paintComponent(g);
		if (id != -1) {
			g.setFont(infoFont);
			g.setColor(Color.WHITE);
			drawString(g, infoStr[id], 0, 50);
			g.setFont(latinFont);
			g.drawString("Press \'L\' to view more", (width-metrics.stringWidth("Press \'L\' to view more"))/2, 650);
		}
		
	}
	/*
	 * Reads particle descriptions from a passed FileReader object and parses the String into an array
	 * Each element in the array holds the description for a corresponding particle in the TableCell static list
	 */
	public static void setText(BufferedReader br) {
		String str = "";
		String total = "";
		try {
			String line = br.readLine();
			while (line != null && line != "") {
				str += line;
				if (line.charAt(line.length()-1) != '~') str += "\n\n";
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] list = str.split(" ");
		str = "";
		for (int i = 0; i < list.length; i++) {
			if (metrics.stringWidth(total+list[i]+" ") <= width) {
				total += list[i]+" ";
			} else {
				str += total + "\n";
				total = list[i]+ " ";
			}
		}
		infoStr = str.split("~");
	}
	/*
	 * Draws string with line breaks onto the screen using a Graphics object
	 */
	private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
}
