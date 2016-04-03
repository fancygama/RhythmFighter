package Core;



public class RhythmTimer extends Thread{

	
	private SongPlayer song;
	private Main main;
	private long songTime;
	
	public RhythmTimer(SongPlayer song, Main main){
		this.song = song;
		songTime = 0;
		this.main = main;
	}
	
	public void startSong(){
		song.startSong();
	}
	
	public long getSongPos(){
		return songTime;
	}
	
	public long getSongLen(){
		return song.getSongLen();
	}
	
	@Override
	public void run(){
		long songPos;
		startSong();
		
		while ((songPos = song.getSongPos()) != song.getSongLen()){
			
			
			songTime = songPos;
			
			//main.update();
			/*if (!(songPos + 5 >= songTime) && !(songPos - 5 <= songTime)){
				songTime = songPos;
			}*/
			try {
				RhythmTimer.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
}
