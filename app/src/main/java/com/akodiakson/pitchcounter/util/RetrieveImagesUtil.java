package com.akodiakson.pitchcounter.util;

import android.content.Context;
import android.content.Intent;

import com.akodiakson.pitchcounter.service.ImageUrlService;

/**
 * Created by ace0808 on 4/27/2016.
 */
public class RetrieveImagesUtil {

    public static void retrieveImages(Context context){
        Intent intent = new Intent(context, ImageUrlService.class);
        intent.setAction(ImageUrlService.ACTION_RETRIEVE_IMAGE_URLS);
        context.startService(intent);
    }
}
