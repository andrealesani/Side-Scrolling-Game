package fr.paris.saclay.sidescroller.controllers.components.menu;

import fr.paris.saclay.sidescroller.controllers.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class GameMenu extends JPanel implements ActionListener {
    private final GameMenuUI ui;
    private final GameMenuModel model;

    private final JPanel menuPanel;

    public GameMenu(MainFrame frame) {
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

    private void initializeMenu(MainFrame frame) {
        model.setTimer(new Timer(200, this));
        model.setPlayButton(new MenuButton("menu_button", "Play", this));
        model.setQuitButton(new MenuButton("menu_button", "Quit", this));
        model.setResumeButton(new MenuButton("menu_button", "Resume", this));
        model.setQuitToMenuButton(new MenuButton("menu_button", "Menu", this));
        model.setStartGameButton(new MenuButton("menu_button", "Start", this));
        model.setBackButton(new MenuButton("menu_button", "Back", this));
        model.setCredits(new JLabel("<html><body style=\"text-align: center\">Credits<br><br>Andrea<br>&<br>Sonny</body></html>"));
        model.getCredits().setForeground(Color.decode(PRIMARY_COLOR));
        model.getCredits().setFont(new Font("Monocraft", Font.PLAIN, 28));
        model.setNextAvatarButton(new SelectionMenuButton("next"));
        model.setPreviousAvatarButton(new SelectionMenuButton("previous"));
        model.setNextBackgroundButton(new SelectionMenuButton("next"));
        model.setPreviousBackgroundButton(new SelectionMenuButton("previous"));
        model.getPlayButton().setVisible(false);
        model.getQuitButton().setVisible(false);
        model.getCredits().setVisible(false);
        model.getQuitToMenuButton().setVisible(false);
        model.getResumeButton().setVisible(false);
        model.getNextBackgroundButton().setVisible(false);
        model.getPreviousBackgroundButton().setVisible(false);
        model.getNextAvatarButton().setVisible(false);
        model.getPreviousAvatarButton().setVisible(false);
        model.getStartGameButton().setVisible(false);
        model.getPlayButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                model.getNextBackgroundButton().setVisible(true);
                model.getPreviousBackgroundButton().setVisible(true);
                model.getNextAvatarButton().setVisible(true);
                model.getPreviousAvatarButton().setVisible(true);
                model.getStartGameButton().setVisible(true);
                model.getPlayButton().setVisible(false);
                model.getQuitButton().setVisible(false);
                model.getBackButton().getModel().setPressed(false);
                model.getBackButton().setIcon(new ImageIcon(GrayFilter.createDisabledImage(model.getPlayButton().getImage())));
                model.getBackButton().setForeground(Color.white);
                setSelectionGridConstraints();
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
                quitToMenu(frame);
            }
        });

        model.getQuitButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });

        model.getNextBackgroundButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                changePreview(model.getCurrentThemeSelection(), true, true);
            }
        });

        model.getNextAvatarButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                changePreview(model.getCurrentPlayerSelection(), false, true);
            }
        });

        model.getPreviousBackgroundButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                changePreview(model.getCurrentThemeSelection(), true, false);
            }
        });

        model.getPreviousAvatarButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                changePreview(model.getCurrentPlayerSelection(), false, false);
            }
        });

        model.getStartGameButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                model.getPlayButton().getModel().setPressed(false);
                model.getPlayButton().setIcon(new ImageIcon(GrayFilter.createDisabledImage(model.getPlayButton().getImage())));
                model.getPlayButton().setForeground(Color.white);
                frame.getGamePanel().startGame();
                frame.getGamePanel().setRunning(true);
                frame.getGamePanel().setFocusable(true);
                frame.getGamePanel().transferFocus();
                frame.getGlassPane().setVisible(false);
                repaint();
            }
        });

        model.getBackButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                model.getPlayButton().getModel().setPressed(false);
                model.getPlayButton().setIcon(new ImageIcon(GrayFilter.createDisabledImage(model.getPlayButton().getImage())));
                model.getPlayButton().setForeground(Color.white);
                model.getPlayButton().setVisible(true);
                model.getQuitButton().setVisible(true);
                setNormalGridConstraints();
                repaint();
            }
        });
    }

    private void changePreview(int currentSelection, boolean isBackground, boolean isNext) {
        int length = model.getBackgroundThemes().size();
        if (!isBackground) {
            length = model.getPlayerThemes().size();
        }
        if (isNext) {
            if (currentSelection == length - 1) {
                currentSelection = 0;
            } else currentSelection++;
        } else {
            if (currentSelection == 0) {
                currentSelection = length - 1;
            } else currentSelection--;
        }
        if (isBackground) {
            model.setCurrentThemeSelection(currentSelection);
            model.getBackgroundPreview().updateTheme(model.getBackgroundThemes().get(currentSelection));
        } else {
            model.setCurrentPlayerSelection(currentSelection);
            model.getAvatarPreview().updateTheme(model.getPlayerThemes().get(currentSelection));
        }
    }

    public void quitToMenu(MainFrame frame) {
        frame.getGamePanel().stop();
        setPauseMenu(false);
        model.getQuitToMenuButton().setIcon(new ImageIcon(GrayFilter.createDisabledImage(model.getQuitToMenuButton().getImage())));
        model.getQuitToMenuButton().setForeground(Color.white);
        model.getQuitToMenuButton().getModel().setPressed(false);
        model.getPlayButton().setVisible(true);
        model.getQuitButton().setVisible(true);
        setNormalGridConstraints();
        repaint();
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
        menuPanel.add(Box.createHorizontalStrut(getPreferredSize().width / 5), constraints);
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

    public void setSelectionGridConstraints() {
        menuPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        menuPanel.add(model.getPreviousBackgroundButton(), constraints);
        constraints.gridx = 1;
        constraints.gridwidth = 3;
        menuPanel.add(model.getBackgroundPreview(), constraints);
        constraints.gridwidth = 1;
        constraints.gridx = 4;
        menuPanel.add(model.getNextBackgroundButton(), constraints);
        constraints.gridx = 5;
        menuPanel.add(Box.createHorizontalStrut(100), constraints);
        constraints.gridx = 6;
        constraints.gridwidth = 3;
        JLabel levelSelection = new JLabel("Select a level");
        levelSelection.setForeground(Color.decode(PRIMARY_COLOR));
        levelSelection.setFont(new Font("Monocraft", Font.PLAIN, 18));
        levelSelection.setHorizontalAlignment(JLabel.LEFT);
        menuPanel.add(levelSelection, constraints);
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.gridwidth = 6;
        menuPanel.add(Box.createVerticalStrut(10), constraints);
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        menuPanel.add(model.getPreviousAvatarButton(), constraints);
        constraints.gridx = 1;
        constraints.gridwidth = 3;
        menuPanel.add(model.getAvatarPreview(), constraints);
        constraints.gridwidth = 1;
        constraints.gridx = 4;
        menuPanel.add(model.getNextAvatarButton(), constraints);
        constraints.gridx = 5;
        menuPanel.add(Box.createHorizontalStrut(100), constraints);
        constraints.gridx = 6;
        constraints.gridwidth = 3;
        JLabel avatarSelection = new JLabel("Select an avatar");
        avatarSelection.setForeground(Color.decode(PRIMARY_COLOR));
        avatarSelection.setFont(new Font("Monocraft", Font.PLAIN, 18));
        avatarSelection.setHorizontalAlignment(JLabel.LEFT);
        menuPanel.add(avatarSelection, constraints);
        constraints.gridwidth = 9;
        constraints.gridy = 3;
        constraints.gridx = 0;
        JPanel menuButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        menuButtons.setOpaque(false);
        menuPanel.add(menuButtons, constraints);
        menuButtons.add(model.getBackButton());
        menuButtons.add(Box.createHorizontalStrut(50));
        menuButtons.add(model.getStartGameButton());
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
