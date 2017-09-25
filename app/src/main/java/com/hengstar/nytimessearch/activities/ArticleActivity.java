package com.hengstar.nytimessearch.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hengstar.nytimessearch.R;
import com.hengstar.nytimessearch.databinding.ActivityArticleBinding;
import com.hengstar.nytimessearch.models.Article;
import com.hengstar.nytimessearch.utils.Constants;

import org.parceler.Parcels;

public class ArticleActivity extends AppCompatActivity {

    private ActivityArticleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toolbar toolbar = findViewById(R.id.too)
        //setSupportActionBar(toolbar);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article);

        Article article = Parcels.unwrap(getIntent().getParcelableExtra(Constants.IntentParams.ARTICLE));

        WebView webView = (WebView) findViewById(R.id.wvArticle);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(article.webUrl);
    }
}
