package com.example.newsapp_v1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    /**
     * Constant value for the article loader ID.
     */
    private static final int ARTICLE_LOADER_ID = 1;
    private static final String NEWS_REQUEST_URL = "http://content.guardianapis.com/search?q=technology&api-key=test";
    /**
     * TextView for when view is empty can display error message
     */
    private TextView newsEmptyStateTextView;
    private ProgressBar mProBar;
    /**
     * Adapter contains list of news articles
     */
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProBar = (ProgressBar) findViewById(R.id.progress);

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        newsEmptyStateTextView = (TextView) findViewById(R.id.news_empty_view);
        newsListView.setEmptyView(newsEmptyStateTextView);

        // Create a new adapter that takes an empty list of articles as input
        mAdapter = new NewsAdapter(this, new ArrayList<Article>());

        // Set the adapter
        // so the list can be generated
        newsListView.setAdapter(mAdapter);

        // Set on click listener to open browser intent
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Article article = mAdapter.getItem(position);
                String url = article.getArticleLink();

                Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(openBrowser);
            }
        });

        // Check network connection
        ConnectivityManager cMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Reference LoaderManager
            LoaderManager loaderManager = getLoaderManager();
            // Initialize loader or display error message R.string.no_internet
            loaderManager.initLoader(ARTICLE_LOADER_ID, null, this);
        } else {
            mProBar.setVisibility(View.GONE);
            newsEmptyStateTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {

        // Create a new loader utilizing final string variable
        return new NewsLoader(this, NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {

        mProBar.setVisibility(View.GONE);

        // Set empty state text to display R.string.no_articles
        newsEmptyStateTextView.setText(R.string.no_articles);

        // Clear the adapter of previous data
        mAdapter.clear();

        // If there is a valid list of {@link Article}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        // load valid list of articles if available, updates the ListView
        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

        // Remove existing data
        mAdapter.clear();
    }
}