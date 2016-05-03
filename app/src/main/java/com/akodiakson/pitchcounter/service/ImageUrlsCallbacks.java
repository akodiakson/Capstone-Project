package com.akodiakson.pitchcounter.service;

import android.util.Log;

import com.akodiakson.pitchcounter.BusProvider;
import com.akodiakson.pitchcounter.event.ImagesRetrievedEvent;
import com.akodiakson.pitchcounter.model.ImageUrl;
import com.akodiakson.pitchcounter.model.ImageUrlsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ace0808 on 4/26/2016.
 */
public class ImageUrlsCallbacks implements Callback<ImageUrlsResponse> {
    @Override
    public void onResponse(Call<ImageUrlsResponse> call, Response<ImageUrlsResponse> response) {
        if(response.isSuccessful()){
            ImageUrlsResponse imageUrlsResponse = response.body();
            if(imageUrlsResponse == null || imageUrlsResponse.getImageUrls() == null || imageUrlsResponse.getImageUrls().size() == 0){
                Log.e("ImageUrlsCallbacks", "onResponse - successful, but no images retrieved");
            } else {
                List<ImageUrl> imageUrls = imageUrlsResponse.getImageUrls();
                BusProvider.getInstance().post(new ImagesRetrievedEvent(imageUrls));
            }
        } else {
            Log.e("ImageUrlsCallbacks", "onResponse - not successful,  no images retrieved");
        }
    }

    @Override
    public void onFailure(Call<ImageUrlsResponse> call, Throwable t) {
        Log.e("ImageUrlsCallbacks", "onFailure", t);
    }
}
