package com.jkanche.optc.optccompanion;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Filter;

public class CharActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    public ArrayList<optcChar> optcchars = new ArrayList<optcChar>();
    public ArrayList<optcCharEvol> optcevols = new ArrayList<optcCharEvol>();
    private RecyclerView mRecyclerView;
    private CharAdapter mAdapter;
    public static final String PREFS_NAME = "optcChars";
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char);

        //Intent intent = getIntent();
        //optcchars = (ArrayList<optcChar>) intent.getExtras().getSerializable("optcCharObj");

        OPTCCompanion application = (OPTCCompanion) getApplication();
        mTracker = application.getDefaultTracker();

        Gson gson = new Gson();

        SharedPreferences optcSP = getSharedPreferences(PREFS_NAME, 0);
        String optcCharObj = optcSP.getString("chars", null);
        String optcEvolObj = optcSP.getString("evols", null);

        Type collectionTypeChar = new TypeToken<ArrayList<optcChar>>(){}.getType();
        Type collectionTypeEvol = new TypeToken<ArrayList<optcCharEvol>>(){}.getType();

        optcchars = gson.fromJson(optcCharObj, collectionTypeChar);
        optcevols = gson.fromJson(optcEvolObj, collectionTypeEvol);


/*        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CharListFragment fragment = new CharListFragment();
        fragmentTransaction.add(R.id.activity_char_container, fragment);
        fragmentTransaction.commit();*/

            //Toast.makeText(this,"Processing JSON", Toast.LENGTH_LONG).show();
            //Log.d("CharActivity", "onCreate PROCESSING JSON");

        mRecyclerView = (RecyclerView) findViewById(R.id.charView);

        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CharAdapter(this, optcchars, optcevols);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setHasFixedSize(true);
    }

/*    public void switchContent(charDetailFragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }*/

/*    public void processJson() {

        String fJSON = loadJSON("chars.json");

        String dJSON = loadJSON("details.json");

        if(fJSON != null & dJSON!= null) {
            try {
                JSONArray rows = new JSONArray(fJSON);

                for(int i = 0; i < rows.length(); i++) {
                    JSONArray row = rows.getJSONArray(i);

                    String charName = row.getString(0);
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
                    int charRecovery = row.getInt(14);


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
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                String classFilter = data.getStringExtra("classSelection");
                String typeFilter = data.getStringExtra("typeSelection");
                int minHealth = data.getIntExtra("minHealth", 0);
                int maxHealth = data.getIntExtra("maxHealth", 0);
                int minAtk = data.getIntExtra("minAtk", 0);
                int maxAtk = data.getIntExtra("maxAtk", 0);
                int minRcv = data.getIntExtra("minRcv", 0);
                int maxRcv = data.getIntExtra("maxRcv", 0);
                int minCost = data.getIntExtra("minCost", 0);
                int maxCost = data.getIntExtra("maxCost", 0);

                mTracker.setScreenName("CharacterFilter~Params~class=[" + classFilter +
                    "]~type=[" + typeFilter + "]~health=[" + minHealth + "," + maxHealth + "]~attack=[" +
                        minAtk + "," + maxAtk + "]~recovery=[" + minRcv + "," + maxRcv + "]~cost=[" +
                        minCost + "," + maxCost + "]");
                mTracker.send(new HitBuilders.ScreenViewBuilder().build());

                mAdapter.flushFilter(false);

                mAdapter.setComplexFilter(classFilter, typeFilter, minHealth, maxHealth,
                        minAtk, maxAtk, minRcv, maxRcv, minCost, maxCost);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_char, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_filter:
                Intent intentFilter = new Intent(this, FilterActivity.class);
                intentFilter.putExtra("optcObj", optcchars);
                startActivityForResult(intentFilter, 1);
                return true;
            case R.id.action_reset:
                mAdapter.flushFilter(true);
                return true;
            case R.id.menuAttackSort:
                mAdapter.setAttackFilter();
                return true;
            case R.id.menuHealthSort:
                mAdapter.setHealthFilter();
                return true;
            case R.id.menuRcvSort:
                mAdapter.setRcvFilter();
                return true;
            case R.id.menuCostSort:
                mAdapter.setCostFilter();
                return true;
            case R.id.menucharIDSort:
                mAdapter.setIDFilter();
                return true;
            case R.id.action_reverse:
                mAdapter.reverseFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


        //noinspection SimplifiableIfStatement
/*
        if (id == R.id.action_search) {



            //return true;

*/
/*            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //callSearch(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    final List<optcChar> filteredModelList = filter(optcchars, query);
                    mAdapter.animateTo(filteredModelList);
                    mRecyclerView.scrollToPosition(0);
                    return true;
                }

                private List<optcChar> filter(List<optcChar> models, String query) {
                    query = query.toLowerCase();

                    final List<optcChar> filteredModelList = new ArrayList<>();
                    for (optcChar model : models) {
                        final String text = model.getCharName().toLowerCase();
                        if (text.contains(query)) {
                            filteredModelList.add(model);
                        }
                    }
                    return filteredModelList;
                }

            });*//*


        }
*/

        //return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.isEmpty()) {
            mAdapter.flushFilter(true);
        }
        else {
            mAdapter.setFilter(query);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

            //final List<optcChar> filteredModelList = filter(optcchars, newText);
            //mAdapter.animateTo(filteredModelList);
            //mRecyclerView.scrollToPosition(0);

        if (newText.isEmpty()) {
            mAdapter.flushFilter(true);
        }
        else {
            mAdapter.setFilter(newText);
        }
        return true;
    }

/*    private List<optcChar> filter(List<optcChar> models, String query) {

        final ArrayList<optcChar> filteredModelList = new ArrayList<>();

            query = query.toLowerCase();

            for (optcChar model : models) {
                final String text = model.getCharName().toLowerCase();
                if (text.contains(query)) {
                    filteredModelList.add(model);
                }
            }

        return filteredModelList;
    }*/
}
