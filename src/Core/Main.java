package Core;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.*;

import Graphics.GamePanel;

public class Main extends Thread{
	
	//the game's display window stuff
	public GamePanel panel;	//the game panel
	public JFrame frame;	//the game frame
	public int frameWidth;
	public int frameHeight;
	//the game's song stuff. The audio player and the current position of the song.
	private SongPlayer song;
	private long songPos;
	//the classes containing the information of each player
	private Player player1;
	private Player player2;
	//booleans that indicate what each player can do and whether or not the game is over
	private boolean gameDone = false;
	public boolean p1CanAtk = false;
	public boolean p2CanAtk = false;
	
	public long p1LastHitOffset;	//the amount that player1's last hit was off the mark in ms
	public long p2LastHitOffset; //the amt player2's last hit was off the mark in ms
	
	//used by InListener to establish what should happen when a key is hit.
	public void playerAttacked(int player, int move){
		if (player == 1){
			p1CanAtk = false;	//the player can no longer attack
			player1.setLastMove(move);
			p1LastHitOffset = Math.abs(SongPlayer.notesInSong.get(0) - song.getSongPos());
		} else {
			p2CanAtk = false;
			player2.setLastMove(move);
			p2LastHitOffset = Math.abs(SongPlayer.notesInSong.get(0) - song.getSongPos());
		}
	}
	
	
	public void startUp(){	//the startup process for the game
		
		//set up the audio stuff
		song = new SongPlayer();
		
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
		panel.addKeyListener(new InListener(this));
		//load it up!
		frame.setVisible(true);
		//panel.drawALine();	//this was just a test thing anyway
		frame.validate();
		frame.pack();
		
		
	}

	
	public void loop(){
		
		songPos = song.getSongPos();
		
		/*	This should be implemented once the array is set up
		 * if (songPos - 100 <= ArrayOfInts.get(0) && songPos + 100 >= ArrayOfInts.get(0)){	//if the song is within +/- 100 ms of the next note
		 * 		p1CanAtk = true;	//the players can use moves
		 * 		p2CanAtk = true;
		 * 
		 * 		if (songPos - 10 <= ArrayOfInts.get(0) && songPos + 10 >= ArrayOfInts.get(0)){	//if the song is within +/- 10 ms of the next note
		 * 			//A beat is happening now!
		 * 			ArrayOfNotesOnScreen.remove(0);
		 * 			ArrayOfBeats.remove(0);	//remove that note from the arrays, as it just happened.
		 * 			
		 * }
		 */
		
		/*	This should also be implemented once the array is set up
		 * if (songPos >= ArrayOfBeats.get(0) - 1000){
		 * 		ArrayOfNotesOnScreen.add(initXCoordinate);
		 * 			//or something like that
		 * 	}
		 * 
		 */
		
		//Update graphics and stuff here
		
		
		
		
		
		
	}
	
	
	@Override
	public void run(){
		
		startUp();
		
		song.startSong();
		while(!gameDone){
			loop();
			
			try {
				Main.sleep(30);	//an arbitrary number of ms between each iteration of loop. might cause lag...
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	public static void main(String[] args) {
		//startup code
		
		Main main = new Main();
		main.start();
		
	}

}
