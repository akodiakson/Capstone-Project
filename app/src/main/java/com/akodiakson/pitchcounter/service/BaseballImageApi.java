package com.akodiakson.pitchcounter.service;

import com.akodiakson.pitchcounter.model.ImageUrlsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ace0808 on 4/26/2016.
 */
public class BaseballImageApi {

    private static final String ENDPOINT_URL = "https://write.as/";
    private static final String FILE_NAME = "msimkcssvq49y.txt";

    private static BaseballImageApi INSTANCE;

    public static BaseballImageApi getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BaseballImageApi();
        }
        return INSTANCE;
    }

    public void retrieveImageUrls(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseballImageUrlsService serviceInterface = retrofit.create(BaseballImageUrlsService.class);
        Call<ImageUrlsResponse> call = serviceInterface.retrieveImageUrls(FILE_NAME);
        Callback<ImageUrlsResponse> callback = new ImageUrlsCallbacks();
        call.enqueue(callback);
    }
}
