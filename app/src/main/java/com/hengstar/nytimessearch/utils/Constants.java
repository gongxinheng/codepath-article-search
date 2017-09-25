package com.hengstar.nytimessearch.utils;

public class Constants {

    public static final int SEARCH_RESULT_COLUMN_NUM = 2;
    public static final String NYTIME_API_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
    public static final String NYTIME_URL_PREFIX = "http://www.nytimes.com/";
    public static final String NEWS_DESK_PREFIX = "news_desk:";

    public static class JsonKeys {
        public static final String URL = "web_url";
        public static final String HEADLINE = "headline";
        public static final String MAIN = "main";
        public static final String MULTIMEDIA = "multimedia";
    }

    public static class SearchParams {
        public static final String API_KEY = "api-key";
        public static final String PAGE_NUM = "page";
        public static final String QUERY = "q";
        public static final String BEGIN_DATE = "begin_date";
        public static final String SORT = "sort";
        public static final String News_Desk_Values = "fq";
    }

    public static class SortParams {
        public static final String NEWEST = "newest";
        public static final String OLDEST = "oldest";
    }

    public static class NewsDeskValuesParams {
        public static final String ARTS   = "Arts";
        public static final String FASHION_STYLE = "Fashion %26 Style";
        public static final String SPORTS = "Sports";
    }

    public static class IntentParams {
        public static final String ARTICLE = "article";
        public static final String QUERY = "query";
    }

    public static class Tags {
        public static final String FILTER_FRAGMENT = "filter_fragment";
        public static final String DATE_PICKER_FRAGMENT = "dete_picker_fragment";
    }
}
