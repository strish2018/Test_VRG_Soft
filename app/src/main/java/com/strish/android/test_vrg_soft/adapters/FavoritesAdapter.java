package com.strish.android.test_vrg_soft.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
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

public class FavoritesAdapter extends ListAdapter<Article, FavoritesAdapter.TabsViewHolder> {
    private OnItemClickedListener mListener;

    public interface OnItemClickedListener {
        void onItemClicked(Article article);

        void onRemoveButtonClicked(Article article);
    }

    public void setOnItemClickedListener(OnItemClickedListener listener) {
        mListener = listener;
    }

    public FavoritesAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Article> DIFF_CALLBACK = new DiffUtil.ItemCallback<Article>() {
        @Override
        public boolean areItemsTheSame(Article oldItem, Article newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

        @Override
        public boolean areContentsTheSame(Article oldItem, Article newItem) {
            return true;
        }
    };

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
                        listener.onRemoveButtonClicked(mArticle);
                    }
                }
            });
        }

        void bindArticle(Article article) {
            mArticle = article;
            Picasso.get().load(mArticle.getThumbnailUrlFavorites()).fit().centerCrop().into(mImageView);
            mTitleTextView.setText(mArticle.getTitle());
            mImageButton.setImageResource(R.drawable.ic_favorite_on);
            mBylineTextView.setText(mArticle.getByline());
        }

    }

    @Override
    @NonNull
    public TabsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TabsViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(TabsViewHolder holder, int position) {
        final Article mArticle = getItem(position);
        holder.bindArticle(mArticle);
    }
}
