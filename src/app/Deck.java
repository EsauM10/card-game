package app;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Deck {
	private List<Card> cards;
	private String suits[]  = {"espadas", "paus", "copas", "ouros"}; //Naipes
	private String values[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

	public Deck(int numDecks){
		cards = new ArrayList<>();
		for(int i=0; i<numDecks; i++)
			this.cards.addAll(create());
	}

	private List<Card> create(){
		List<Card> array = new ArrayList<>(52);
		for(String suit: suits)
			for(String value: values)
				array.add(new Card(value, suit));
		return array;
	}

	/**
	 * Consome uma quantidade de cartas aleatorias do baralho
	 * @param cardsQty - Quantidade de cartas
	 * @return uma lista de cartas embaralhadas
	 */
	public List<Card> getCards(int cardsQty, boolean isFaceUp){
		List<Card> array = new ArrayList<>(cardsQty);
		while(array.size() < cardsQty){  	
			int index = randomInt(cards.size());
			Card card = cards.remove(index);
			if(isFaceUp) card.turnUp();
			array.add(card);  
		}
		return array;
	}

	private int randomInt(int bound){
		return new Random().nextInt(bound);
	}
	
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	public boolean hasSameColor(Card card1, Card card2){
		return (card1.color()==card2.color());
	}

	public boolean hasSameNextCardValue(Card card1, Card card2){
		return (nextCardValue(card1)==card2.value());
	}

	public boolean hasSamePriorCardValue(Card card1, Card card2){
		return (priorCardValue(card1)==card2.value());
	}

	public boolean hasSameSuit(Card card1, Card card2){
		return (card1.suit()==card2.suit());
	}

	/**
	 * Obtem o valor imediatamente seguinte da carta atual
	 * @param card
	 * @return
	 */
	public String nextCardValue(Card card){
		int next = getIndex(card.value())+1;
		if(next > values.length-1) return null;
		return values[next];
	}

	/**
	 * Obtem o valor imediatamente anterior da carta atual 
	 * @param card
	 * @return 
	 */
	public String priorCardValue(Card card){
		int prior = getIndex(card.value())-1;
		if(prior < 0) return null;
		return values[prior];
	}

	/**
	 * Obtem o indice de um valor dentro do array values
	 * @param value
	 * @return a posicao do valor dentro array, ou -1 se nao encontrou
	 */
	private int getIndex(String value){
		for(int i=0; i<values.length; i++)
			if(values[i].equals(value)) return i;
		return -1;
	}
}
 