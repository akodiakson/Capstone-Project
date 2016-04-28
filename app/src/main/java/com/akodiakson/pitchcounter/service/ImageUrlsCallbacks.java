package com.akodiakson.pitchcounter.service;

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
                System.out.println("ImageUrlsCallbacks.onResponse success, but no urls retrieved");
            } else {
                System.out.println("ImageUrlsCallbacks.onResponse success");
                List<ImageUrl> imageUrls = imageUrlsResponse.getImageUrls();
                BusProvider.getInstance().post(new ImagesRetrievedEvent(imageUrls));
            }
        }
    }

    @Override
    public void onFailure(Call<ImageUrlsResponse> call, Throwable t) {
        System.out.println("ImageUrlsCallbacks.onFailure");
        System.out.println("t = " + t);
    }
}
