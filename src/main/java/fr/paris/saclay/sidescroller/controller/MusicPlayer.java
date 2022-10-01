package fr.paris.saclay.sidescroller.controller;

import fr.paris.saclay.sidescroller.abstraction.MusicPlayerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static fr.paris.saclay.sidescroller.utils.Constants.PRIMARY_COLOR;
import static fr.paris.saclay.sidescroller.utils.Constants.SECONDARY_COLOR;

public class MusicPlayer extends JPanel {
    private MusicBar musicBar;

    private MusicPlayerModel model;

    private JLabel currentSongLabel = new JLabel("");

    public MusicPlayer() {
        model = new MusicPlayerModel();
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        setupButtons(constraints);
    }

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

    private void next() {
        if (model.getCurrentSong() + 1 >= model.getSoundtrack().size()) {
            model.setCurrentSong(0);
        } else model.setCurrentSong(model.getCurrentSong() + 1);
        musicBar.getMediaPlayer().stop();
        musicBar.play();
    }

    private void pause() {
        model.setCurrentSongTimestamp(musicBar.getMediaPlayer().getMicrosecondPosition());
        musicBar.getMediaPlayer().stop();
    }

    private void previous() {
        if (musicBar.getMediaPlayer().getMicrosecondPosition() / 1000000 <= 2) {
            if ((model.getCurrentSong() - 1) < 0) {
                model.setCurrentSong(0);
            } else model.setCurrentSong(model.getCurrentSong() - 1);
        } else {
            model.setCurrentSong(model.getCurrentSong());
        }
        musicBar.getMediaPlayer().stop();
        musicBar.play();
    }

    private void resume() {
        if(!musicBar.getMediaPlayer().isRunning()){
            musicBar.getMediaPlayer().setMicrosecondPosition(model.getCurrentSongTimestamp());
            musicBar.getMediaPlayer().start();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(model.getImage(), 0, 0, 200, 100, null);
    }

    public void start() {
        musicBar.getMediaPlayer().start();
    }

    public void stop() {
        musicBar.getMediaPlayer().stop();
    }

    public void close() {
        model.setCurrentSongTimestamp(0);
        musicBar.getMediaPlayer().close();
    }

    public MusicPlayerModel getModel() {
        return model;
    }

    public JLabel getCurrentSongLabel() {
        return currentSongLabel;
    }

    public MusicBar getMusicBar() {
        return musicBar;
    }
}
