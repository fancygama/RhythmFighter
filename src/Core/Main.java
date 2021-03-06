package Core;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.*;

import Graphics.Anim;
import Graphics.Animations;
import Graphics.Background;
import Graphics.GamePanel;

public class Main extends Thread{

	//the game's display window stuff
	public GamePanel panel;	//the game panel
	public JFrame frame;	//the game frame
	public Background backgroundLayer;	//displays the background
	public int frameWidth;	//width of the frame
	public int frameHeight;	//height of the frame
	
	public static int LAGCOMP = 50;	//compensation for video lag. approx. 50 ms early.

	private SongPlayer menuMusic;	//the player that plays the menu music
	private RhythmTimer timer;	//the class that takes care of playing the game music.
	private long songPos;	//current position of the song in ms
	private String songName;	//name of the file that the song is contained in (no extension)
	
	//the classes containing the information of each player
	public Player player1;
	public Player player2;
	
	//booleans that indicate what each player can do and whether or not the game is over
	private boolean gameDone = false;	//whether or not the song chart is over
	public boolean atkWindow = false;	//not actually used anymore
	public boolean p1CanAtk = false;	//whether or not player1 can input commands
	public boolean p2CanAtk = false;	//whether or not player2 can input commands

	public long p1LastHitOffset;	//the amount that player1's last hit was off the mark in ms. default is >200
	public long p2LastHitOffset; //the amt player2's last hit was off the mark in ms. default is >200
	public int p1ComboFlag = 0;	//is 1 if the player has an unredeemed combo finisher
	public int p2ComboFlag = 0;	//same

	public ArrayList<Long> beatsInSong;	//a list of the millisecond positions of each note in the song
	public int currBeat = 0;	//the index of the next note to be played
	public int currBeatAdd = 0;	//the index of the next note to be added to the screen.
	public int lastBeatp1 = -1;	//the index of the last beat the player hit.
	public int lastBeatp2 = -1;	//the index of the last beat the player hit.

	private double curAnimProg = 0; // progress of current animation (from 0 to 2pi)
	private Anim curAnim = Anim.p1Punch; // current animation in progress (all are the same if curAnimProg = 0)
	public double speed = 12.5;
	private Color p1Color = Color.white;
	private Color p2Color = Color.white;

	public int tutorialMode = 0;
	private int flashFrame = -1;
	private int greatFrame = -1;
	private int gamePhase;
	private int lastPlayerToWin = 0;

	//used by InListener to establish what should happen when a key is hit.
	public void playerAttacked(int player, int move){
		if (player == 1){
			p1CanAtk = false;	//the player can no longer attack
			lastBeatp1 = currBeat;
			p1ComboFlag = player1.setLastMove(move);
			p1LastHitOffset = Math.abs(SongPlayer.notesInSong.get(currBeat) - timer.getSongPos());
		} else {
			p2CanAtk = false;
			lastBeatp2 = currBeat;
			p2ComboFlag = player2.setLastMove(move);
			p2LastHitOffset = Math.abs(SongPlayer.notesInSong.get(currBeat) - timer.getSongPos());
		}
	}

	public void quit(){
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
	public void initGame(){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frameWidth = gd.getDisplayMode().getWidth();
		frameHeight = gd.getDisplayMode().getHeight();
		
		//set up the game's frame
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setPreferredSize(new Dimension(frameWidth,frameHeight));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 	//goes fullscreen!
		frame.setUndecorated(true);
		panel = new GamePanel(frameWidth, frameHeight, this);
		panel.setFocusable(false);
		panel.setOpaque(false);
		
		backgroundLayer = new Background(frameWidth, frameHeight);
		backgroundLayer.setOpaque(true);
		backgroundLayer.setPreferredSize(new Dimension(frameWidth, frameHeight));
		backgroundLayer.setBounds(0,0,frameWidth,frameHeight);
		backgroundLayer.setFocusable(false);
		backgroundLayer.setLayout(null);
		frame.add(backgroundLayer);
		backgroundLayer.add(panel);
		
	}

	public void startUpMenu(){

		gamePhase = 0;
		menuMusic = new SongPlayer("Resources/sounds/menumusic.wav");
		menuMusic.getClip().loop(Clip.LOOP_CONTINUOUSLY);
		panel.displayMenu();
		
		panel.repaint();

		frame.validate();
		frame.setVisible(true);
		frame.pack();
	}
	public void enterSongSelect() {
		
		// TODO Auto-generated method stub
		gamePhase = 3;
	}

	public void startUpSongSelect(){

//		try {
//			Main.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		panel.removeAll();
		panel.reset();
		panel.displaySongs();
		panel.repaint();

		frame.validate();
	}

	public void enterTutPhase(){
		songName = "Tutorial";
		gamePhase = 2;
	}

	public void setSongName(String song){
		songName = song;
	}


	public void enterGamePhase(String song){
		songName = song;
		gamePhase = 1;
	}

	public void startUp(){	//the startup process for the game

		//set up the audio stuff
		timer = new RhythmTimer(new SongPlayer("Resources/sounds/" + songName + ".wav"), this);
		SongPlayer.initNotesInSong(songName);
		beatsInSong = SongPlayer.notesInSong;

		//set up the players
		player1 = new Player();
		player2 = new Player();

		//add in the panel to display everything
		frame.addKeyListener(new InListener(this));

		backgroundLayer.setBackground();
		backgroundLayer.repaint();

		panel.removeAll();
		panel.reset();
		backgroundLayer.drawNoteLanes();

		BufferedImage startButtonImage = new BufferedImage(frameWidth/4, frameHeight/6, BufferedImage.TYPE_INT_ARGB);
		BufferedImage startButtonDepressed = new BufferedImage(frameWidth/4, frameHeight/6, BufferedImage.TYPE_INT_ARGB);

		try {
			startButtonImage.createGraphics().drawImage(ImageIO.read(new File("Resources/buttons/start.png")), 0, 0, frameWidth/4, frameHeight/6, null);
			startButtonDepressed.createGraphics().drawImage(ImageIO.read(new File("Resources/buttons/startpush.png")), 0, 0, frameWidth/4, frameHeight/6, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JButton startButton = new JButton();
		startButton.setOpaque(false);
		startButton.setFocusable(false);
		startButton.setContentAreaFilled(false);
		startButton.setBorderPainted(false);
		startButton.setBounds(frameWidth/2 - frameWidth/8, frameHeight/2, frameWidth/4, frameHeight/6);
		startButton.setIcon(new ImageIcon(startButtonImage));
		startButton.setPressedIcon(new ImageIcon(startButtonDepressed));
		startButton.setOpaque(false);
		startButton.addActionListener(new StartListener());

		panel.add(startButton);
		panel.repaint();

		//load it up!
		frame.validate();
		frame.setVisible(true);
		frame.pack();



	}

	public void startUpTut(){	//startup process for the tutorial
		
		tutorialMode = 1;
		//set up the audio stuff
		timer = new RhythmTimer(new SongPlayer("Resources/sounds/Tutorial.wav"), this);
		SongPlayer.initNotesInTut();
		beatsInSong = SongPlayer.notesInSong;
		//tutorialOn = true;

		//set up the players
		player1 = new Player();
		player2 = new Player();

		backgroundLayer.setBackgroundTut();
		backgroundLayer.repaint();

		frame.addKeyListener(new InListener(this));
		panel.removeAll();
		panel.reset();
		backgroundLayer.drawNoteLanes();
		//load it up!
		frame.validate();
		frame.setVisible(true);
		frame.pack();

	}

	public void update(){	//main game loop updater

		if (gameDone){
			return;
		}
		songPos = timer.getSongPos();
		//This should be implemented once the array is set up
		panel.reset();

		if (beatsInSong.size() > currBeat - 1){

			if (songPos - 100 - LAGCOMP <= beatsInSong.get(currBeat) && songPos + 100 >= beatsInSong.get(currBeat)){	//if the song is within +/- 100 ms of the next note
				p1CanAtk = true;
				p2CanAtk = true;

			} else {
				p1CanAtk = false;
				p2CanAtk = false;
			}
			//System.out.print("Evaluating for " + beatsInSong.get(currBeat) + ". ");
			if (songPos - 20 >= beatsInSong.get(currBeat) /*&& songPos + 20 >= beatsInSong.get(currBeat)*/){	//if the song is within +/- 10 ms of the next note
				//A beat is happening now!
				flashFrame = 5;
				//System.out.println(" Beat happened");
				//panel.flashBackground();
				if (panel.getNotesOnScreen().size() != 0){
					panel.getNotesOnScreen().remove(0);
					//System.out.println("removed at " + songPos);
				}
				currBeat++;
	  			if (currBeat >= beatsInSong.size()){
	  				//System.out.println("Wahoo!");
	  				gameDone = true;
	  				return;
	  			}


			}

			if (currBeatAdd < beatsInSong.size()){
				if (songPos - 20 >= beatsInSong.get(currBeatAdd) - 1000 /*&& songPos - 20 <= beatsInSong.get(currBeatAdd) - 1000*/){	//adds a new beat to the panel if it's time

					//panel.getNotesOnScreen().add(frameWidth/2);
					panel.getNotesOnScreen().add(beatsInSong.get(currBeatAdd));
					//System.out.println("added at " + songPos + " compared to " + timer.getSongPos() + ". Added for " + beatsInSong.get(currBeatAdd));
					currBeatAdd++;
					//System.out.println("SongPos: " + songPos);
					//System.out.println("Current: " + beatsInSong.get(currBeat));
					//or something like that
				}
			}

			if (songPos + 100 >= beatsInSong.get(currBeat)){	//it's time to see who won!
				if (p1LastHitOffset < 200 || p2LastHitOffset < 200){
					if (p1LastHitOffset < p2LastHitOffset && player1.getLastMove() != Player.BLOCK){
						//p1 is the winner! here's where we add visuals to represent this
						lastPlayerToWin = 1;
						greatFrame = 9;
						if (player1.getLastMove() == Player.PUNCH) curAnim =Anim.p1Punch;
						else if (player1.getLastMove() == Player.KICK) curAnim =Anim.p1Kick;
						player1.incScore(10);
						player2.setLastMove(Player.NONE);
						if (p1ComboFlag != 0){
							player1.incScore(40);
							//combo! here's where we add visuals to represent this
							p1Color = Color.BLUE;
							lastPlayerToWin = 3;
							greatFrame = 14;
							p1ComboFlag = 0;
							player1.setLastMove(Player.NONE);
						}
						curAnimProg = 2 * Math.PI / speed;
					} else if (p2LastHitOffset < p1LastHitOffset && player2.getLastMove() != Player.BLOCK){
						//p2 is the winner! here's where we add visuals to represent this
						lastPlayerToWin = 2;
						greatFrame = 9;
						if (player2.getLastMove() == Player.PUNCH) curAnim =Anim.p2Punch;
						else if (player2.getLastMove() == Player.KICK) curAnim =Anim.p2Kick;
						player2.incScore(10);
						player1.setLastMove(Player.NONE);
						if (p2ComboFlag != 0){
							player2.incScore(40);
							//combo! here's where we add visuals to represent this
							p2Color = Color.RED;
							lastPlayerToWin = 4;
							greatFrame = 14;
							p2ComboFlag = 0;
							player2.setLastMove(Player.NONE);
						}
						curAnimProg = 2 * Math.PI / speed;
					}
					else if (p1LastHitOffset < p2LastHitOffset && player1.getLastMove()== Player.BLOCK)
					{
						curAnimProg = 2 * Math.PI / speed;
						int inc = 20;
						if (player2.getLastMove() == Player.PUNCH) curAnim = Anim.p2PunchBlock;
						else if (player2.getLastMove() == Player.KICK) curAnim = Anim.p2KickBlock;
						else
						{
							curAnimProg = 0;
							inc = 0;
						}
						player1.incScore(inc);
						player2.setLastMove(Player.NONE);

					}
					else if (p2LastHitOffset < p1LastHitOffset && player2.getLastMove()== Player.BLOCK)
					{
						curAnimProg = 2 * Math.PI / speed;
						int inc = 20;
						if (player1.getLastMove() == Player.PUNCH) curAnim = Anim.p1PunchBlock;
						else if (player1.getLastMove() == Player.KICK) curAnim = Anim.p1KickBlock;
						else
						{
							curAnimProg = 0;
							inc = 0;
						}
						player2.incScore(inc);
						player1.setLastMove(Player.NONE);

					}
					p1LastHitOffset = 200;
					p2LastHitOffset = 200;
				}
			}


		}

		if (flashFrame != -1){
			panel.drawFlash(flashFrame);
			flashFrame--;
		}

		if (greatFrame != -1){
			panel.drawGreat(greatFrame, lastPlayerToWin);
			greatFrame--;
		}

		//Update graphics and stuff here
		switch (curAnim){
		case p1Punch: {
			Animations.punch(panel, curAnimProg, true, true, p1Color);
			Animations.punch(panel, curAnimProg, true, false, p1Color);
			break;
		}
		case p1Kick: {
			Animations.kick(panel, curAnimProg, true, true, p1Color);
			Animations.kick(panel, curAnimProg, true, false, p1Color);
			break;
		}
		case p2Punch: {
			Animations.punch(panel, curAnimProg, false, true,  p2Color);
			Animations.punch(panel, curAnimProg, false, false, p2Color);
			break;
		}
		case p2Kick: {
			Animations.kick(panel, curAnimProg, false, true,  p2Color);
			Animations.kick(panel, curAnimProg, false, false, p2Color);
			break;
		}
		case p1PunchBlock: {
			Animations.punchBlock(panel, curAnimProg, true, true, p1Color);
			Animations.punchBlock(panel, curAnimProg, true, false,  p1Color);
			break;
		}
		case p1KickBlock: {
			Animations.kickBlock(panel, curAnimProg, true, true, p1Color);
			Animations.kickBlock(panel, curAnimProg, true, false,  p1Color);
			break;
		}
		case p2PunchBlock: {
			Animations.punchBlock(panel, curAnimProg, false, true,  p2Color);
			Animations.punchBlock(panel, curAnimProg, false, false,p2Color);
			break;
		}
		case p2KickBlock: {
			Animations.kickBlock(panel, curAnimProg, false, true,  p2Color);
			Animations.kickBlock(panel, curAnimProg, false, false,p2Color);
			break;
		}
		default:
			break;

		}
		if (curAnimProg != 0) curAnimProg += 2 * Math.PI / speed;
		if (curAnimProg >= 2* Math.PI)
		{
			p1Color = Color.white;
			p2Color = Color.white;
			curAnimProg = 0;
		}
		//panel.drawNoteLanes();
		panel.drawScores();
		panel.updateNotes(songPos);
		//and then animation updates etc etc

		//System.out.println("This iteration started at " + songPos + " and ended at " + timer.getSongPos());

	}
	
	public void startUpResults(){
		
		backgroundLayer.setBackgroundResult();
		backgroundLayer.repaint();
				
		panel.removeAll();
		panel.reset();
		
		BufferedImage startButtonImage = new BufferedImage(frameWidth/4, frameHeight/6, BufferedImage.TYPE_INT_ARGB);
		BufferedImage startButtonDepressed = new BufferedImage(frameWidth/4, frameHeight/6, BufferedImage.TYPE_INT_ARGB);
		
		try {
			startButtonImage.createGraphics().drawImage(ImageIO.read(new File("Resources/buttons/return.png")), 0, 0, frameWidth/4, frameHeight/6, null);
			startButtonDepressed.createGraphics().drawImage(ImageIO.read(new File("Resources/buttons/returnpush.png")), 0, 0, frameWidth/4, frameHeight/6, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JButton returnButton = new JButton();
		returnButton.setOpaque(false);
		returnButton.setFocusable(false);
		returnButton.setContentAreaFilled(false);
		returnButton.setBorderPainted(false);
		returnButton.setBounds(frameWidth/2 - frameWidth/8, frameHeight/4*3, frameWidth/4, frameHeight/6);
		returnButton.setIcon(new ImageIcon(startButtonImage));
		returnButton.setPressedIcon(new ImageIcon(startButtonDepressed));
		returnButton.setOpaque(false);
		returnButton.addActionListener(new StartListener());
		
		backgroundLayer.displayResults(player1.getScore(), player2.getScore());
		panel.add(returnButton);
		panel.repaint();
		
		//load it up!
		frame.validate();
		frame.setVisible(true);
		frame.pack();
		
		//System.out.println("Did result screen");
}


	@SuppressWarnings("deprecation")
	@Override
	public void run(){
		
		initGame();
		while(true){
		startUpMenu();
//		Timer menuTimer = new Timer(16, null);
//		menuTimer.setInitialDelay(0);
//		menuTimer.start();
		
		
		
		while (gamePhase == 0){
			try {
				Main.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		menuMusic.getClip().stop();
		if (gamePhase == 1){
			startUp();
		} else if (gamePhase == 2){
			startUpTut();
		} else if (gamePhase == 3){
			startUpSongSelect();
			gamePhase = 0;
			while (gamePhase == 0){
				try {
					Main.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			startUp();
			gamePhase = 0;
			while (gamePhase == 0){
				try {
					Main.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		Timer newTimer = new Timer(16, new RhythmListener(this));
		newTimer.setInitialDelay(0);
		newTimer.start();
		timer.start();
		//System.out.println("Start time: " + timer.getSongPos());
		while (!gameDone){
			try {
				Main.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("Out of the loop");
//		while (timer.getSongPos() < timer.getSongLen()){
//			try {
//				Main.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		timer.stop();
		newTimer.stop();
		//System.out.println("timer stopped");
		startUpResults();
		gamePhase = 0;
		gameDone = false;
		tutorialMode = 0;
		int currFrame = 5;
		while(gamePhase == 0){
			try {
				Main.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			panel.reset();
			panel.drawWinner(currFrame);
			panel.repaint();
			currFrame--;
			if (currFrame < 0)
				currFrame = 5;
		}
		panel.removeAll();
		continue;
		
		}
	}


	public static void main(String[] args) {
		//startup code

		Main main = new Main();
		main.start();

	}

	public class StartListener implements ActionListener{


		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			gamePhase = 1;
			panel.removeAll();
		}

	}

}