package Graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private BufferedImage panelImage;
	private int width, height;
	private ArrayList<Integer> notesOnScreen;
	private int pixPerUpdate;
	
	public GamePanel(int width, int height){
		
		this.setBounds(0,0,width,height);	//this just forces the panel to be at this place.
		this.width = width;
		this.height = height;
		notesOnScreen = new ArrayList<Integer>();
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
	
	public void reset(){
		Graphics g = panelImage.getGraphics();
		Color temp = g.getColor();
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		g.setColor(temp);
	}
	
	public void drawNoteLanes(){
		Graphics g = panelImage.getGraphics();
		Graphics2D gg = (Graphics2D) g;
		gg.setStroke(new BasicStroke(5));
		gg.setColor(Color.white);
		gg.drawLine(0, this.height/10, this.width, this.height/10);
		gg.drawLine(0, this.height/10*2, this.width, this.height/10*2);
		gg.drawLine(this.width/2, this.height/10, this.width/2, this.height/10*2);
		gg.setColor(Color.red);
		gg.drawRect(this.width/20, this.height/10, 5, this.height/10);
		gg.drawRect(this.width - this.width/20, this.height/10, 5, this.height/10);
		this.repaint();
	}
	
	public BufferedImage getImage()
	{
		return panelImage;
	}
	
	public ArrayList<Integer> getNotesOnScreen(){
		return notesOnScreen;
	}
	
	public void updateNotes(){
		
		for (int i = 0; i < notesOnScreen.size(); i++){
			
			notesOnScreen.set(i, notesOnScreen.get(i) + pixPerUpdate);
			Graphics g = panelImage.getGraphics();
			g.setColor(Color.BLUE);
			g.fillRect(notesOnScreen.get(i), this.height/10, 10, this.height/10);
			
			g.setColor(Color.RED);
			g.fillRect((notesOnScreen.get(i) - this.width/2) - this.width/2, this.height/10, 10, this.height/10);
			//I think that's it...
		}
		
		
	}
	
	
	
	
	
}
