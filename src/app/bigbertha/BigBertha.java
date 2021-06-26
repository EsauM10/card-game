package app.bigbertha;

import java.awt.EventQueue;
import java.util.List;

import app.Card;
import app.CardGame;
import app.Deck;
import app.Pile;
import app.Stackable;
import app.view.MainWindow;
import app.view.Screen;


public class BigBertha extends CardGame{
	public final int FOUNDATION_SIZE = 8;
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
			Pile foundation = new Pile(piles.size()+1, name);
			foundation.setStackingMethod(stackableOnFoundation(foundation));
			piles.add(foundation);
		}

		Pile kingFoundation = new Pile(piles.size()+1, "FUNDACAO 9");
		kingFoundation.setStackingMethod(stackableOnKingFoundation(kingFoundation));
		piles.add(kingFoundation);
			
		for(int i=0; i < TABLEAU_SIZE; i++){
			String name = String.format("TABLEAU %d", i+1);
			Pile tableau = new Pile(piles.size()+1, name, deck.getCards(6, faceUp));
			tableau.setStackingMethod(stackableOnTableau(tableau));
			piles.add(tableau);
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
		int tableau1 = 10;
		if(!piles.get(0).isEmpty()) return false;
		for(int i = tableau1; i < (tableau1 + TABLEAU_SIZE); i++){
			if(!piles.get(i).isEmpty()) return false;
		}
		return true;
	}

	@Override
	public boolean isValidMove(int fromIndex, int toIndex){
		Pile fromPile = piles.get(fromIndex-1);
		Pile toPile   = piles.get(toIndex-1);

		if(fromPile.type().equals("ESTOQUE")  && toPile.type().equals("FUNDACAO")) return true;
		if(fromPile.type().equals("ESTOQUE")  && toPile.type().equals("TABLEAU")) return true;
		if(fromPile.type().equals("TABLEAU")  && toPile.type().equals("TABLEAU"))  return true;
		if(fromPile.type().equals("TABLEAU")  && toPile.type().equals("FUNDACAO")) return true;
		return false;
	}

	@Override
	public void move(int fromIndex, int toIndex, Pile cards) throws Exception {
		Pile fromPile = piles.get(fromIndex-1);
		Pile toPile   = piles.get(toIndex-1);
		
		if(cards.isEmpty()) return;
		if(!pileIsAscendent(cards)) 
			throw new Exception("A sub pilha selecionada nao possui ordem ascendente!\n");
		
		if(toPile.type().equals("FUNDACAO") && cards.size() > 1)
			throw new Exception("So e permitido mover uma carta por vez!\n");
		
		if(fromPile.type().equals("ESTOQUE")){
			Card card = cards.pickLastCard();
			moveCard(cards, toPile);
			fromPile.removeCard(card);
			return;
		}
		
		while(!cards.isEmpty()){
			moveCard(cards, toPile);
			fromPile.removeLastCard();
		}
	}

	public boolean pileIsAscendent(Pile pile){
		if(pile.size()==1) return true;

		List<Card> cardsClone = pile.getCardsAsList();
		Pile ascendent = new Pile(0, "ASCENDENT");
		ascendent.push(cardsClone.remove(cardsClone.size()-1));
		ascendent.setStackingMethod(stackableOnTableau(ascendent));
		
		try {
			while(!cardsClone.isEmpty()){
				Card card = cardsClone.remove(cardsClone.size()-1);
				ascendent.addCard(card);
			}
		} catch (Exception e) { return false; } 
		
		return true;
	}
	

	private Stackable stackableOnFoundation(Pile pile) {
		return (Card card) -> {
			if(pile.isEmpty()) {
				if(!card.value().equals("A")) return false;
			}
			else{
				Card lastCard = pile.pickLastCard();
				if(!deck.hasSameSuit(card, lastCard)) return false;
				if(!deck.hasSamePriorCardValue(card, lastCard)) return false;
			}
			return true;
		};
	}

	private Stackable stackableOnKingFoundation(Pile pile) {
		return (Card card) -> {
			return card.value().equals("K");
		};
	}

	private Stackable stackableOnTableau(Pile pile) {
		return (Card card) -> {
			if(pile.isEmpty() && !card.value().equals("K")) 	
				return false;
				
			Card lastCard = pile.pickLastCard();
			if(deck.hasSameColor(card, lastCard)) return false;
			if(!deck.hasSameNextCardValue(card, lastCard)) return false;
			return true;
		};
	}
}
