package app.solitaire;

import app.CardGame;
import app.view.Screen;

public class SolitaireScreen extends Screen{
	
	public SolitaireScreen(CardGame game){
		super(game);
	}

	@Override
	public void setPilePositions() {
		int x = this.WIDTH/4;
		this.addPilePosition("ESTOQUE",  x, 50, 0, 0);
		this.addPilePosition("DESCARTE", x+100, 50, 0, 0);

		for(int i=0; i < 4; i++){
			String name = String.format("FUNDACAO %d", (i+1));
			this.addPilePosition(name, x+200+100*(i+1), 50, 0, 0);
		}

		for(int i=0; i < 7; i++){
			String name = String.format("TABLEAU %d", i+1);
			this.addPilePosition(name, x+100*i, 200, 0, 30);
		}
	}

	
}
