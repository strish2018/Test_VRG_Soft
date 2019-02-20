package com.strish.android.test_vrg_soft.adapters;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.strish.android.test_vrg_soft.Article;
import com.strish.android.test_vrg_soft.R;

import java.util.List;

public class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.TabsViewHolder> {
    private List<Article> mArticles;
    private OnItemClickedListener mListener;

    public interface OnItemClickedListener {
        void onItemClicked(Article article);

        void onFavoriteButtonClicked(Article article, Drawable drawable);
    }

    public void setOnItemClickedListener(OnItemClickedListener listener) {
        mListener = listener;
    }

    static class TabsViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTitleTextView;
        TextView mBylineTextView;
        ImageButton mImageButton;
        private Article mArticle;

        TabsViewHolder(View itemView, final OnItemClickedListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view_list);
            mTitleTextView = itemView.findViewById(R.id.list_item_title_text_view);
            mBylineTextView = itemView.findViewById(R.id.list_item_byline_text_view);
            mImageButton = itemView.findViewById(R.id.list_item_favorites_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClicked(mArticle);
                    }
                }
            });

            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onFavoriteButtonClicked(mArticle, mImageView.getDrawable());
                    }
                    if (mArticle.isFavorite()) {
                        mImageButton.setImageResource(R.drawable.ic_favorite_on);
                    } else {
                        mImageButton.setImageResource(R.drawable.ic_favorite_off);
                    }

                }
            });
        }

        void bindArticle(Article article) {
            mArticle = article;
            Picasso.get().load(mArticle.getMedia().get(0).getLargestImageURL()).fit().centerCrop().into(mImageView);
            mTitleTextView.setText(mArticle.getTitle());
            mBylineTextView.setText(mArticle.getByline());
            if (mArticle.isFavorite()) {
                mImageButton.setImageResource(R.drawable.ic_favorite_on);
            } else {
                mImageButton.setImageResource(R.drawable.ic_favorite_off);
            }
        }

    }

    public TabsAdapter(List<Article> articles) {
        mArticles = articles;
    }

    @NonNull
    @Override
    public TabsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TabsViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(TabsViewHolder holder, int position) {
        final Article mArticle = mArticles.get(position);
        holder.bindArticle(mArticle);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void setArticles(List<Article> articles) {
        mArticles = articles;
        notifyDataSetChanged();
    }
}
