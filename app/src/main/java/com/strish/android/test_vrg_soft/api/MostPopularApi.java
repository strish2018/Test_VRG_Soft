package com.strish.android.test_vrg_soft.api;

import com.strish.android.test_vrg_soft.pojo.JSONResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MostPopularApi {

    String API_KEY = "5224f85fb17142b083c39ee037f881d8";

    @GET("svc/mostpopular/v2/mostemailed/all-sections/30.json?api-key=" + API_KEY)
    Observable<JSONResponse> getMostEmailedArticles();

    @GET("svc/mostpopular/v2/mostshared/all-sections/30.json?api-key=" + API_KEY)
    Observable<JSONResponse> getMostSharedArticles();

    @GET("svc/mostpopular/v2/mostviewed/all-sections/30.json?api-key=" + API_KEY)
    Observable<JSONResponse> getMostViewedArticles();

}
