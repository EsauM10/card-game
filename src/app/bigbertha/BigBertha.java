package app.bigbertha;

import java.awt.EventQueue;

import app.CardGame;
import app.Deck;
import app.Pile;
import app.view.MainWindow;
import app.view.Screen;


public class BigBertha extends CardGame{
	public final int FOUNDATION_SIZE = 9;
	public final int TABLEAU_SIZE = 15;
	private Deck deck;

	public BigBertha() {
		super();
	}
	
	@Override
	public void createPiles(){
		deck = new Deck(2);
		boolean faceUp = true;
		piles.add(new Pile(1, "ESTOQUE", deck.getCards(14, faceUp)));

		for(int i=0; i < FOUNDATION_SIZE; i++){
			String name = String.format("FUNDACAO %d", i+1);
			piles.add(new Pile(piles.size()+1, name));
		}
			
		for(int i=0; i < TABLEAU_SIZE; i++){
			String name = String.format("TABLEAU %d", i+1);
			piles.add(new Pile(piles.size()+1, name, deck.getCards(6, faceUp)));
		}
	}

	@Override
	public void start(){
		Screen screen = new BigBerthaScreen(this);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainWindow("BigBertha", screen).setVisible(true);
			}
		});
	}


	@Override
	public boolean checkWinner(){
		return false;
	}

	@Override
	public boolean isValidMove(int fromIndex, int toIndex){
		Pile fromPile = piles.get(fromIndex-1);
		Pile toPile   = piles.get(toIndex-1);

		if(fromPile.type().equals("ESTOQUE")  && toPile.type().equals("DESCARTE")) return true;
		if(fromPile.type().equals("DESCARTE") && toPile.type().equals("FUNDACAO")) return true;
		if(fromPile.type().equals("DESCARTE") && toPile.type().equals("TABLEAU"))  return true;
		if(fromPile.type().equals("TABLEAU")  && toPile.type().equals("TABLEAU"))  return true;
		if(fromPile.type().equals("TABLEAU")  && toPile.type().equals("FUNDACAO")) return true;
		return false;
	}

	@Override
	public void move(int fromIndex, int toIndex, int cardsQty) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
