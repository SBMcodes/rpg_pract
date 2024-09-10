package main;

import java.awt.*;

public class EventRect extends Rectangle {

    public int eventRectDefaultX,eventRectDefaultY;
    // For one time events
    public boolean eventDone = false;
    // If event has been activated & only will be
    // deactivated once player moves a certain distance
    public boolean eventActivated = false;
    public boolean init=false;

    public EventRect(){
        this.init=true;
    }
}
