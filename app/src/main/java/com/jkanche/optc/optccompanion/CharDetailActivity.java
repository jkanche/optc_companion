package com.jkanche.optc.optccompanion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

public class CharDetailActivity extends AppCompatActivity {

    private Tracker mTracker;
    optcChar optcCharsSelc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        OPTCCompanion application = (OPTCCompanion) getApplication();
        mTracker = application.getDefaultTracker();

        optcCharsSelc = (optcChar) getIntent().getExtras().getSerializable("optcCharSelc");

        int charId = optcCharsSelc.getCharId();
        String charName = optcCharsSelc.getCharName();
        String charType = optcCharsSelc.getCharType();
        String charClass = optcCharsSelc.getCharClass();
        int charHealth = optcCharsSelc.getCharHealth();
        int charAttack = optcCharsSelc.getCharAttack();
        int charRecovery = optcCharsSelc.getCharRecovery();
        int charCost = optcCharsSelc.getCharCost();
        int charStars = optcCharsSelc.getCharStars();
        int charMaxLevel = optcCharsSelc.getCharMaxLevel();
        int charCombo = optcCharsSelc.getCharCombo();
        int charMaxExp = optcCharsSelc.getCharMaxExp();
        int charSlots = optcCharsSelc.getCharSlots();
        String charSpecial = optcCharsSelc.getCharSpecial();
        String charSpecialName = optcCharsSelc.getCharSpecialName();
        String captainSpl = optcCharsSelc.getCaptainSpl();
        String charCooldown = optcCharsSelc.getCharCooldown();


/*        int charId = getIntent().getExtras().getInt("charId");
        String charName = getIntent().getExtras().getString("charName");
        String charType = getIntent().getExtras().getString("charType");
        String charClass = getIntent().getExtras().getString("charClass");
        int charHealth = getIntent().getExtras().getInt("charHealth");
        int charAttack = getIntent().getExtras().getInt("charAttack");
        int charRecovery = getIntent().getExtras().getInt("charRecovery");
        int charCost = getIntent().getExtras().getInt("charCost");
        int charStars = getIntent().getExtras().getInt("charStars");
        int charMaxLevel = getIntent().getExtras().getInt("charMaxLevel");
        int charCombo = getIntent().getExtras().getInt("charCombo");
        int charMaxExp = getIntent().getExtras().getInt("charMaxExp");
        int charSlots = getIntent().getExtras().getInt("charSlots");
        String charSpecial = getIntent().getExtras().getString("charSpecial");
        String charSpecialName = getIntent().getExtras().getString("charSpecialName");
        String captainSpl = getIntent().getExtras().getString("captainSpl");
        String charCooldown = getIntent().getExtras().getString("charCooldown");*/

        //Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName("CharacterDetail~" + charName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        TextView tcharName = (TextView) findViewById(R.id.charName);
        tcharName.setText(charName);

        TextView tcharType = (TextView) findViewById(R.id.charType);
        tcharType.setText(charType);

        TextView tcharClass = (TextView) findViewById(R.id.charClass);
        charClass = charClass.replaceAll("\"|\\[|\\]", "");
        charClass = charClass.replaceAll(",", ", ");
        tcharClass.setText(charClass);

        TextView tcharHealth = (TextView) findViewById(R.id.charHealth);
        tcharHealth.setText("Health: " + Integer.toString(charHealth));

        TextView tcharAttack = (TextView) findViewById(R.id.charAttack);
        tcharAttack.setText("Attack: " + Integer.toString(charAttack));

        TextView tcharRecovery = (TextView) findViewById(R.id.charRecovery);
        tcharRecovery.setText("Recovery: " + Integer.toString(charRecovery));

        TextView tcharCost = (TextView) findViewById(R.id.charCost);
        tcharCost.setText("Cost: " + Integer.toString(charCost));

        TextView tcharSlots = (TextView) findViewById(R.id.charSlots);
        tcharSlots.setText("Slots: " + Integer.toString(charSlots));

        TextView tcharMaxLevel = (TextView) findViewById(R.id.charMaxLevel);
        tcharMaxLevel.setText("Max Level: " + Integer.toString(charMaxLevel));

        TextView tcharCombo = (TextView) findViewById(R.id.charCombo);
        tcharCombo.setText("Combo: " + Integer.toString(charCombo));

        TextView tcharMaxExp = (TextView) findViewById(R.id.charExpMax);
        tcharMaxExp.setText("Max Exp: " + Integer.toString(charMaxExp));

        TextView tcharSpecial = (TextView) findViewById(R.id.charSpecial);
        tcharSpecial.setText(charSpecial);

        TextView tcaptainSpl = (TextView) findViewById(R.id.charCaptainAbility);
        tcaptainSpl.setText("Captain: " + captainSpl);

        TextView tcharCooldown = (TextView) findViewById(R.id.charCooldown);
        charCooldown = charCooldown.replaceAll("\"|\\[|\\]", "");
        charCooldown = charCooldown.replaceAll(",", "-->");
        tcharCooldown.setText("Cooldown: " + charCooldown);

        TextView tcharSpecialName = (TextView) findViewById(R.id.charSplName);
        tcharSpecialName.setText("Special: " + charSpecialName);

        ImageView charPImg = (ImageView) findViewById(R.id.charFullProfile);

        RatingBar tcharStars = (RatingBar) findViewById(R.id.charStars);
        tcharStars.setRating(charStars);

        if (charId == 0) {
            //new DownloadImageTask(charPImg).execute("http://onepiece-treasurecruise.com/wp-content/themes/onepiece-treasurecruise/images/noimage.png");
            Picasso.with(this).load("http://onepiece-treasurecruise.com/wp-content/themes/onepiece-treasurecruise/images/noimage.png").into(charPImg);
        }
        else {
            String cid = "0000" + charId;
            String ccid = cid.substring(cid.length() - 4);
            Picasso.with(this).load("http://onepiece-treasurecruise.com/wp-content/uploads/c" + ccid + ".png").into(charPImg);
            //new DownloadImageTask( charPImg).execute("http://onepiece-treasurecruise.com/wp-content/uploads/c" + ccid + ".png");
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_char_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
