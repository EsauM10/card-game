package app.solitaire;

import app.view.MainWindow;
import app.view.Screen;

import java.util.List;
import java.awt.EventQueue;

import app.Card;
import app.CardGame;
import app.Deck;
import app.Pile;
import app.Stackable;

public class Solitaire extends CardGame{
	private Deck deck;
	private final int FOUNDATION_SIZE = 4;
	private final int TABLEAU_SIZE = 7;

	public Solitaire(){
		super();
		turnCardsFromStock = 1;
	}
	
	@Override
	public void createPiles() {
		deck  = new Deck(1);
		piles.add(new Pile(1, "ESTOQUE", deck.getCards(24, false)));
		piles.add(new Pile(2, "DESCARTE"));

		//Inicia as pilhas de fundacoes vazias
		for(int i=0; i < FOUNDATION_SIZE; i++){
			String name = String.format("FUNDACAO %d", i+1);
			Pile foundation = new Pile(piles.size()+1, name);
			foundation.setStackingMethod(stackableOnFoundation(foundation));
			piles.add(foundation);
		}
		
		//Inicia as pilhas de fileiras com cartas aleatorias
		for(int i=0; i < TABLEAU_SIZE; i++) {
			String name = String.format("TABLEAU %d", i+1);
			boolean faceUp = false;
			Pile tableau = new Pile(piles.size()+1, name, deck.getCards(i+1, faceUp));
			tableau.turnUpCard();
			tableau.setStackingMethod(stackableOnTableau(tableau));
			piles.add(tableau);
		}
	}

	@Override
	public void start(){
		Screen screen = new SolitaireScreen(this);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainWindow("Solitaire", screen).setVisible(true);
			}
		});
	}

	@Override
	public boolean checkWinner(){
		int foundationIndexes[] = {2, 3, 4, 5};
		for(int index: foundationIndexes)
			if(piles.get(index).size()!=13) return false;
		return true;
	}

	@Override
	public boolean isValidMove(int fromIndex, int toIndex){
		Pile fromPile = piles.get(fromIndex-1);
		Pile toPile   = piles.get(toIndex-1);

		if(fromPile.type().equals("ESTOQUE")  && toPile.type().equals("DESCARTE")) return true;
		if(fromPile.type().equals("DESCARTE") && toPile.type().equals("FUNDACAO")) return true;
		if(fromPile.type().equals("DESCARTE") && toPile.type().equals("TABLEAU"))  return true;
		if(fromPile.type().equals("FUNDACAO") && toPile.type().equals("TABLEAU"))  return true;
		if(fromPile.type().equals("TABLEAU")  && toPile.type().equals("TABLEAU"))  return true;
		if(fromPile.type().equals("TABLEAU")  && toPile.type().equals("FUNDACAO")) return true;
		return false;
	}

	@Override
	public void move(int fromIndex, int toIndex, int cardsQty) throws Exception{
		Pile source 	 = piles.get(fromIndex-1);
		Pile destination = piles.get(toIndex-1);

		if(source.name().equals("ESTOQUE") && destination.name().equals("DESCARTE"))
			moveCardFromStockToWaste();
		else{
			moveOneOrMoreCards(fromIndex, toIndex, cardsQty);
		}
	}

	public void moveCardFromStockToWaste() throws Exception{
		Pile stock = piles.get(0);
		Pile waste = piles.get(1);
		
		while(!waste.isEmpty()) {
			Card card = waste.removeBelow();
			card.turnDown();
			stock.addCardInFinal(card);
		}

		for(int i=0; i < turnCardsFromStock && !stock.isEmpty(); i++){
			moveCard(stock, waste);
		}
	}

	public void moveOneOrMoreCards(int fromIndex, int toIndex, int cardsQty) throws Exception{
		Pile fromPile = piles.get(fromIndex-1);
		Pile toPile   = piles.get(toIndex-1);
		Pile newPile  = fromPile.pickLastCards(cardsQty);
		
		if(newPile.isEmpty()) return;
		if(!pileIsAscendent(newPile)) 
			throw new Exception("A sub pilha selecionada nao possui ordem ascendente!\n");
		
		if(toPile.type().equals("FUNDACAO") && cardsQty > 1)
			throw new Exception("So e permitido mover uma carta por vez!\n");

		while(!newPile.isEmpty()){
			moveCard(newPile, toPile);
			fromPile.removeLastCard();
		}

		if(!fromPile.isEmpty() && fromPile.type().equals("TABLEAU"))
			fromPile.turnUpCard();
	}

	public boolean pileIsAscendent(Pile newPile){
		if(newPile.size()==1) return true;

		List<Card> cardsClone = newPile.getCardsAsList();
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

	public Stackable stackableOnFoundation(Pile pile){
		return new Stackable(){
			
			@Override
			public boolean cardIsStackable(Card card) {
				if(pile.isEmpty()){
					if(!card.value().equals("A")) return false;
				}	
				else{
					Card lastCard = pile.pickLastCard();
					if(!deck.hasSameSuit(card, lastCard)) 			return false;
					if(!deck.hasSamePriorCardValue(card, lastCard)) return false;
				}
				return true;
			}
			
		};
	}

	public Stackable stackableOnTableau(Pile pile){
		return new Stackable(){
			@Override
			public boolean cardIsStackable(Card card) {
				if(pile.isEmpty()){
					if(!card.value().equals("K")) return false;
				}
				else{
					Card lastCard = pile.pickLastCard();
					if(deck.hasSameColor(card, lastCard))			return false;
					if(!deck.hasSameNextCardValue(card, lastCard)) 	return false;
				}
				return true;
			}
			
		};
	}	
}
