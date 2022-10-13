package fr.paris.saclay.sidescroller.controllers.components.musicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static fr.paris.saclay.sidescroller.utils.Constants.PRIMARY_COLOR;

/**
 * Music player used for audio playback of the soundtrack.
 */
public class MusicPlayer extends JPanel {
    /**
     * Model reference.
     */
    private final MusicPlayerModel model;
    /**
     * Song title.
     */
    private final JLabel currentSongLabel = new JLabel("");
    /**
     * Music bar displayed below the song title.
     */
    private MusicBar musicBar;

    /**
     * Creates a MusicPlayer instance
     */
    public MusicPlayer() {
        model = new MusicPlayerModel();
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        setupButtons(constraints);
        setOpaque(false);
    }

    /**
     * Setups buttons' position.
     *
     * @param constraints GridBagConstraints from layout.
     */
    private void setupButtons(GridBagConstraints constraints) {
        MusicButton previousButton = new MusicButton("previous");
        MusicButton playButton = new MusicButton("play");
        MusicButton pauseButton = new MusicButton("pause");
        MusicButton nextButton = new MusicButton("next");
        addMouseListeners(previousButton, playButton, pauseButton, nextButton);
        musicBar = new MusicBar(160, 10, this);
        JPanel musicPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        musicPanel.setOpaque(false);
        musicPanel.add(musicBar);
        currentSongLabel.setForeground(Color.decode(PRIMARY_COLOR));
        currentSongLabel.setFont(new Font("Monocraft", Font.PLAIN, 12));
        setupConstraints(constraints, previousButton, playButton, pauseButton, nextButton, musicPanel);
    }

    /**
     * Adds mouse listeners to each media button of the music player.
     *
     * @param previousButton previous song button.
     * @param playButton     play song button.
     * @param pauseButton    pause song button.
     * @param nextButton     next song button.
     */
    private void addMouseListeners(MusicButton previousButton, MusicButton playButton, MusicButton pauseButton, MusicButton nextButton) {
        previousButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                previous();
            }
        });
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                resume();
            }
        });
        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pause();
            }
        });
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                next();
            }
        });
    }

    /**
     * Sets the layout of buttons.
     *
     * @param constraints    GridBagConstraints from layout.
     * @param previousButton previous song button.
     * @param playButton     play song button.
     * @param pauseButton    pause song button.
     * @param nextButton     next song button.
     * @param musicPanel     parent panel.
     */
    private void setupConstraints(GridBagConstraints constraints, MusicButton previousButton, MusicButton playButton, MusicButton pauseButton, MusicButton nextButton, JPanel musicPanel) {
        constraints.gridwidth = 4;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(currentSongLabel, constraints);
        constraints.gridy = 1;
        add(musicPanel, constraints);
        constraints.weightx = 0.2;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(previousButton, constraints);
        constraints.gridx = 1;
        add(playButton, constraints);
        constraints.gridx = 2;
        add(pauseButton, constraints);
        constraints.gridx = 3;
        add(nextButton, constraints);
    }

    /**
     * Go back to previous song.
     */
    private void previous() {
        if (musicBar.getMediaPlayer().getMicrosecondPosition() / 1000000 <= 2) {
            model.setCurrentSong(Math.max((model.getCurrentSong() - 1), 0));
        } else {
            model.setCurrentSong(model.getCurrentSong());
        }
        musicBar.getMediaPlayer().stop();
        musicBar.play();
    }

    /**
     * Resume audio playback.
     */
    private void resume() {
        if (!musicBar.getMediaPlayer().isRunning()) {
            musicBar.getMediaPlayer().setMicrosecondPosition(model.getCurrentSongTimestamp());
            musicBar.getMediaPlayer().start();
        }
    }

    /**
     * Pause audio playback.
     */
    private void pause() {
        model.setCurrentSongTimestamp(musicBar.getMediaPlayer().getMicrosecondPosition());
        musicBar.getMediaPlayer().stop();
    }

    /**
     * Skip to next song.
     */
    private void next() {
        if (model.getCurrentSong() + 1 >= model.getSoundtrack().size()) {
            model.setCurrentSong(0);
        } else model.setCurrentSong(model.getCurrentSong() + 1);
        musicBar.getMediaPlayer().stop();
        musicBar.play();
    }

    /**
     * Draws the MusicPlayer component.
     *
     * @param graphics the rendering environment.
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(model.getImage(), 0, 0, 200, 100, null);
    }

    /**
     * Start audio playback.
     */
    public void start() {
        musicBar.getMediaPlayer().start();
    }

    /**
     * Stop audio playback.
     */
    public void stop() {
        musicBar.getMediaPlayer().stop();
    }

    /**
     * Close audio clip.
     */
    public void close() {
        model.setCurrentSongTimestamp(0);
        musicBar.getMediaPlayer().close();
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public MusicPlayerModel getModel() {
        return model;
    }

    /**
     * Gets current song label.
     *
     * @return the current song label
     */
    public JLabel getCurrentSongLabel() {
        return currentSongLabel;
    }

    /**
     * Gets music bar.
     *
     * @return the music bar
     */
    public MusicBar getMusicBar() {
        return musicBar;
    }
}
