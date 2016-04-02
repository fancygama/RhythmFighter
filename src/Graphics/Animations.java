package Graphics;

import java.awt.*;
import java.awt.image.*;
import java.lang.Math;

public class Animations {

	public static void p1Punch(GamePanel panel, double stage)
	{
		BufferedImage b = panel.getImage();
		Graphics2D g = (Graphics2D) b.getGraphics();
		panel.reset(g);
		g.setStroke(new BasicStroke(5));
		g.setColor(Color.BLACK);
		if (stage <= 1/3 * Math.PI) stage *= 5; // Does the extension 3x as fast
		else if (stage <= 1.5*Math.PI) stage = 2*Math.PI; //So it can pause here
		else  stage = (2*Math.PI - stage); // And then go backs slowly
		stage = (.75*Math.PI-.125*stage);
		double length = 150;
		double xSpeed = 200;
		int xOff = 300;
		int yOff = 400;
		int x2 = (int)(xOff+xSpeed*Math.abs(stage-Math.PI));
		int y2 = (int)(yOff);
		g.drawLine(x2-(int)(length/1.5), y2, x2, y2);
		int x3 = (int)(length*Math.sin(stage)+xOff+xSpeed*Math.abs(stage-Math.PI));
		int y3 = (int)(length*Math.cos(stage)+yOff);
		g.drawLine(x2,y2,x3,y3);
		g.fillOval(x3-15, y3-10, 30, 20);
		panel.repaint();
	}

}
