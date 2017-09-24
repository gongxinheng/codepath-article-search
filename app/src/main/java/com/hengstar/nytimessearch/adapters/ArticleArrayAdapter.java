package com.hengstar.nytimessearch.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengstar.nytimessearch.R;
import com.hengstar.nytimessearch.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    static class ViewHolder {
        @BindView(R.id.ivImage) ImageView ivImage;
        @BindView(R.id.tvTitle) TextView tvTitle;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public ArticleArrayAdapter(@NonNull Context context, @NonNull List<Article> objects) {
        super(context, R.layout.item_article_result, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the data item for position
        Article article = this.getItem(position);

        // view lookup cache stored in tag
        ViewHolder viewHolder;

        // check to see if existing view being reused/recycled
        // not using a recycled view -> inflate the layout
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
            viewHolder = new ViewHolder(convertView);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Context context = convertView.getContext();

        // clear out recycled image from convertView from last time
        viewHolder.ivImage.setImageResource(0);
        viewHolder.tvTitle.setText(article.getHeadline());

        // populate the thumnail image
        // remove download the image in the background

        String thumnail = article.getThumbNail();

        if (!TextUtils.isEmpty(thumnail)) {
            Picasso.with(getContext()).load(thumnail).into(viewHolder.ivImage);
        }

        return convertView;
    }
}
