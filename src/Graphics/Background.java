package Graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Background extends JPanel{

	private BufferedImage backgroundImg;
	private int currBackground;
	private int width;
	private int height;
	public Background(int width, int height){
		this.width = width;
		this.height = height;
		
		backgroundImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	public void setBackground(){
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		currBackground = rand.nextInt(3);
		try {
			BufferedImage originalImage = ImageIO.read(new File("src/resources/background" + currBackground + ".png"));
			backgroundImg.createGraphics().drawImage(originalImage, 0, 0, this.getWidth(), this.getHeight(), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setBackgroundResult(){
		try {
			BufferedImage originalImage = ImageIO.read(new File("src/resources/Results.png"));
			backgroundImg.createGraphics().drawImage(originalImage, 0, 0, this.getWidth(), this.getHeight(), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		
		g.drawImage(backgroundImg, 0, 0, null);
	}
	
	public void drawNoteLanes(){
		Graphics g = backgroundImg.getGraphics();
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
	
	public void displayResults(int p1Score, int p2Score){
		
		Graphics g = backgroundImg.getGraphics();
		String p1 = "Score: " + p1Score;
		String p2 = "Score: " + p2Score;
		g.setFont(new Font("Courier", Font.PLAIN, 50));
		g.setColor(Color.black);
		g.drawString(p1, width/8, height/2);
		g.drawString(p2, width - width/4 - width/8, height/2);
		repaint();
		
	}

	public void setBackgroundTut() {
		
		try {
			BufferedImage originalImage = ImageIO.read(new File("src/resources/tutorialbackground" + ".png"));
			backgroundImg.createGraphics().drawImage(originalImage, 0, 0, this.getWidth(), this.getHeight(), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
