package Core;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.*;

import Graphics.GamePanel;

public class Main {
	
	public static GamePanel panel;
	public static JFrame frame;
	public static int frameWidth;
	public static int frameHeight;
	
	
	public static void startUp(){
		
		//get the resolution of the main monitor
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frameWidth = gd.getDisplayMode().getWidth();
		frameHeight = gd.getDisplayMode().getHeight();
		
		//set up the game's frame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(frameWidth,frameHeight));
		//add in the panel to display everything
		GamePanel panel = new GamePanel(frameWidth, frameHeight);
		frame.add(panel);
		//load it up!
		frame.setVisible(true);
		panel.drawALine();
		frame.validate();
		frame.pack();
		
		
	}

	public static void main(String[] args) {
		//startup code
		startUp();
		
		
	}

}
