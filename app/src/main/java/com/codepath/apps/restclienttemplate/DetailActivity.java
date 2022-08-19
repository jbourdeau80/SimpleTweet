package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweets"));


        txName = findViewById(R.id.txName);
        txBody = findViewById(R.id.txBody);
        image = findViewById(R.id.image);

        txName.setText(tweet.getUser().getName());
        txBody.setText(tweet.body);
//        date1.setText(Tweet.getFormattedTime(tweet.createdAt));


        Glide.with(this).load(tweet.user.profileImageUrl).transform(new RoundedCorners(50)).into(image);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }
    }