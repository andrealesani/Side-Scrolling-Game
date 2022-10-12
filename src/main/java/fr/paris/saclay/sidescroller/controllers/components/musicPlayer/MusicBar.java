package fr.paris.saclay.sidescroller.controllers.components.musicPlayer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;

import static fr.paris.saclay.sidescroller.utils.Constants.PRIMARY_COLOR;
import static fr.paris.saclay.sidescroller.utils.Constants.SECONDARY_COLOR;

public class MusicBar extends JPanel {


    private final MusicPlayer player;

    private Clip mediaPlayer;

    public MusicBar(int width, int height, MusicPlayer player) {
        this.player = player;
        try {
            mediaPlayer = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(width, height));
    }

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

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.decode(PRIMARY_COLOR));
        graphics2D.fillRect(getX(), getY(), getWidth(), 3);
        graphics2D.fillRect(getDeltaSong(), getY() - 5, 5, 10);
        graphics2D.setColor(Color.decode(SECONDARY_COLOR));
        graphics2D.fillRect(getDeltaSong() + 1, getY() - 4, 3, 8);
    }

    public int getDeltaSong() {
        double songProgress = mediaPlayer.getMicrosecondPosition() / 1000000.0;
        if (songProgress >= player.getModel().getSongDuration()) {
            mediaPlayer.setMicrosecondPosition(0);
        }
        if (player.getModel().getSongDuration() != 0) {
            return (int) (songProgress * getWidth() / player.getModel().getSongDuration());
        } else return 0;
    }

    public Clip getMediaPlayer() {
        return mediaPlayer;
    }
}
