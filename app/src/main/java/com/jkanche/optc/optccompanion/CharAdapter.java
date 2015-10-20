package com.jkanche.optc.optccompanion;

/**
 * Created by jayar on 10/11/2015.
 */
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jkanche.optc.optccompanion.optcChar;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CharAdapter extends RecyclerView.Adapter <CharAdapter.CharViewHolder> {

    private Fragment mFragment;
    private Bundle mBundle;

    public class CharViewHolder extends RecyclerView.ViewHolder
//            implements View.OnClickListener
        {

       // public CardView cv;
        public TextView charName, charClass, charHealth, charAttack, charRecovery, charCost, charID;
        public ImageView charSImg, charType;
        public RatingBar charStars;

        //private ClickListener clickListener;

        public CharViewHolder(View itemView) {
            super(itemView);
            //cv = (CardView)itemView.findViewById(R.id.charView);
            charName = (TextView)itemView.findViewById(R.id.charName);
            //charType = (ImageView)itemView.findViewById(R.id.charType);
            charClass = (TextView)itemView.findViewById(R.id.charClass);
            charHealth = (TextView)itemView.findViewById(R.id.charHealth);
            charAttack = (TextView)itemView.findViewById(R.id.charAttack);
            charRecovery = (TextView)itemView.findViewById(R.id.charRecovery);
            charStars = (RatingBar)itemView.findViewById(R.id.charStars);
            charSImg = (ImageView)itemView.findViewById(R.id.charProfile);
            charCost = (TextView)itemView.findViewById(R.id.charCost);
            charID = (TextView)itemView.findViewById(R.id.charID);

            //itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context cxt = v.getContext();

                    Intent intent = new Intent(cxt, CharDetailActivity.class);

                    optcChar temp = optcchars_filtered.get(getPosition());

                    //optcevols_filtered.clear();

                    optcevols_filtered = new ArrayList<optcCharEvol>();

                    for (optcCharEvol tmp: optcevols) {
                        if(tmp.getParentCharID() == temp.getCharId()) {
                            optcevols_filtered.add(tmp);
                        }
                    }

                    intent.putExtra("optcCharSelc",temp);
                    intent.putExtra("optcEvolsSel", optcevols_filtered);

/*                    int charId = temp.getCharId();
                    String charName = temp.getCharName();
                    String charType = temp.getCharType();
                    String charClass = temp.getCharClass();
                    int charHealth = temp.getCharHealth();
                    int charAttack = temp.getCharAttack();
                    int charRecovery = temp.getCharRecovery();
                    int charCost = temp.getCharCost();
                    int charStars = temp.getCharStars();
                    int charMaxLevel = temp.getCharMaxLevel();
                    int charCombo = temp.getCharCombo();
                    int charMaxExp = temp.getCharMaxExp();
                    int charSlots = temp.getCharSlots();
                    String charSpecial = temp.getCharSpecial();
                    String charSpecialName = temp.getCharSpecialName();
                    String captainSpl = temp.getCaptainSpl();
                    String charCooldown = temp.getCharCooldown();

                    intent.putExtra("charId", charId);
                    intent.putExtra("charName", charName);
                    intent.putExtra("charType", charType);
                    intent.putExtra("charClass", charClass);
                    intent.putExtra("charHealth", charHealth);
                    intent.putExtra("charAttack", charAttack);
                    intent.putExtra("charRecovery", charRecovery);
                    intent.putExtra("charCost", charCost);
                    intent.putExtra("charStars", charStars);
                    intent.putExtra("charMaxLevel", charMaxLevel);
                    intent.putExtra("charCombo", charCombo);
                    intent.putExtra("charMaxExp", charMaxExp);
                    intent.putExtra("charSpecial", charSpecial);
                    intent.putExtra("charSpecialName", charSpecialName);
                    intent.putExtra("captainSpl", captainSpl);
                    intent.putExtra("charCooldown", charCooldown);
                    intent.putExtra("charSlots", charSlots);*/

                    cxt.startActivity(intent);

                    //charDetailFragment fragment = new charDetailFragment();
                    //mBundle = new Bundle();
                    //CharActivity mainActivity = (CharActivity) mInflater.getContext();
                    //mainActivity.switchContent(fragment);



                    //Toast.makeText(cxt, "Position = " + getPosition(), Toast.LENGTH_LONG).show();
                }
            });
        }

/*        public interface ClickListener {

            *//**
             * Called when the view is clicked.
             *
             * @param v view that is clicked
             * @param position of the clicked item
             * @param isLongClick true if long click, false otherwise
             *//*
            public void onClick(View v, int position, boolean isLongClick);

        }

        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }*/

/*        @Override
        public void onClick(View v) {

            // If not long clicked, pass last variable as false.
            //clickListener.onClick(v, getPosition(), false);

            Context cxt = v.getContext();

            int Itemid =  recyclerView.getChildPosition(v);;
            Intent intent = new Intent(cxt, CharDetailActivity.class)
                    .putExtra(Intent.EXTRA_TEXT, Itemid);

            cxt.startActivity(intent);

            Toast.makeText(cxt, "Something is clicked" + Itemid, Toast.LENGTH_SHORT).show();

        }*/

/*        @Override
        public boolean onLongClick(View v) {

            // If long clicked, passed last variable as true.
            //clickListener.onClick(v, getPosition(), true);
            Toast.makeText(getActivity(), textView.getText(), Toast.LENGTH_SHORT).show();
            return true;
        }*/

    }

    ArrayList<optcChar> optcchars;
    ArrayList<optcChar> optcchars_filtered;
    ArrayList<optcCharEvol> optcevols;
    ArrayList<optcCharEvol> optcevols_filtered;
    private final LayoutInflater mInflater;

    public CharAdapter(Context context, ArrayList<optcChar> optcchars, ArrayList<optcCharEvol> optcevols) {
        this.mInflater = LayoutInflater.from(context);
        this.optcchars = optcchars;
        this.optcchars_filtered = optcchars;
        this.optcevols = optcevols;
        this.optcevols_filtered = optcevols;
    }

    @Override
    public int getItemCount() {
        return optcchars_filtered.size();
    }

    @Override
    public CharViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.char_item, viewGroup, false);
        View v = mInflater.inflate(R.layout.char_item, viewGroup, false);
        CharViewHolder cvh = new CharViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CharViewHolder cvh, int i) {

        final optcChar item = optcchars_filtered.get(i);

        cvh.charName.setText(optcchars_filtered.get(i).getCharName());

        String chClass = optcchars_filtered.get(i).getCharClass();
        chClass = chClass.replaceAll("\"|\\[|\\]", "");
        chClass = chClass.replaceAll(",", ", ");

        cvh.charClass.setText(chClass);

        cvh.charHealth.setText("Health: " + Integer.toString(optcchars_filtered.get(i).getCharHealth()));
        cvh.charAttack.setText("Attack: " + Integer.toString(optcchars_filtered.get(i).getCharAttack()));
        cvh.charRecovery.setText("Recovery: " + Integer.toString(optcchars_filtered.get(i).getCharRecovery()));
        cvh.charCost.setText("Cost: " + Integer.toString(optcchars_filtered.get(i).getCharCost()));
        cvh.charStars.setRating(optcchars_filtered.get(i).getCharStars());

        int charId = optcchars_filtered.get(i).getCharId();

        cvh.charID.setText(Integer.toString(charId));

        if (charId == 0) {
            //new DownloadImageTask( cvh.charSImg).execute("http://onepiece-treasurecruise.com/wp-content/themes/onepiece-treasurecruise/images/noimage.png");
            Picasso.with(mInflater.getContext()).load("http://onepiece-treasurecruise.com/wp-content/themes/onepiece-treasurecruise/images/noimage.png").into(cvh.charSImg);
        }
        else {
            String cid = "0000" + charId;
            String ccid = cid.substring(cid.length() - 4);
            Picasso.with(mInflater.getContext()).load("http://onepiece-treasurecruise.com/wp-content/uploads/f" + ccid + ".png").into(cvh.charSImg);
            //new DownloadImageTask( cvh.charSImg).execute("http://onepiece-treasurecruise.com/wp-content/uploads/f" + ccid + ".png");
        }

/*        cvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //fragmentJump(item);

*//*                Context cxt = v.getContext();

                Intent intent = new Intent(cxt, CharDetailActivity.class);

                //optcChar temp = optcchars_filtered.get(item);

                int charId = temp.getCharId();
                String charName = temp.getCharName();
                String charType = temp.getCharType();
                String charClass = temp.getCharClass();
                int charHealth = temp.getCharHealth();
                int charAttack = temp.getCharAttack();
                int charRecovery = temp.getCharRecovery();
                int charCost = temp.getCharCost();
                int charStars = temp.getCharStars();
                int charMaxLevel = temp.getCharMaxLevel();
                int charCombo = temp.getCharCombo();
                int charMaxExp = temp.getCharMaxExp();
                int charSlots = temp.getCharSlots();
                String charSpecial = temp.getCharSpecial();
                String charSpecialName = temp.getCharSpecialName();
                String captainSpl = temp.getCaptainSpl();
                String charCooldown = temp.getCharCooldown();

                intent.putExtra("charId", charId);
                intent.putExtra("charName", charName);
                intent.putExtra("charType", charType);
                intent.putExtra("charClass", charClass);
                intent.putExtra("charHealth", charHealth);
                intent.putExtra("charAttack", charAttack);
                intent.putExtra("charRecovery", charRecovery);
                intent.putExtra("charCost", charCost);
                intent.putExtra("charStars", charStars);
                intent.putExtra("charMaxLevel", charMaxLevel);
                intent.putExtra("charCombo", charCombo);
                intent.putExtra("charMaxExp", charMaxExp);
                intent.putExtra("charSpecial", charSpecial);
                intent.putExtra("charSpecialName", charSpecialName);
                intent.putExtra("captainSpl", captainSpl);
                intent.putExtra("charCooldown", charCooldown);
                intent.putExtra("charSlots", charSlots);

                cxt.startActivity(intent);*//*

                //charDetailFragment fragment = new charDetailFragment();

                //FragmentManager fragmentManager = getFragmentManager();



                //Toast.makeText(cxt, "Position = " + getPosition(), Toast.LENGTH_LONG).show();
            }
        });*/
    }

/*    private void fragmentJump(optcChar mItemSelected) {
        mFragment = new charDetailFragment();
        mBundle = new Bundle();
        mBundle.putParcelable("item_selected_key", mItemSelected);
        mFragment.setArguments(mBundle);
        switchContent(R.id.charDet, mFragment);
    }

    public void switchContent(int id, Fragment fragment) {
        if (mInflater == null)
            return;
        if (mInflater.getContext() instanceof CharActivity) {
            CharActivity mainActivity = (CharActivity) mInflater.getContext();
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag);
        }
    }*/

/*    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
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
    }*/

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void flushFilter(boolean notify){
        optcchars_filtered=new ArrayList<>();
        optcchars_filtered.addAll(optcchars);
        if(notify) {
            notifyDataSetChanged();
        }
    }

    public void setFilter(String queryText) {

        optcchars_filtered = new ArrayList<>();
        String query = queryText.toString().toLowerCase();
        for (optcChar item: optcchars) {
            if (item.getCharName().toLowerCase().contains(query))
                optcchars_filtered.add(item);
        }
        notifyDataSetChanged();
    }

    public void reverseFilter() {
        Collections.reverse(optcchars_filtered);
        notifyDataSetChanged();
    }

    public void setHealthFilter() {
        //Sorting
        Collections.sort(optcchars_filtered, optcChar.Comparators.HEALTH);

        notifyDataSetChanged();
    }

    public void setIDFilter() {
        //Sorting
        Collections.sort(optcchars_filtered, optcChar.Comparators.CHARID);

        notifyDataSetChanged();
    }

    public void setAttackFilter() {
        //Sorting
        Collections.sort(optcchars_filtered, optcChar.Comparators.ATTACK);

        notifyDataSetChanged();
    }

    public void setCostFilter() {
        //Sorting
        Collections.sort(optcchars_filtered, optcChar.Comparators.COST);

        notifyDataSetChanged();
    }

    public void setRcvFilter() {
        //Sorting
        Collections.sort(optcchars_filtered, optcChar.Comparators.RECOVERY);

        notifyDataSetChanged();
    }

    public void setComplexFilter(String classFilter, String typeFilter, int minHealth, int maxHealth,
                                 int minAtk, int maxAtk, int minRcv, int maxRcv, int minCost, int maxCost) {

        optcchars_filtered = new ArrayList<>();

        String classFilt = classFilter.toLowerCase();
        String classType = typeFilter.toLowerCase();

        for (optcChar item: optcchars) {
            if ((item.getCharClass().toLowerCase().contains(classFilt) || classFilt.contains(item.getCharClass().toLowerCase()))
                    && (item.getCharType().toLowerCase().contains(classType) || classType.contains(item.getCharType().toLowerCase()))
                    && (item.getCharHealth() > minHealth && item.getCharHealth() < maxHealth)
                    && (item.getCharAttack() > minAtk && item.getCharAttack() < maxAtk)
                    && (item.getCharRecovery() > minRcv && item.getCharRecovery() < maxRcv)
                    && (item.getCharCost() > minCost && item.getCharCost() < maxCost)
                    )
                optcchars_filtered.add(item);
        }
        notifyDataSetChanged();
    }

/*    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<optcChar> results = new ArrayList<optcChar>();
                if (optcchars == null)
                    optcchars = items;
                if (constraint != null) {
                    if (optcchars != null & optcchars.size() > 0) {
                        for (final optcChar g : optcchars) {
                            if (g.getCharName().toLowerCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (ArrayList<optcChar>) results.values;
                notifyDataSetChanged();

            }
        };
    }*/

/*    public optcChar removeItem(int position) {
        final optcChar model = optcchars.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, optcChar model) {
        optcchars.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final optcChar model = optcchars.remove(fromPosition);
        optcchars.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<optcChar> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<optcChar> newModels) {
        for (int i = optcchars.size() - 1; i >= 0; i--) {
            final optcChar model = optcchars.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<optcChar> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final optcChar model = newModels.get(i);
            if (!optcchars.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<optcChar> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final optcChar model = newModels.get(toPosition);
            final int fromPosition = optcchars.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }*/
}