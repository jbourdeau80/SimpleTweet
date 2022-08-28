package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TweetDao {

     @Query("SELECT Tweet.body AS tweet_body,Tweet.createdAt AS tweet_createdAt,Tweet.id AS tweet_id,Tweet.count_favorite As tweet_count_favorite" +
             ",Tweet.count_retweet As tweet_count_retweet,Tweet.favorite As tweet_favorite,Tweet.retweet As tweet_retweet,Media.*,User.*" +
             " FROM Media,Tweet INNER JOIN User ON Tweet.userId = User.id ORDER BY Tweet.createdAt DESC LIMIT 5")
     List<TweetWithUser> recentItems();

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insertModel(Tweet... tweets);

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insertModel(User... Users);

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insertModel(Media... medias);
}
