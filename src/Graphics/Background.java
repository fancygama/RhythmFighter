package Graphics;

import java.awt.Graphics;
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
	
	@Override
	public void paintComponent(Graphics g){
		
		g.drawImage(backgroundImg, 0, 0, null);
	}
	
	
}
