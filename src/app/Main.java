package app;


import app.solitaire.Solitaire;
import app.bigbertha.BigBertha;

public class Main {

	public static void main(String[] args) throws Exception {
		//CardGame game = new Solitaire();
		CardGame game = new BigBertha();
		game.start();
	}
}