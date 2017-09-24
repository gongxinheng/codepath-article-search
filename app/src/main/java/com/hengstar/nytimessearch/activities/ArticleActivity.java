package com.hengstar.nytimessearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hengstar.nytimessearch.R;
import com.hengstar.nytimessearch.models.Article;
import com.hengstar.nytimessearch.utils.Constants;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        //Toolbar toolbar = findViewById(R.id.too)
        //setSupportActionBar(toolbar);

        Article article = (Article) getIntent().getSerializableExtra(Constants.IntentParams.ARTICLE);

        WebView webView = (WebView) findViewById(R.id.wvArticle);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(article.getWebUrl());
    }
}