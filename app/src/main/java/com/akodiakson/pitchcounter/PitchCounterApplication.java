package com.akodiakson.pitchcounter;

import android.app.Application;

import com.akodiakson.pitchcounter.model.ImageUrl;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ace0808 on 4/26/2016.
 */
public class PitchCounterApplication extends Application {
    private Tracker mTracker;
    private List<ImageUrl> imageUrls;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    public List<ImageUrl> getImageUrls() {
        if(imageUrls == null){
            imageUrls = new ArrayList<>();
        }
        return imageUrls;
    }

    public void setImageUrls(List<ImageUrl> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
