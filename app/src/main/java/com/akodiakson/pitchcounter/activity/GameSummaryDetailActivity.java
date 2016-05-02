package com.akodiakson.pitchcounter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.akodiakson.pitchcounter.BusProvider;
import com.akodiakson.pitchcounter.PitchCounterApplication;
import com.akodiakson.pitchcounter.R;
import com.akodiakson.pitchcounter.event.ImagesRetrievedEvent;
import com.akodiakson.pitchcounter.fragment.GameSummaryDetailFragment;
import com.akodiakson.pitchcounter.model.ImageUrl;
import com.akodiakson.pitchcounter.util.RetrieveImagesUtil;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;

/**
 * An activity representing a single GameSummary detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link GameSummaryListActivity}.
 */
//TODO -- 1. Initialize calls to get average stats
//TODO -- 2. Initialize calls to get season stats
public class GameSummaryDetailActivity extends AppCompatActivity {

    private Tracker defaultTracker;

    @Bind(R.id.background_image_view)
    ImageView backgroundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PitchCounterApplication application = (PitchCounterApplication) getApplication();
        defaultTracker = application.getDefaultTracker();

        setContentView(R.layout.activity_gamesummary_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putSerializable(GameSummaryDetailFragment.ARG_ITEM_ID,
                    getIntent().getSerializableExtra(GameSummaryDetailFragment.ARG_ITEM_ID));
            GameSummaryDetailFragment fragment = new GameSummaryDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.gamesummary_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        defaultTracker.setScreenName("SummaryActivity");
        defaultTracker.send(new HitBuilders.ScreenViewBuilder().build());

        BusProvider.getInstance().register(this);
        PitchCounterApplication application = (PitchCounterApplication) getApplication();
        if(application.getImageUrls().size() == 0){
            RetrieveImagesUtil.retrieveImages(this);
        } else {
            displayBackgroundImage(application.getImageUrls());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ButterKnife.unbind(this);
        BusProvider.getInstance().unregister(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, GameSummaryListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onImagesRetrieved(ImagesRetrievedEvent event){
        PitchCounterApplication application = (PitchCounterApplication)getApplication();
        application.setImageUrls(event.getImageUrls());
        displayBackgroundImage(event.getImageUrls());
    }

    private void displayBackgroundImage(List<ImageUrl> imageUrls) {
        int position = new Random().nextInt(imageUrls.size());
        ImageUrl imageUrlTO = imageUrls.get(position);
        String url = imageUrlTO.getImageUrl();
        Picasso.with(this).load(url)
                .transform(new GrayscaleTransformation())
                .transform(new VignetteFilterTransformation(this))
                .into(backgroundImageView);

    }
}
