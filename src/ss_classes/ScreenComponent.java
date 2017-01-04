package ss_classes;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * Superclass of most classes that are displayed on screen (with the exception of BgImage)
 * Contains useful fields and methods such as a float for storing the object's alpha (transparency) value
 * 
 */
@SuppressWarnings("serial")
public class ScreenComponent extends JPanel {
	//2D Array containing information regarding each TableCell
	protected static final String[][] cellText = {{"up","2.3MeV/c2","2/3","1/2","u"},{"charm","1.275GeV/c2","2/3","1/2","c"},{"top","173.07GeV/c2","2/3","1/2","t"},
			{"gluon","0","0","1","g"},{"down","4.8MeV/c2","-1/3","1/2","d"},{"strange","95MeV/c2","-1/3","1/2","s"},{"bottom","4.18GeV/c2","-1/3","1/2","b"},
			{"photon","0","0","1","\u03B3"},{"electron","0.511MeV","-1","1/2","e"},{"muon","105.7MeV/c2","-1","1/2","\u03BC"},{"tau","1.777GeV/c2","-1","1/2","\u03C4"},
			{"Z boson","91.2GeV/c2","0","1","Z"},{"electron neutrino","2.2eV/c2","0","1/2","\u03BDe"},{"muon neutrino","0.17MeV/c2","0","1/2","\u03BD\u03BC"},
			{"tau neutrino","15.5MeV/c2","0","1/2","\u03BD\u03C4"},{"W boson","80.4GeV/c2","\u00B11","1","W"},{"Higgs boson","126GeV/c2","0","0","H"}};
	
	//Instance Variables
	protected float alpha = 0.5f;
	protected int id; //Stores an index of a certain array from which the object retrieves information from
	private float delta = 0.02f;
	
	//Timers
	private Timer inTimer = new Timer(0, null);
	private Timer outTimer = new Timer(0, null);
	
	/*
	 * Paints the object onto the swing component holding it.
	 * Transparency of the object can be changed by altering the "alpha" variable
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON );
    	g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha)); //Sets object transparency based on the value of "alpha"
	}
	/*
	 * Fades in the object until the alpha value in the parameters is reached
	 */
	public void fadeIn(float toAlpha) {
		stopTimers(); //Stops all occurring fades
		inTimer = new Timer(20, new ActionListener(){ //Object is repainted every 20ms
			@Override
			public void actionPerformed(ActionEvent e) {
				if (alpha + delta <= toAlpha) alpha += delta;
				else inTimer.stop();
				repaint();
			}
		});
		inTimer.start();
	}
	/*
	 * Fades out the object until the alpha value in the parameters is reached
	 */
	public void fadeOut(float toAlpha) {
		stopTimers(); //Stops all occurring fades
		outTimer = new Timer(20, new ActionListener(){ //Object is repainted every 20ms
			@Override
			public void actionPerformed(ActionEvent e) {
				if (alpha - delta >= toAlpha) alpha -= delta;
				else outTimer.stop();
				repaint();
			}
		});
		outTimer.start();
	}

	/*
	 * Makes the object invisible
	 */
	public void vanish() {
		//vanish();
		stopTimers();
		alpha = 0f;
		repaint();
	}
	/*
	 * Stops the fading of this object
	 */
	public void stopTimers() {
		inTimer.stop();
		outTimer.stop();
	}
	/*
	 * Modifier method for the object's alpha value
	 */
	public void setAlpha(float toAlpha) {
		alpha = toAlpha;
		//Changes the rate at which the object's alpha changes based on the value supplied in the parameter
//		if (alpha > 0) delta = alpha / 50; 
	}
	/*
	 * Accessor method for the object's alpha value
	 */
	public float getAlpha() {
		return alpha;
	}
	/*
	 * Modifier method for the object's id
	 */
	public void setID(int id) {
		this.id = id;
	}
	/*
	 * Accessor method for the object's id
	 */
	public int getID() {
		return id;
	}
}
