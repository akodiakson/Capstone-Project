package com.akodiakson.pitchcounter.event;

import com.akodiakson.pitchcounter.model.ImageUrl;

import java.util.List;

/**
 * Created by ace0808 on 4/27/2016.
 */
public class ImagesRetrievedEvent {
    private final List<ImageUrl> imageUrls;

    public ImagesRetrievedEvent(List<ImageUrl> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<ImageUrl> getImageUrls() {
        return imageUrls;
    }
}
