package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {
    TextView txName;
    TextView txBody;
    TextView Username;
    ImageView image;
    TextView date1;
    ImageView ivImage;
    TextView favorite;
    TextView retweet;
    TextView heart;
    TextView heart1;
    TextView ic_repeat;
    TextView ic_repeat2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweets"));


        txName = findViewById(R.id.dtvName);
        txBody = findViewById(R.id.txBody);
        Username = findViewById(R.id.userName);
        image = findViewById(R.id.image);
        date1 = findViewById(R.id.date1);
        ivImage = findViewById(R.id.ivImage1);
        favorite = findViewById(R.id.favorite);
        retweet = findViewById(R.id.retweet);
        heart = findViewById(R.id.ic_heart2);
        heart1 = findViewById(R.id.ic_heart3);
        ic_repeat = findViewById(R.id.ic_repeat);
        ic_repeat2 = findViewById(R.id.ic_repeat2);


        txName.setText(tweet.getUser().getName());
        txBody.setText(tweet.body);
        Username.setText("@"+tweet.user.screenName);
        date1.setText(Tweet.getFormattedTime(tweet.createdAt));

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left_thin);
        getSupportActionBar().setLogo(R.drawable.ic_twitter);
        getSupportActionBar().setTitle("  Tweet");


        if (tweet.favorite) {
            heart.setVisibility(View.INVISIBLE);
            heart1.setVisibility(View.VISIBLE);
        } else {
            heart.setVisibility(View.VISIBLE);
            heart1.setVisibility(View.INVISIBLE);
        }
        if (tweet.favorite) {
            ic_repeat.setVisibility(View.INVISIBLE);
            ic_repeat2.setVisibility(View.VISIBLE);
        } else {
            ic_repeat.setVisibility(View.VISIBLE);
            ic_repeat2.setVisibility(View.INVISIBLE);
        }


        Glide.with(this).load(tweet.user.profileImageUrl).transform(new RoundedCorners(50)).into(image);


        if(!tweet.media.getMediaUrl().isEmpty()) {
            ivImage.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(tweet.media.getMediaUrl())
                    .transform(new RoundedCorners(50))
                    .into(ivImage);
        }


        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tweet.count_favorite++;
                heart.setVisibility(View.INVISIBLE);
                heart1.setVisibility(View.VISIBLE);
                heart1.setText(tweet.getCount_favorite());
                tweet.favorite = true;
            }
        });

        heart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tweet.count_favorite--;
                heart.setVisibility(View.VISIBLE);
                heart1.setVisibility(View.INVISIBLE);
                heart.setText(tweet.getCount_favorite());
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
        retweet.setText(tweet.count_retweet + " Retweets");
        favorite.setText(tweet.count_favorite + " Favorites");

    }
        @Override
        public boolean onOptionsItemSelected (MenuItem menuItem){
            Intent i = new Intent(DetailActivity.this, TimelineActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(i, 0);
            return true;
        }
    }
