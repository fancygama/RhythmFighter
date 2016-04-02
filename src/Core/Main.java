package Core;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

import javax.swing.*;

import Graphics.GamePanel;

public class Main extends Thread{
	
	//the game's display window stuff
	public GamePanel panel;	//the game panel
	public JFrame frame;	//the game frame
	public int frameWidth;
	public int frameHeight;
	//the game's song stuff. The audio player and the current position of the song.
	private RhythmTimer timer;
	private long songPos;
	//the classes containing the information of each player
	private Player player1;
	private Player player2;
	//booleans that indicate what each player can do and whether or not the game is over
	private boolean gameDone = false;
	private boolean tutorialOn = false;
	public boolean p1CanAtk = false;
	public boolean p2CanAtk = false;
	
	public long p1LastHitOffset;	//the amount that player1's last hit was off the mark in ms
	public long p2LastHitOffset; //the amt player2's last hit was off the mark in ms
	
	public ArrayList<Long> beatsInSong;
	private int currBeat = 0;
	private int beatOffset = 1;
	
	//used by InListener to establish what should happen when a key is hit.
	public void playerAttacked(int player, int move){
		if (player == 1){
			p1CanAtk = false;	//the player can no longer attack
			player1.setLastMove(move);
			p1LastHitOffset = Math.abs(SongPlayer.notesInSong.get(0) - timer.getSongPos());
		} else {
			p2CanAtk = false;
			player2.setLastMove(move);
			p2LastHitOffset = Math.abs(SongPlayer.notesInSong.get(0) - timer.getSongPos());
		}
	}
	
	
	public void startUp(){	//the startup process for the game
		
		//set up the audio stuff
		timer = new RhythmTimer(new SongPlayer("src/Resources/Song.wav"), this);
		SongPlayer.initNotesInSong();
		beatsInSong = SongPlayer.notesInSong;
		
		//set up the players
		player1 = new Player();
		player2 = new Player();
		
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
		frame.addKeyListener(new InListener(this));
		panel.drawNoteLanes();
		//load it up!
		//panel.drawALine();	//this was just a test thing anyway
		frame.validate();
		frame.setVisible(true);
		frame.pack();
		
		
	}
	
	public void startUpTut(){
		
		//set up the audio stuff
				timer = new RhythmTimer(new SongPlayer("src/Resources/Tutorial.wav"), this);
				SongPlayer.initNotesInTut();
				beatsInSong = SongPlayer.notesInTutorial;
				tutorialOn = true;
				
				//set up the players
				player1 = new Player();
				player2 = new Player();
				
				//get the resolution of the main monitor
				GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
				frameWidth = gd.getDisplayMode().getWidth();
				frameHeight = gd.getDisplayMode().getHeight();
				
				//set up the game's frame
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setPreferredSize(new Dimension(frameWidth,frameHeight));
				//add in the panel to display everything
			
				panel = new GamePanel(frameWidth, frameHeight);
				frame.add(panel);
				frame.addKeyListener(new InListener(this));
				panel.drawNoteLanes();
				//load it up!
				//panel.drawALine();	//this was just a test thing anyway
				frame.validate();
				frame.setVisible(true);
				frame.pack();
		
	}
	
	public void update(){

		songPos = timer.getSongPos();
		//This should be implemented once the array is set up
		panel.reset();

	if (beatsInSong.size() != currBeat - 1){
	
		  if (songPos - 100 <= beatsInSong.get(currBeat) && songPos + 100 >= beatsInSong.get(currBeat)){	//if the song is within +/- 100 ms of the next note
		  		p1CanAtk = true;	//the players can use moves
		  		p2CanAtk = true;
		  		if (songPos - 17 <= beatsInSong.get(currBeat) && songPos + 17 >= beatsInSong.get(currBeat)){	//if the song is within +/- 10 ms of the next note
		  			//A beat is happening now!
		  			System.out.println(" Beat happened");
		  			//panel.flashBackground();
		  			panel.getNotesOnScreen().remove(0);
		  			System.out.println("removed at " + songPos);
		  			currBeat++;
		  			beatOffset = 1;

		  		}
		  		
		  }
		  
		  if (songPos + 20 >= beatsInSong.get(currBeat + 1) - 1000 && songPos - 20 <= beatsInSong.get(currBeat + 1) - 1000){	//adds a new beat to the panel if it's time
		  		//panel.getNotesOnScreen().add(frameWidth/2);
			  	panel.getNotesOnScreen().add(beatsInSong.get(currBeat + 1));
		  		System.out.println("added at " + songPos + " compared to " + timer.getSongPos());
		  		beatOffset++;
				//System.out.println("SongPos: " + songPos);
				//System.out.println("Current: " + beatsInSong.get(currBeat));
		  			//or something like that
		  	}
	} else if (songPos >= timer.getSongLen()){
		gameDone = true;
		return;
	}
	 
	
	//Update graphics and stuff here
	  //panel.drawBackground();		//for once we have a background
	  panel.drawNoteLanes();
	  panel.updateNotes(songPos);
	  //and then animation updates etc etc
	  
	  System.out.println("This iteration started at " + songPos + " and ended at " + timer.getSongPos());
		
	}

	
	public void loop(){
		
		songPos = timer.getSongPos();
			//This should be implemented once the array is set up
		panel.reset();

		if (beatsInSong.size() != currBeat - 1){
		
			  if (songPos - 100 <= beatsInSong.get(currBeat) && songPos + 100 >= beatsInSong.get(currBeat)){	//if the song is within +/- 100 ms of the next note
			  		p1CanAtk = true;	//the players can use moves
			  		p2CanAtk = true;
			  		if (songPos - 15 <= beatsInSong.get(currBeat) && songPos + 15 >= beatsInSong.get(currBeat)){	//if the song is within +/- 10 ms of the next note
			  			//A beat is happening now!
			  			System.out.println(" Beat happened");
			  			panel.flashBackground();
			  			currBeat++;
			  			if (panel.getNotesOnScreen().size() != 0){
			  			panel.getNotesOnScreen().remove(0);
			  			//beatsInSong.remove(0);	//remove that note from the arrays, as it just happened.
			  			}
			  		}
			  }
			  
			//	This should also be implemented once the array is set up
			  if (songPos + 10 >= beatsInSong.get(currBeat + 1) - 1000 && songPos - 10 <= beatsInSong.get(currBeat + 1) - 1000){	//adds a new beat to the panel if it's time
			  		//panel.getNotesOnScreen().add(frameWidth/2);
				  	panel.getNotesOnScreen().add(beatsInSong.get(currBeat + 1));
			  		System.out.println("added");
					System.out.println("SongPos: " + songPos);
					System.out.println("Current: " + beatsInSong.get(currBeat));
			  			//or something like that
			  	}
		} else if (songPos >= timer.getSongLen()){
			gameDone = true;
			return;
		}
		 
		
		//Update graphics and stuff here
		  //panel.drawBackground();		//for once we have a background
		  panel.drawNoteLanes();
		  panel.updateNotes(songPos);
		  //and then animation updates etc etc
		
		
	}
	
	
	@Override
	public void run(){
		
		startUpTut();
		//startUp();
		Timer newTimer = new Timer(16, new RhythmListener(this));
		newTimer.setInitialDelay(0);
		newTimer.start();
		timer.start();
		System.out.println("Start time: " + timer.getSongPos());
		
		
		
	}
	
	
	public static void main(String[] args) {
		//startup code
		
		Main main = new Main();
		main.start();
		
	}

}
