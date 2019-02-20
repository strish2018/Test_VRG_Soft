package com.strish.android.test_vrg_soft.pojo;

import com.strish.android.test_vrg_soft.Article;

import java.io.Serializable;
import java.util.List;

public class JSONResponse implements Serializable {
    private List<Article> results;

    public List<Article> getResults() {
        return results;
    }

    public void setResults(List<Article> results) {
        this.results = results;
    }
}
