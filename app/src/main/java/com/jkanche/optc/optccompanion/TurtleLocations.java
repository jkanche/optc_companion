package com.jkanche.optc.optccompanion;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by jayar on 10/13/2015.
 */
public class TurtleLocations implements ParentListItem {
    private List<TurtleLocationTimes> mChildrenList;

    private String turtleLocation;

    public TurtleLocations(String turtleLocation) {
        this.turtleLocation = turtleLocation;
    }

    public String getTurtleLocation() {
        return turtleLocation;
    }

    public void setTurtleLocation(String turtleLocation) {
        this.turtleLocation = turtleLocation;
    }

    /* Your constructors, variables, data and methods for your Object go here */

    /**
     * You can either return a newly created list of children here or attach them later
     */
    @Override
    public List<TurtleLocationTimes> getChildItemList() {
        return mChildrenList;
    }

    /**
     * Allows you to specify if the list item should be expanded when first shown to the user
     */
    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public List<TurtleLocationTimes> getChildObjectList() {
        return mChildrenList;
    }

    public void setChildObjectList(List<TurtleLocationTimes> list) {
        mChildrenList = list;
    }
}
