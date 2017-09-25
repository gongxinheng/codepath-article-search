package com.hengstar.nytimessearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengstar.nytimessearch.R;
import com.hengstar.nytimessearch.activities.ArticleActivity;
import com.hengstar.nytimessearch.models.Article;
import com.hengstar.nytimessearch.utils.Constants;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleArrayAdapter extends RecyclerView.Adapter<ArticleArrayAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ivImage) ImageView ivImage;
        @BindView(R.id.tvTitle) TextView tvTitle;

        GridItemClickListener itemClickListener;

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getLayoutPosition());
        }

        interface GridItemClickListener {
            void onItemClick(View v, int position);
        }

        ViewHolder(View view, GridItemClickListener itemClickListener) {
            super(view);
            ButterKnife.bind(this, view);
            this.itemClickListener = itemClickListener;
            view.setOnClickListener(this);
        }
    }

    private ArrayList<Article> articles;
    private Context context;

    public ArticleArrayAdapter(@NonNull Context context, @NonNull ArrayList<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.item_article_result, parent, false);

        // Return a new holder instance
        return new ViewHolder(articleView, new ViewHolder.GridItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // create an intent to display the article
                Intent i = new Intent(v.getContext(), ArticleActivity.class);
                // get the article to display
                Article article = articles.get(position);
                // pass in that article into intent
                i.putExtra(Constants.IntentParams.ARTICLE, Parcels.wrap(article));
                // launch the activity
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the data item for position
        Article article = articles.get(position);


        // clear out recycled image from convertView from last time
        holder.ivImage.setImageResource(0);
        holder.tvTitle.setText(article.headline);

        // populate the thumnail image
        // remove download the image in the background

        String thumnail = article.thumbNail;

        if (!TextUtils.isEmpty(thumnail)) {
            Picasso.with(context).load(thumnail).into(holder.ivImage);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
