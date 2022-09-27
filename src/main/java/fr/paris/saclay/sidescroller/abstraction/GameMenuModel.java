package fr.paris.saclay.sidescroller.abstraction;

import fr.paris.saclay.sidescroller.controller.MenuButton;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class GameMenuModel {
    private final List<ChangeListener> changeListeners = new ArrayList<>();

    private boolean isPauseMenu = false;
    
    private MenuButton playButton;

    private MenuButton quitButton;

    private MenuButton resumeButton;

    private MenuButton quitToMenuButton;

    private JLabel credits;
    
    private Timer timer;

    public void addChangeListener(ChangeListener changeListener){
        changeListeners.add(changeListener);

    }
    public Dimension getPreferredSize() {
        return new Dimension(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
    }

    public Dimension getSize() {
        return new Dimension(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
    }

    public boolean isPauseMenu() {
        return isPauseMenu;
    }

    public void setPauseMenu(boolean pauseMenu) {
        isPauseMenu = pauseMenu;
    }

    public MenuButton getPlayButton() {
        return playButton;
    }

    public MenuButton getQuitButton() {
        return quitButton;
    }

    public MenuButton getResumeButton() {
        return resumeButton;
    }

    public MenuButton getQuitToMenuButton() {
        return quitToMenuButton;
    }

    public JLabel getCredits() {
        return credits;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setPlayButton(MenuButton playButton) {
        this.playButton = playButton;
    }

    public void setQuitButton(MenuButton quitButton) {
        this.quitButton = quitButton;
    }

    public void setResumeButton(MenuButton resumeButton) {
        this.resumeButton = resumeButton;
    }

    public void setQuitToMenuButton(MenuButton quitToMenuButton) {
        this.quitToMenuButton = quitToMenuButton;
    }

    public void setCredits(JLabel credits) {
        this.credits = credits;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
