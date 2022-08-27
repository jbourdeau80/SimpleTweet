package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))
public class Tweet {

    @PrimaryKey
    @ColumnInfo
    public long id;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public  String createdAt;

    @ColumnInfo
    public Long userId;

    @Ignore
    public User user;
    @ColumnInfo
    public boolean favorite;
    @ColumnInfo
    public boolean retweet;
    @ColumnInfo
    public int count_favorite;
    @ColumnInfo
    public int count_retweet;
    @Ignore
    public Media media;

    // empty constructor needed by parceler library
    public Tweet(){}



    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;
        tweet.userId = user.id;
        tweet.favorite = jsonObject.getBoolean("retweeted");
        tweet.retweet = jsonObject.getBoolean("favorited");
        tweet.count_favorite = jsonObject.getInt("favorite_count");
        tweet.count_retweet = jsonObject.getInt("retweet_count");
        tweet.media = Media.fromJson(jsonObject.getJSONObject("entities"));
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

    public String getUrl() {
        return  "https://twitter.com" +user.screenName+ "/status/"+id;
    }
}
