package app;

import java.util.ArrayList;
import java.util.List;

public abstract class CardGame {
	public List<Pile> piles;
	public boolean running = false;
	public int turnCardsFromStock;
	public String name;

	public CardGame(String name){
		piles = new ArrayList<>();
		this.name = name;
	}

	public abstract void createPiles();

	public abstract boolean checkWinner();

	public abstract boolean isValidMove(int fromIndex, int toIndex);

	public abstract void move(int fromIndex, int toIndex, Pile cards) throws Exception;

	public void start(){
		piles.clear();
		createPiles();
	}

	public Pile getPile(String name){
		for(Pile pile: piles){
			if(pile.name().equals(name)) return pile;
		}
		return null;
	}

	public void moveCard(Pile fromPile, Pile toPile) throws Exception{
		Card cardFromPile = fromPile.pickLastCard();
		cardFromPile.turnUp();
		toPile.addCard(cardFromPile);
		fromPile.removeLastCard();
	}
	
	public void performPlay(int fromIndex, int toIndex, Pile cards) throws Exception{
		if(!isValidMove(fromIndex, toIndex)) throw new Exception("Jogada Invalida!\n");
		move(fromIndex, toIndex, cards);
	}
}
