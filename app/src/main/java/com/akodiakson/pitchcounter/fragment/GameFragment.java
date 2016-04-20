package com.akodiakson.pitchcounter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akodiakson.pitchcounter.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    @Bind(R.id.game_entry_strike_button)
    View strikeButton;

    @Bind(R.id.game_entry_strike_button_value)
    View strikeCount;

    @Bind(R.id.game_entry_ball_button)
    View ballButton;

    @Bind(R.id.game_entry_ball_button_value)
    View ballCount;

    @Bind(R.id.game_entry_hit_button)
    View hitButton;

    @Bind(R.id.game_entry_hit_button_value)
    View hitCount;

    @Bind(R.id.game_entry_walk_button)
    View walkButton;

    @Bind(R.id.game_entry_walk_button_value)
    View walkCount;

    @Bind(R.id.game_entry_strikeout_button)
    View strikeoutButton;

    @Bind(R.id.game_entry_hit_strikeout_button_value)
    View strikeoutCount;

    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.game_entry_strike_button)
    public void strikeTapped(View buttonView){

    }

    @OnClick(R.id.game_entry_ball_button)
    public void ballTapped(View buttonView){

    }

    @OnClick(R.id.game_entry_hit_button)
    public void hitTapped(View buttonView){

    }

    @OnClick(R.id.game_entry_walk_button)
    public void walkTapped(View buttonView){

    }

    @OnClick(R.id.game_entry_strikeout_button)
    public void strikeoutTapped(View buttonView){

    }
}
