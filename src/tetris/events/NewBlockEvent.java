package tetris.events;

import java.util.EventObject;
/**
 * Event dat aangeroepen wordt wanneer er een nieuwe block is.
 */
public class NewBlockEvent extends EventObject {

  public NewBlockEvent(Object source) {
    super(source);
  }
}
