
package tetris.events;

import java.util.EventObject;


public class NewScoreEvent extends EventObject {
    public NewScoreEvent(Object source){
        super(source);
    }
}
