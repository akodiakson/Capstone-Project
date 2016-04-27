package com.akodiakson.pitchcounter.service;

import android.app.IntentService;
import android.content.Intent;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class ImageUrlService extends IntentService {

    public static final String ACTION_RETRIEVE_IMAGE_URLS = "com.akodiakson.pitchcounter.service.action.ACTION_RETRIEVE_IMAGE_URLS";

    public ImageUrlService() {
        super("ImageUrlService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_RETRIEVE_IMAGE_URLS.equals(action)) {
                handleActionRetrieveImageUrls();
            }
        }
    }

    private void handleActionRetrieveImageUrls() {
        BaseballImageApi.getInstance().retrieveImageUrls();
    }
}
