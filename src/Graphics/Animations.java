package Graphics;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.lang.Math;

import javax.swing.JFrame;

public class Animations {
	static double ovrLength = 75 / (Math.sqrt(1902*1902+1033*1033));
	static double ovrXOff = 825/1902.0;
	static double ovrYOff = 600/1033.0;
	public static void punch(GamePanel panel, double stage, boolean p1, boolean curTurn, Color color)
	{
		boolean combo = (color!=color.white && curTurn);
		BufferedImage b = panel.getImage();
		double length = ovrLength * (Math.sqrt(b.getHeight()*b.getHeight()+b.getWidth()*b.getWidth()));
		double xOff = b.getWidth() * ovrXOff;
		double yOff = b.getHeight() * ovrYOff;
		Graphics2D g = (Graphics2D) b.getGraphics();
		g.setStroke(new BasicStroke(3));
		if (combo) g.setStroke(new BasicStroke(6));
		if (combo) g.setColor(color);
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
		xCoords[8] = (int)(xCoords[1] - .25 *length - .75 * length*Math.abs(stage-.75*Math.PI));
		yCoords[8] = (int)(yCoords[1] + 1.25* length);
		xCoords[9] = (int)(xCoords[8] - .25 * length - .25 * length*Math.abs(stage-.75*Math.PI));
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
		xCoords[6] = (int)(xCoords[4]+.5*Math.sqrt(2.0)*length - .25* length*Math.abs(stage-.75*Math.PI) );
		yCoords[6] = (int)(yCoords[4]-.25*Math.sqrt(2.0)*length  +  length*Math.abs(stage-.75*Math.PI) );
		xCoords[7] = (int)(xCoords[6] + .5*Math.sqrt(2.0)* length);
		yCoords[7] = (int)(yCoords[6] - .5*Math.sqrt(2.0)*length);
		if (combo)
		{
			xCoords[14] = (int)(xCoords[5]-.5*length);
			yCoords[14] = (int)(yCoords[5]-.5*length);
			xCoords[15] = (int)(xCoords[14]);
			yCoords[15] = (int)(yCoords[14]-2*length);
			xCoords[16] = (int)(xCoords[15] - .25* length);
			yCoords[16] = (int)(yCoords[15] + .75 * length);
			xCoords[17] = (int)(xCoords[16] );
			yCoords[17] = (int)(yCoords[16] + length);
			xCoords[18] = (int)(xCoords[17] + .125* length);
			yCoords[18] = (int)(yCoords[17] + .2 * length);
			xCoords[19] = (int)(xCoords[18] - .1* length);
			yCoords[19] = (int)(yCoords[18] + .2 * length);
		}
		int powDiam = 0;
		g.setFont(new Font("comic", Font.PLAIN, 0));
		if (!curTurn)
		{
			if (tempStage >= .4 * Math.PI)
			{
				double curLength =  length * (tempStage-.4*Math.PI);
				if (color != color.white) curLength *= 2;
				xCoords[4] -= .25* curLength;
				xCoords[5] -= .4* curLength;
				xCoords[2] -=  .4*curLength;
				xCoords[3] -=  .5 *curLength;
				xCoords[6] -=  .4 *curLength;
				xCoords[7] -= .5* curLength;
				yCoords[2] -=  .125*curLength;
				yCoords[3] -=  .25 *curLength;
				yCoords[6] -=  .125 *curLength;
				yCoords[7] -= .25* curLength;
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
		double boomWidth = length+length*Math.abs(.75*Math.PI-stage);
		g.fillOval((int)(xCoords[3]-(.15*boomWidth)), (int)(yCoords[3]-(.1*boomWidth)), (int)(.3*boomWidth), (int)(.2*boomWidth)); // draws the left fist
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
		if (color!=color.white)
		{
			for (int i = 14; i <= 18; i++)
			{
				g.drawLine(xCoords[i], yCoords[i], xCoords[i+1], yCoords[i+1]);
			}
		}
//		if (!curTurn)
//		{
//			g.drawOval((int)(xCoords[1]-(.5*powDiam)), (int)(yCoords[1]-.5*.5*powDiam - 4 * length), (int)(powDiam), (int)(.5*powDiam));
//			g.drawChars("POW!".toCharArray(), 0,4,(int)(xCoords[1]-.5*length),(int)(yCoords[1]-4 * length));
//		}
		panel.repaint();
	}
	
	public static void kick(GamePanel panel, double stage, boolean p1, boolean curTurn, Color color)
	{
		boolean combo = (color!=color.white && curTurn);
		BufferedImage b = panel.getImage();
		double length = ovrLength * (Math.sqrt(b.getHeight()*b.getHeight()+b.getWidth()*b.getWidth()));
		double xOff = b.getWidth() * ovrXOff;
		double yOff = b.getHeight() * ovrYOff;
		Graphics2D g = (Graphics2D) b.getGraphics();
		g.setStroke(new BasicStroke(3));
		if (combo) g.setStroke(new BasicStroke(6));
		if (combo) g.setColor(color);
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
		xCoords[3] = (int)(xCoords[2]+length);
		yCoords[3] = (int)(yCoords[2]-length);
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
		xCoords[11] = (int)(xCoords[2] - .75* length + .25 * length*Math.abs(stage-.75*Math.PI));
		yCoords[11] = (int)(yCoords[1] +  1.25 * length - 1.5 * length*Math.abs(stage-.75*Math.PI));
		xCoords[12] = (int)(xCoords[11]+ 1.25 * length*Math.abs(stage-.75*Math.PI));
		yCoords[12] = (int)(yCoords[11] +  1.25* length);
		xCoords[13] = (int)(xCoords[12] + .25 * length);
		yCoords[13] = (int)(yCoords[12]- .25 * length*Math.abs(stage-.75*Math.PI));
		xCoords[5] = xCoords[4];
		yCoords[5] = (int)(yCoords[4]-.5*length);
		xCoords[6] = (int)(xCoords[4]+.5*Math.sqrt(2.0)*length);
		yCoords[6] = (int)(yCoords[4]-.25*Math.sqrt(2.0)*length);
		xCoords[7] = (int)(xCoords[6] + .5*Math.sqrt(2.0)* length);
		yCoords[7] = (int)(yCoords[6] - .5*Math.sqrt(2.0)*length);
		xCoords[2] -= .5*length*Math.abs(stage-Math.PI);
		xCoords[3] -= .75*length*Math.abs(stage-Math.PI);
		xCoords[6] -= .25*length*Math.abs(stage-Math.PI);
		xCoords[7] -= .5*length*Math.abs(stage-Math.PI);
		if (combo)
		{
			xCoords[14] = (int)(xCoords[5]-.5*length);
			yCoords[14] = (int)(yCoords[5]-.5*length);
			xCoords[15] = (int)(xCoords[14]);
			yCoords[15] = (int)(yCoords[14]-2*length);
			xCoords[16] = (int)(xCoords[15] - .25* length);
			yCoords[16] = (int)(yCoords[15] + .75 * length);
			xCoords[17] = (int)(xCoords[16] );
			yCoords[17] = (int)(yCoords[16] + length);
			xCoords[18] = (int)(xCoords[17] + .125* length);
			yCoords[18] = (int)(yCoords[17] + .2 * length);
			xCoords[19] = (int)(xCoords[18] - .1* length);
			yCoords[19] = (int)(yCoords[18] + .2 * length);
		}
		int powDiam = 0;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 0));
		if (!curTurn)
		{
			if (tempStage >= .5*Math.PI)
			{
				double curLength = length * (tempStage-.5*Math.PI);
				if (color != color.white) curLength *= 2;
				xCoords[4] += .25* curLength;
				xCoords[5] += .4* curLength;
				yCoords[4] += .1* curLength;
				yCoords[5] += .15* curLength;
				xCoords[2] += .1 * curLength;
				xCoords[3] += .1 * curLength;
				xCoords[6] += .15 * curLength;
				xCoords[7] += .15 * curLength;
				yCoords[2] += .15 * curLength;
				yCoords[3] += .15 * curLength;
				yCoords[6] += .25 * curLength;
				yCoords[7] += .25 * curLength;
				xCoords[11] -= .125 * curLength;
				xCoords[12] -= .125 * curLength;
				xCoords[13] -= .125 * curLength;
				powDiam += curLength;
				g.setFont(new Font("TimesRoman", Font.PLAIN, (int)(curLength/10)));
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
		if (color!=color.white)
		{
			for (int i = 14; i <= 18; i++)
			{
				g.drawLine(xCoords[i], yCoords[i], xCoords[i+1], yCoords[i+1]);
			}
		}
//		if (!curTurn)
//		{
//			g.drawOval((int)(xCoords[1]-(.5*powDiam)), (int)(yCoords[1]-.5*.5*powDiam - 4 * length), (int)(powDiam), (int)(.5*powDiam));
//			g.drawChars("WHAP!".toCharArray(), 0,5,(int)(xCoords[1]-.5*length),(int)(yCoords[1]-4 * length));
//		}
		panel.repaint();
	}
	
	public static void punchBlock(GamePanel panel, double stage, boolean p1, boolean curTurn, Color color)
	{
		BufferedImage b = panel.getImage();
		double length = ovrLength * (Math.sqrt(b.getHeight()*b.getHeight()+b.getWidth()*b.getWidth()));
		double xOff = b.getWidth() * ovrXOff;
		double yOff = b.getHeight() * ovrYOff;
		Graphics2D g = (Graphics2D) b.getGraphics();
		g.setStroke(new BasicStroke(3));
		g.setColor(color);
		double tempStage = stage;
		if (stage <= 1/3 * Math.PI) stage *= 5; // Does the extension 3x as fast
		else if (stage <= 1*Math.PI) stage = 2*Math.PI; //So it can pause here
		else  stage = (2*Math.PI - stage); // And then go back slowly
		if (!curTurn) stage = 0;
		stage = (.75*Math.PI-.125*stage);
		int[] xCoords = new int[20];
		int[] yCoords = new int[20];
		xCoords[2] = (int)(xOff+1.5*length*Math.abs(stage-Math.PI)-1.5*length*Math.abs(stage-.75*Math.PI));
		yCoords[2] = (int)(yOff);
		xCoords[3] = (int)(.8*length*Math.sin(stage)+xCoords[2]);
		yCoords[3] = (int)(length*Math.cos(stage)+yCoords[2]);
		xCoords[4] = (int)(xCoords[2]-length);
		yCoords[4] = yCoords[2];
		xCoords[1] = xCoords[4];
		yCoords[1] = (int)(yCoords[4]+1.5*length);
		xCoords[8] = (int)(xCoords[1] - .25 *length - .75 * length*Math.abs(stage-.75*Math.PI));
		yCoords[8] = (int)(yCoords[1] + 1.25* length);
		xCoords[9] = (int)(xCoords[8] - .25 * length - .25 * length*Math.abs(stage-.75*Math.PI));
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
		xCoords[6] = (int)(xCoords[4]+.5*Math.sqrt(2.0)*length - .25* length*Math.abs(stage-.75*Math.PI)-.5*length*Math.abs(stage-.75*Math.PI) );
		yCoords[6] = (int)(yCoords[4]-.25*Math.sqrt(2.0)*length  +  length*Math.abs(stage-.75*Math.PI) );
		xCoords[7] = (int)(xCoords[6] + .5*Math.sqrt(2.0)* length);
		yCoords[7] = (int)(yCoords[6] - .5*Math.sqrt(2.0)*length);
		int powDiam = 0;
		g.setFont(new Font("comic", Font.PLAIN, 0));
		if (!curTurn)
		{
			if (tempStage >= Math.PI)
			{
				double curLength = length * (tempStage-.5*Math.PI);
				xCoords[7] -= .1* curLength;
				yCoords[7] -= .1 * curLength;
				xCoords[3] -= .2* curLength;
				yCoords[3] -= .2 * curLength;
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
//		if (!curTurn)
//		{
//			g.drawOval((int)(xCoords[1]-(.5*powDiam)), (int)(yCoords[1]-.5*.5*powDiam - 4 * length), (int)(powDiam), (int)(.5*powDiam));
//			g.drawChars("BLOCKED!".toCharArray(), 0,7,(int)(xCoords[1]-.5*length),(int)(yCoords[1]-4 * length));
//		}
		panel.repaint();
	}
	public static void kickBlock(GamePanel panel, double stage, boolean p1, boolean curTurn, Color color)
	{
		BufferedImage b = panel.getImage();
		double length = ovrLength * (Math.sqrt(b.getHeight()*b.getHeight()+b.getWidth()*b.getWidth()));
		double xOff = b.getWidth() * ovrXOff;
		double yOff = b.getHeight() * ovrYOff;
		Graphics2D g = (Graphics2D) b.getGraphics();
		g.setStroke(new BasicStroke(3));
		g.setColor(color);
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
		xCoords[3] = (int)(xCoords[2]+length);
		yCoords[3] = (int)(yCoords[2]-length);
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
		xCoords[11] = (int)(xCoords[2] - .75* length + .25 * length*Math.abs(stage-.75*Math.PI));
		yCoords[11] = (int)(yCoords[1] +  1.25 * length - 1.5 * length*Math.abs(stage-.75*Math.PI));
		xCoords[12] = (int)(xCoords[11]+ 1.25 * length*Math.abs(stage-.75*Math.PI));
		yCoords[12] = (int)(yCoords[11] +  1.25* length);
		xCoords[13] = (int)(xCoords[12] + .25 * length);
		yCoords[13] = (int)(yCoords[12]- .25 * length*Math.abs(stage-.75*Math.PI));
		xCoords[5] = xCoords[4];
		yCoords[5] = (int)(yCoords[4]-.5*length);
		xCoords[6] = (int)(xCoords[4]+.5*Math.sqrt(2.0)*length);
		yCoords[6] = (int)(yCoords[4]-.25*Math.sqrt(2.0)*length);
		xCoords[7] = (int)(xCoords[6] + .5*Math.sqrt(2.0)* length);
		yCoords[7] = (int)(yCoords[6] - .5*Math.sqrt(2.0)*length);
		xCoords[2] -= .5*length*Math.abs(stage-Math.PI);
		xCoords[3] -= .75*length*Math.abs(stage-Math.PI);
		xCoords[6] -= .25*length*Math.abs(stage-Math.PI);
		xCoords[7] -= .5*length*Math.abs(stage-Math.PI);
		int powDiam = 0;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 0));
		if (!curTurn)
		{
				double curLength = length * (tempStage);
				for (int i = 1; i <= 13; i++)
				{
					yCoords[i] -=  .75 * curLength;
				}
				xCoords[8] += .25 * curLength;
				xCoords[9] += .3 * curLength;
				xCoords[10] += .3 * curLength;
				xCoords[11] -= .25 * curLength;
				xCoords[12] -= .4 * curLength;
				xCoords[13] -= .4 * curLength;
				powDiam += curLength;
				g.setFont(new Font("TimesRoman", Font.PLAIN, (int)(curLength/10)));
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
//		if (!curTurn)
//		{
//			g.drawOval((int)(xCoords[1]-(.5*powDiam)), (int)(yCoords[1]-.5*.5*powDiam - 4 * length), (int)(powDiam), (int)(.5*powDiam));
//			g.drawChars("DODGED!".toCharArray(), 0,7,(int)(xCoords[1]-.5*length),(int)(yCoords[1]-4 * length));
//		}
		panel.repaint();
	}
	

}
