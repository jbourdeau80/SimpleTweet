package com.codepath.apps.restclienttemplate.models;

import androidx.room.Embedded;

import java.util.ArrayList;
import java.util.List;

public class TweetWithUser {
    // @Embedded notation flattens the properties of the user object into the object, preserving encapsulation
    @Embedded
    User user;
    @Embedded(prefix = "tweet_")
    Tweet tweet;
    @Embedded
    Media media;

    public static List<Tweet> getTweetList(List<TweetWithUser> tweetWithUsers) {
        List<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < tweetWithUsers.size();i++){
//
            Tweet tweet = tweetWithUsers.get(i).tweet;
            tweet.user = tweetWithUsers.get(i).user;
            tweet.media = tweetWithUsers.get(i).media;
            tweets.add(tweet);
        }
        return tweets;
    }

}
