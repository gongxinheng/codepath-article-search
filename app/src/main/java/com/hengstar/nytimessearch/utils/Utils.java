package com.hengstar.nytimessearch.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.hengstar.nytimessearch.models.FilterOptions;

import java.text.SimpleDateFormat;
import java.util.HashSet;

public class Utils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @NonNull
    public static SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    @NonNull
    public static String formatNewsDeskValues(@NonNull HashSet<FilterOptions.NewsDeskValue> newsDeskValues) {
        if (newsDeskValues.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder(Constants.NEWS_DESK_PREFIX);
        sb.append('(');
        boolean first = true;
        for (FilterOptions.NewsDeskValue newsDeskValue : newsDeskValues) {
            if (!first) sb.append(' ');
            first = false;
            sb.append('\"');
            sb.append(newsDeskValue.toString());
            sb.append('\"');
        }
        sb.append(')');

        return sb.toString();
    }

    public static Boolean isNetworkAvailable(@NonNull final Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
