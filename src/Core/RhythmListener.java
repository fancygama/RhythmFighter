package Core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RhythmListener implements ActionListener{

	private Main main;
	
	public RhythmListener(Main main){
		this.main = main;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		main.update();
	}

}
