package fr.paris.saclay.sidescroller.controllers.components.menu;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_HEIGHT;
import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_WIDTH;

public class GameMenuModel {

    private final java.util.List<String> backgroundThemes = Arrays.asList("grass", "mushroom", "castle");
    private final java.util.List<String> playerThemes = Arrays.asList("pink", "blue", "green", "yellow");
    private boolean isPauseMenu = false;
    private MenuButton playButton;
    private MenuButton quitButton;
    private MenuButton resumeButton;
    private MenuButton quitToMenuButton;
    private MenuButton startGameButton;
    private MenuButton backButton;
    private SelectionMenuButton nextBackgroundButton;
    private SelectionMenuButton previousBackgroundButton;
    private SelectionMenuButton nextAvatarButton;
    private SelectionMenuButton previousAvatarButton;
    private JLabel credits;
    private Timer timer;
    private int currentThemeSelection = 0;
    private final ImagePreview backgroundPreview = new ImagePreview(130, 70, backgroundThemes.get(currentThemeSelection), true);
    private int currentPlayerSelection = 0;
    private final ImagePreview avatarPreview = new ImagePreview(130, 100, playerThemes.get(currentPlayerSelection), false);

    public Dimension getPreferredSize() {
        return new Dimension(SCREEN_WIDTH / 2 + 100, SCREEN_HEIGHT / 2 + 100);
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

    public java.util.List<String> getBackgroundThemes() {
        return backgroundThemes;
    }

    public java.util.List<String> getPlayerThemes() {
        return playerThemes;
    }

    public int getCurrentThemeSelection() {
        return currentThemeSelection;
    }

    public void setCurrentThemeSelection(int currentThemeSelection) {
        this.currentThemeSelection = currentThemeSelection;
    }

    public int getCurrentPlayerSelection() {
        return currentPlayerSelection;
    }

    public void setCurrentPlayerSelection(int currentPlayerSelection) {
        this.currentPlayerSelection = currentPlayerSelection;
    }

    public SelectionMenuButton getNextBackgroundButton() {
        return nextBackgroundButton;
    }

    public void setNextBackgroundButton(SelectionMenuButton nextBackgroundButton) {
        this.nextBackgroundButton = nextBackgroundButton;
    }

    public SelectionMenuButton getPreviousBackgroundButton() {
        return previousBackgroundButton;
    }

    public void setPreviousBackgroundButton(SelectionMenuButton previousBackgroundButton) {
        this.previousBackgroundButton = previousBackgroundButton;
    }

    public SelectionMenuButton getNextAvatarButton() {
        return nextAvatarButton;
    }

    public void setNextAvatarButton(SelectionMenuButton nextAvatarButton) {
        this.nextAvatarButton = nextAvatarButton;
    }

    public SelectionMenuButton getPreviousAvatarButton() {
        return previousAvatarButton;
    }

    public void setPreviousAvatarButton(SelectionMenuButton previousAvatarButton) {
        this.previousAvatarButton = previousAvatarButton;
    }

    public ImagePreview getBackgroundPreview() {
        return backgroundPreview;
    }

    public ImagePreview getAvatarPreview() {
        return avatarPreview;
    }

    public MenuButton getStartGameButton() {
        return startGameButton;
    }

    public void setStartGameButton(MenuButton startGameButton) {
        this.startGameButton = startGameButton;
    }

    public MenuButton getBackButton() {
        return backButton;
    }

    public void setBackButton(MenuButton backButton) {
        this.backButton = backButton;
    }
}
