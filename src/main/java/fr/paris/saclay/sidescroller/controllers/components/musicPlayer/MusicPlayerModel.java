package fr.paris.saclay.sidescroller.controllers.components.musicPlayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Model of MusicPlayer class.
 */
public class MusicPlayerModel {
    /**
     * Soundtrack.
     */
    private final List<String> soundtrack;
    /**
     * Total song duration.
     */
    private int songDuration;
    /**
     * Current song timestamp
     */
    private long currentSongTimestamp;
    /**
     * Current song index.
     */
    private int currentSong = 0;
    /**
     * Media player background image.
     */
    private Image image;

    /**
     * Creates a MusicPlayerModel instance, assigning songs to soundtrack's list.
     */
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

    /**
     * Gets song duration.
     *
     * @return the song duration
     */
    public int getSongDuration() {
        return songDuration;
    }

    /**
     * Sets song duration.
     *
     * @param songDuration the song duration
     */
    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }

    /**
     * Gets current song timestamp.
     *
     * @return the current song timestamp
     */
    public long getCurrentSongTimestamp() {
        return currentSongTimestamp;
    }

    /**
     * Sets current song timestamp.
     *
     * @param currentSongTimestamp the current song timestamp
     */
    public void setCurrentSongTimestamp(long currentSongTimestamp) {
        this.currentSongTimestamp = currentSongTimestamp;
    }

    /**
     * Gets soundtrack.
     *
     * @return the soundtrack
     */
    public List<String> getSoundtrack() {
        return soundtrack;
    }

    /**
     * Gets current song.
     *
     * @return the current song
     */
    public int getCurrentSong() {
        return currentSong;
    }

    /**
     * Sets current song.
     *
     * @param currentSong the current song
     */
    public void setCurrentSong(int currentSong) {
        this.currentSong = currentSong;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public Image getImage() {
        return image;
    }
}
