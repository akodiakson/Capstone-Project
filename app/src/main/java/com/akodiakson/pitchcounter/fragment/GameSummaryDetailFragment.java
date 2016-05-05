package com.akodiakson.pitchcounter.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akodiakson.pitchcounter.PitchCounterApplication;
import com.akodiakson.pitchcounter.R;
import com.akodiakson.pitchcounter.activity.GameSummaryDetailActivity;
import com.akodiakson.pitchcounter.activity.GameSummaryListActivity;
import com.akodiakson.pitchcounter.model.Game;
import com.akodiakson.pitchcounter.util.DateUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * A fragment representing a single GameSummary detail screen.
 * This fragment is either contained in a {@link GameSummaryListActivity}
 * in two-pane mode (on tablets) or a {@link GameSummaryDetailActivity}
 * on handsets.
 */
public class GameSummaryDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
//    private DummyContent.DummyItem mItem;

    private Game mItem;
    private Tracker defaultTracker;

    private View rootView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GameSummaryDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        PitchCounterApplication application = (PitchCounterApplication) getActivity().getApplication();
        defaultTracker = application.getDefaultTracker();

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = (Game) getArguments().getSerializable(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_game_summary_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            setPitchStats();
            setPitchDistribution();
            setGameStats();
        }

        return rootView;
    }

    private void setPitchStats() {
        TextView balls = (TextView) rootView.findViewById(R.id.season_average_balls);
        TextView pitches = (TextView) rootView.findViewById(R.id.season_average_pitches);
        TextView strikes = (TextView) rootView.findViewById(R.id.season_average_strikes);
        balls.setText(String.valueOf(mItem.getBalls()));
        pitches.setText(String.valueOf(mItem.getPitches()));
        strikes.setText(String.valueOf(mItem.getStrikes()));
    }

    private void setPitchDistribution() {
        View barBalls = rootView.findViewById(R.id.bar_balls);
        View barStrikes = rootView.findViewById(R.id.bar_strikes);
        LinearLayout.LayoutParams ballLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, mItem.getBalls());
        barBalls.setLayoutParams(ballLayoutParams);
        LinearLayout.LayoutParams strikeLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, mItem.getStrikes());
        barStrikes.setLayoutParams(strikeLayoutParams);
    }

    private void setGameStats() {
        TextView hitsTotal = (TextView) rootView.findViewById(R.id.season_totals_hits);
        TextView strikeoutsTotal = (TextView) rootView.findViewById(R.id.season_totals_strikeouts);
        TextView walksTotal = (TextView) rootView.findViewById(R.id.season_totals_walks);
        hitsTotal.setText(String.valueOf(mItem.getHits()));
        strikeoutsTotal.setText(String.valueOf(mItem.getStrikeouts()));
        walksTotal.setText(String.valueOf(mItem.getWalks()));
    }


    @Override
    public void onResume() {
        super.onResume();
        defaultTracker.setScreenName("DetailFragment");
        defaultTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(DateUtil.getDisplayableDate(mItem.getDate()));
        }

        AdView adView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
    }
}
