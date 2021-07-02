package app.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;

public class MainWindow extends JFrame{
    private final int WIDTH  = 500;
    private final int HEIGHT = 500;
    private final int cardPanelSize = 100;

    private JPanel mainScreen;
    private List<Screen> screens;

    public MainWindow(List<Screen> screens){
        this.screens = screens;
        initComponents();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

    private void initComponents(){
        mainScreen = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 20));
        mainScreen.setBackground(new Color(1, 126, 198));
		mainScreen.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		mainScreen.setDoubleBuffered(true);
		mainScreen.setFocusable(true);


        for(Screen screen: this.screens){
            JPanel cardPanel = getGameThumbnail(screen);
            cardPanel.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    changeScreen(screen);
                }
                
            });
            mainScreen.add(cardPanel);
        }
        this.add(mainScreen);
    }


    private JPanel getGameThumbnail(Screen screen){
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(null);
        cardPanel.setBackground(screen.primaryColor());
        cardPanel.setPreferredSize(new Dimension(cardPanelSize, cardPanelSize));
        
        JLabel title = new JLabel(screen.name());
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBounds(0, 0, cardPanelSize, 30);
        title.setOpaque(true);
        title.setBackground(screen.secondaryColor());
        title.setForeground(Color.WHITE);
        cardPanel.add(title);

        return cardPanel;
    }

    private void changeScreen(Screen screen){
        this.remove(mainScreen);
        screen.restart();
        this.add(screen);
        this.validate();
        this.repaint();
    }
}
