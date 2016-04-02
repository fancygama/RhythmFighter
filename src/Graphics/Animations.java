package Graphics;

import java.awt.*;
import java.awt.image.*;
import java.lang.Math;

import javax.swing.JFrame;

public class Animations {
	static double length = 75;
	static double xSpeed = 200;
	static int xOff = 825;
	static int yOff = 600;
	public static void punch(GamePanel panel, double stage, boolean p1, boolean curTurn)
	{
		BufferedImage b = panel.getImage();
		Graphics2D g = (Graphics2D) b.getGraphics();
		g.setStroke(new BasicStroke(5));
		g.setColor(Color.WHITE);
		//stage = 1.5*Math.PI;
		double tempStage = stage;
		if (stage <= 1/3 * Math.PI) stage *= 5; // Does the extension 3x as fast
		else if (stage <= 1*Math.PI) stage = 2*Math.PI; //So it can pause here
		else  stage = (2*Math.PI - stage); // And then go back slowly
		if (!curTurn) stage = 0;
		stage = (.75*Math.PI-.125*stage);
		int[] xCoords = new int[20];
		int[] yCoords = new int[20];
		xCoords[2] = (int)(xOff+1.5*length*Math.abs(stage-Math.PI));
		yCoords[2] = (int)(yOff);
		xCoords[3] = (int)(length*Math.sin(stage)+xCoords[2]);
		yCoords[3] = (int)(length*Math.cos(stage)+yCoords[2]);
		xCoords[4] = (int)(xCoords[2]-length);
		yCoords[4] = yCoords[2];
		xCoords[1] = xCoords[4];
		yCoords[1] = (int)(yCoords[4]+1.5*length);
		xCoords[8] = (int)(xCoords[1] - .5 * length*Math.abs(stage-Math.PI));
		yCoords[8] = (int)(yCoords[1] + 1.25* length);
		xCoords[9] = (int)(xCoords[8] - .25 * length);
		yCoords[9] = (int)(yCoords[8] +  1.25 *  length);
		xCoords[10] = (int)(xCoords[9] + .25 * length);
		yCoords[10] = yCoords[9];
		xCoords[11] = (int)(xCoords[2] - .75* length);
		yCoords[11] = (int)(yCoords[1] +  1.25 * length);
		xCoords[12] = xCoords[11];
		yCoords[12] = (int)(yCoords[11] +  1.25* length);
		xCoords[13] = (int)(xCoords[12] + .25 * length);
		yCoords[13] = yCoords[12];
		xCoords[5] = xCoords[4];
		yCoords[5] = (int)(yCoords[4]-.5*length);
		xCoords[6] = (int)(xCoords[4]+.5*Math.sqrt(2.0)*length);
		yCoords[6] = (int)(yCoords[4]-.25*Math.sqrt(2.0)*length);
		xCoords[7] = (int)(xCoords[6] + .5*Math.sqrt(2.0)* length);
		yCoords[7] = (int)(yCoords[6] - .5*Math.sqrt(2.0)*length);
		int powDiam = 0;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 0));
		if (!curTurn)
		{
			if (tempStage >= Math.PI)
			{
				double curLength = length * (tempStage-Math.PI
			);
				xCoords[4] -= .25* curLength;
				xCoords[5] -= .4* curLength;
				xCoords[2] -=  .4*curLength;
				xCoords[3] -=  .5 *curLength;
				xCoords[6] -=  .4 *curLength;
				xCoords[7] -= .5* curLength;
				yCoords[2] -=  .15*curLength;
				yCoords[3] -=  .4 *curLength;
				yCoords[6] -=  .15 *curLength;
				yCoords[7] -= .4* curLength;
				powDiam += curLength;
				g.setFont(new Font("CourierNew", Font.PLAIN, (int)(curLength/10)));
				}
		}
		if (!curTurn)
			for (int i = 0; i < xCoords.length; i++)
			{
				if (xCoords[i] != 0) xCoords[i] = b.getWidth() - xCoords[i];
			}		
		if (!p1)
		{
			for (int i = 0; i < xCoords.length; i++)
			{
				if (xCoords[i] != 0) xCoords[i] = b.getWidth() - xCoords[i];
			}	
		}
		g.drawLine(xCoords[2],yCoords[2],xCoords[3],yCoords[3]); //draws right forearm
		g.fillOval((int)(xCoords[3]-(.15*length)), (int)(yCoords[3]-(.1*length)), (int)(.3*length), (int)(.2*length)); // draws the left fist
		g.drawLine(xCoords[2],yCoords[2], xCoords[4], yCoords[4]); //draws right upper arm
		g.drawLine(xCoords[1], yCoords[1], xCoords[4], yCoords[4]); // draws the torso
		g.drawLine(xCoords[4], yCoords[4], xCoords[5], yCoords[5]);
		g.drawOval((int)(xCoords[5]-(.5*length)), (int)(yCoords[5]-length), (int)(length), (int)(length)); //draws the head
		g.drawLine(xCoords[4], yCoords[4], xCoords[6], yCoords[6]); //draws left upper arm
		g.drawLine(xCoords[6], yCoords[6], xCoords[7], yCoords[7]); //draws right upper arm
		g.fillOval((int)(xCoords[7]-(.15*length)), (int)(yCoords[7]-(.1*length)), (int)(.3*length), (int)(.2*length)); // draws the right fist
		g.drawLine(xCoords[1], yCoords[1], xCoords[8], yCoords[8]);
		g.drawLine(xCoords[8], yCoords[8], xCoords[9], yCoords[9]);
		g.drawLine(xCoords[9], yCoords[9], xCoords[10], yCoords[10]);
		g.drawLine(xCoords[1], yCoords[1], xCoords[11], yCoords[11]);
		g.drawLine(xCoords[11], yCoords[11], xCoords[12], yCoords[12]);
		g.drawLine(xCoords[12], yCoords[12], xCoords[13], yCoords[13]);
		if (!curTurn)
		{
			g.drawOval((int)(xCoords[1]-(.5*powDiam)), (int)(yCoords[1]-.5*.5*powDiam - 4 * length), (int)(powDiam), (int)(.5*powDiam));
			g.drawChars("POW!".toCharArray(), 0,4,(int)(xCoords[1]-.5*length),(int)(yCoords[1]-4 * length));
		}
		panel.repaint();
	}
	
	public static void kick(GamePanel panel, double stage, boolean p1, boolean curTurn)
	{
		BufferedImage b = panel.getImage();
		Graphics2D g = (Graphics2D) b.getGraphics();
		g.setStroke(new BasicStroke(5));
		g.setColor(Color.BLACK);
		//stage = 1.5*Math.PI;
		double tempStage = stage;
		if (stage <= 1/3 * Math.PI) stage *= 5; // Does the extension 3x as fast
		else if (stage <= 1*Math.PI) stage = 2*Math.PI; //So it can pause here
		else  stage = (2*Math.PI - stage); // And then go back slowly
		if (!curTurn) stage = 0;
		stage = (.75*Math.PI-.125*stage);
		int[] xCoords = new int[20];
		int[] yCoords = new int[20];
		xCoords[2] = (int)(xOff+1.5*length*Math.abs(stage-Math.PI));
		yCoords[2] = (int)(yOff);
		xCoords[3] = (int)(length*Math.sin(stage)+xCoords[2]);
		yCoords[3] = (int)(length*Math.cos(stage)+yCoords[2]);
		xCoords[4] = (int)(xCoords[2]-length);
		yCoords[4] = yCoords[2];
		xCoords[1] = xCoords[4];
		yCoords[1] = (int)(yCoords[4]+1.5*length);
		xCoords[8] = (int)(xCoords[1] - .5 * length*Math.abs(stage-Math.PI));
		yCoords[8] = (int)(yCoords[1] + 1.25* length);
		xCoords[9] = (int)(xCoords[8] - .25 * length);
		yCoords[9] = (int)(yCoords[8] +  1.25 *  length);
		xCoords[10] = (int)(xCoords[9] + .25 * length);
		yCoords[10] = yCoords[9];
		xCoords[11] = (int)(xCoords[2] - .75* length);
		yCoords[11] = (int)(yCoords[1] +  1.25 * length);
		xCoords[12] = xCoords[11];
		yCoords[12] = (int)(yCoords[11] +  1.25* length);
		xCoords[13] = (int)(xCoords[12] + .25 * length);
		yCoords[13] = yCoords[12];
		xCoords[5] = xCoords[4];
		yCoords[5] = (int)(yCoords[4]-.5*length);
		xCoords[6] = (int)(xCoords[4]+.5*Math.sqrt(2.0)*length);
		yCoords[6] = (int)(yCoords[4]-.25*Math.sqrt(2.0)*length);
		xCoords[7] = (int)(xCoords[6] + .5*Math.sqrt(2.0)* length);
		yCoords[7] = (int)(yCoords[6] - .5*Math.sqrt(2.0)*length);
		int powDiam = 0;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 0));
		if (!curTurn)
		{
			if (tempStage >= Math.PI)
			{
				double curLength = length * (tempStage-Math.PI
			);
				xCoords[4] -= .25* curLength;
				xCoords[5] -= .4* curLength;
				xCoords[2] -=  .4*curLength;
				xCoords[3] -=  .5 *curLength;
				xCoords[6] -=  .4 *curLength;
				xCoords[7] -= .5* curLength;
				yCoords[2] -=  .15*curLength;
				yCoords[3] -=  .4 *curLength;
				yCoords[6] -=  .15 *curLength;
				yCoords[7] -= .4* curLength;
				powDiam += curLength;
				g.setFont(new Font("CourierNew", Font.PLAIN, (int)(curLength/10)));
				}
		}
		if (!curTurn)
			for (int i = 0; i < xCoords.length; i++)
			{
				if (xCoords[i] != 0) xCoords[i] = b.getWidth() - xCoords[i];
			}		
		if (!p1)
		{
			for (int i = 0; i < xCoords.length; i++)
			{
				if (xCoords[i] != 0) xCoords[i] = b.getWidth() - xCoords[i];
			}	
		}
		g.drawLine(xCoords[2],yCoords[2],xCoords[3],yCoords[3]); //draws right forearm
		g.fillOval((int)(xCoords[3]-(.15*length)), (int)(yCoords[3]-(.1*length)), (int)(.3*length), (int)(.2*length)); // draws the left fist
		g.drawLine(xCoords[2],yCoords[2], xCoords[4], yCoords[4]); //draws right upper arm
		g.drawLine(xCoords[1], yCoords[1], xCoords[4], yCoords[4]); // draws the torso
		g.drawLine(xCoords[4], yCoords[4], xCoords[5], yCoords[5]);
		g.drawOval((int)(xCoords[5]-(.5*length)), (int)(yCoords[5]-length), (int)(length), (int)(length)); //draws the head
		g.drawLine(xCoords[4], yCoords[4], xCoords[6], yCoords[6]); //draws left upper arm
		g.drawLine(xCoords[6], yCoords[6], xCoords[7], yCoords[7]); //draws right upper arm
		g.fillOval((int)(xCoords[7]-(.15*length)), (int)(yCoords[7]-(.1*length)), (int)(.3*length), (int)(.2*length)); // draws the right fist
		g.drawLine(xCoords[1], yCoords[1], xCoords[8], yCoords[8]);
		g.drawLine(xCoords[8], yCoords[8], xCoords[9], yCoords[9]);
		g.drawLine(xCoords[9], yCoords[9], xCoords[10], yCoords[10]);
		g.drawLine(xCoords[1], yCoords[1], xCoords[11], yCoords[11]);
		g.drawLine(xCoords[11], yCoords[11], xCoords[12], yCoords[12]);
		g.drawLine(xCoords[12], yCoords[12], xCoords[13], yCoords[13]);
		if (!curTurn)
		{
			g.drawOval((int)(xCoords[1]-(.5*powDiam)), (int)(yCoords[1]-.5*.5*powDiam - 4 * length), (int)(powDiam), (int)(.5*powDiam));
			g.drawChars("POW!".toCharArray(), 0,4,(int)(xCoords[1]-.5*length),(int)(yCoords[1]-4 * length));
		}
		panel.repaint();
	}
	
	public static void main(String[] args)
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int frameWidth = gd.getDisplayMode().getWidth();
		int frameHeight = gd.getDisplayMode().getHeight();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(frameWidth,frameHeight));
		//add in the panel to display everything
		GamePanel panel = new GamePanel(frameWidth, frameHeight);
		frame.add(panel);
		frame.validate();
		frame.setVisible(true);
		frame.pack();
		while (true)
		{
			int precision = 5;
			for (int i = 1; i <= precision ; i++)
			{
				double stage = 2 * Math.PI / precision * i;
				panel.reset();
				punch(panel,stage,false,true);
				punch(panel,stage,false,false);
				try {
					Thread.sleep(33);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			for (int i = 1; i <= precision ; i++)
			{
				double stage = 2 * Math.PI / precision * i;
				panel.reset();
				punch(panel,0,true,true);
				punch(panel,0,true,false);
				try {
					Thread.sleep(33);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}

}
