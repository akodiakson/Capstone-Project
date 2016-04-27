package com.akodiakson.pitchcounter.service;

import com.akodiakson.pitchcounter.model.ImageUrlsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by ace0808 on 4/26/2016.
 */
public interface BaseballImageUrlsService {
    @GET
    Call<ImageUrlsResponse> retrieveImageUrls(@Url String url);
}
