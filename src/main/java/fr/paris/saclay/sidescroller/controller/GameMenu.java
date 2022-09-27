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
    private final GameMenuUI ui;
    private final GameMenuModel model;

    private final JPanel menuPanel;

    public GameMenu(RPGSidescroller frame) {
        ui = new GameMenuUI();
        model = new GameMenuModel();
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        menuPanel = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, menuPanel, (SCREEN_HEIGHT - getPreferredSize().height / 2) / 2,
                SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, menuPanel, (SCREEN_WIDTH - getPreferredSize().width + 100) / 2,
                SpringLayout.WEST, this);
        //create buttons
        initializeMenu(frame);
        // set grid layout menu
        menuPanel.setLayout(new GridBagLayout());
        setNormalGridConstraints();
        add(menuPanel);
        model.getTimer().start();
    }

    private void initializeMenu(RPGSidescroller frame) {
        model.setTimer(new Timer(200, this));
        model.setPlayButton(new MenuButton("menu_button_alt", "Play", this));
        model.setQuitButton(new MenuButton("menu_button_alt", "Quit", this));
        model.setResumeButton(new MenuButton("menu_button_alt", "Resume", this));
        model.setQuitToMenuButton(new MenuButton("menu_button_alt", "Menu", this));
        model.setCredits(new JLabel("<html><body style=\"text-align: center\">Credits<br><br>Sonny<br>&<br>Andrea</body></html>"));
        model.getCredits().setForeground(Color.black);
        model.getCredits().setFont(new Font("Monocraft", Font.PLAIN, 28));
        model.getPlayButton().setVisible(false);
        model.getQuitButton().setVisible(false);
        model.getCredits().setVisible(false);
        model.getPlayButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                frame.getGamePanel().startGame();
                frame.getGamePanel().setRunning(true);
                frame.getGamePanel().setFocusable(true);
                frame.getGamePanel().transferFocus();
                frame.getGlassPane().setVisible(false);
                repaint();
            }
        });

        model.getResumeButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                frame.getGamePanel().play();
                frame.getGamePanel().setRunning(true);
                frame.getGamePanel().setFocusable(true);
                frame.getGamePanel().transferFocus();
                frame.getGlassPane().setVisible(false);
                repaint();
            }
        });

        model.getQuitToMenuButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                frame.getGamePanel().stop();
                setPauseMenu(false);
                setNormalGridConstraints();
                repaint();
                revalidate();
            }
        });

        model.getQuitButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });
    }

    public void setNormalGridConstraints() {
        menuPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        menuPanel.add(model.getPlayButton(), constraints);
        constraints.gridx = 1;
        menuPanel.add(Box.createHorizontalStrut(getPreferredSize().width / 6), constraints);
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        menuPanel.add(model.getCredits(), constraints);
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        menuPanel.add(Box.createVerticalStrut(getPreferredSize().height / 10), constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        menuPanel.add(model.getQuitButton(), constraints);
        menuPanel.setOpaque(false);
    }

    public void setPausedGridConstraints() {
        menuPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        menuPanel.add(model.getResumeButton(), constraints);
        constraints.gridy = 1;
        menuPanel.add(Box.createVerticalStrut(getPreferredSize().height / 10), constraints);
        constraints.gridy = 2;
        menuPanel.add(model.getQuitToMenuButton(), constraints);
        menuPanel.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        ui.paint(graphics, this);
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
        if (ui.getCurrentAnimation() == 4) {
            ui.displayNormalMenu(model);
        } else {
            ui.updateImage();
            repaint();
        }
    }

    public boolean isPauseMenu() {
        return model.isPauseMenu();
    }

    public void setPauseMenu(boolean pauseMenu) {
        model.setPauseMenu(pauseMenu);
    }

    public GameMenuModel getModel() {
        return model;
    }

}
