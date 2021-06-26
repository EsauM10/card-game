package app.bigbertha;

import app.CardGame;
import app.view.Screen;

import java.awt.Color;

public class BigBerthaScreen extends Screen{

	public BigBerthaScreen(CardGame game){
		super(game);
		Color background = new Color(66, 34, 101);
		Color toolbar = new Color(40, 19, 62);
		Color button = new Color(108, 57, 163);
		setTheme(background, toolbar, button);
	}

	@Override
	public void setPilePositions() {
		this.addPilePosition("ESTOQUE",  20, 50, 20, 0);

		for(int i=0; i < 9; i++){
			String name = String.format("FUNDACAO %d", (i+1));
			this.addPilePosition(name, 300+100*(i+1), 50, 0, 0);
		}

		for(int i=0; i < 15; i++){
			String name = String.format("TABLEAU %d", i+1);
			this.addPilePosition(name, 5+90*i, 200, 0, 30);
		}
	}

	
}
