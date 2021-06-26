package app;


import app.solitaire.Solitaire;

public class Main {

	public static void main(String[] args) throws Exception {
		CardGame game = new Solitaire();
		game.start();
	}
}