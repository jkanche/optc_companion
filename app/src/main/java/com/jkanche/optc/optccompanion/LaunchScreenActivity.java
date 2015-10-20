package com.jkanche.optc.optccompanion;

/**
 * Created by jayar on 10/12/2015.
 */

import android.content.Context;
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
    public ArrayList<optcCharEvol> optcevols = new ArrayList<optcCharEvol>();
    private ProgressBar mProgressBar;
    public Context mContext;
    public static final String PREFS_NAME = "optcChars";
    public static final String PREFS_EVOL = "optcEvols";
    public TextView updateTask;
    String response;
    String forceDataReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        forceDataReload = getIntent().getStringExtra("forceReload");

        if(forceDataReload == null) {
            forceDataReload = "false";
        }

        mContext = this;

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

            updateTask.setText("Updating Characters and Evolutions...");

            intent = new Intent(mContext, HomeScreenActivity.class);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            /*  Use this method to load background
            * data that your app needs. */

                //Thread.sleep(SPLASH_TIME);

            SharedPreferences optcSP = getSharedPreferences(PREFS_NAME, 0);
            String dataSP = optcSP.getString("chars", null);

            //SharedPreferences optcEP = getSharedPreferences(PREFS_EVOL, 0);
            String dataEP = optcSP.getString("evols", null);

            Type collectionTypeChar = new TypeToken<ArrayList<optcChar>>(){}.getType();
            Type collectionTypeEvol = new TypeToken<ArrayList<optcCharEvol>>(){}.getType();

            if(dataSP == null || dataEP == null || forceDataReload.equals("true")) {
                processJson();
            }
            else {
                optcchars = gson.fromJson(dataSP, collectionTypeChar);
                optcevols = gson.fromJson(dataEP, collectionTypeEvol);
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

                    //mProgressBar.setMax(rows.length());

                    JSONObject drows = new JSONObject(dJSON);

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
                            boolean charBaseForm = false;




                            if(drows.has(Integer.toString(charId))) {

                                JSONObject drowRec = drows.getJSONObject(Integer.toString(charId));

                                String spl = "-";

                                if( drowRec.has("special") ) {
                                    spl = drowRec.getString("special");
                                }

                                String splName = "-";

                                if( drowRec.has("specialName")) {
                                    splName = drowRec.getString("specialName");
                                }

                                String captSpl = "-";

                                if( drowRec.has("captain")) {
                                    captSpl = drowRec.getString("captain");
                                }

                                String cooldown = "-";

                                if( drowRec.has("cooldown")) {
                                    cooldown = drowRec.getString("cooldown");
                                }

                                if(drowRec.has("evolution")) {
                                    if(drowRec.get("evolution") instanceof JSONArray) {

                                        JSONArray drowEvols = drowRec.getJSONArray("evolution");

                                        for (int dre=0; dre < drowEvols.length(); dre++) {

                                            int childEvol = drowEvols.getInt(dre);
                                            String evolChar = drowRec.getJSONArray("evolvers").getString(dre);

                                            optcCharEvol tmpEvol = new optcCharEvol(charId, childEvol, evolChar);
                                            optcevols.add(tmpEvol);
                                        }

                                    }
                                    else /*if(drowRec.get("evolution") instanceof int)*/ {

                                        int childEvol = drowRec.getInt("evolution");
                                        String evolChar = drowRec.getString("evolvers");

                                        optcCharEvol tmpEvol = new optcCharEvol(charId, childEvol, evolChar);
                                        optcevols.add(tmpEvol);
                                    }

                                }
                                else {
                                    charBaseForm = true;
                                }

                                optcChar tempChar = new optcChar(charId, charName, charType, charClass,
                                        charHealth, charAttack, charRecovery, charCost, charStars,
                                        charMaxLevel, charCombo, charMaxExp, spl, splName, captSpl, cooldown, charSlots);

                                optcchars.add(tempChar);

                            }

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
            //editor.apply();

            String edata = gson.toJson(optcevols);
/*            SharedPreferences settings2 = getSharedPreferences(PREFS_EVOL, 0);
            SharedPreferences.Editor editor2 = settings2.edit();*/
            editor.putString("evols", edata);

            editor.apply();

            //String jresp = gson.toJson(response);

/*            Bundle b = new Bundle();
            b.putSerializable("optcCharObj", optcchars);
            b.putSerializable("optcEvolObj", optcevols);

            intent.putExtras(b);*/

            //intent.putExtra("optcCharObj", optcchars);
            //intent.putExtra("ObjoptcEvolObj", optcevols);
            //intent.putExtra("optcCharObj", jdata);
            //intent.putExtra("optcEvolObj", edata);
            intent.putExtra("turtleTimeResp", response);

            startActivity(intent);
            finish();
        }
    }
}