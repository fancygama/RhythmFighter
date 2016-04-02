package Core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SongPlayer {

	public static ArrayList<Long> notesInSong;
	
	private String songPath = "src/resources/Weekend.wav";	//CHANGE THIS
	private AudioInputStream audioIn;
	private Clip songClip;
	
	public SongPlayer(){
		
		
		try {
			audioIn = AudioSystem.getAudioInputStream(new File(songPath));
			songClip = AudioSystem.getClip();
			songClip.open(audioIn);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
			System.out.println("Audio system did not init properly");
		}
		
	}
	
	public void startSong(){
		songClip.start();
	}
	
	public long getSongPos(){
		return songClip.getMicrosecondPosition() / 1000;
	}
	
}
