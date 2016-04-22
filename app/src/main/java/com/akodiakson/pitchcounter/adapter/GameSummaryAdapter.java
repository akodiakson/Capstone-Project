package com.akodiakson.pitchcounter.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akodiakson.pitchcounter.R;
import com.akodiakson.pitchcounter.activity.GameSummaryDetailActivity;
import com.akodiakson.pitchcounter.activity.GameSummaryDetailFragment;
import com.akodiakson.pitchcounter.adapter.viewholder.GameSummaryViewHolder;
import com.akodiakson.pitchcounter.adapter.viewholder.SeasonTotalsViewHolder;
import com.akodiakson.pitchcounter.model.Game;
import com.akodiakson.pitchcounter.model.SummaryItemTO;
import com.akodiakson.pitchcounter.model.SummaryItemType;

import java.lang.ref.WeakReference;
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
    private static final int VIEW_TYPE_SEASON_TOTALS = 3;

    public GameSummaryAdapter(List<SummaryItemTO> items, boolean twoPane, WeakReference<FragmentActivity> fragmentWeakReference) {
        mValues = items;
        mTwoPane = twoPane;
        mActivityWeakReference = fragmentWeakReference;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;

        switch (viewType){
            case VIEW_TYPE_GAME_SUMMARY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gamesummary_list_content, parent, false);
                holder = new GameSummaryViewHolder(view);
                break;
            case VIEW_TYPE_SEASON_AVERAGES:
                break;
            case VIEW_TYPE_SEASON_TOTALS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_summaries_season_totals, parent, false);
                holder = new SeasonTotalsViewHolder(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch(getItemViewType(position)){
            case VIEW_TYPE_GAME_SUMMARY:
                bindGameSummaryItem((GameSummaryViewHolder) holder, position);
                break;
            case VIEW_TYPE_SEASON_AVERAGES:
//                bindGameSummaryItem((GameSummaryViewHolder) holder, position);
                break;
            case VIEW_TYPE_SEASON_TOTALS:
//                bindGameSummaryItem((GameSummaryViewHolder) holder, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        SummaryItemTO summaryItemTO = mValues.get(position);
        SummaryItemType summaryItemType = summaryItemTO.getSummaryItemType();
        if(summaryItemType == SummaryItemType.GAME){
            return VIEW_TYPE_GAME_SUMMARY;
        }
        if(summaryItemType == SummaryItemType.AVERAGE){
            return VIEW_TYPE_SEASON_AVERAGES;
        }
        if(summaryItemType == SummaryItemType.SEASON){
            return VIEW_TYPE_SEASON_TOTALS;
        }

        return super.getItemViewType(position);
    }

    private void bindGameSummaryItem(final GameSummaryViewHolder holder, int position) {
        SummaryItemTO summaryItemTO = mValues.get(position);
        Game data = (Game) summaryItemTO.getData();
        holder.getSummaryCount().setText(String.valueOf(data.getPitches()));
        holder.getDateText().setText(String.valueOf(data.getDate()));
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
                    Context context = v.getContext();
                    Intent intent = new Intent(context, GameSummaryDetailActivity.class);
                    intent.putExtra(GameSummaryDetailFragment.ARG_ITEM_ID, game);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}