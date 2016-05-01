package com.akodiakson.pitchcounter.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akodiakson.pitchcounter.R;

/**
 * Created by ace0808 on 4/21/2016.
 */
public class GameSummaryViewHolder extends RecyclerView.ViewHolder {

    private TextView summaryCount;
    private TextView dateText;

    private LinearLayout ballStrikeDistributionBar;

    public GameSummaryViewHolder(View rowView){
        super(rowView);

        summaryCount = (TextView) rowView.findViewById(R.id.game_summary_count);
        dateText = (TextView) rowView.findViewById(R.id.game_summary_date_text);
        ballStrikeDistributionBar = (LinearLayout) rowView.findViewById(R.id.ball_strike_distribution_bar);
    }

    public TextView getSummaryCount() {
        return summaryCount;
    }

    public TextView getDateText() {
        return dateText;
    }

    public LinearLayout getBallStrikeDistributionBar() {
        return ballStrikeDistributionBar;
    }
}
