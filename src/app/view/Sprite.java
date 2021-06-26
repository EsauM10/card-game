package app.view;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.awt.Point;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {

	private HashMap<String, Point> cardSprites; 
	private BufferedImage image;
	private int width, height;

	public Sprite(String pathImage, int widthSubImage, int heightSubImage){
		this.width  = widthSubImage;
		this.height = heightSubImage;
		loadImage(pathImage);
		cardSprites = new HashMap<>();
		mapCards();
	}
	
	private void loadImage(String path) {
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {e.printStackTrace();}
	}

	private BufferedImage getImage(int row, int col, int width, int height) {
		int x = (width)*col;
		int y = (height)*row;
		return image.getSubimage(x, y, width, height);
	}

	private void mapCards(){
		String suits[]  = {"copas", "ouros", "paus", "espadas"};
		String values[] = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
		
		for(int row=0; row<suits.length; row++){
			for(int column=0; column<values.length; column++){
				String key = String.format("[%s %s]", values[column], suits[row]);
				cardSprites.put(key, new Point(row, column));
			}
		}
		cardSprites.put("[<>]", new Point(4, 0));
	}

	public BufferedImage getCard(String card){
		Point p = cardSprites.get(card);
		int row = p.x, column = p.y;
		return getImage(row, column, this.width, this.height);
	}

}
