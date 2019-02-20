package com.strish.android.test_vrg_soft.fragments;

import android.arch.lifecycle.Observer;
import android.graphics.drawable.Drawable;
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
import com.strish.android.test_vrg_soft.adapters.TabsAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment implements TabsAdapter.OnItemClickedListener {

    public static final String ARGS_TAB_NUM = "args_tab_num";

    private RecyclerView mRecyclerView;
    private TabsAdapter mAdapter;
    private ArticleViewModel mArticleViewModel;
    private int tabNum;

    public static TabFragment newInstance(int i) {
        Bundle args = new Bundle();
        TabFragment fragment = new TabFragment();
        args.putInt(ARGS_TAB_NUM, i);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_layout, container, false);

        tabNum = getArguments().getInt(ARGS_TAB_NUM, 0);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TabsAdapter(new ArrayList<Article>());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickedListener(this);

        mArticleViewModel = ((MainActivity) getActivity()).getArticleViewModel();
        mArticleViewModel.getArticlesById(tabNum).observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                mAdapter.setArticles(articles);
            }
        });

        return view;
    }

    @Override
    public void onItemClicked(Article article) {
        mArticleViewModel.articleClicked(article);
    }

    @Override
    public void onFavoriteButtonClicked(Article article, Drawable drawable) {
        mArticleViewModel.favoriteButtonClicked(article, tabNum, drawable);
    }
}
