package app;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Pile {
	public Stack<Card> cards;
	private int id;
	private String name;
	private Stackable stackable;
	
	
	public Pile(int id, String name) {
		this.id   = id;
		this.name = name;
		cards = new Stack<Card>();
	}
	
	public Pile(int id, String name, List<Card> cardList) {
		this(id, name);
		this.init(cardList);
	}
	
	/**
	 * Inicia a pilha com uma lista de cartas
	 * @param cardList
	 */
	private void init(List<Card> cardList) {
		cardList.forEach(card->cards.push(card));
	}
	
	public void addCard(Card card) throws Exception {
		if(this.stackable != null){
			if(!this.stackable.cardIsStackable(card)){
				String message = String.format("A carta %s nao pode ser adicionada a %s!\n", card.toString(), this.name);
				throw new Exception(message);
			} 
		}
		this.cards.push(card); 
	}

	public void push(Card card){
		this.cards.push(card);
	}
	
	public void addCardInFinal(Card card) {
		this.cards.add(0, card);
	}

	public List<Card> getCardsAsList(){
		Card cards[] = this.cards.toArray(new Card[0]);
		return new ArrayList<Card>(Arrays.asList(cards));
	}

	public boolean isEmpty(){
		return cards.isEmpty();
	}
	
	public Card pickLastCard(){
		return cards.peek();
	}

	public Pile pickLastCards(int cardsQty) {
		if(this.isEmpty()) return new Pile(0, "EMPTY");

		Stack<?> newStack = (Stack<?>) this.cards.clone();
		Pile cardsPile = new Pile(0, "NEW");

		while(cardsPile.size() < cardsQty){
			Card card = (Card) newStack.peek();
			if(card.isFaceDown()) break;
			cardsPile.push((Card) newStack.pop());
		}
		return cardsPile;
	}
	
	public Card removeLastCard(){
		return cards.pop(); 
	}
	
	public Card removeCard(int index){
		return cards.remove(index);
	}

	public Card removeCard(Card card){
		int index = cards.indexOf(card);
		return cards.remove(index);
	}

	public void setStackingMethod(Stackable stackableObject){
		this.stackable = stackableObject;
	}

	public void show() {
		System.out.print(this.toString());
		cards.forEach(card->System.out.print(card.toString() + " "));
		System.out.println("");
	}

	public int size() {
		return cards.size();
	}
	
	public void turnUpCard(){
		cards.peek().turnUp();
	}
	
	public String toString() {
		return String.format("%2d - %10s == ", id, name);
	}

	public String name(){
		return name;
	}

	public String type(){
		return name.split(" ")[0];
	}

	public int index(){
		return id;
	}
}
