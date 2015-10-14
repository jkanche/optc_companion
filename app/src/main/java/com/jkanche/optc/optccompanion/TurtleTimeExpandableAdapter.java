package com.jkanche.optc.optccompanion;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jayar on 10/13/2015.
 */
public class TurtleTimeExpandableAdapter extends ExpandableRecyclerAdapter<TurtleTimeParentViewHolder, TurtleTimeChildViewHolder> {
    private LayoutInflater mInflater;
    public static final String PREFS_NAME_Turtle = "turtleTimes";

    /**
     * Public primary constructor.
     *
     * @param parentList the list of parent items to be displayed in the RecyclerView
     */
    public TurtleTimeExpandableAdapter(Context context, List<ParentListItem> parentList) {
        super(parentList);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public TurtleTimeParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.parent_turtletime, viewGroup, false);
        return new TurtleTimeParentViewHolder(view);
    }

    @Override
    public TurtleTimeChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.child_turtletime, viewGroup, false);
        return new TurtleTimeChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(TurtleTimeParentViewHolder turtleTimeParentViewHolder, int i, ParentListItem parentListItem) {
        TurtleLocations tloc = (TurtleLocations) parentListItem;
        turtleTimeParentViewHolder.turtleLocation.setText(tloc.getTurtleLocation());
    }

    @Override
    public void onBindChildViewHolder(TurtleTimeChildViewHolder turtleTimeChildViewHolder, int i, Object childObject) {
        final TurtleLocationTimes tlocTime = (TurtleLocationTimes) childObject;
        turtleTimeChildViewHolder.turtleDigit.setText(tlocTime.getDigitID());
        turtleTimeChildViewHolder.turtleTime.setText(tlocTime.getTurtleTime());
        turtleTimeChildViewHolder.turtleNotify.setChecked(tlocTime.isNotifySwitch());

        turtleTimeChildViewHolder.turtleNotify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<TurtleLocationTimes>>(){}.getType();

                SharedPreferences optcTT = mInflater.getContext().getSharedPreferences(PREFS_NAME_Turtle, 0);

                if(tlocTime.getLocation().toLowerCase().equals("global")) {
                    String dataTTGlobal = optcTT.getString("globalTT", null);
                    ArrayList<TurtleLocationTimes> globalChildList = new ArrayList<TurtleLocationTimes>();

                    globalChildList = gson.fromJson(dataTTGlobal, collectionType);

                    for(int gi = 0; gi < globalChildList.size(); gi++) {
                        TurtleLocationTimes temp = globalChildList.get(gi);

                        if(temp.getDigitID().equals(tlocTime.getDigitID())) {
                            ((TurtleLocationTimes) globalChildList.get(gi)).toggleNotifySwitch();
                        }
                    }

                    String gcldata = gson.toJson(globalChildList);
                    SharedPreferences.Editor editor = optcTT.edit();
                    editor.putString("globalTT", gcldata);
                    editor.commit();

                }
                else if(tlocTime.getLocation().toLowerCase().equals("japan")) {
                    String dataTTJapan = optcTT.getString("japanTT", null);
                    ArrayList<TurtleLocationTimes> japanChildList = new ArrayList<TurtleLocationTimes>();
                    japanChildList = gson.fromJson(dataTTJapan, collectionType);

                    for(int gi = 0; gi < japanChildList.size(); gi++) {
                        TurtleLocationTimes temp = japanChildList.get(gi);

                        if(temp.getDigitID().equals(tlocTime.getDigitID())) {
                            ((TurtleLocationTimes) japanChildList.get(gi)).toggleNotifySwitch();
                        }
                    }


                    SharedPreferences.Editor editor = optcTT.edit();
                    String jcldata = gson.toJson(japanChildList);
                    editor.putString("japanTT", jcldata);
                    editor.commit();
                }
            }
        });

    }
}
