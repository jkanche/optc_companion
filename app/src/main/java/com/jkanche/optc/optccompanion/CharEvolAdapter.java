package com.jkanche.optc.optccompanion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by jayar on 10/18/2015.
 */
public class CharEvolAdapter extends RecyclerView.Adapter <CharEvolAdapter.CharEvolViewHolder> {

    public class CharEvolViewHolder extends RecyclerView.ViewHolder {

        public ImageView charEvolChild, evol1, evol2, evol3, evol4, evol5;

        public CharEvolViewHolder(View itemView) {
            super(itemView);

            charEvolChild = (ImageView)itemView.findViewById(R.id.evolChild);
            evol1 = (ImageView)itemView.findViewById(R.id.evol1);
            evol2 = (ImageView)itemView.findViewById(R.id.evol2);
            evol3 = (ImageView)itemView.findViewById(R.id.evol3);
            evol4 = (ImageView)itemView.findViewById(R.id.evol4);
            evol5 = (ImageView)itemView.findViewById(R.id.evol5);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context cxt = v.getContext();

                    Intent intent = new Intent(cxt, CharDetailActivity.class);

                    optcCharEvol tempEvol = optcevols_filtered.get(getPosition());

                    optcChar temp = null;

                    for (optcChar tmpChar: optcchars) {
                        if(tempEvol.getChildCharID() == tmpChar.getCharId()) {
                            temp = tmpChar;
                            break;
                        }
                    }

                    //optcChar temp = optcchars.get(tempEvol.getChildCharID());

                    ArrayList<optcCharEvol> optcevols_tmp = new ArrayList<optcCharEvol>();

                    for (optcCharEvol tmp: optcevols) {
                        if(tmp.getParentCharID() == temp.getCharId()) {
                            optcevols_tmp.add(tmp);
                        }
                    }

                    intent.putExtra("optcCharSelc",temp);
                    intent.putExtra("optcEvolsSel", optcevols_tmp);

                    cxt.startActivity(intent);
                }
            });
        }
    }

    ArrayList<optcChar> optcchars;
    ArrayList<optcCharEvol> optcevols;
    ArrayList<optcCharEvol> optcevols_filtered;
    private final LayoutInflater mInflater;

    public CharEvolAdapter(Context context, ArrayList<optcChar> optcchars, ArrayList<optcCharEvol> optcevols, ArrayList<optcCharEvol> optcevols_filtered) {
        this.mInflater = LayoutInflater.from(context);
        this.optcchars = optcchars;
        this.optcevols = optcevols;
        this.optcevols_filtered = optcevols_filtered;
    }

    @Override
    public int getItemCount() {
        return optcevols_filtered.size();
    }

    @Override
    public CharEvolViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.char_item, viewGroup, false);
        View v = mInflater.inflate(R.layout.charevol_item, viewGroup, false);
        CharEvolViewHolder cvh = new CharEvolViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CharEvolViewHolder cvh, int i) {

        final optcCharEvol item = optcevols_filtered.get(i);

        //cvh.charName.setText(optcchars_filtered.get(i).getCharName());

        int chClass = item.getChildCharID();

        String cid = "0000" + chClass;
        String ccid = cid.substring(cid.length() - 4);
        Picasso.with(mInflater.getContext()).load("http://onepiece-treasurecruise.com/wp-content/uploads/f" + ccid + ".png").into(cvh.charEvolChild);

        String evolvers = item.getEvolCharID();
        evolvers = evolvers.replaceAll("\"|\\[|\\]", "");
        //chClass = chClass.replaceAll(",", ", ");

        int evolImgId = 0;
        for (String retval: evolvers.split(",")) {
            String ecid = "0000" + retval;
            String eccid = ecid.substring(ecid.length() - 4);
            if(evolImgId == 0) {
                Picasso.with(mInflater.getContext()).load("http://onepiece-treasurecruise.com/wp-content/uploads/f" + eccid + ".png").into(cvh.evol1);
            }
            else if(evolImgId == 1) {
                Picasso.with(mInflater.getContext()).load("http://onepiece-treasurecruise.com/wp-content/uploads/f" + eccid + ".png").into(cvh.evol2);
            }
            else if(evolImgId == 2) {
                Picasso.with(mInflater.getContext()).load("http://onepiece-treasurecruise.com/wp-content/uploads/f" + eccid + ".png").into(cvh.evol3);
            }
            else if(evolImgId == 3) {
                Picasso.with(mInflater.getContext()).load("http://onepiece-treasurecruise.com/wp-content/uploads/f" + eccid + ".png").into(cvh.evol4);
            }
            else if(evolImgId == 4) {
                Picasso.with(mInflater.getContext()).load("http://onepiece-treasurecruise.com/wp-content/uploads/f" + eccid + ".png").into(cvh.evol5);
            }

            evolImgId++;
        }
    }

}
