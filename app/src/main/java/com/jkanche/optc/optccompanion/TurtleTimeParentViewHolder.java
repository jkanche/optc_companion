package com.jkanche.optc.optccompanion;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

/**
 * Created by jayar on 10/13/2015.
 */
public class TurtleTimeParentViewHolder extends ParentViewHolder {

    public TextView turtleLocation;

    public TurtleTimeParentViewHolder(View itemView) {
        super(itemView);

        turtleLocation = (TextView) itemView.findViewById(R.id.turtleTimeLocation);
    }
}
