package fr.paris.saclay.sidescroller.controllers;

import fr.paris.saclay.sidescroller.controllers.components.GamePanel;
import fr.paris.saclay.sidescroller.controllers.components.menu.GameMenu;
import fr.paris.saclay.sidescroller.controllers.components.musicPlayer.MusicPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_HEIGHT;
import static fr.paris.saclay.sidescroller.utils.Constants.SCREEN_WIDTH;

public class MainFrame extends JFrame {

    private final GamePanel gamePanel;

    private final GameMenu gameMenu;

    private final MusicPlayer musicPlayer;

    public MainFrame() {
        super("RPG Side Scroller");
        //set application icon
        List<Image> icons = new ArrayList<>();
        try {
            icons.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/icon16x16.png")));
            icons.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/icon32x32.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setIconImages(icons);
        //set macOS taskbar icon
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.startsWith("mac os x")) {
            try {
                Image icon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/icon32x32.png"));
                Taskbar.getTaskbar().setIconImage(icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        registerFont();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameMenu = new GameMenu(this);
        musicPlayer = new MusicPlayer();
        gamePanel = new GamePanel(this);
        JLayeredPane layeredPane = createLayeredPane();
        setLayeredPane(layeredPane);
        setGlassPane(gameMenu);
        getGlassPane().setVisible(true);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT + 25)); // +25 is needed because JFrame takes into account the title bar
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void registerFont() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("fonts/Monocraft.otf")));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JLayeredPane createLayeredPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        musicPlayer.setBounds(SCREEN_WIDTH / 2 - 100, 10, 200, 100);
        layeredPane.add(musicPlayer, JLayeredPane.POPUP_LAYER);
        gamePanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.setVisible(true);
        return layeredPane;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public GameMenu getGameMenu() {
        return gameMenu;
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }
}
