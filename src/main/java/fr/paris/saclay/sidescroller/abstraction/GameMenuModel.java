package fr.paris.saclay.sidescroller.abstraction;

import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static fr.paris.saclay.sidescroller.utils.Constants.*;

public class GameMenuModel {
    private final List<ChangeListener> changeListeners = new ArrayList<>();
    public GameMenuModel() {
    }

    public void addChangeListener(ChangeListener changeListener){
        changeListeners.add(changeListener);

    }
    public Dimension getPreferredSize() {
        return new Dimension(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
    }

    public Dimension getSize() {
        return new Dimension(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
    }
}
