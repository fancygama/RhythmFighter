package Graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import Core.Main;

public class GamePanel extends JPanel {

	private BufferedImage panelImage;
	private int width, height;
	private ArrayList<Long> notesOnScreen;
	private int pixPerUpdate;
	private Main main;
	
	private int currBackground;
	
	public GamePanel(int width, int height, Main main){
		
		this.setBounds(0,0,width,height);	//this just forces the panel to be at this place.
		this.width = width;
		this.height = height;
		notesOnScreen = new ArrayList<Long>();
		this.main = main;
		init();
	}
	
	public void init(){
		
		this.setOpaque(true);
		panelImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);	//init the bufferedimage
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
		panelImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		//panelImage.getGraphics().fillRect(0, 0, width, height);
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
	
	public void flashBackground(){
		Graphics g = panelImage.getGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, width, height);
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
			//System.out.print("NotePos: " + notePos);
			
			int xVal = (int) (((double)(songPos - (notePos - 1000))/1000.0) * (width/2));
			//System.out.println(" Percent of time passed: " + ((double)(songPos - (notePos - 1000))/1000.0));
			Graphics g = panelImage.getGraphics();
			g.setColor(Color.BLUE);
			g.fillRect(xVal, this.height/10, 10, this.height/10);
			//System.out.println("Beat Now at X: " + xVal);
			g.setColor(Color.RED);
			g.fillRect(width - (xVal), this.height/10, 10, this.height/10);
			
			//notesOnScreen.set(i, notesOnScreen.get(i) + pixPerUpdate);
			//I think that's it...
		}
		
		
	}
	
	public void drawScores(){
		Graphics g = panelImage.getGraphics();
		String p1Score = "" + main.player1.getScore();
		String p2Score = "" + main.player2.getScore();
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
		g.drawString(p1Score, width/10, 3*height/10 + height/20);
		g.drawString(p2Score, width - width/10, 3*height/10 + height/20);
	}
	
	public void displayMenu(){
		BufferedImage startButtonImage = new BufferedImage(this.getWidth()/4, this.getHeight()/6, BufferedImage.TYPE_INT_ARGB);
		BufferedImage tutorialButtonImage = new BufferedImage(this.getWidth()/4, this.getHeight()/6, BufferedImage.TYPE_INT_ARGB);
		BufferedImage startButtonDepressed = new BufferedImage(this.getWidth()/4, this.getHeight()/6, BufferedImage.TYPE_INT_ARGB);
		BufferedImage tutorialButtonDepressed = new BufferedImage(this.getWidth()/4, this.getHeight()/6, BufferedImage.TYPE_INT_ARGB);
		BufferedImage quitButtonImage = new BufferedImage(this.getWidth()/4, this.getHeight()/6, BufferedImage.TYPE_INT_ARGB);
		BufferedImage quitButtonDepressed = new BufferedImage(this.getWidth()/4, this.getHeight()/6, BufferedImage.TYPE_INT_ARGB);

		try {
			BufferedImage originalImage = ImageIO.read(new File("src/resources/menu.png"));
			panelImage.createGraphics().drawImage(originalImage, 0, 0, width, height, null);
			
			startButtonImage.createGraphics().drawImage(ImageIO.read(new File("src/resources/start.png")), 0, 0, this.getWidth()/4, this.getHeight()/6, null);
			tutorialButtonImage.createGraphics().drawImage(ImageIO.read(new File("src/resources/tutorial.png")), 0, 0, this.getWidth()/4, this.getHeight()/6, null);
			startButtonDepressed.createGraphics().drawImage(ImageIO.read(new File("src/resources/startpush.png")), 0, 0, this.getWidth()/4, this.getHeight()/6, null);
			tutorialButtonDepressed.createGraphics().drawImage(ImageIO.read(new File("src/resources/tutorialpush.png")), 0, 0, this.getWidth()/4, this.getHeight()/6, null);
			quitButtonImage.createGraphics().drawImage(ImageIO.read(new File("src/resources/quit.png")), 0, 0, this.getWidth()/4, this.getHeight()/6, null);
			quitButtonDepressed.createGraphics().drawImage(ImageIO.read(new File("src/resources/quitpressed.png")), 0, 0, this.getWidth()/4, this.getHeight()/6, null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Menu Background failed to load");
		}
		
		JButton startButton = new JButton();
		this.setLayout(null);
		startButton.setOpaque(false);
		startButton.setFocusable(false);
		startButton.setContentAreaFilled(false);
		startButton.setBorderPainted(false);
		startButton.setBounds(width/2 - width/8, height/2, width/4, height/6);
		startButton.setIcon(new ImageIcon(startButtonImage));
		startButton.setPressedIcon(new ImageIcon(startButtonDepressed));
		startButton.setOpaque(false);
		
		JButton quitButton = new JButton();
		quitButton.setOpaque(false);
		quitButton.setFocusable(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setBorderPainted(false);
		quitButton.setBounds(width/2 - width/8, height/2 + 2*height/6, width/4, height/6);
		quitButton.setIcon(new ImageIcon(quitButtonImage));
		quitButton.setPressedIcon(new ImageIcon(quitButtonDepressed));
		quitButton.setOpaque(false);
		
		JButton tutorialButton = new JButton();
		this.setLayout(null);
		tutorialButton.setOpaque(false);
		tutorialButton.setFocusable(false);
		tutorialButton.setContentAreaFilled(false);
		tutorialButton.setBorderPainted(false);
		tutorialButton.setBounds(width/2 - width/8, height/2 + height/6, width/4, height/6);
		tutorialButton.setIcon(new ImageIcon(tutorialButtonImage));
		tutorialButton.setPressedIcon(new ImageIcon(tutorialButtonDepressed));
		tutorialButton.setOpaque(false);
		
		startButton.addActionListener(new ButtonListener(0));
		tutorialButton.addActionListener(new ButtonListener(-1));
		quitButton.addActionListener(new ButtonListener(1));
		
		this.add(startButton);
		this.add(tutorialButton);
		this.add(quitButton);
	}
	
	public void displaySongs(){
		System.out.println("Hork");
		BufferedImage theforceImage = new BufferedImage(this.getWidth()/4, this.getHeight()/6, BufferedImage.TYPE_INT_ARGB);
		BufferedImage cherrypepsiImage = new BufferedImage(this.getWidth()/4, this.getHeight()/6, BufferedImage.TYPE_INT_ARGB);
		BufferedImage theforceDepressed = new BufferedImage(this.getWidth()/4, this.getHeight()/6, BufferedImage.TYPE_INT_ARGB);
		BufferedImage cherrypepsiDepressed = new BufferedImage(this.getWidth()/4, this.getHeight()/6, BufferedImage.TYPE_INT_ARGB);

		try {
			BufferedImage originalImage = ImageIO.read(new File("src/resources/songs.png"));
			panelImage.createGraphics().drawImage(originalImage, 0, 0, width, height, null);
			
			theforceImage.createGraphics().drawImage(ImageIO.read(new File("src/resources/theforcebutton.png")), 0, 0, this.getWidth()/4, this.getHeight()/6, null);
			cherrypepsiImage.createGraphics().drawImage(ImageIO.read(new File("src/resources/cherrypepsibutton.png")), 0, 0, this.getWidth()/4, this.getHeight()/6, null);
			theforceDepressed.createGraphics().drawImage(ImageIO.read(new File("src/resources/theforcebuttonpressed.png")), 0, 0, this.getWidth()/4, this.getHeight()/6, null);
			cherrypepsiDepressed.createGraphics().drawImage(ImageIO.read(new File("src/resources/cherrypepsibuttonpressed.png")), 0, 0, this.getWidth()/4, this.getHeight()/6, null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Menu Background failed to load");
		}
		
		JButton theforceButton = new JButton();
		this.setLayout(null);
		theforceButton.setOpaque(false);
		theforceButton.setFocusable(false);
		theforceButton.setContentAreaFilled(false);
		theforceButton.setBorderPainted(false);
		theforceButton.setBounds(width/2 - width/8, height/3, width/4, height/6);
		theforceButton.setIcon(new ImageIcon(theforceImage));
		theforceButton.setPressedIcon(new ImageIcon(theforceDepressed));
		theforceButton.setOpaque(false);
		theforceButton.addActionListener(new SongSelectListener(1));
		
		JButton cherrypepsiButton = new JButton();
		this.setLayout(null);
		cherrypepsiButton.setOpaque(false);
		cherrypepsiButton.setFocusable(false);
		cherrypepsiButton.setContentAreaFilled(false);
		cherrypepsiButton.setBorderPainted(false);
		cherrypepsiButton.setBounds(width/2 - width/8, height/3 + height/6, width/4, height/6);
		cherrypepsiButton.setIcon(new ImageIcon(cherrypepsiImage));
		cherrypepsiButton.setPressedIcon(new ImageIcon(cherrypepsiDepressed));
		cherrypepsiButton.setOpaque(false);
		cherrypepsiButton.addActionListener(new SongSelectListener(2));
		
		this.add(theforceButton);
		this.add(cherrypepsiButton);
	}
	
	public void addBackground(){	//also resets the frame
		Random rand = new Random();
		currBackground = rand.nextInt(2);
		try {
			BufferedImage originalImage = ImageIO.read(new File("src/resources/background" + currBackground + ".png"));
			panelImage.createGraphics().drawImage(originalImage, 0, 0, width, height, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void drawFlash(int frame){
		if (frame == -1){
			return;
		}
		try {
			BufferedImage originalImage = ImageIO.read(new File("src/resources/flash/flash" + frame + ".png"));
			panelImage.createGraphics().drawImage(originalImage, width/2 - width/40, height/10, width/20, height/10, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public class ButtonListener implements ActionListener{
		
		private int choice;
		
		String soundName = "src/resources/buttonpress.wav";    
		AudioInputStream audioInputStream;
		Clip clip;
		
		public ButtonListener(int i){
			choice = i;
			
			
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
				clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clip.start();
			if (choice == -1){
				//start game
				main.enterTutPhase();
			} else if (choice == 0){
				main.enterSongSelect();
				return;
			} else if (choice == 1){
				main.quit();
			} else if (choice == 2){
				main.enterGamePhase("cherrypepsi");
			}
			
		}
		
		
		
		
	}
	
	public class SongSelectListener implements ActionListener{

		private int choice;
		
		String soundName = "src/resources/buttonpress.wav";    
		AudioInputStream audioInputStream;
		Clip clip;
		
		public SongSelectListener(int choice){
			this.choice = choice;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
				clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clip.start();
			if (choice == 1){
				System.out.println("TheForce");
				main.enterGamePhase("theforce");
			} else if (choice == 2){
				System.out.println("CherryPepsi");
				main.enterGamePhase("cherrypepsi");
			} else if (choice == 3)
				System.out.println("Clip");
				main.enterGamePhase("clip");
				
		}
		
	}

	
	public void drawGreat(int greatFrame, int playerFlag) {
		// TODO Auto-generated method stub
		try {
			BufferedImage originalImage;
			if (playerFlag > 2){
				originalImage = ImageIO.read(new File("src/resources/combo/combo" + greatFrame + ".png"));
			} else {
				originalImage = ImageIO.read(new File("src/resources/great/great" + greatFrame + ".png"));
			}
			if (playerFlag % 2 != 0){
				panelImage.createGraphics().drawImage(originalImage, width/4, height/4, width/5, width/10, null);
			} else {
				panelImage.createGraphics().drawImage(originalImage, width - width/2, height/4, width/5, width/10, null);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
