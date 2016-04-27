package com.akodiakson.pitchcounter.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ace0808 on 4/26/2016.
 */
public class ImageUrlsResponse implements Serializable{

    private List<ImageUrl> imageUrls;

    public List<ImageUrl> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<ImageUrl> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
