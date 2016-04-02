package Core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SongPlayer {

	public static ArrayList<Long> notesInSong = new ArrayList<Long>();
	public static ArrayList<Long> notesInTutorial = new ArrayList<Long>();
	private static int tutBPM = 120;
	
	private String songPath = "src/Resources/Song.wav";
	private AudioInputStream audioIn;
	private Clip songClip;
	
public static void initNotesInTut(){
		
		for (int i = 3; i < 60000/500; i++){
			notesInTutorial.add(((long)i)*500);
			System.out.println("beat " + (i - 2) + ": " + notesInTutorial.get(notesInTutorial.size() - 1));
		}
		
	}
	
	public static void initNotesInSong(){
		Charset charset = Charset.forName("UTF-8");
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(FileSystems.getDefault().getPath("src","resources", "songchart" + ".txt"), charset);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not read file");
			return;
		}
		
		String line = null;
	    try {
			while ((line = reader.readLine()) != null){
				notesInSong.add(Long.parseLong(line));
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IO error!");
		}
		
	}
	
	
	public SongPlayer(String songPath){
		
		
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
	
	public long getSomeLong(){
		return 10000;
	}

	
	public long getSongPos(){
		return songClip.getMicrosecondPosition() / 1000;
	}
	
	public long getSongLen(){
		return songClip.getMicrosecondLength() / 1000;
	}
	
}
