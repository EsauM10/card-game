package app.view;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import app.Card;
import app.CardGame;
import app.Pile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;

import java.awt.geom.Rectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public abstract class Screen extends JPanel{
	
	public final int WIDTH       = 1350;
	public final int HEIGHT      = 650;
	private final int DELAY      = 100;
	private final int INTERVAL   = 25;
	private final int cardWidth  = 81;
	private final int cardHeight = 117;

	private JToolBar toolbar;
	private JButton restartButton;
	private Color primaryColor    = new Color(33, 138, 90);
	private Color secondaryColor  = new Color(30, 115, 77);

	private Timer timer;
	private Sprite sprite;
	private CardGame game;
	private List<Pile2D> piles2D;
	private Pile2D selectedPile;
	private int sourceIndex;
	private int destinationIndex;

	public Screen(CardGame game){
		super(new BorderLayout());
		this.game = game;
		sprite  = new Sprite("/app/view/image/card-sprite.gif", cardWidth, cardHeight);
		piles2D = new ArrayList<>();
		initComponents();

		setBackground(primaryColor);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setDoubleBuffered(true);
		setFocusable(true);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				performPlay(e);
			}
		});

		timer = new Timer();
		timer.scheduleAtFixedRate(new Task(), DELAY, INTERVAL);
	}

	private void initComponents(){
		toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setBackground(secondaryColor);
		toolbar.setLayout(null);
		toolbar.setBorder(null);
		toolbar.setPreferredSize(new Dimension(WIDTH, 40));

		restartButton = new JButton("Restart");
		restartButton.setBounds(5, 5, 60, 30);
		restartButton.setForeground(Color.white);
		restartButton.setBackground(new Color(3, 157, 252));
		restartButton.setBorder(null);
		restartButton.addActionListener(e -> restart());

		toolbar.add(restartButton);
		this.add(toolbar, BorderLayout.NORTH);
	}

	public void addPilePosition(String name, int x, int y, int xOffset, int yOffset){
		Pile pile = game.getPile(name);
		if(pile == null) return;
		piles2D.add(new Pile2D(pile, x, y, xOffset, yOffset, cardWidth, cardHeight));
	}

	public abstract void setPilePositions();

	public void setTheme(Color background, Color toolbar, Color button){
		this.primaryColor   = background;
		this.secondaryColor = toolbar;
		this.setBackground(background);
		this.toolbar.setBackground(toolbar);
		this.restartButton.setBackground(button);
	}

	public Color primaryColor(){ return this.primaryColor; }

	public Color secondaryColor(){ return this.secondaryColor; }

	public String name(){ return game.name; }

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		drawPiles(g2);
		if(selectedPile != null){
			drawSelectedPile(g2, selectedPile);
		}
	}

	private void drawPiles(Graphics2D g2){
		for(Pile2D p: piles2D)
			drawPile(g2, p);
	}

	private void drawPile(Graphics2D g2, Pile2D pile2D){
		int x = pile2D.x;
		int y = pile2D.y;
		int xOffset = pile2D.xOffset;
		int yOffset = pile2D.yOffset;

		if(pile2D.pile.isEmpty()){
			drawEmptyPile(g2, x, y);
		}
		else{
			for(Card card: pile2D.pile.cards){
				String cardName = card.toString();
				g2.drawImage(sprite.getCard(cardName), x, y, this);
				x += xOffset;
				y += yOffset;
			}
		}
	}

	private void drawEmptyPile(Graphics2D g2, int x, int y){
		Rectangle2D rect = new Rectangle2D.Float(x, y, cardWidth, cardHeight);
		g2.setColor(secondaryColor);
		g2.fill(rect);
	}

	private void drawSelectedPile(Graphics2D g2, Pile2D pile2D){
		g2.setColor(new Color(52, 229, 235));
		g2.setStroke(new BasicStroke(3.5f));
		g2.draw(pile2D.getBounds());	
	}

	private Pile2D getSelectedPile(int x, int y){
		for(Pile2D pile2D: piles2D){
			if(pile2D.isInsidePile(x, y))
				return pile2D;
		}
		return null;
	}

	private void resetPlay(){
		selectedPile = null;
		sourceIndex  = destinationIndex = -1;
	}

	public void restart(){
		game.start();
		piles2D.clear();
		setPilePositions();
		resetPlay();
	}


	private void performPlay(MouseEvent e){
		int mouseX = e.getX();
		int mouseY = e.getY();
		if(selectedPile == null){
			Pile2D source = getSelectedPile(mouseX, mouseY);
			if(source == null) return;
			
			sourceIndex   = source.pile.index();
			selectedPile  = source.getSelectedCards(mouseX, mouseY);
		}
		else{
			Pile2D destination = getSelectedPile(mouseX, mouseY);
			if(destination == null) return;
			destinationIndex = destination.pile.index();

			try {
				game.performPlay(sourceIndex, destinationIndex, selectedPile.pile);
			} catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
			
			if(game.checkWinner())
				JOptionPane.showMessageDialog(this, "Parabens voce completou todas as fundacoes!");
			
			resetPlay();
		}
	}

	private class Task extends TimerTask{
		@Override
		public void run() {
			repaint();
		}
	}
}
