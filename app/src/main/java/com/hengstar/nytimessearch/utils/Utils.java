package com.hengstar.nytimessearch.utils;

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
}
