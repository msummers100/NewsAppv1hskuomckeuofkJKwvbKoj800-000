package com.example.newsapp_v1;

import java.util.ArrayList;

/**
 * Created by Michael on 2/17/2017.
 */
public class Article extends ArrayList<Article> {
    private String newsTitle;
    private String newsSectionName;
    private String newsArticleLink;

    public Article(String title, String sectionName, String articleLink) {
        newsTitle = title;
        newsSectionName = sectionName;
        newsArticleLink = articleLink;
    }

    public String getTitle() {
        return newsTitle;
    }

    public String getSectionName() {
        return newsSectionName;
    }

    public String getArticleLink() {
        return newsArticleLink;
    }
}