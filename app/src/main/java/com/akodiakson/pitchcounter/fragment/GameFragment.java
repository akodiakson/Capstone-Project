package com.akodiakson.pitchcounter.fragment;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.akodiakson.pitchcounter.R;
import com.akodiakson.pitchcounter.activity.GameSummaryListActivity;
import com.akodiakson.pitchcounter.data.GameContentProvider;
import com.akodiakson.pitchcounter.data.GameContract;
import com.akodiakson.pitchcounter.data.GameCursorUtil;
import com.akodiakson.pitchcounter.data.LoaderIdConstants;
import com.akodiakson.pitchcounter.data.StatType;
import com.akodiakson.pitchcounter.data.UpdateStatQueryHandler;
import com.akodiakson.pitchcounter.model.Game;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, UpdateStatQueryHandler.UpdateStatQueryListener {

    private static final String TAG = "GameFragment";
    @Bind(R.id.game_entry_pitch_count_value)
    TextView pitchCount;

    @Bind(R.id.game_entry_strike_button)
    View strikeButton;

    @Bind(R.id.game_entry_strike_button_value)
    TextView strikeCount;

    @Bind(R.id.game_entry_ball_button)
    View ballButton;

    @Bind(R.id.game_entry_ball_button_value)
    TextView ballCount;

    @Bind(R.id.game_entry_hit_button)
    View hitButton;

    @Bind(R.id.game_entry_hit_button_value)
    TextView hitCount;

    @Bind(R.id.game_entry_walk_button)
    View walkButton;

    @Bind(R.id.game_entry_walk_button_value)
    TextView walkCount;

    @Bind(R.id.game_entry_strikeout_button)
    View strikeoutButton;

    @Bind(R.id.game_entry_hit_strikeout_button_value)
    TextView strikeoutCount;

    private String gameDate;

//    private int strikeCountValue = 0;

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
        getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_DOES_GAME_EXIST_FOR_DATE, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_game_entry, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_item_history_master) {
            Intent intent = new Intent(getActivity(), GameSummaryListActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.game_entry_strike_button)
    public void strikeTapped(View buttonView) {
        getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_GET_CURRENT_STRIKE_COUNT, null, this);
    }

    private void updateStatValueAsync(StatType statType, int newValue) {
        UpdateStatQueryHandler handler = new UpdateStatQueryHandler(getContext().getContentResolver(), new WeakReference<UpdateStatQueryHandler.UpdateStatQueryListener>(this));
        ContentValues contentValues = new ContentValues();
        System.out.println("newValue = " + newValue);
        contentValues.put(statType.getAssociatedStatColumn(), newValue);
        String where = GameContract.DATE + " = ?";
        String[] selectionArgs = new String[]{gameDate};
        ContentResolver contentResolver = getContext().getContentResolver();
        contentResolver.update(GameContentProvider.CONTENT_URI, contentValues, where, selectionArgs);
        handler.startUpdate(statType.ordinal(), newValue, GameContentProvider.CONTENT_URI, contentValues, where, selectionArgs);
    }

    @OnClick(R.id.game_entry_ball_button)
    public void ballTapped(View buttonView) {
        getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_GET_CURRENT_BALL_COUNT, null, this);
    }

    @OnClick(R.id.game_entry_hit_button)
    public void hitTapped(View buttonView) {

    }

    @OnClick(R.id.game_entry_walk_button)
    public void walkTapped(View buttonView) {

    }

    @OnClick(R.id.game_entry_strikeout_button)
    public void strikeoutTapped(View buttonView) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String selection = null;
        String selectionArgs[] = null;

        switch (id) {
            case LoaderIdConstants.LOADER_ID_GET_GAME_SUMMARIES:
                selection = null;
                selectionArgs = new String[]{};
                return new CursorLoader(
                        getContext(), //context
                        GameContentProvider.CONTENT_URI, //Uri
                        GameContract.PROJECTION_ALL_COLUMNS, //projection aka columns
                        GameContract.DATE + " = ?", //selection
                        new String[]{gameDate}, //selectionArgs
                        null);
            case LoaderIdConstants.LOADER_ID_DOES_GAME_EXIST_FOR_DATE:
                selection = GameContract.DATE + " = ?";
                selectionArgs = new String[]{gameDate};
                return new CursorLoader(
                        getContext(), //context
                        GameContentProvider.CONTENT_URI, //Uri
                        GameContract.PROJECTION_GAME_COUNT_FOR_DATE, //projection aka columns
                        selection, //selection
                        selectionArgs, //selectionArgs
                        null);
            case LoaderIdConstants.LOADER_ID_GET_CURRENT_STRIKE_COUNT:
                selection = GameContract.DATE + " = ?";
                selectionArgs = new String[]{gameDate};
                return new CursorLoader(
                        getContext(),
                        GameContentProvider.CONTENT_URI,
                        new String[]{GameContract.STRIKES},
                        selection,
                        selectionArgs,
                        null);
            case LoaderIdConstants.LOADER_ID_GET_CURRENT_BALL_COUNT:
                selection = GameContract.DATE + " = ?";
                selectionArgs = new String[]{gameDate};
                return new CursorLoader(
                        getContext(),
                        GameContentProvider.CONTENT_URI,
                        new String[]{GameContract.BALLS},
                        selection,
                        selectionArgs,
                        null);
            case LoaderIdConstants.LOADER_ID_GET_CURRENT_TOTAL_PITCH_COUNT:
                selection = GameContract.DATE + " = ?";
                selectionArgs = new String[]{gameDate};
                return new CursorLoader(
                        getContext(),
                        GameContentProvider.CONTENT_URI,
                        new String[]{GameContract.PITCHES},
                        selection,
                        selectionArgs,
                        null);

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LoaderIdConstants.LOADER_ID_DOES_GAME_EXIST_FOR_DATE:
                int countForDateIndex = data.getColumnIndex(GameContract.GAME_COUNT_FOR_DATE);
                data.moveToFirst();
                int numberOfGamesToday = data.getInt(countForDateIndex);
                boolean doesGameExist = numberOfGamesToday > 0;
                if (!doesGameExist) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(GameContract.DATE, gameDate);
                    getContext().getContentResolver().insert(GameContentProvider.CONTENT_URI, contentValues);
                } else {
                    //Populate values on the six fields
                    getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_GET_GAME_SUMMARIES, null, this);
                }
                break;
            case LoaderIdConstants.LOADER_ID_GET_CURRENT_STRIKE_COUNT:
                int strikeCountIndex = data.getColumnIndex(GameContract.STRIKES);
                data.moveToFirst();

                int currentStrikeCount = data.getInt(strikeCountIndex);
                int updatedCount = currentStrikeCount + 1;
                updateStatValueAsync(StatType.STRIKE, updatedCount);
                break;
            case LoaderIdConstants.LOADER_ID_GET_CURRENT_BALL_COUNT:
                int ballCountIndex = data.getColumnIndex(GameContract.BALLS);
                data.moveToFirst();
                int currentBallCount = data.getInt(ballCountIndex);
                currentBallCount += 1;
                updateStatValueAsync(StatType.BALL, currentBallCount);
                break;
            case LoaderIdConstants.LOADER_ID_GET_CURRENT_TOTAL_PITCH_COUNT:
                int totalPitchCountIndex = data.getColumnIndex(GameContract.PITCHES);
                data.moveToFirst();
                int totalPitchCount = data.getInt(totalPitchCountIndex);
                System.out.println("totalPitchCount after update = " + totalPitchCount);
                totalPitchCount += 1;
                updateStatValueAsync(StatType.TOTAL_PITCHES, totalPitchCount);
                break;
            case LoaderIdConstants.LOADER_ID_GET_GAME_SUMMARIES:
                data.moveToFirst();
                Game game = GameCursorUtil.buildGame(data);
                //TODO -- Andrew -- update text of 6 fields
                pitchCount.setText(String.valueOf(game.getPitches()));
                strikeCount.setText(String.valueOf(game.getStrikes()));
                ballCount.setText(String.valueOf(game.getBalls()));
                walkCount.setText(String.valueOf(game.getWalks()));
                hitCount.setText(String.valueOf(game.getHits()));
                strikeoutCount.setText(String.valueOf(game.getStrikeouts()));
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onUpdateComplete(int token, Object cookie, int result) {
        if (token == StatType.STRIKE.ordinal()) {
            ((TextView) strikeCount).setText(String.valueOf(cookie));
            getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_GET_CURRENT_TOTAL_PITCH_COUNT, null, this);
        } else if (token == StatType.BALL.ordinal()) {
            ((TextView) ballCount).setText(String.valueOf(cookie));
            getLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_GET_CURRENT_TOTAL_PITCH_COUNT, null, this);
        } else if (token == StatType.TOTAL_PITCHES.ordinal()) {
            ((TextView) pitchCount).setText(String.valueOf(cookie));
        }
    }
}
