package com.jkanche.optc.optccompanion;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onesignal.OneSignal;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;


public class FilterActivity extends AppCompatActivity {

    public ArrayList<optcChar> optcchars = new ArrayList<optcChar>();
    public Activity mActivity;
    ArrayList<String> classList = new ArrayList<String>(), typeList = new ArrayList<String>();
    ArrayList<Integer> mSelectedClasses = new ArrayList<Integer>(), mSelectedTypes = new ArrayList<Integer>();
    boolean[] checkedClasses , checkedTypes;
    ArrayList<String> fSelectedClasses = new ArrayList<String>(), fSelectedTypes = new ArrayList<String>();
    int minHealth = 300, maxHealth = 0,
            minAtk = 300, maxAtk = 0,
            minRcv = 300, maxRcv = 0,
            minCost = 300, maxCost = 0;
    int fminHealth = 0, fmaxHealth = 0,
            fminAtk = 0, fmaxAtk = 0,
            fminRcv = 0, fmaxRcv = 0,
            fminCost = 0, fmaxCost = 0;

    String fClass = null, fType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mActivity = this;

        Intent intent = getIntent();
        optcchars = (ArrayList<optcChar>) intent.getExtras().getSerializable("optcObj");

        ArrayList<String> tclassList = new ArrayList<String>(), ttypeList = new ArrayList<String>();

        for(optcChar item: optcchars) {
            if(!ttypeList.contains(item.getCharType())) {
                ttypeList.add(item.getCharType());
            }
/*            if(!typeList.contains(item.getCharType())) {
                typeList.add(item.getCharType());
            }*/

            if(!tclassList.contains(item.getCharClass())) {
                tclassList.add(item.getCharClass());
            }
/*            if(!classList.contains(item.getCharClass())) {
                classList.add(item.getCharClass());
            }*/


            if(item.getCharHealth() < minHealth) {
                minHealth = item.getCharHealth();
            }
            if(item.getCharHealth() > maxHealth) {
                maxHealth = item.getCharHealth();
            }

            if(item.getCharAttack() < minAtk) {
                minAtk = item.getCharAttack();
            }
            if(item.getCharAttack() > maxAtk) {
                maxAtk = item.getCharAttack();
            }

            if(item.getCharRecovery() < minRcv) {
                minRcv = item.getCharRecovery();
            }
            if(item.getCharRecovery() > maxRcv) {
                maxRcv = item.getCharRecovery();
            }

            if(item.getCharCost() < minCost) {
                minCost = item.getCharCost();
            }
            if(item.getCharCost() > maxCost) {
                maxCost = item.getCharCost();
            }
        }

        for (int i=0; i < tclassList.size(); i++) {
            String tmp = tclassList.get(i);

            tmp = tmp.replaceAll("\"|\\[|\\]", "");
            for (String retval: tmp.split(",")){
                if(!classList.contains(retval)) {
                    classList.add(retval);
                }
            }
        }

        typeList.addAll(ttypeList);


        final CharSequence[] charSequenceClasses = (CharSequence[]) classList.toArray(new CharSequence[classList.size()]);

        checkedClasses = new boolean[classList.size()];

        for (int j=0; j < classList.size(); j++) {
            checkedClasses[j] = true;
            mSelectedClasses.add(j);
        }

        TextView classText = (TextView) findViewById(R.id.classText);
        fClass = TextUtils.join(", ", classList);
        classText.setText(fClass);

        Button classFilter = (Button) findViewById(R.id.classfilter);

        classFilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                fSelectedClasses.clear();

                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("Select Character Class");
                builder.setMultiChoiceItems(charSequenceClasses, checkedClasses, new OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            mSelectedClasses.add(which);
                            checkedClasses[which] = true;
                        } else if (mSelectedClasses.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            mSelectedClasses.remove(Integer.valueOf(which));
                            checkedClasses[which] = false;
                        }
                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        for (Integer item : mSelectedClasses) {
                            fSelectedClasses.add(classList.get(item));
                        }

                        TextView classText = (TextView) findViewById(R.id.classText);
                        fClass = TextUtils.join(", ", fSelectedClasses);
                        classText.setText(fClass);

                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                builder.create().show();
            }
        });

        Button typeFilter = (Button) findViewById(R.id.typeFilter);

        final CharSequence[] typeSequenceTypes = (CharSequence[]) typeList.toArray(new CharSequence[typeList.size()]);

        checkedTypes = new boolean[typeList.size()];

        for (int k=0; k < typeList.size(); k++) {
            checkedTypes[k] = true;
            mSelectedTypes.add(k);
        }

        TextView typeText = (TextView) findViewById(R.id.typeText);
        fType = TextUtils.join(", ", typeList);
        typeText.setText(fType);

        typeFilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                fSelectedTypes.clear();

                AlertDialog.Builder tbuilder = new AlertDialog.Builder(mActivity);
                tbuilder.setTitle("Select Character Type");
                tbuilder.setMultiChoiceItems(typeSequenceTypes, checkedTypes, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            mSelectedTypes.add(which);
                            checkedTypes[which] = true;
                        } else if (mSelectedTypes.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            mSelectedTypes.remove(Integer.valueOf(which));
                            checkedTypes[which] = false;
                        }
                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                        for (Integer item : mSelectedTypes) {
                            fSelectedTypes.add(typeList.get(item));
                        }

                        TextView typeText = (TextView) findViewById(R.id.typeText);
                        fType = TextUtils.join(", ", fSelectedTypes);
                        typeText.setText(fType);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                tbuilder.create().show();
            }
        });

        RangeSeekBar healthRangeSeekBar = (RangeSeekBar) findViewById(R.id.healthSeekbar);
        healthRangeSeekBar.setRangeValues(minHealth, maxHealth);
        healthRangeSeekBar.setSelectedMinValue(minHealth);
        healthRangeSeekBar.setSelectedMaxValue(maxHealth);
        healthRangeSeekBar.setTextAboveThumbsColorResource(android.R.color.holo_blue_bright);
        //healthRangeSeekBar.show

        fminHealth = minHealth;
        fmaxHealth = maxHealth;

        healthRangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                fminHealth = (int) minValue;
                fmaxHealth = (int) maxValue;
            }
        });

        RangeSeekBar attackRangeSeekBar = (RangeSeekBar) findViewById(R.id.attackSeekbar);
        attackRangeSeekBar.setRangeValues(minAtk, maxAtk);
        attackRangeSeekBar.setSelectedMinValue(minAtk);
        attackRangeSeekBar.setSelectedMaxValue(maxAtk);
        attackRangeSeekBar.setTextAboveThumbsColorResource(android.R.color.holo_blue_bright);

        fminAtk = minAtk;
        fmaxAtk = maxAtk;

        attackRangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                fminAtk = (int) minValue;
                fmaxAtk = (int) maxValue;
            }
        });

        RangeSeekBar recoveryRangeSeekBar = (RangeSeekBar) findViewById(R.id.rcvSeekbar);
        recoveryRangeSeekBar.setRangeValues(minRcv, maxRcv);
        recoveryRangeSeekBar.setSelectedMinValue(minRcv);
        recoveryRangeSeekBar.setSelectedMaxValue(maxRcv);
        recoveryRangeSeekBar.setTextAboveThumbsColorResource(android.R.color.holo_blue_bright);

        fminRcv = minRcv;
        fmaxRcv = maxRcv;

        recoveryRangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                fminRcv = (int) minValue;
                fmaxRcv = (int) maxValue;
            }
        });

        RangeSeekBar costRangeSeekBar = (RangeSeekBar) findViewById(R.id.costSeekbar);
        costRangeSeekBar.setRangeValues(minCost, maxCost);
        costRangeSeekBar.setSelectedMinValue(minCost);
        costRangeSeekBar.setSelectedMaxValue(maxCost);
        costRangeSeekBar.setTextAboveThumbsColorResource(android.R.color.holo_blue_bright);

        fminCost = minCost;
        fmaxCost = maxCost;

        costRangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                fminCost = (int) minValue;
                fmaxCost = (int) maxValue;
            }
        });

        Button sButton = (Button) findViewById(R.id.submitButton);

        sButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("classSelection", fClass);
                returnIntent.putExtra("typeSelection", fType);
                returnIntent.putExtra("minHealth", fminHealth);
                returnIntent.putExtra("maxHealth", fmaxHealth);
                returnIntent.putExtra("minAtk", fminAtk);
                returnIntent.putExtra("maxAtk", fmaxAtk);
                returnIntent.putExtra("minRcv", fminRcv);
                returnIntent.putExtra("maxRcv", fmaxRcv);
                returnIntent.putExtra("minCost", fminCost);
                returnIntent.putExtra("maxCost", fmaxCost);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });


/*        */
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
