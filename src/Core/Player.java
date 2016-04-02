package Core;

public class Player {
	
	//these should always be used when referring to kicks/punches/blocks. none means the player didnt hit.
	public static final int PUNCH = 1;
	public static final int KICK = 2;
	public static final int BLOCK = 3;
	public static final int NONE = 0;
	
	public static final int[][] COMBOS = 
		{
		{PUNCH, KICK, PUNCH},
		{PUNCH, PUNCH, KICK},
		{KICK, PUNCH, KICK},
		{KICK, KICK, PUNCH},
		{BLOCK, KICK, PUNCH},
		{BLOCK, PUNCH, KICK}
		};
	
	private int score = 0;
	private int[] prev2Moves = {NONE, NONE};
	
	public void incScore(int amt){
		score += amt;
	}
	
	public int setLastMove(int move){	//returns an int other than 0 if a combo is triggered
		
		for (int i = 0; i < COMBOS.length; i++){
			if (prev2Moves[0] == COMBOS[i][0] && prev2Moves[1] == COMBOS[i][1] && move == COMBOS[i][2]){
				return i + 1;
			}
		}
		
		return 0;
	}
}
