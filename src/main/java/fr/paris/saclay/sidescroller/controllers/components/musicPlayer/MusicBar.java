package fr.paris.saclay.sidescroller.controllers.components.musicPlayer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;

import static fr.paris.saclay.sidescroller.utils.Constants.PRIMARY_COLOR;
import static fr.paris.saclay.sidescroller.utils.Constants.SECONDARY_COLOR;

/**
 * Song bar displaying the current progress of the song.
 */
public class MusicBar extends JPanel {
    /**
     * Music player reference.
     */
    private final MusicPlayer player;
    /**
     * CLip responsible for audio mixing.
     */
    private Clip mediaPlayer;

    /**
     * Creates a MusicBar instance, starting the audio playback.
     *
     * @param width  width of the component.
     * @param height height of the component.
     * @param player music player.
     */
    public MusicBar(int width, int height, MusicPlayer player) {
        this.player = player;
        try {
            mediaPlayer = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Starts audio playback when level starts.
     */
    public void play() {
        try {
            String song = player.getModel().getSoundtrack().get(player.getModel().getCurrentSong());
            String pathname = "sound/";
            AudioInputStream currentSong = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(pathname + song)));
            player.getCurrentSongLabel().setText(song.split(".wav")[0]);
            mediaPlayer.close();
            mediaPlayer.open(currentSong);
            mediaPlayer.loop(Clip.LOOP_CONTINUOUSLY);
            AudioFormat format = currentSong.getFormat();
            long frames = currentSong.getFrameLength();
            player.getModel().setSongDuration((int) Math.ceil((frames + 0.0) / format.getFrameRate()));
            player.getModel().setCurrentSongTimestamp(0);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    /**
     * Draws the MusicBar, displaying current progress with a little vertical rectangle.
     *
     * @param graphics the rendering environment.
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.decode(PRIMARY_COLOR));
        graphics2D.fillRect(getX(), getY(), getWidth(), 3);
        graphics2D.fillRect(getDeltaSong(), getY() - 5, 5, 10);
        graphics2D.setColor(Color.decode(SECONDARY_COLOR));
        graphics2D.fillRect(getDeltaSong() + 1, getY() - 4, 3, 8);
    }

    /**
     * Get current song timestamp.
     *
     * @return current song progress.
     */
    public int getDeltaSong() {
        double songProgress = mediaPlayer.getMicrosecondPosition() / 1000000.0;
        if (songProgress >= player.getModel().getSongDuration()) {
            mediaPlayer.setMicrosecondPosition(0);
        }
        if (player.getModel().getSongDuration() != 0) {
            return (int) (songProgress * getWidth() / player.getModel().getSongDuration());
        } else return 0;
    }

    /**
     * Gets media player.
     *
     * @return the media player
     */
    public Clip getMediaPlayer() {
        return mediaPlayer;
    }
}
