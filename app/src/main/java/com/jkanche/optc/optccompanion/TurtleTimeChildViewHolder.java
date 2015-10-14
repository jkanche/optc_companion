package com.jkanche.optc.optccompanion;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

/**
 * Created by jayar on 10/13/2015.
 */
public class TurtleTimeChildViewHolder extends ChildViewHolder {

    public TextView turtleDigit, turtleTime;
    public Switch turtleNotify;

    public TurtleTimeChildViewHolder(View itemView) {
        super(itemView);

        turtleDigit = (TextView) itemView.findViewById(R.id.digitID);
        turtleTime = (TextView) itemView.findViewById(R.id.turtleTimeDate);
        turtleNotify = (Switch) itemView.findViewById(R.id.notifyTurtle);

    }
}
