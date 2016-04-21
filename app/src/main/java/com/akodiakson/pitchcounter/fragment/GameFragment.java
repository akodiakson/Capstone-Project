package com.akodiakson.pitchcounter.fragment;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.akodiakson.pitchcounter.R;
import com.akodiakson.pitchcounter.activity.GameSummaryListActivity;
import com.akodiakson.pitchcounter.data.GameContentProvider;
import com.akodiakson.pitchcounter.data.GameContract;
import com.akodiakson.pitchcounter.data.LoaderIdConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "GameFragment";
    @Bind(R.id.game_entry_pitch_count_value)
    View pitchCount;

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

    private String gameDate;

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
        ContentValues contentValues = new ContentValues();
        contentValues.put(GameContract.STRIKES, 1);
        String where = GameContract.DATE + " = ?";
        String[] selectionArgs = new String[]{gameDate};
        ContentResolver contentResolver = getContext().getContentResolver();
        contentResolver.update(GameContentProvider.CONTENT_URI, contentValues, where, selectionArgs);
    }

    @OnClick(R.id.game_entry_ball_button)
    public void ballTapped(View buttonView) {

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

        switch (id){
            case LoaderIdConstants.LOADER_ID_DOES_GAME_EXIST_FOR_DATE:
                String selection = GameContract.DATE + " = ?";
                String[] selectionArgs = new String[]{gameDate};
                return new CursorLoader(
                        getContext(), //context
                        GameContentProvider.CONTENT_URI, //Uri
                        GameContract.PROJECTION_GAME_COUNT_FOR_DATE, //projection aka columns
                        selection, //selection
                        selectionArgs, //selectionArgs
                        null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()){
            case LoaderIdConstants.LOADER_ID_DOES_GAME_EXIST_FOR_DATE:
                int countForDateIndex = data.getColumnIndex(GameContract.GAME_COUNT_FOR_DATE);
                data.moveToFirst();
                int numberOfGamesToday = data.getInt(countForDateIndex);
                Log.i(TAG, "onLoadFinished: data " + numberOfGamesToday);
                boolean doesGameExist = numberOfGamesToday > 0;
                if(!doesGameExist){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(GameContract.DATE, gameDate);
                    getContext().getContentResolver().insert(GameContentProvider.CONTENT_URI, contentValues);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
