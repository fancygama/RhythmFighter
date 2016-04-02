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
	
	public GamePanel(int width, int height){
		
		this.setBounds(0,0,width,height);	//this just forces the panel to be at this place.
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
	
	public void drawALine(){	//just an example
		
		Graphics g = panelImage.getGraphics();	//graphics of the panel.
		Graphics2D gg = (Graphics2D) g;	//this lets you set the width of the line.
		gg.setStroke(new BasicStroke(5));	//sets the width of the line. 5 is an arbitrary number.
		gg.setColor(Color.RED);	//set color of paintbrush
		g.drawLine(50, 50, 200, 200);	//paint a line from 50,50 to 200,200
		this.repaint();	//basically calls paintComponent which pastes this on the screen.
		
	}
	
	
	
	
	
}
