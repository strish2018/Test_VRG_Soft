package com.strish.android.test_vrg_soft.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.strish.android.test_vrg_soft.Article;
import com.strish.android.test_vrg_soft.R;

import maes.tech.intentanim.CustomIntent;

public class ArticleActivity extends AppCompatActivity {

    public static final String ARTICLE_EXTRA = "article_extra";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_layout);

        Article mArticle = (Article) getIntent().getSerializableExtra(ARTICLE_EXTRA);

        ImageView mImageView = findViewById(R.id.image_view_item);
        TextView mTitleTextView = findViewById(R.id.item_title_text_view);
        TextView mBylineTextView = findViewById(R.id.item_byline_text_view);
        TextView mCaptionTextView = findViewById(R.id.item_caption_text_view);
        TextView mAbstractTextView = findViewById(R.id.item_abstract_text_view);
        TextView mDateTextView = findViewById(R.id.item_date_text_view);

        mTitleTextView.setText(mArticle.getTitle());
        mBylineTextView.setText(mArticle.getByline());
        mAbstractTextView.setText(mArticle.getAbstractText());
        mDateTextView.setText(mArticle.getPublishedDate());

        if (isNetworkAvailableAndConnected()) {
            if (mArticle.getThumbnailUrlFavorites() != null) {
                Picasso.get().load(mArticle.getThumbnailUrlFavorites()).fit().centerCrop().into(mImageView);
                mCaptionTextView.setText(mArticle.getCaptionFavorites());
            } else {
                Picasso.get().load(mArticle.getMedia().get(0).getLargestImageURL()).fit().centerCrop().into(mImageView);
                mCaptionTextView.setText(mArticle.getMedia().get(0).getCaption());
            }
        } else {
            if (mArticle.getThumbnailByteFavorites() != null) {
                byte[] data = mArticle.getThumbnailByteFavorites();
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                mImageView.setImageBitmap(bmp);
                mCaptionTextView.setText(mArticle.getCaptionFavorites());
            }
        }
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }

}
