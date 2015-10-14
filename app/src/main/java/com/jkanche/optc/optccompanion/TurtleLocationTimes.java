package com.jkanche.optc.optccompanion;

/**
 * Created by jayar on 10/13/2015.
 */
public class TurtleLocationTimes {

    String digitID;
    String turtleTime;
    boolean notifySwitch;
    String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        location = location;
    }

    public String getDigitID() {
        return digitID;
    }

    public void setDigitID(String digitID) {
        this.digitID = digitID;
    }

    public String getTurtleTime() {
        return turtleTime;
    }

    public void setTurtleTime(String turtleTime) {
        this.turtleTime = turtleTime;
    }

    public boolean isNotifySwitch() {
        return notifySwitch;
    }

    public void toggleNotifySwitch() {
        if (notifySwitch) {
            notifySwitch = false;
        }
        else {
            notifySwitch = true;
        }
    }

    public void setNotifySwitch(boolean notifySwitch) {
        this.notifySwitch = notifySwitch;
    }

    public TurtleLocationTimes(String tdigit, boolean tnotify, String ttime, String tlocation) {
        digitID = tdigit;
        turtleTime = ttime;
        notifySwitch = tnotify;
        location = tlocation;
        //Location;
    }
}
