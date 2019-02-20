package com.strish.android.test_vrg_soft;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.strish.android.test_vrg_soft.database.ArticleDao;
import com.strish.android.test_vrg_soft.database.ArticleDatabase;

import java.util.List;

public class ArticleRepository {

    private ArticleDao mArticleDao;
    private LiveData<List<Article>> allArticles;

    public ArticleRepository(Application application) {
        ArticleDatabase database = ArticleDatabase.getInstance(application);
        mArticleDao = database.articleDao();
        allArticles = mArticleDao.getAllArticles();
    }

    public void insert(Article article) {
        new InsertNoteAsyncTask(mArticleDao).execute(article);
    }

    public void deleteByTitle(String title) {
        new DeleteNoteAsyncTask(mArticleDao).execute(title);
    }

    public LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Article, Void, Void> {
        private ArticleDao mArticleDao;

        private InsertNoteAsyncTask(ArticleDao articleDao) {
            this.mArticleDao = articleDao;
        }

        @Override
        protected Void doInBackground(Article... notes) {
            mArticleDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<String, Void, Void> {
        private ArticleDao mArticleDao;

        private DeleteNoteAsyncTask(ArticleDao articleDao) {
            this.mArticleDao = articleDao;
        }

        @Override
        protected Void doInBackground(String... title) {
            mArticleDao.deleteByTitle(title[0]);
            return null;
        }
    }

}
