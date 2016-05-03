package com.akodiakson.pitchcounter.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.akodiakson.pitchcounter.R;

/**
 * Created by ace0808 on 4/22/2016.
 */
public class SeasonAveragesViewHolder extends RecyclerView.ViewHolder{

    private TextView balls;
    private TextView pitches;
    private TextView strikes;

    private TextView hitsTotal;
    private TextView strikeoutsTotal;
    private TextView walksTotal;

    public SeasonAveragesViewHolder(View rowView){
        super(rowView);
        balls = (TextView) rowView.findViewById(R.id.season_average_balls);
        pitches = (TextView) rowView.findViewById(R.id.season_average_pitches);
        strikes = (TextView) rowView.findViewById(R.id.season_average_strikes);

        hitsTotal = (TextView) rowView.findViewById(R.id.season_totals_hits);
        strikeoutsTotal = (TextView) rowView.findViewById(R.id.season_totals_strikeouts);
        walksTotal = (TextView) rowView.findViewById(R.id.season_totals_walks);
    }

    public TextView getBalls() {
        return balls;
    }

    public TextView getPitches() {
        return pitches;
    }

    public TextView getStrikes() {
        return strikes;
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
