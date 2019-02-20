package com.strish.android.test_vrg_soft.fragments;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strish.android.test_vrg_soft.Article;
import com.strish.android.test_vrg_soft.ArticleViewModel;
import com.strish.android.test_vrg_soft.R;
import com.strish.android.test_vrg_soft.activities.MainActivity;
import com.strish.android.test_vrg_soft.adapters.FavoritesAdapter;

import java.util.List;

public class FavoritesFragment extends Fragment implements FavoritesAdapter.OnItemClickedListener {

    private RecyclerView mRecyclerView;
    private FavoritesAdapter mAdapter;
    private ArticleViewModel mArticleViewModel;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_layout, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FavoritesAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickedListener(this);

        mArticleViewModel = ((MainActivity) getActivity()).getArticleViewModel();
        mArticleViewModel.getAllArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                mAdapter.submitList(articles);
            }
        });

        return view;
    }

    @Override
    public void onItemClicked(Article article) {
        mArticleViewModel.articleClicked(article);
    }

    @Override
    public void onRemoveButtonClicked(Article article) {
        mArticleViewModel.removeButtonClicked(article);
    }
}
