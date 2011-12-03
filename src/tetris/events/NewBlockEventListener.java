package tetris.events;

import java.util.EventListener;

public interface NewBlockEventListener extends EventListener {
  public void newBlockEventOccurred(NewBlockEvent e);
}
