package com.strish.android.test_vrg_soft.activities;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.strish.android.test_vrg_soft.Article;
import com.strish.android.test_vrg_soft.ArticleViewModel;
import com.strish.android.test_vrg_soft.fragments.FavoritesFragment;
import com.strish.android.test_vrg_soft.R;
import com.strish.android.test_vrg_soft.fragments.TabFragment;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {

    private ArticleViewModel mArticleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount() - 1);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mArticleViewModel.setOpenArticleActivity(new MutableLiveData<Article>());
        mArticleViewModel.getOpenArticleActivity().observe(this, new Observer<Article>() {
            @Override
            public void onChanged(@Nullable Article article) {
                openArticleActivity(article);
            }
        });

    }

    public ArticleViewModel getArticleViewModel() {
        return mArticleViewModel;
    }

    private void openArticleActivity(Article article) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(ArticleActivity.ARTICLE_EXTRA, article);
        startActivity(intent);
        CustomIntent.customType(this, "fadein-to-fadeout");
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = TabFragment.newInstance(0);
                    break;
                case 1:
                    fragment = TabFragment.newInstance(1);
                    break;
                case 2:
                    fragment = TabFragment.newInstance(2);
                    break;
                case 3:
                    fragment = FavoritesFragment.newInstance();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
