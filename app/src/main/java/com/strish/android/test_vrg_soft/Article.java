package com.strish.android.test_vrg_soft;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.strish.android.test_vrg_soft.pojo.Media;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "article_table")
public class Article implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int dbId;
    private String title;
    private String byline;
    @SerializedName("abstract")
    private String abstractText;
    @SerializedName("published_date")
    private String publishedDate;
    @Ignore
    private List<Media> media;
    private boolean favorite = false;
    private String captionFavorites;
    private String thumbnailUrlFavorites;
    private byte[] thumbnailByteFavorites;

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getCaptionFavorites() {
        return captionFavorites;
    }

    public void setCaptionFavorites(String captionFavorites) {
        this.captionFavorites = captionFavorites;
    }

    public String getThumbnailUrlFavorites() {
        return thumbnailUrlFavorites;
    }

    public void setThumbnailUrlFavorites(String thumbnailUrlFavorites) {
        this.thumbnailUrlFavorites = thumbnailUrlFavorites;
    }

    public byte[] getThumbnailByteFavorites() {
        return thumbnailByteFavorites;
    }

    public void setThumbnailByteFavorites(byte[] thumbnailByteFavorites) {
        this.thumbnailByteFavorites = thumbnailByteFavorites;
    }
}

