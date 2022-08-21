package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    Context context;
    List<Tweet> tweets;
    // Pass in the context and list of tweets


    public TweetsAdapter(Context context,List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){this.listener=listener;}


    // For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        TweetsAdapter.ViewHolder viewHolder = new TweetsAdapter.ViewHolder(view,listener);
        //return new ViewHolder(view);
        return viewHolder;
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    // Get the data at position
        Tweet tweet = tweets.get(position);
        // Bind the tweet with the view holder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clear all elements of the recycler
    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Tweet>tweetList){
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }


   public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout container;
       ImageView ivProfileImage;
       TextView tvBody;
       TextView tvScreenName;
       TextView date;
       TextView ivimage;
       TextView ic_heart;
       TextView ic_heart1;
       TextView ic_repeat;
       TextView ic_repeat2;


       public ViewHolder(@NonNull View itemView ,final OnItemClickListener clickListener) {
           super(itemView);
           ivProfileImage = itemView.findViewById(R.id.ivprofileImage);
           tvBody = itemView.findViewById(R.id.tvBody);
           tvScreenName = itemView.findViewById(R.id.tvScreenName);
           container = itemView.findViewById(R.id.container);
            date = itemView.findViewById(R.id.date);
            ic_heart = itemView.findViewById(R.id.ic_heart);
            ic_heart1 = itemView.findViewById(R.id.ic_heart1);
            ic_repeat = itemView.findViewById(R.id.ic_repeat);
            ic_repeat2 = itemView.findViewById(R.id.ic_repeat2);





       }


       public void bind(Tweet tweet) {
           tvBody.setText(tweet.body);
           tvScreenName.setText(tweet.user.screenName);
           date.setText(Tweet.getFormattedTime1(tweet.createdAt));


           if(tweet.favorite){
               ic_heart.setVisibility(View.INVISIBLE);
               ic_heart1.setVisibility(View.VISIBLE);
           }
           else{
               ic_heart.setVisibility(View.VISIBLE);
               ic_heart1.setVisibility(View.INVISIBLE);
           }
           if(tweet.favorite){
               ic_repeat.setVisibility(View.INVISIBLE);
               ic_repeat2.setVisibility(View.VISIBLE);
           }
           else {
               ic_repeat.setVisibility(View.VISIBLE);
               ic_repeat2.setVisibility(View.INVISIBLE);
           }




           Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);

            ic_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tweet.count_favorite++;
                    ic_heart.setVisibility(View.INVISIBLE);
                    ic_heart1.setVisibility(View.VISIBLE);
                    ic_heart1.setText(tweet.getCount_favorite());
                    tweet.favorite = true;
                }
            });

           ic_heart1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   tweet.count_favorite--;
                   ic_heart.setVisibility(View.VISIBLE);
                   ic_heart1.setVisibility(View.INVISIBLE);
                   ic_heart.setText(tweet.getCount_favorite());
                   tweet.favorite = false;
               }
           });

           ic_repeat2.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                 tweet.count_retweet--;
                 ic_repeat.setVisibility(View.INVISIBLE);
                 ic_repeat2.setVisibility(View.INVISIBLE);

                 ic_repeat.setText(tweet.getCount_retweet());
                tweet.favorite = false;
               }
           });





           container.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {

                   Intent i = new Intent(context,DetailActivity.class);
                   i.putExtra("tweets", Parcels.wrap(tweet));
                   context.startActivity(i);
               }
           });
       }
   }
}
