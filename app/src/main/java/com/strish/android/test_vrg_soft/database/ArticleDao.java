package com.strish.android.test_vrg_soft.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.strish.android.test_vrg_soft.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    @Insert
    void insert(Article article);

    @Query("DELETE FROM article_table WHERE title = :title")
    void deleteByTitle(String title);

    @Query("SELECT * FROM article_table")
    LiveData<List<Article>> getAllArticles();

}
