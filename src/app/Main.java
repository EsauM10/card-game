package app;

import app.view.MainWindow;
import app.view.Screen;

import app.solitaire.Solitaire;
import app.solitaire.SolitaireScreen;
import app.bigbertha.BigBertha;
import app.bigbertha.BigBerthaScreen;

import java.util.List;
import java.util.ArrayList;


public class Main {

	public static void main(String[] args) throws Exception {
		List<Screen> screens = new ArrayList<>();
		screens.add(new SolitaireScreen(new Solitaire()));
		screens.add(new BigBerthaScreen(new BigBertha()));

		MainWindow frame = new MainWindow(screens);
        frame.setVisible(true);
	}
}