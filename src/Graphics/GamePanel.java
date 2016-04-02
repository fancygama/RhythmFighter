package Graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private BufferedImage panelImage;
	private int width, height;
	
	public GamePanel(int width, int height){
		
		this.setBounds(0,0,width,height);	//this just forces the panel to be at this place.
		this.width = width;
		this.height = height;
		init();
	}
	
	public void init(){
		
		this.setOpaque(true);
		panelImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);	//init the bufferedimage
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		g.drawImage(panelImage, 0, 0, null);	//this is all we ever need in this method
		
	}
	
	public void reset(Graphics g){
		Color temp = g.getColor();
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		g.setColor(temp);
	}
	
	public void drawALine(){	//just an example
		
		Graphics g = panelImage.getGraphics();	//graphics of the panel.
		Graphics2D gg = (Graphics2D) g;	//this lets you set the width of the line.
		gg.setStroke(new BasicStroke(5));	//sets the width of the line. 5 is an arbitrary number.
		gg.setColor(Color.RED);	//set color of paintbrush
		g.drawLine(50, 50, 200, 200);	//paint a line from 50,50 to 200,200
		this.repaint();	//basically calls paintComponent which pastes this on the screen.
		reset(g);
		g.drawLine(700, 50, 200, 200);	//paint a line from 50,50 to 200,200
		this.repaint();
		
	}
	
	
	
	
	
}
