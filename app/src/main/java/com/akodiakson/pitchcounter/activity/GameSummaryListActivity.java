package com.akodiakson.pitchcounter.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.akodiakson.pitchcounter.R;
import com.akodiakson.pitchcounter.adapter.SimpleItemRecyclerViewAdapter;
import com.akodiakson.pitchcounter.data.GameContentProvider;
import com.akodiakson.pitchcounter.data.GameContract;
import com.akodiakson.pitchcounter.data.GameCursorUtil;
import com.akodiakson.pitchcounter.data.LoaderIdConstants;
import com.akodiakson.pitchcounter.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of GameSummaries. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link GameSummaryDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class GameSummaryListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private List<Game> adapterDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("GameSummaryListActivity.onCreate");
        setContentView(R.layout.activity_gamesummary_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_summary_list_start_or_resume_game);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        View recyclerView = findViewById(R.id.gamesummary_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.gamesummary_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        getSupportLoaderManager().restartLoader(LoaderIdConstants.LOADER_ID_GET_GAME_SUMMARIES, null, this);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        System.out.println("GameSummaryListActivity.onCreateLoader");
        String selection = null;
        String selectionArgs[] = null;

        switch (id) {
            case LoaderIdConstants.LOADER_ID_GET_GAME_SUMMARIES:
                selection = null;
                selectionArgs = new String[]{};
                return new CursorLoader(
                        this, //context
                        GameContentProvider.CONTENT_URI, //Uri
                        GameContract.PROJECTION_ALL_COLUMNS, //projection aka columns
                        selection, //selection
                        selectionArgs, //selectionArgs
                        GameContract.DATE + " DESC");
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        System.out.println("GameSummaryListActivity.onLoadFinished");
        switch (loader.getId()) {
            case LoaderIdConstants.LOADER_ID_GET_GAME_SUMMARIES:
                adapterDataSet = new ArrayList<>();
                System.out.println("GameSummaryListActivity.myCase");

                System.out.println("data.getCount() = " + data.getCount());
//                data.moveToFirst();
//                data.moveToPosition(0);
//                for (data.moveToFirst(); !data.isAfterLast(); data.moveToNext()){
                while(data.moveToNext()) {
                    Game game = GameCursorUtil.buildGame(data);
                    adapterDataSet.add(game);
                }
//                }
                setAdapterData();

                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setAdapterData(){
        System.out.println("GameSummaryListActivity.setAdapterData");
        System.out.println("adapterDataSet = " + adapterDataSet);
        View recyclerView = findViewById(R.id.gamesummary_list);
        assert recyclerView != null;
        ((RecyclerView)recyclerView).setAdapter(new SimpleItemRecyclerViewAdapter(adapterDataSet));
    }
}
