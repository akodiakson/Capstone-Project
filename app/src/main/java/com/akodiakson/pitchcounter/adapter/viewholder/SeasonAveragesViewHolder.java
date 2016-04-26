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

    private TextView hitsTotal;
    private TextView strikeoutsTotal;
    private TextView walksTotal;

    public SeasonAveragesViewHolder(View rowView){
        super(rowView);
        first = (TextView) rowView.findViewById(R.id.season_average_balls);
        second = (TextView) rowView.findViewById(R.id.season_average_pitches);
        third = (TextView) rowView.findViewById(R.id.season_average_strikes);

        hitsTotal = (TextView) rowView.findViewById(R.id.season_totals_hits);
        strikeoutsTotal = (TextView) rowView.findViewById(R.id.season_totals_strikeouts);
        walksTotal = (TextView) rowView.findViewById(R.id.season_totals_walks);
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

    public TextView getHitsTotal() {
        return hitsTotal;
    }

    public TextView getStrikeoutsTotal() {
        return strikeoutsTotal;
    }

    public TextView getWalksTotal() {
        return walksTotal;
    }
}
