package com.akodiakson.pitchcounter.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.akodiakson.pitchcounter.R;

/**
 * Created by ace0808 on 4/22/2016.
 */
public class SeasonAveragesViewHolder extends RecyclerView.ViewHolder{

    private TextView first;
    private TextView second;
    private TextView third;

    public SeasonAveragesViewHolder(View rowView){
        super(rowView);
        first = (TextView) rowView.findViewById(R.id.season_average_balls);
        second = (TextView) rowView.findViewById(R.id.season_average_pitches);
        third = (TextView) rowView.findViewById(R.id.season_average_strikes);
    }

    public TextView getFirst() {
        return first;
    }

    public TextView getSecond() {
        return second;
    }

    public TextView getThird() {
        return third;
    }
}
