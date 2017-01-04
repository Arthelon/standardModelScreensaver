package ss_classes;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class TableLayout implements LayoutManager {
	//Object Sizes
	private int cellSize = 170; //Length / Width of TableCell objects
	private int yGap = 5; //Vertical gap between TableCell objects
	private int xGap = 10; //Horizontal gap between TableCell objects
	private int headGap = 10; //Gap between TableCell objects and TableHeader objects
	private int largeGap = 20; //Gap between different TableCell particle families
	private int headWidth = 30; //Width of TableHeader objects
	private int width = xGap*3 + largeGap + cellSize*5; //Width of all Table components
	private int height = 4*cellSize + 2*yGap + largeGap; //Height of all Table components
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}
	@Override
	public void removeLayoutComponent(Component comp) {	
	}
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}
	/*
	 * Sets the bounds (size, position) of Table components and Information state components
	 */
	@Override
	public void layoutContainer(Container parent) {
		Dimension screen = parent.getSize();
		int startX = (int) (screen.getWidth()/2 - width/2); 
		int startY = (int) (screen.getHeight()/2 - height/2);
		for (TableCell c: TableCell.getList()) {
			int posX = startX;
			int posY = startY;
			
			if (c.getID() == 16) {
				posX += 3*xGap + largeGap + 4*cellSize;
				posY += largeGap/2+cellSize+cellSize/2;
			} else {
				posX += c.getID()%4*cellSize;
				posY += c.getID()/4*cellSize;
				if (c.getID() % 4 == 3) posX += largeGap + xGap * 2;
				else posX += c.getID() % 4 * xGap;
				if (c.getID() > 7) posY += largeGap;
				posY += c.getID()/4 * yGap;
			}
			c.setBounds(posX, posY, cellSize, cellSize);
		}
		TableHeader.getList()[0].setBounds(startX - headGap - headWidth,
				startY + cellSize*2+xGap+largeGap, headWidth, height - cellSize*2-yGap-largeGap);
		TableHeader.getList()[1].setBounds(startX-headGap-headWidth, startY, headWidth,
				height-cellSize*2-yGap-largeGap);
		TableHeader.getList()[2].setBounds(startX, startY-headGap-headWidth, width-cellSize*2-largeGap-xGap,
				headWidth);
		TableHeader.getList()[3].setBounds(startX+cellSize*3+xGap*2+largeGap, startY-headGap-headWidth,
				width-cellSize*3-xGap*2-largeGap, headWidth);
		TableHeader.getList()[4].setBounds(startX+width+headGap, startY, headWidth, height);
		BgPanel.getInfoList()[0].setBounds(50, 200, 400, 400);
		BgPanel.getInfoList()[1].setBounds(500, 50, 700, 700);
	}

}
