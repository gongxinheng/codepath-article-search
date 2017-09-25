package com.hengstar.nytimessearch.models;

import com.hengstar.nytimessearch.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Article {

    public String webUrl;
    public String headline;
    public String thumbNail;

    public Article() {}

    public Article(JSONObject jsonObject) {
        try {
            this.webUrl = jsonObject.getString(Constants.JsonKeys.URL);
            this.headline = jsonObject.getJSONObject(Constants.JsonKeys.HEADLINE)
                    .getString(Constants.JsonKeys.MAIN);

            JSONArray multimedie = jsonObject.getJSONArray(Constants.JsonKeys.MULTIMEDIA);

            if (multimedie.length() > 0) {
                JSONObject multimediaJson = multimedie.getJSONObject(0);
                this.thumbNail = Constants.NYTIME_URL_PREFIX + multimediaJson.getString("url");
            } else {
                this.thumbNail = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Article> fromJSONArray(JSONArray array) {
        ArrayList<Article> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Article(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
