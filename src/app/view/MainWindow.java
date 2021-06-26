package app.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame{
	
	public MainWindow(String title, JPanel screen){
		super(title);
		this.add(screen);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
