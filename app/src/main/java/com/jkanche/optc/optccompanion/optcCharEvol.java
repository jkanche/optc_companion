package com.jkanche.optc.optccompanion;
import java.io.Serializable;

/**
 * Created by jayar on 10/17/2015.
 */
public class optcCharEvol implements Serializable {

    private int parentCharID;
    private int childCharID;
    private String evolCharID;

    public optcCharEvol(int parentCharID, int childCharID, String evolCharID) {
        this.parentCharID = parentCharID;
        this.childCharID = childCharID;
        this.evolCharID = evolCharID;
    }

    public int getParentCharID() {
        return parentCharID;
    }

    public void setParentCharID(int parentCharID) {
        this.parentCharID = parentCharID;
    }

    public int getChildCharID() {
        return childCharID;
    }

    public void setChildCharID(int childCharID) {
        this.childCharID = childCharID;
    }

    public String getEvolCharID() {
        return evolCharID;
    }

    public void setEvolCharID(String evolCharID) {
        this.evolCharID = evolCharID;
    }
}
