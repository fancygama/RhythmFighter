package Core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuListener implements ActionListener{

	private Main main;
	
	public MenuListener(Main main){
		this.main = main;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		main.menuLoop();
	}

}
