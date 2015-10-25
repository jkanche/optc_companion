package com.jkanche.optc.optccompanion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onesignal.OneSignal;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.onesignal.OneSignal;
import com.onesignal.OneSignal.NotificationOpenedHandler;

/**
 * Created by jayar on 10/13/2015.
 */
public class TurtleTimeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private String response;
    ArrayList<ParentListItem> parentObjects = new ArrayList<ParentListItem>();
    public static final String PREFS_NAME_Turtle = "turtleTimes";
    TurtleLocations globalTurtle = new TurtleLocations("Global"), japanTurtle = new TurtleLocations("Japan");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_turtletime);

        Intent intent = getIntent();
        response = intent.getExtras().getString("turtleTimeResp");

        if(response == null) {
            response = "-";
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.turtleTimeView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mAdapter = new CharAdapter(this, optcchars);
        //mRecyclerView.setAdapter(mAdapter);


        TurtleTimeExpandableAdapter mTurtleExpandableAdapter = null;
        try {
            mTurtleExpandableAdapter = new TurtleTimeExpandableAdapter(this, generateTimes());
            //mTurtleExpandableAdapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
            //mTurtleExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
            //mTurtleExpandableAdapter.setParentAndIconExpandOnClick(true);

            mRecyclerView.setAdapter(mTurtleExpandableAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<ParentListItem> generateTimes() throws IOException, JSONException {
        //CrimeLab crimeLab = CrimeLab.get(this);
        //List<Crime> crimes = crimeLab.getCrimes();

        ArrayList<TurtleLocationTimes> globalChildList = new ArrayList<TurtleLocationTimes>(), japanChildList = new ArrayList<TurtleLocationTimes>();
        Gson gson = new Gson();

        SharedPreferences optcTT = getSharedPreferences(PREFS_NAME_Turtle, 0);
        String dataTTGlobal = optcTT.getString("globalTT", null);
        String dataTTJapan = optcTT.getString("japanTT", null);
        Type collectionType = new TypeToken<ArrayList<TurtleLocationTimes>>(){}.getType();

        if(dataTTGlobal == null || dataTTJapan == null) {

            JSONObject rows = new JSONObject(response);
            JSONArray rrows = rows.getJSONArray("result");

            for(int i = 0; i < rrows.length(); i++) {
                JSONObject tmp = rrows.getJSONObject(i);

                if(tmp.getString("Location").toLowerCase().equals("global")) {
                    globalChildList.add(new TurtleLocationTimes(tmp.getString("sixth_digit"), false, tmp.getString("Time"), "Global"));
                }
                else if(tmp.getString("Location").toLowerCase().equals("japan")){
                    japanChildList.add(new TurtleLocationTimes(tmp.getString("sixth_digit"), false, tmp.getString("Time"), "Japan"));
                }
            }

            globalTurtle.setChildObjectList(globalChildList);
            japanTurtle.setChildObjectList(japanChildList);

            parentObjects.add(globalTurtle);
            parentObjects.add(japanTurtle);
        }
        else {
            globalChildList = gson.fromJson(dataTTGlobal, collectionType);
            japanChildList = gson.fromJson(dataTTJapan, collectionType);

            JSONObject rows = new JSONObject(response);
            JSONArray rrows = rows.getJSONArray("result");

            for(int i = 0; i < rrows.length(); i++) {
                JSONObject tmp = rrows.getJSONObject(i);

                if(tmp.getString("Location").toLowerCase().equals("global")) {

                    for(int gi = 0; gi < globalChildList.size(); gi++) {
                        TurtleLocationTimes temp = globalChildList.get(gi);

                        if(temp.getDigitID().equals(tmp.getString("sixth_digit"))) {
                            ((TurtleLocationTimes) globalChildList.get(gi)).setTurtleTime(tmp.getString("Time"));
                        }
                    }
                }
                else if(tmp.getString("Location").toLowerCase().equals("japan")){

                    for(int gi = 0; gi < japanChildList.size(); gi++) {
                        TurtleLocationTimes temp = japanChildList.get(gi);

                        if(temp.getDigitID().equals(tmp.getString("sixth_digit"))) {
                            ((TurtleLocationTimes) japanChildList.get(gi)).setTurtleTime(tmp.getString("Time"));
                        }
                    }
                }
            }

            globalTurtle.setChildObjectList(globalChildList);
            japanTurtle.setChildObjectList(japanChildList);

            parentObjects.add(globalTurtle);
            parentObjects.add(japanTurtle);
        }

        String gcldata = gson.toJson(globalChildList);
        SharedPreferences.Editor editor = optcTT.edit();
        editor.putString("globalTT", gcldata);
        //editor.commit();


        String jcldata = gson.toJson(japanChildList);
        editor.putString("japanTT", jcldata);
        editor.commit();

        return parentObjects;
    }

    @Override
    protected void onPause() {
        super.onPause();
        OneSignal.onPaused();
    }
    @Override
    protected void onResume() {
        super.onResume();
        OneSignal.onResumed();
    }
}
