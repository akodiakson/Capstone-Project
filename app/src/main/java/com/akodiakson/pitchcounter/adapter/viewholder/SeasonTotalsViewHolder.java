package com.akodiakson.pitchcounter.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.akodiakson.pitchcounter.R;

/**
 * Created by ace0808 on 4/22/2016.
 */
public class SeasonTotalsViewHolder extends RecyclerView.ViewHolder {

    private TextView hitsTotal;
    private TextView strikeoutsTotal;
    private TextView walksTotal;

    public SeasonTotalsViewHolder(View rowView){
        super(rowView);
        hitsTotal = (TextView) rowView.findViewById(R.id.season_totals_hits);
        strikeoutsTotal = (TextView) rowView.findViewById(R.id.season_totals_strikeouts);
        walksTotal = (TextView) rowView.findViewById(R.id.season_totals_walks);
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
