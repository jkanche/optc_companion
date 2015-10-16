package com.jkanche.optc.optccompanion;

/**
 * Created by jayar on 10/12/2015.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

public class LaunchScreenActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 3000;

    public ArrayList<optcChar> optcchars = new ArrayList<optcChar>();
    private ProgressBar mProgressBar;
    public static final String PREFS_NAME = "optcChars";
    public TextView updateTask;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        mProgressBar = (ProgressBar) findViewById (R.id.progressBar);

        updateTask = (TextView) findViewById(R.id.updateTask);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        new TurtleBackgroundTask().execute("https://sheetsu.com/apis/78c5b290");
    }

    private class TurtleBackgroundTask extends AsyncTask {
        Gson gson = new Gson();

        OkHttpClient client = new OkHttpClient();

        String doGetRequest(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String urlt = (String) params[0];
            try {
                response = doGetRequest(urlt);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            updateTask.setText("Loading Turtle Times...");
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            new BackgroundTask().execute();
        }
    }


    private class BackgroundTask extends AsyncTask {
        Intent intent;
        Gson gson = new Gson();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            updateTask.setText("Updating Characters...");

            intent = new Intent(LaunchScreenActivity.this, HomeScreenActivity.class);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            /*  Use this method to load background
            * data that your app needs. */

                //Thread.sleep(SPLASH_TIME);

            SharedPreferences optcSP = getSharedPreferences(PREFS_NAME, 0);
            String dataSP = optcSP.getString("chars", null);
            Type collectionType = new TypeToken<ArrayList<optcChar>>(){}.getType();

            if(dataSP == null) {
                processJson();
            }
            else {
                optcchars = gson.fromJson(dataSP, collectionType);
            }

            return null;
        }

        public void processJson() {

            String fJSON = loadJSON("chars.json");

            String dJSON = loadJSON("details.json");

            if(fJSON != null & dJSON!= null) {
                try {
                    JSONArray rows = new JSONArray(fJSON);

                    mProgressBar.setMax(rows.length());

                    for(int i = 0; i < rows.length(); i++) {
                        //JSONArray row = rows.getJSONArray(i);

                        JSONObject crow = rows.getJSONObject(i);

                        if(!crow.getString("name").toLowerCase().equals("unknown")) {

/*                            String charName = row.getString(0);
                            String charType = row.getString(1);
                            String charClass = row.getString(2);
                            int charStars = row.getInt(3);
                            int charId = i+1;
                            int charCost = row.getInt(4);
                            int charCombo = row.getInt(5);
                            int charSlots = row.getInt(6);
                            int charMaxLevel = row.getInt(7);
                            int charMaxExp = row.getInt(8);
                            int charHealth = row.getInt(12);
                            int charAttack = row.getInt(13);
                            int charRecovery = row.getInt(14);*/

                            String charName = crow.getString("name");
                            String charType = crow.getString("type");
                            String charClass = crow.getString("class");
                            int charStars = crow.getInt("stars");
                            int charId = i+1;
                            int charCost = crow.getInt("cost");
                            int charCombo = crow.getInt("combo");
                            int charSlots = crow.getInt("slots");
                            int charMaxLevel = crow.getInt("maxLevel");
                            int charMaxExp = crow.getInt("maxEXP");
                            int charHealth = crow.getInt("maxHP");
                            int charAttack = crow.getInt("maxATK");
                            int charRecovery = crow.getInt("maxRCV");


                            JSONObject drows = new JSONObject(dJSON);

                            String spl = "-";

                            if( drows.getJSONObject(Integer.toString(charId)).has("special") ) {
                                spl = drows.getJSONObject(Integer.toString(charId)).getString("special");
                            }

                            String splName = "-";

                            if( drows.getJSONObject(Integer.toString(charId)).has("specialName")) {
                                splName = drows.getJSONObject(Integer.toString(charId)).getString("specialName");
                            }

                            String captSpl = "-";

                            if( drows.getJSONObject(Integer.toString(charId)).has("captain")) {
                                captSpl = drows.getJSONObject(Integer.toString(charId)).getString("captain");
                            }

                            String cooldown = "-";

                            if( drows.getJSONObject(Integer.toString(charId)).has("cooldown")) {
                                cooldown = drows.getJSONObject(Integer.toString(charId)).getString("cooldown");
                            }

                            optcChar tempChar = new optcChar(charId, charName, charType, charClass,
                                    charHealth, charAttack, charRecovery, charCost, charStars,
                                    charMaxLevel, charCombo, charMaxExp, spl, splName, captSpl, cooldown, charSlots);

                            optcchars.add(tempChar);

                            mProgressBar.setProgress(i);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public String loadJSON(String fileName){

            String json = null;
            InputStream is = null;

            try {
                is = getAssets().open(fileName);
                int size = is.available();

                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                json = new String(buffer, "UTF-8");

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            return json;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//            Pass your loaded data here using Intent

//            intent.putExtra("data_key", "");

            String jdata = gson.toJson(optcchars);
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("chars", jdata);
            editor.commit();

            //String jresp = gson.toJson(response);

            intent.putExtra("optcCharObj", optcchars);
            intent.putExtra("turtleTimeResp", response);

            startActivity(intent);
            finish();
        }
    }
}