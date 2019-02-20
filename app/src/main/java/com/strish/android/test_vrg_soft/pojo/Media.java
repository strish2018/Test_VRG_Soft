package com.strish.android.test_vrg_soft.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Media implements Serializable {

    @SerializedName("media-metadata")
    private List<MediaMetadata> mMediaMetadata;
    private String caption;

    public List<MediaMetadata> getMediaMetadata() {
        return mMediaMetadata;
    }

    public void setMediaMetadata(List<MediaMetadata> mediaMetadata) {
        mMediaMetadata = mediaMetadata;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLargestImageURL() {
        int maxWidth = mMediaMetadata.get(0).getWidth();
        int maxIndex = 0;
        for (int i = 1; i < mMediaMetadata.size(); i++) {
            if (mMediaMetadata.get(i).getWidth() > maxWidth) {
                maxWidth = mMediaMetadata.get(i).getWidth();
                maxIndex = i;
            }
        }
        return mMediaMetadata.get(maxIndex).getUrl();
    }

}
