package com.jkanche.optc.optccompanion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by jayar on 10/13/2015.
 */
public class HomeScreenActivity extends AppCompatActivity {

    public ArrayList<optcChar> optcchars = new ArrayList<optcChar>();
    public ArrayList<optcCharEvol> optcevols = new ArrayList<optcCharEvol>();
    public String respo;
    public static final String PREFS_NAME = "optcChars";
    private static Activity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.homescreen);

        currentActivity = this;

        Intent intent = getIntent();

        //Gson gson = new Gson();

        //Bundle extras = intent.getExtras();

        //optcchars = (ArrayList<optcChar>) extras.getSerializable("optcCharObj");
        //optcevols = (ArrayList<optcCharEvol>) extras.getSerializable("optcEvolObj");

        //String optcCharObj = (String) intent.getExtras().getSerializable("optcCharObj");
        //String optcEvolObj = (String) intent.getExtras().getSerializable("optcEvolObj");

/*        SharedPreferences optcSP = getSharedPreferences(PREFS_NAME, 0);
        String optcCharObj = optcSP.getString("chars", null);
        String optcEvolObj = optcSP.getString("evols", null);

        Type collectionTypeChar = new TypeToken<ArrayList<optcChar>>(){}.getType();
        Type collectionTypeEvol = new TypeToken<ArrayList<optcCharEvol>>(){}.getType();

        optcchars = gson.fromJson(optcCharObj, collectionTypeChar);
        optcevols = gson.fromJson(optcEvolObj, collectionTypeEvol);*/

        respo = intent.getExtras().getString("turtleTimeResp");

        Button turtleTime = (Button) findViewById (R.id.turtletimeButton);
        Button charSearch = (Button) findViewById (R.id.charSearchButton);
        Button reloadData = (Button) findViewById (R.id.resetButton);

        turtleTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTT = new Intent(HomeScreenActivity.this, TurtleTimeActivity.class);
                intentTT.putExtra("turtleTimeResp", respo);
                startActivity(intentTT);
            }
        });

        charSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCS = new Intent(HomeScreenActivity.this, CharActivity.class);
                //intentCS.putExtra("optcCharObj", optcchars);
                startActivity(intentCS);
            }
        });

        reloadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRD = new Intent(HomeScreenActivity.this, LaunchScreenActivity.class);
                intentRD.putExtra("forceReload", "true");
                startActivity(intentRD);
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


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
