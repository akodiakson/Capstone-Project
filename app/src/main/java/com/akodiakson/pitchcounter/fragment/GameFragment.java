package com.akodiakson.pitchcounter.fragment;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akodiakson.pitchcounter.PitchCounterApplication;
import com.akodiakson.pitchcounter.R;
import com.akodiakson.pitchcounter.activity.GameSummaryListActivity;
import com.akodiakson.pitchcounter.data.GameContentProvider;
import com.akodiakson.pitchcounter.data.GameContract;
import com.akodiakson.pitchcounter.data.GameCursorUtil;
import com.akodiakson.pitchcounter.data.LoaderIdConstants;
import com.akodiakson.pitchcounter.data.StatType;
import com.akodiakson.pitchcounter.data.UpdateStatQueryHandler;
import com.akodiakson.pitchcounter.model.Game;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, UpdateStatQueryHandler.UpdateStatQueryListener {

    private static final int UPDATE_COOKIE = -1;
    @Bind(R.id.game_entry_pitch_count_value)
    TextView pitchCount;

    @Bind(R.id.game_entry_strike_button_value)
    TextView strikeCount;

    @Bind(R.id.game_entry_ball_button_value)
    TextView ballCount;

    @Bind(R.id.game_entry_hit_button_value)
    TextView hitCount;

    @Bind(R.id.game_entry_walk_button_value)
    TextView walkCount;

    @Bind(R.id.game_entry_hit_strikeout_button_value)
    TextView strikeoutCount;

    @Bind(R.id.bar_balls)
    View barBalls;

    @Bind(R.id.bar_strikes)
    View barStrikes;

    private String gameDate;

    private Stack<StatType> userActionsStack = new Stack<>();

    private View rootView;
    private Tracker defaultTracker;

    public GameFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PitchCounterApplication application = (PitchCounterApplication) getActivity().getApplication();
        defaultTracker = application.getDefaultTracker();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_game, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        Date today = new Date();
        gameDate = new SimpleDateFormat("yyyyMMdd").format(today);
    }

    @Override
    public void onResume() {
        super.onResume();
        defaultTracker.setScreenName("GameFragment");
        defaultTracker.send(new HitBuilders.ScreenViewBuilder().build());
        getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_DOES_GAME_EXIST_FOR_DATE, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_game_entry, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_item_history_undo) {
            if (!userActionsStack.isEmpty()) {
                StatType peekedStatType = userActionsStack.peek();
                if(peekedStatType == StatType.BALL || peekedStatType == StatType.STRIKE){
                    updateStatAndTotalPitchValues(peekedStatType, getValueFromFieldToPop(peekedStatType)-1, Integer.valueOf(pitchCount.getText().toString())-1);
                } else {
                    updateStatValue(peekedStatType, getValueFromFieldToPop(peekedStatType)-1);
                }
                userActionsStack.pop();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int getValueFromFieldToPop(StatType statTypeToPop) {
        switch (statTypeToPop) {

            case STRIKE:
                return Integer.valueOf(strikeCount.getText().toString());
            case BALL:
                return Integer.valueOf(ballCount.getText().toString());
            case HIT:
                return Integer.valueOf(hitCount.getText().toString());
            case WALK:
                return Integer.valueOf(walkCount.getText().toString());
            case STRIKEOUT:
                return Integer.valueOf(strikeoutCount.getText().toString());

        }
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.game_entry_strike_button)
    public void strikeTapped(View buttonView) {
        userActionsStack.push(StatType.STRIKE);
        getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_GET_CURRENT_STRIKE_COUNT, null, this);
    }

    private void updateStatAndTotalPitchValues(StatType statType, int newValue, int newTotalPitches) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(statType.getAssociatedStatColumn(), newValue);
        contentValues.put(GameContract.PITCHES, newTotalPitches);
        updateStatValue(statType, contentValues);
    }

    @OnClick(R.id.game_entry_ball_button)
    public void ballTapped(View buttonView) {
        userActionsStack.push(StatType.BALL);
        getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_GET_CURRENT_BALL_COUNT, null, this);
    }

    @OnClick(R.id.game_entry_hit_button)
    public void hitTapped(View buttonView) {
        userActionsStack.push(StatType.HIT);
        getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_GET_HIT_COUNT, null, this);
    }

    @OnClick(R.id.game_entry_walk_button)
    public void walkTapped(View buttonView) {
        userActionsStack.push(StatType.WALK);
        getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_GET_WALK_COUNT, null, this);
    }

    @OnClick(R.id.game_entry_strikeout_button)
    public void strikeoutTapped(View buttonView) {
        userActionsStack.push(StatType.STRIKEOUT);
        getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_GET_STRIKEOUT_COUNT, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id) {
            case LoaderIdConstants.LOADER_ID_GET_GAME_SUMMARY:
                return new CursorLoader(
                        getContext(), //context
                        GameContentProvider.CONTENT_URI, //Uri
                        GameContract.PROJECTION_ALL_COLUMNS, //projection aka columns
                        GameContract.DATE + " = ?", //selection
                        new String[]{gameDate}, //selectionArgs
                        null);
            case LoaderIdConstants.LOADER_ID_DOES_GAME_EXIST_FOR_DATE:
                return new CursorLoader(
                        getContext(), //context
                        GameContentProvider.CONTENT_URI, //Uri
                        GameContract.PROJECTION_GAME_COUNT_FOR_DATE, //projection aka columns
                        GameContract.DATE + " = ?", //selection
                        new String[]{gameDate}, //selectionArgs
                        null);
            case LoaderIdConstants.LOADER_ID_GET_CURRENT_STRIKE_COUNT:
                return new CursorLoader(
                        getContext(),
                        GameContentProvider.CONTENT_URI,
                        new String[]{GameContract.STRIKES, GameContract.PITCHES},
                        GameContract.DATE + " = ?", //selection
                        new String[]{gameDate}, //selectionArgs
                        null);
            case LoaderIdConstants.LOADER_ID_GET_CURRENT_BALL_COUNT:
                return new CursorLoader(
                        getContext(),
                        GameContentProvider.CONTENT_URI,
                        new String[]{GameContract.BALLS, GameContract.PITCHES},
                        GameContract.DATE + " = ?", //selection
                        new String[]{gameDate}, //selectionArgs
                        null);
            case LoaderIdConstants.LOADER_ID_GET_HIT_COUNT:
                return new CursorLoader(
                        getContext(),
                        GameContentProvider.CONTENT_URI,
                        new String[]{GameContract.HITS},
                        GameContract.DATE + " = ?", //selection
                        new String[]{gameDate}, //selectionArgs
                        null);
            case LoaderIdConstants.LOADER_ID_GET_WALK_COUNT:
                return new CursorLoader(
                        getContext(),
                        GameContentProvider.CONTENT_URI,
                        new String[]{GameContract.WALKS},
                        GameContract.DATE + " = ?", //selection
                        new String[]{gameDate}, //selectionArgs
                        null);
            case LoaderIdConstants.LOADER_ID_GET_STRIKEOUT_COUNT:
                return new CursorLoader(
                        getContext(),
                        GameContentProvider.CONTENT_URI,
                        new String[]{GameContract.STRIKEOUTS},
                        GameContract.DATE + " = ?", //selection
                        new String[]{gameDate}, //selectionArgs
                        null);

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LoaderIdConstants.LOADER_ID_DOES_GAME_EXIST_FOR_DATE:
                int countForDateIndex = data.getColumnIndex(GameContract.NUM_GAMES);
                data.moveToFirst();
                int numberOfGamesToday = data.getInt(countForDateIndex);
                boolean doesGameExist = numberOfGamesToday > 0;
                if (!doesGameExist) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(GameContract.DATE, gameDate);
                    getContext().getContentResolver().insert(GameContentProvider.CONTENT_URI, contentValues);
                } else {
                    //Populate values on the six fields
                    getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_GET_GAME_SUMMARY, null, this);
                }
                break;
            case LoaderIdConstants.LOADER_ID_GET_CURRENT_STRIKE_COUNT:
                data.moveToFirst();
                int currentStrikeCount = data.getInt(data.getColumnIndex(GameContract.STRIKES));
                int updatedStrikeCount = currentStrikeCount + 1;
                int currentPitchCount = data.getInt(data.getColumnIndex(GameContract.PITCHES));
                int updatedPitchCount = currentPitchCount + 1;
                updateStatAndTotalPitchValues(StatType.STRIKE, updatedStrikeCount, updatedPitchCount);
                break;
            case LoaderIdConstants.LOADER_ID_GET_CURRENT_BALL_COUNT:
                data.moveToFirst();
                int currentBallCount = data.getInt(data.getColumnIndex(GameContract.BALLS));
                int currentTotalPitches = data.getInt(data.getColumnIndex(GameContract.PITCHES));
                currentBallCount += 1;
                currentTotalPitches += 1;
                updateStatAndTotalPitchValues(StatType.BALL, currentBallCount, currentTotalPitches);
                break;
            case LoaderIdConstants.LOADER_ID_GET_HIT_COUNT:
                data.moveToFirst();
                int hitCount = data.getInt(data.getColumnIndex(GameContract.HITS));
                hitCount += 1;
                updateStatValue(StatType.HIT, hitCount);
                break;
            case LoaderIdConstants.LOADER_ID_GET_WALK_COUNT:
                data.moveToFirst();
                int walkCount = data.getInt(data.getColumnIndex(GameContract.WALKS));
                walkCount += 1;
                updateStatValue(StatType.WALK, walkCount);
                break;
            case LoaderIdConstants.LOADER_ID_GET_STRIKEOUT_COUNT:
                data.moveToFirst();
                int strikeoutCount = data.getInt(data.getColumnIndex(GameContract.STRIKEOUTS));
                strikeoutCount += 1;
                updateStatValue(StatType.STRIKEOUT, strikeoutCount);
                break;
            case LoaderIdConstants.LOADER_ID_GET_GAME_SUMMARY:
                data.moveToFirst();
                Game game = GameCursorUtil.buildGame(data);
                updateCounts(game);
                updatePitchDistribution(game);
                break;
        }
    }

    private void updatePitchDistribution(Game game) {
        LinearLayout.LayoutParams ballLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, game.getBalls());
        barBalls.setLayoutParams(ballLayoutParams);
        LinearLayout.LayoutParams strikeLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, game.getStrikes());
        barStrikes.setLayoutParams(strikeLayoutParams);
    }

    private void updateCounts(Game game) {
        pitchCount.setText(String.valueOf(game.getPitches()));
        strikeCount.setText(String.valueOf(game.getStrikes()));
        ballCount.setText(String.valueOf(game.getBalls()));
        walkCount.setText(String.valueOf(game.getWalks()));
        hitCount.setText(String.valueOf(game.getHits()));
        strikeoutCount.setText(String.valueOf(game.getStrikeouts()));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onUpdateComplete(int token) {
        //When you're done, query to update the count TextViews
        if (token != StatType.TOTAL_PITCHES.ordinal()) {
            getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_GET_GAME_SUMMARY, null, this);
        }
    }

    private void updateStatValue(StatType statType, int newValue) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(statType.getAssociatedStatColumn(), newValue);
        updateStatValue(statType, contentValues);
    }

    private void updateStatValue(StatType statType, ContentValues contentValues) {
        String where = GameContract.DATE + " = ?";
        String[] selectionArgs = new String[]{gameDate};
        ContentResolver contentResolver = getContext().getContentResolver();
        contentResolver.update(GameContentProvider.CONTENT_URI, contentValues, where, selectionArgs);
        UpdateStatQueryHandler handler = new UpdateStatQueryHandler(getContext().getContentResolver(), new WeakReference<UpdateStatQueryHandler.UpdateStatQueryListener>(this));
        handler.startUpdate(statType.ordinal(), UPDATE_COOKIE, GameContentProvider.CONTENT_URI, contentValues, where, selectionArgs);
    }

    @OnClick(R.id.fab_game_entry_to_summary)
    public void onGameSummaryClicked(View view){
        Intent intent = new Intent(getActivity(), GameSummaryListActivity.class);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), rootView.findViewById(R.id.ball_strike_distribution_bar), getString(R.string.transition_name_game_to_master));
        ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
    }
}
