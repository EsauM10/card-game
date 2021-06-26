package app.view;

import app.Pile;

import java.awt.geom.Rectangle2D;

public class Pile2D {
	
	public Pile pile;
	public int x, y, yOffset, xOffset;
	private int cardWidth, cardHeight;

	public Pile2D(Pile pile, int x, int y, int xOffset, int yOffset, int cardWidth, int cardHeight){
		this.pile = pile;
		this.x = x;
		this.y = y;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.cardWidth = cardWidth;
		this.cardHeight = cardHeight;
	}

	public Rectangle2D getBounds(){
		int size = this.pile.size();
		int width = this.xOffset*(size-1) + this.cardWidth;
		int height  = this.yOffset*(size-1) + this.cardHeight;	
		return new Rectangle2D.Float(x, y, width, height);
	}

	public boolean isInsidePile(int mouseX, int mouseY){
		return this.getBounds().contains(mouseX, mouseY);
	}

	
	private boolean isHorizontalAlignment(){
		return (xOffset > 0);
	}

	private boolean isVerticalAlignment(){
		return (yOffset > 0);
	}

	private int horizontalOffset(){
		return this.x + xOffset*pile.size();
	}
	
	private int verticalOffset(){
		return this.y + yOffset*pile.size();
	}

	public int indexOfSelectedCard(int mouseX, int mouseY){
		if(pile.isEmpty()) return -1;
		if(isHorizontalAlignment() && mouseX < horizontalOffset())
			return ((mouseX-this.x) / xOffset);

		if(isVerticalAlignment() && mouseY < verticalOffset())
			return ((mouseY-this.y) / yOffset);

		return pile.size()-1;
	}

	public Pile2D getSelectedCards(int x, int y){
		int index = indexOfSelectedCard(x, y);
		int cardsQty = pile.size()-index;

		if(index < 0) return null;
		if(isHorizontalAlignment()){
			Pile subpile = new Pile(0, "EMPTY");
			subpile.push(pile.cards.get(index));
			return new Pile2D(subpile, this.x + xOffset*index, this.y, 0, 0, cardWidth, cardHeight);
		}
		
		Pile subpile = this.pile.pickLastCards(cardsQty);
		return new Pile2D(subpile, this.x, this.y + yOffset*index, xOffset, yOffset, cardWidth, cardHeight);
	}


}
