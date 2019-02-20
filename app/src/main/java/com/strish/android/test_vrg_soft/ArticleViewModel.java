package com.strish.android.test_vrg_soft;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.strish.android.test_vrg_soft.api.MostPopularApi;
import com.strish.android.test_vrg_soft.api.RetrofitClient;
import com.strish.android.test_vrg_soft.pojo.JSONResponse;

import java.io.ByteArrayOutputStream;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ArticleViewModel extends AndroidViewModel {

    private static final int EMAILED_TAB_NUM = 0;
    private static final int SHARED_TAB_NUM = 1;
    private static final int VIEWED_TAB_NUM = 2;

    private ArticleRepository repository;
    private LiveData<List<Article>> allArticles;
    private MutableLiveData<Article> openArticleActivity = new MutableLiveData<>();

    private MutableLiveData<List<Article>> mostEmailedArticles = new MutableLiveData<>();
    private MutableLiveData<List<Article>> mostSharedArticles = new MutableLiveData<>();
    private MutableLiveData<List<Article>> mostViewedArticles = new MutableLiveData<>();

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        fetchAllArticles();
        repository = new ArticleRepository(application);
        allArticles = repository.getAllArticles();
    }

    private void fetchAllArticles() {
        fetchMostEmailedArticles();
        fetchMostSharedArticles();
        fetchMostViewedArticles();
    }

    public MutableLiveData<List<Article>> getArticlesById(int id) {
        MutableLiveData<List<Article>> arts = new MutableLiveData<>();
        switch (id) {
            case 0:
                arts = mostEmailedArticles;
                break;
            case 1:
                arts = mostSharedArticles;
                break;
            case 2:
                arts = mostViewedArticles;
                break;
        }
        return arts;
    }

    private void updateLists(int except_tab_num) {
        if (except_tab_num != EMAILED_TAB_NUM) {
            List<Article> articles = mostEmailedArticles.getValue();
            checkArticles(articles);
            mostEmailedArticles.postValue(articles);
        }
        if (except_tab_num != SHARED_TAB_NUM) {
            List<Article> articles = mostSharedArticles.getValue();
            checkArticles(articles);
            mostSharedArticles.postValue(articles);
        }
        if (except_tab_num != VIEWED_TAB_NUM) {
            List<Article> articles = mostViewedArticles.getValue();
            checkArticles(articles);
            mostViewedArticles.postValue(articles);
        }
    }

    public void favoriteButtonClicked(Article article, final int except_tab_num, Drawable articleImage) {
        article.setFavorite(!article.isFavorite());
        if (article.isFavorite()) {
            setValues(article, articleImage);
            repository.insert(article);
        } else {
            repository.deleteByTitle(article.getTitle());
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                updateLists(except_tab_num);
            }
        }, 100);
    }

    public void removeButtonClicked(Article article) {
        repository.deleteByTitle(article.getTitle());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                updateLists(-1);
            }
        }, 100);
    }

    public void articleClicked(Article article) {
        openArticleActivity.setValue(article);
    }

    private void setValues(Article article, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        article.setThumbnailByteFavorites(imageInByte);
        article.setCaptionFavorites(article.getMedia().get(0).getCaption());
        article.setThumbnailUrlFavorites(article.getMedia().get(0).getLargestImageURL());
    }

    public LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }

    public MutableLiveData<Article> getOpenArticleActivity() {
        return openArticleActivity;
    }

    public void setOpenArticleActivity(MutableLiveData<Article> openArticleActivity) {
        this.openArticleActivity = openArticleActivity;
    }

    private void checkArticles(List<Article> articles) {
        List<Article> favorites = repository.getAllArticles().getValue();
        for (Article art : articles) {
            art.setFavorite(false);
            for (Article fav : favorites) {
                if (art.getTitle().equals(fav.getTitle())) {
                    art.setFavorite(true);
                }
            }
        }
    }

    private void fetchMostEmailedArticles() {
        Retrofit retrofit = RetrofitClient.getClient();
        MostPopularApi mostPopularApi = retrofit.create(MostPopularApi.class);
        mostPopularApi.getMostEmailedArticles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<JSONResponse>() {
                    @Override
                    public void onNext(JSONResponse jsonResponse) {
                        checkArticles(jsonResponse.getResults());
                        mostEmailedArticles.postValue(jsonResponse.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void fetchMostSharedArticles() {
        Retrofit retrofit = RetrofitClient.getClient();
        MostPopularApi mostPopularApi = retrofit.create(MostPopularApi.class);
        mostPopularApi.getMostSharedArticles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<JSONResponse>() {
                    @Override
                    public void onNext(JSONResponse jsonResponse) {
                        checkArticles(jsonResponse.getResults());
                        mostSharedArticles.postValue(jsonResponse.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void fetchMostViewedArticles() {
        Retrofit retrofit = RetrofitClient.getClient();
        MostPopularApi mostPopularApi = retrofit.create(MostPopularApi.class);
        mostPopularApi.getMostViewedArticles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<JSONResponse>() {
                    @Override
                    public void onNext(JSONResponse jsonResponse) {
                        checkArticles(jsonResponse.getResults());
                        mostViewedArticles.postValue(jsonResponse.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
