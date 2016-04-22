package com.akodiakson.pitchcounter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akodiakson.pitchcounter.R;
import com.akodiakson.pitchcounter.adapter.viewholder.GameSummaryViewHolder;
import com.akodiakson.pitchcounter.model.Game;

import java.util.List;

/**
 * Created by ace0808 on 4/21/2016.
 */
public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Game> mValues;

    public SimpleItemRecyclerViewAdapter(List<Game> items) {
        mValues = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gamesummary_list_content, parent, false);
        return new GameSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);

        ((GameSummaryViewHolder)holder).getSummaryCount().setText(String.valueOf(mValues.get(position).getPitches()));
        ((GameSummaryViewHolder)holder).getDateText().setText(String.valueOf(mValues.get(position).getDate()));
//        holder.mContentView.setText(mValues.get(position).content);
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putString(GameSummaryDetailFragment.ARG_ITEM_ID, holder.mItem.id);
//                    GameSummaryDetailFragment fragment = new GameSummaryDetailFragment();
//                    fragment.setArguments(arguments);
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.gamesummary_detail_container, fragment)
//                            .commit();
//                } else {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, GameSummaryDetailActivity.class);
//                    intent.putExtra(GameSummaryDetailFragment.ARG_ITEM_ID, holder.mItem.id);
//
//                    context.startActivity(intent);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

//        public class ViewHolder extends RecyclerView.ViewHolder {
//            public final View mView;
//            public final TextView mIdView;
//            public final TextView mContentView;
//            public DummyContent.DummyItem mItem;
//
//            public ViewHolder(View view) {
//                super(view);
//                mView = view;
//                mIdView = (TextView) view.findViewById(R.id.id);
//                mContentView = (TextView) view.findViewById(R.id.content);
//            }
//
//            @Override
//            public String toString() {
//                return super.toString() + " '" + mContentView.getText() + "'";
//            }
//        }
}