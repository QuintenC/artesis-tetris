package tetris.events;

import java.util.EventListener;

public interface NewScoreEventListener extends EventListener {
    public void newScoreEventOccurred(NewScoreEvent e);
}
