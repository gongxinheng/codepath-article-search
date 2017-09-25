package com.hengstar.nytimessearch.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hengstar.nytimessearch.R;
import com.hengstar.nytimessearch.adapters.ArticleArrayAdapter;
import com.hengstar.nytimessearch.fragments.FilterFragment;
import com.hengstar.nytimessearch.listeners.EndlessRecyclerViewScrollListener;
import com.hengstar.nytimessearch.models.Article;
import com.hengstar.nytimessearch.models.FilterOptions;
import com.hengstar.nytimessearch.utils.Constants;
import com.hengstar.nytimessearch.utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements FilterFragment.FilterOptionsDialogListener {

    @BindView(R.id.etQuery) EditText etQuery;
    @BindView(R.id.btnSearch) Button btnSearch;
    @BindView(R.id.rvArticles) RecyclerView rvArticles;

    private ArrayList<Article> articles;
    private ArticleArrayAdapter adapter;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;
    // stored filter options
    private FilterOptions filterOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        // Setup layout manager for items with orientation
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                Constants.SEARCH_RESULT_COLUMN_NUM, StaggeredGridLayoutManager.VERTICAL);
        // Optionally customize the position you want to default scroll to
        layoutManager.scrollToPosition(0);
        // Attach layout manager to the RecyclerView
        rvArticles.setLayoutManager(layoutManager);
        rvArticles.setAdapter(adapter);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvArticles.addOnScrollListener(scrollListener);
    }

    public void onArticleSearch(View view) {
        reset();
        executeSearch(0, null);
    }

    public void onArticleFilter(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FilterFragment filterFragment = FilterFragment.newInstance();
        filterFragment.show(fm, Constants.Tags.FILTER_FRAGMENT);
    }

    @Override
    public void onFilter(FilterOptions filterOptions) {
        reset();
        executeSearch(0, filterOptions);
    }

    private void executeSearch(int page, @Nullable final FilterOptions filterOptions) {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etQuery.getWindowToken(), 0);
        etQuery.clearFocus();
        if (!Utils.isNetworkAvailable(this)) {
            Toast.makeText(this, getResources().getString(R.string.err_msg_internet_not_available), Toast.LENGTH_LONG).show();
            return;
        }
        // save filter options
        if (filterOptions != null) {
            this.filterOptions = filterOptions;
        }
        String query = etQuery.getText().toString();

        //Toast.makeText(this, "Searching for" + query, Toast.LENGTH_LONG).show();
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put(Constants.SearchParams.API_KEY, "f3e1af64791f412c89c24bc440759679");
        params.put(Constants.SearchParams.PAGE_NUM, page);
        params.put(Constants.SearchParams.QUERY, query);

        if (this.filterOptions != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date beginDate = this.filterOptions.getBeginDate();
            if (beginDate != null) {
                String beginDateString = simpleDateFormat.format(beginDate);
                params.put(Constants.SearchParams.BEGIN_DATE, beginDateString);
            }
            FilterOptions.SortOrder sortOrder = this.filterOptions.getSortOrder();
            if (sortOrder != null) {
                params.put(Constants.SearchParams.SORT, this.filterOptions.getSortOrder().toString());
            }
            HashSet<FilterOptions.NewsDeskValue> newsDeskValues = this.filterOptions.getNewsDeskValues();
            if (newsDeskValues != null && !newsDeskValues.isEmpty()) {
                params.put(Constants.SearchParams.News_Desk_Values,
                        Utils.formatNewsDeskValues(newsDeskValues));
            }
        }

        client.get(Constants.NYTIME_API_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResults;

                try{
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    articles.addAll(Article.fromJSONArray(articleJsonResults));
                    adapter.notifyItemRangeChanged(adapter.getItemCount(), articles.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Append the next page of data into the adapter
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        executeSearch(offset, null);
    }

    // Reset all views and clear items
    private void reset() {
        scrollListener.resetState();
        articles.clear();
        adapter.notifyDataSetChanged();
    }
}
