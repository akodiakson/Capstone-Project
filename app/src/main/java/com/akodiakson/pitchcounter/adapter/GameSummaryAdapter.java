package com.akodiakson.pitchcounter.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.akodiakson.pitchcounter.R;
import com.akodiakson.pitchcounter.activity.GameSummaryDetailActivity;
import com.akodiakson.pitchcounter.adapter.viewholder.GameSummaryViewHolder;
import com.akodiakson.pitchcounter.adapter.viewholder.SeasonAveragesViewHolder;
import com.akodiakson.pitchcounter.fragment.GameSummaryDetailFragment;
import com.akodiakson.pitchcounter.model.Game;
import com.akodiakson.pitchcounter.model.SeasonStatsTO;
import com.akodiakson.pitchcounter.model.SummaryItemTO;
import com.akodiakson.pitchcounter.model.SummaryItemType;
import com.akodiakson.pitchcounter.util.DateUtil;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by ace0808 on 4/21/2016.
 */
public class GameSummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<SummaryItemTO> mValues;
    private final boolean mTwoPane;
    private final WeakReference<FragmentActivity> mActivityWeakReference;

    private static final int VIEW_TYPE_GAME_SUMMARY = 1;
    private static final int VIEW_TYPE_SEASON_AVERAGES = 2;

    public GameSummaryAdapter(List<SummaryItemTO> items, boolean twoPane, WeakReference<FragmentActivity> fragmentWeakReference) {
        mValues = items;
        mTwoPane = twoPane;
        mActivityWeakReference = fragmentWeakReference;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case VIEW_TYPE_GAME_SUMMARY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_summary, parent, false);
                holder = new GameSummaryViewHolder(view);
                break;
            case VIEW_TYPE_SEASON_AVERAGES:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_game_summary_master_header, parent, false);
                holder = new SeasonAveragesViewHolder(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_GAME_SUMMARY:
                bindGameSummaryItem((GameSummaryViewHolder) holder, position);
                break;
            case VIEW_TYPE_SEASON_AVERAGES:
                bindAverages((SeasonAveragesViewHolder) holder, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        SummaryItemTO summaryItemTO = mValues.get(position);
        SummaryItemType summaryItemType = summaryItemTO.getSummaryItemType();
        if (summaryItemType == SummaryItemType.GAME) {
            return VIEW_TYPE_GAME_SUMMARY;
        }
        if (summaryItemType == SummaryItemType.AVERAGE) {
            return VIEW_TYPE_SEASON_AVERAGES;
        }

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private void bindAverages(SeasonAveragesViewHolder holder, int position) {
        SummaryItemTO summaryItemTO = mValues.get(position);
        SeasonStatsTO data = (SeasonStatsTO) summaryItemTO.getData();
        holder.getBalls().setText(DecimalFormat.getPercentInstance().format(data.calculatePercentBalls()));
        float pitchesPerGame = data.calculateAveragePitchesPerGame();
        DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        holder.getPitches().setText(decimalFormat.format(pitchesPerGame));
        holder.getStrikes().setText(DecimalFormat.getPercentInstance().format(data.calculatePercentStrikes()));
        holder.getHitsTotal().setText(String.valueOf(data.getTotalHits()));
        holder.getStrikeoutsTotal().setText(String.valueOf(data.getTotalStrikeouts()));
        holder.getWalksTotal().setText(String.valueOf(data.getTotalWalks()));
    }

    private void bindGameSummaryItem(final GameSummaryViewHolder holder, int position) {
        SummaryItemTO summaryItemTO = mValues.get(position);
        Game data = (Game) summaryItemTO.getData();
        holder.getSummaryCount().setText(String.valueOf(data.getPitches()));
        holder.getDateText().setText(DateUtil.getDisplayableDate(data.getDate()));
        View barBalls = holder.getBallStrikeDistributionBar().findViewById(R.id.bar_balls);
        View barStrikes = holder.getBallStrikeDistributionBar().findViewById(R.id.bar_strikes);
        LinearLayout.LayoutParams ballLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, data.getBalls());
        barBalls.setLayoutParams(ballLayoutParams);
        LinearLayout.LayoutParams strikeLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, data.getStrikes());
        barStrikes.setLayoutParams(strikeLayoutParams);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                SummaryItemTO summaryItemTO1 = mValues.get(position);
                Game game = (Game) summaryItemTO1.getData();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(GameSummaryDetailFragment.ARG_ITEM_ID, game);
                    GameSummaryDetailFragment fragment = new GameSummaryDetailFragment();
                    fragment.setArguments(arguments);
                    if (mActivityWeakReference != null && mActivityWeakReference.get() != null) {
                        mActivityWeakReference.get().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.gamesummary_detail_container, fragment)
                                .commit();
                    }
                } else {
                    if (mActivityWeakReference != null && mActivityWeakReference.get() != null) {
                        FragmentActivity fragmentActivity = mActivityWeakReference.get();
                        Intent intent = new Intent(fragmentActivity, GameSummaryDetailActivity.class);
                        intent.putExtra(GameSummaryDetailFragment.ARG_ITEM_ID, game);
                        fragmentActivity.startActivity(intent);
                        fragmentActivity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                    }
                }
            }
        });
    }
}