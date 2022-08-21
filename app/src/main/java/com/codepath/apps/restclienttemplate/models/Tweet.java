package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel

public class Tweet {
    public String body;
    public  String createdAt;
    public long id;
    public User user;
    public boolean favorite;
    public boolean retweet;
    public int count_favorite;
    public int count_retweet;


    public Tweet(){}



    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.favorite = jsonObject.getBoolean("retweeted");
        tweet.retweet = jsonObject.getBoolean("favorited");
        tweet.count_favorite = jsonObject.getInt("favorite_count");
        tweet.count_retweet = jsonObject.getInt("retweet_count");
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length();i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }


    public String getCount_favorite() {
        return String.valueOf( count_favorite);
    }

    public boolean isFavorite() {
        return favorite;
    }

    public boolean isRetweet() {
        return retweet;
    }

    public String getCount_retweet() {
        return String .valueOf(count_retweet);
    }

    public static String getFormattedTime1(String createdAt) {
        return TimeFormatter.getTimeDifference(createdAt);
    }

    public static String getFormattedTime(String createdAt) {
        return TimeFormatter.getTimeStamp(createdAt);
    }
}
