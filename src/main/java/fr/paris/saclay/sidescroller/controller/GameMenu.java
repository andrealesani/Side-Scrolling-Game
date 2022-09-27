package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.abstraction.GameMenuModel;
import fr.paris.saclay.sidescroller.presentation.GameMenuUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_HEIGHT;
import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_WIDTH;

public class GameMenu extends JPanel implements ActionListener {
    private GameMenuUI ui;
    private GameMenuModel model;

    private MenuButton playButton;

    private MenuButton quitButton;

    private JLabel credits;
    private Timer timer;
    public GameMenu(GamePanel gamePanel, JFrame frame, CardLayout cardLayout)  {
        timer = new Timer(200, this);
        ui = new GameMenuUI();
        model = new GameMenuModel();
        model.addChangeListener(e -> repaint());
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        JPanel menuPanel = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, menuPanel, (SCREEN_HEIGHT- getPreferredSize().height/2)/2,
                SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, menuPanel, (SCREEN_WIDTH- getPreferredSize().width + 100)/2,
                SpringLayout.WEST, this);
        add(menuPanel);
//        menuPanel.setBackground(Color.green);
        playButton = new MenuButton("menu_button_alt", "Play", this);
        quitButton = new MenuButton("menu_button_alt", "Quit", this);
        credits =new JLabel("<html><body style=\"text-align: center\">Credits<br><br>Sonny<br>&<br>Andrea</body></html>");
        credits.setForeground(Color.black);
        credits.setFont(new Font("Monocraft", Font.PLAIN, 28));
        menuPanel.setLayout(new GridBagLayout());
        playButton.setVisible(false);
        quitButton.setVisible(false);
        credits.setVisible(false);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        menuPanel.add(playButton,constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        menuPanel.add(Box.createHorizontalStrut(getPreferredSize().width/6), constraints);
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        menuPanel.add(credits, constraints);
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        menuPanel.add(Box.createVerticalStrut(getPreferredSize().height/10), constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        menuPanel.add(quitButton,constraints);
        menuPanel.setOpaque(false);
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gamePanel.startGame();
                gamePanel.setFocusable(true);
//                gamePanel.requestFocusInWindow();
                gamePanel.transferFocus();
                cardLayout.show(frame.getContentPane(), "Game Panel");
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        ui.paint( graphics, this);
    }



    @Override
    public Dimension getPreferredSize() {
        return model.getPreferredSize();
    }

    @Override
    public Dimension getSize() {
        return model.getSize();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ui.getCurrentAnimation() == 4){
            timer.stop();
            playButton.setVisible(true);
            quitButton.setVisible(true);
            credits.setVisible(true);
        }
        else {
            ui.updateImage();
            repaint();
        }
    }
}
