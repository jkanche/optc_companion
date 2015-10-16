package com.jkanche.optc.optccompanion;

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
    public String respo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.homescreen);

        Intent intent = getIntent();
        optcchars = (ArrayList<optcChar>) intent.getExtras().getSerializable("optcCharObj");
        respo = intent.getExtras().getString("turtleTimeResp");

        Button turtleTime = (Button) findViewById (R.id.turtletimeButton);
        Button charSearch = (Button) findViewById (R.id.charSearchButton);

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
                intentCS.putExtra("optcCharObj", optcchars);
                startActivity(intentCS);
            }
        });


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);


    }
}
