package Graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	
	
	public GamePanel(int width, int height){
		
		this.setPreferredSize(new Dimension(width, height));
		init();
	}
	
	public void init(){
		
		this.setOpaque(true);
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
	}
	
	
	
	
	
}
