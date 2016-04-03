package Core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InListener implements KeyListener{

	Main main;
	private static final int p1Punch = KeyEvent.VK_A;
	private static final int p1Kick = KeyEvent.VK_S;
	private static final int p1Block = KeyEvent.VK_D;
	
	private static final int p2Punch = KeyEvent.VK_LEFT;
	private static final int p2Kick = KeyEvent.VK_DOWN;
	private static final int p2Block = KeyEvent.VK_RIGHT;
	
	public InListener(Main main){
		this.main = main;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int key = arg0.getKeyCode();
		//System.out.println("Pressed");
		switch (key){
		
		case p1Punch:
			if (main.p1CanAtk && main.lastBeatp1 != main.currBeat) main.playerAttacked(1, Player.PUNCH);
			break;
			
		case p1Kick:
			if (main.p1CanAtk && main.lastBeatp1 != main.currBeat) main.playerAttacked(1, Player.KICK);
			break;
			
		case p1Block:
			if (main.p1CanAtk && main.lastBeatp1 != main.currBeat) main.playerAttacked(1, Player.BLOCK);
			break;
			
		case p2Punch:
			if (main.p2CanAtk && main.lastBeatp2 != main.currBeat) main.playerAttacked(2, Player.PUNCH);
			break;
			
		case p2Kick:
			if (main.p2CanAtk && main.lastBeatp2 != main.currBeat) main.playerAttacked(2, Player.KICK);
			break;
			
		case p2Block:
			if (main.p2CanAtk && main.lastBeatp2 != main.currBeat) main.playerAttacked(2, Player.BLOCK);
			break;
		
		default: break;	//false alarm
		
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
