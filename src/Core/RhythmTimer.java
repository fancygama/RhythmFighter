package Core;



public class RhythmTimer extends Thread{

	
	private SongPlayer song;
	private long songTime;
	public RhythmTimer(SongPlayer song){
		this.song = song;
		songTime = 0;
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
			
			
			songTime+= 5;
			if (!(songPos + 5 >= songTime) && !(songPos - 5 <= songTime)){
				songTime = songPos;
			}
			
			try {
				RhythmTimer.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
}
