package fr.paris.saclay.sidescroller.abstraction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MusicPlayerModel {
    private int songDuration;

    private long currentSongTimestamp;

    private int currentSong = 0;

    private Image image;
    private final List<String> soundtrack;

    public MusicPlayerModel() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/media_player/media_player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentSongTimestamp = 0;
        soundtrack = Arrays.asList(
                "Hordes and Hordes.wav",
                "In the Before Times.wav",
                "Madness All Around Me.wav",
                "Pattlebaddle.wav",
                "Pleasant Adventure.wav",
                "The Circus Is Here.wav",
                "The Sand Level.wav",
                "To the Moon.wav"
        );
    }

    public int getSongDuration() {
        return songDuration;
    }

    public long getCurrentSongTimestamp() {
        return currentSongTimestamp;
    }

    public void setCurrentSongTimestamp(long currentSongTimestamp) {
        this.currentSongTimestamp = currentSongTimestamp;
    }

    public List<String> getSoundtrack() {
        return soundtrack;
    }

    public int getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(int currentSong) {
        this.currentSong = currentSong;
    }

    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }

    public Image getImage() {
        return image;
    }
}
