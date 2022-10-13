package fr.paris.saclay.sidescroller.controllers.components.menu;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_HEIGHT;
import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_WIDTH;

public class GameMenuModel {
    /**
     * Background themes.
     */
    private final List<String> backgroundThemes = Arrays.asList("grass", "mushroom", "castle");
    /**
     * Avatar themes.
     */
    private final List<String> playerThemes = Arrays.asList("pink", "blue", "green", "yellow");
    /**
     * Identifies if current menu is the pause menu.
     */
    private boolean isPauseMenu = false;
    /**
     * Play game and proceed to selection menu button.
     */
    private MenuButton playButton;
    /**
     * Quit game button.
     */
    private MenuButton quitButton;
    /**
     * Resume game button (pause menu).
     */
    private MenuButton resumeButton;
    /**
     * Quit to main menu button (pause menu).
     */
    private MenuButton quitToMenuButton;
    /**
     * Start game button (selection menu).
     */
    private MenuButton startGameButton;
    /**
     * Back button (selection menu).
     */
    private MenuButton backButton;
    /**
     * Next background preview button (selection menu).
     */
    private SelectionMenuButton nextBackgroundButton;
    /**
     * Previous background preview button (selection menu).
     */
    private SelectionMenuButton previousBackgroundButton;
    /**
     * Next avatar preview button (selection menu).
     */
    private SelectionMenuButton nextAvatarButton;
    /**
     * Previous background avatar button (selection menu).
     */
    private SelectionMenuButton previousAvatarButton;
    /**
     * Credits of the application.
     */
    private JLabel credits;
    /**
     * Animation timer.
     */
    private Timer timer;
    /**
     * Current background selection.
     */
    private int currentThemeSelection = 0;
    /**
     * Current background preview.
     */
    private final ImagePreview backgroundPreview = new ImagePreview(130, 70, backgroundThemes.get(currentThemeSelection), true);
    /**
     * Current avatar selection.
     */
    private int currentPlayerSelection = 0;
    /**
     * Current avatar preview.
     */
    private final ImagePreview avatarPreview = new ImagePreview(130, 100, playerThemes.get(currentPlayerSelection), false);

    /**
     * Gets preferred size.
     *
     * @return the preferred size
     */
    public Dimension getPreferredSize() {
        return new Dimension(SCREEN_WIDTH / 2 + 100, SCREEN_HEIGHT / 2 + 100);
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public Dimension getSize() {
        return new Dimension(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
    }

    /**
     * Is pause menu boolean.
     *
     * @return the boolean
     */
    public boolean isPauseMenu() {
        return isPauseMenu;
    }

    /**
     * Sets pause menu.
     *
     * @param pauseMenu the pause menu
     */
    public void setPauseMenu(boolean pauseMenu) {
        isPauseMenu = pauseMenu;
    }

    /**
     * Gets play button.
     *
     * @return the play button
     */
    public MenuButton getPlayButton() {
        return playButton;
    }

    /**
     * Sets play button.
     *
     * @param playButton the play button
     */
    public void setPlayButton(MenuButton playButton) {
        this.playButton = playButton;
    }

    /**
     * Gets quit button.
     *
     * @return the quit button
     */
    public MenuButton getQuitButton() {
        return quitButton;
    }

    /**
     * Sets quit button.
     *
     * @param quitButton the quit button
     */
    public void setQuitButton(MenuButton quitButton) {
        this.quitButton = quitButton;
    }

    /**
     * Gets resume button.
     *
     * @return the resume button
     */
    public MenuButton getResumeButton() {
        return resumeButton;
    }

    /**
     * Sets resume button.
     *
     * @param resumeButton the resume button
     */
    public void setResumeButton(MenuButton resumeButton) {
        this.resumeButton = resumeButton;
    }

    /**
     * Gets quit to menu button.
     *
     * @return the quit to menu button
     */
    public MenuButton getQuitToMenuButton() {
        return quitToMenuButton;
    }

    /**
     * Sets quit to menu button.
     *
     * @param quitToMenuButton the quit to menu button
     */
    public void setQuitToMenuButton(MenuButton quitToMenuButton) {
        this.quitToMenuButton = quitToMenuButton;
    }

    /**
     * Gets credits.
     *
     * @return the credits
     */
    public JLabel getCredits() {
        return credits;
    }

    /**
     * Sets credits.
     *
     * @param credits the credits
     */
    public void setCredits(JLabel credits) {
        this.credits = credits;
    }

    /**
     * Gets timer.
     *
     * @return the timer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Sets timer.
     *
     * @param timer the timer
     */
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    /**
     * Gets background themes.
     *
     * @return the background themes
     */
    public List<String> getBackgroundThemes() {
        return backgroundThemes;
    }

    /**
     * Gets player themes.
     *
     * @return the player themes
     */
    public List<String> getPlayerThemes() {
        return playerThemes;
    }

    /**
     * Gets current theme selection.
     *
     * @return the current theme selection
     */
    public int getCurrentThemeSelection() {
        return currentThemeSelection;
    }

    /**
     * Sets current theme selection.
     *
     * @param currentThemeSelection the current theme selection
     */
    public void setCurrentThemeSelection(int currentThemeSelection) {
        this.currentThemeSelection = currentThemeSelection;
    }

    /**
     * Gets current player selection.
     *
     * @return the current player selection
     */
    public int getCurrentPlayerSelection() {
        return currentPlayerSelection;
    }

    /**
     * Sets current player selection.
     *
     * @param currentPlayerSelection the current player selection
     */
    public void setCurrentPlayerSelection(int currentPlayerSelection) {
        this.currentPlayerSelection = currentPlayerSelection;
    }

    /**
     * Gets next background button.
     *
     * @return the next background button
     */
    public SelectionMenuButton getNextBackgroundButton() {
        return nextBackgroundButton;
    }

    /**
     * Sets next background button.
     *
     * @param nextBackgroundButton the next background button
     */
    public void setNextBackgroundButton(SelectionMenuButton nextBackgroundButton) {
        this.nextBackgroundButton = nextBackgroundButton;
    }

    /**
     * Gets previous background button.
     *
     * @return the previous background button
     */
    public SelectionMenuButton getPreviousBackgroundButton() {
        return previousBackgroundButton;
    }

    /**
     * Sets previous background button.
     *
     * @param previousBackgroundButton the previous background button
     */
    public void setPreviousBackgroundButton(SelectionMenuButton previousBackgroundButton) {
        this.previousBackgroundButton = previousBackgroundButton;
    }

    /**
     * Gets next avatar button.
     *
     * @return the next avatar button
     */
    public SelectionMenuButton getNextAvatarButton() {
        return nextAvatarButton;
    }

    /**
     * Sets next avatar button.
     *
     * @param nextAvatarButton the next avatar button
     */
    public void setNextAvatarButton(SelectionMenuButton nextAvatarButton) {
        this.nextAvatarButton = nextAvatarButton;
    }

    /**
     * Gets previous avatar button.
     *
     * @return the previous avatar button
     */
    public SelectionMenuButton getPreviousAvatarButton() {
        return previousAvatarButton;
    }

    /**
     * Sets previous avatar button.
     *
     * @param previousAvatarButton the previous avatar button
     */
    public void setPreviousAvatarButton(SelectionMenuButton previousAvatarButton) {
        this.previousAvatarButton = previousAvatarButton;
    }

    /**
     * Gets background preview.
     *
     * @return the background preview
     */
    public ImagePreview getBackgroundPreview() {
        return backgroundPreview;
    }

    /**
     * Gets avatar preview.
     *
     * @return the avatar preview
     */
    public ImagePreview getAvatarPreview() {
        return avatarPreview;
    }

    /**
     * Gets start game button.
     *
     * @return the start game button
     */
    public MenuButton getStartGameButton() {
        return startGameButton;
    }

    /**
     * Sets start game button.
     *
     * @param startGameButton the start game button
     */
    public void setStartGameButton(MenuButton startGameButton) {
        this.startGameButton = startGameButton;
    }

    /**
     * Gets back button.
     *
     * @return the back button
     */
    public MenuButton getBackButton() {
        return backButton;
    }

    /**
     * Sets back button.
     *
     * @param backButton the back button
     */
    public void setBackButton(MenuButton backButton) {
        this.backButton = backButton;
    }
}
