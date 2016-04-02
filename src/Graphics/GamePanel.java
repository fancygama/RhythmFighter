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
	private ArrayList<Long> notesOnScreen;
	private int pixPerUpdate;
	
	public GamePanel(int width, int height){
		
		this.setBounds(0,0,width,height);	//this just forces the panel to be at this place.
		this.width = width;
		this.height = height;
		notesOnScreen = new ArrayList<Long>();
		init();
	}
	
	public void init(){
		
		this.setOpaque(true);
		panelImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);	//init the bufferedimage
		System.out.println(width/2 - width/20);
		pixPerUpdate = (int) (((double)(width/2 - width/20) / 1000.0) * 10);
		
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		g.drawImage(panelImage, 0, 0, null);	//this is all we ever need in this method
		
	}
	
	public void reset(){
		//Graphics g = panelImage.getGraphics();
		//Color temp = g.getColor();
		//g.setColor(Color.white);
		//g.fillRect(0, 0, width, height);
		//g.setColor(temp);
		panelImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		panelImage.getGraphics().fillRect(0, 0, width, height);
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
	
	public ArrayList<Long> getNotesOnScreen(){
		return notesOnScreen;
	}
	
	public void updateNotes(long songPos){
		
		for (int i = 0; i < notesOnScreen.size(); i++){
			
			long notePos = notesOnScreen.get(i);
			System.out.println("NotePos: " + notePos);
			int pixelsToTravel = (width/2 - width/20);
			
			Graphics g = panelImage.getGraphics();
			g.setColor(Color.BLUE);
			g.fillRect((int)(pixelsToTravel/(double)(notePos/(double)(notePos - songPos))), this.height/10, 10, this.height/10);
			System.out.println("Beat Now at X: " + (int)(pixelsToTravel/(double)(notePos/(double)(notePos - songPos))));
			g.setColor(Color.RED);
			g.fillRect(this.width - (int)(pixelsToTravel/(double)(notePos/(double)(notePos - songPos))), this.height/10, 10, this.height/10);
			
			//notesOnScreen.set(i, notesOnScreen.get(i) + pixPerUpdate);
			//I think that's it...
		}
		
		
	}
	
	
	
	
	
}
