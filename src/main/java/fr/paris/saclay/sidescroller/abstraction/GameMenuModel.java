package fr.paris.saclay.sidescroller.abstraction;

import fr.paris.saclay.sidescroller.controller.MenuButton;

import javax.swing.*;
import java.awt.*;

import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_HEIGHT;
import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_WIDTH;

public class GameMenuModel {

    private boolean isPauseMenu = false;

    private MenuButton playButton;

    private MenuButton quitButton;

    private MenuButton resumeButton;

    private MenuButton quitToMenuButton;

    private JLabel credits;

    private Timer timer;

    public Dimension getPreferredSize() {
        return new Dimension(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
    }

    public Dimension getSize() {
        return new Dimension(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
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

    public void setPlayButton(MenuButton playButton) {
        this.playButton = playButton;
    }

    public MenuButton getQuitButton() {
        return quitButton;
    }

    public void setQuitButton(MenuButton quitButton) {
        this.quitButton = quitButton;
    }

    public MenuButton getResumeButton() {
        return resumeButton;
    }

    public void setResumeButton(MenuButton resumeButton) {
        this.resumeButton = resumeButton;
    }

    public MenuButton getQuitToMenuButton() {
        return quitToMenuButton;
    }

    public void setQuitToMenuButton(MenuButton quitToMenuButton) {
        this.quitToMenuButton = quitToMenuButton;
    }

    public JLabel getCredits() {
        return credits;
    }

    public void setCredits(JLabel credits) {
        this.credits = credits;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
