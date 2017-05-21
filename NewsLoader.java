package com.example.newsapp_v1;

import android.content.Context;
import android.content.AsyncTaskLoader;

import com.example.newsapp_v1.Article;
import com.example.newsapp_v1.QueryUtils;

import java.util.List;
/**
 * Created by Michael on 2/17/2017.
 */
public class NewsLoader extends AsyncTaskLoader<List<Article>> {

   // Query Url
    private String mUrl;


    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {

        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of articles.
        List<Article> articles = QueryUtils.fetchArticleData(mUrl);
        return articles;
    }
}