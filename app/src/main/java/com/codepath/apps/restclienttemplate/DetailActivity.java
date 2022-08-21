package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity{
   private TextView txName;
   private  TextView txBody;
   private ImageView image;
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


        txName = findViewById(R.id.txName);
        txBody = findViewById(R.id.txBody);
        image = findViewById(R.id.image);
        date1 = findViewById(R.id.date1);
        ivImage = findViewById(R.id.ivImage1);
        favorite = findViewById(R.id.favorite);
        retweet = findViewById(R.id.retweet);
        heart = findViewById(R.id.ic_heart);
        heart1 = findViewById(R.id.ic_heart1);
        ic_repeat = findViewById(R.id.ic_repeat);
        ic_repeat2 =findViewById(R.id.ic_repeat2);



        txName.setText(tweet.getUser().getName());
        txBody.setText(tweet.body);
       date1.setText(Tweet.getFormattedTime(tweet.createdAt));

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left_thin);
        getSupportActionBar().setLogo(R.drawable.ic_twitter);
        getSupportActionBar().setTitle("  Tweet");






        Glide.with(this).load(tweet.user.profileImageUrl).transform(new RoundedCorners(100)).into(image);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent i = new Intent(DetailActivity.this,TimelineActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(i,0);
        return true;
    }
    }