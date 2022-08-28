package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

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
    TextView reply;
    TextView share;
    EditText etCompose;
    Button btnTweet;
    TwitterClient client;
    Context context;

    public  static final String TAG ="DetailActivity";
    public static final int MAX_TWEET_LENGTH = 140;

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
        reply = findViewById(R.id.reply);
        share = findViewById(R.id.share);
        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);
        client = TwitterApp.getRestClient(this);

        txName.setText(tweet.getUser().getName());
        txBody.setText(tweet.body);
        Username.setText("@" + tweet.user.screenName);
        date1.setText(Tweet.getFormattedTime(tweet.createdAt));


        etCompose.setHint("Reply to " + tweet.user.getName());
        etCompose.setText("@"+ tweet.user.getScreenName());



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


        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(DetailActivity.this, "Sorry,your tweet cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tweetContent.length() > MAX_TWEET_LENGTH) {
                    Toast.makeText(DetailActivity.this, "Sorry,your tweet cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(DetailActivity.this, tweetContent, Toast.LENGTH_SHORT).show();
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG,"onSuccess to publish tweet");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG,"published tweet says:" + tweet.body);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));
                            // Set result code and bundle data for response
                            setResult(RESULT_OK,intent);
                            // C;ose the activity,pass data to parent
                            finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG,"onFailure to publish tweet",throwable);
                    }
                });
            }
        });
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


                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, tweet.getUrl());
                        startActivity(Intent.createChooser(shareIntent, "Share link using"));

                    }
                });


                reply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showEditDialog2();
                    }

                    public void showEditDialog2() {
                        FragmentManager fmt = getSupportFragmentManager();
                        ComposeDialogFragment composeDialogFragment = ComposeDialogFragment.newInstance("Some Title");
                        composeDialogFragment.show(fmt, "fragment");
                    }
                });

                reply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showEditDialog3(Parcels.wrap(tweet));
                    }

                    public void showEditDialog3(Parcelable tweet) {
                        FragmentManager fmt = getSupportFragmentManager();
                        ComposeDialogFragment composeDialogFragment = ComposeDialogFragment.newInstance("Some Title");
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("tweets", tweet);
                        // bundle.putParcelable("userInfo",Parcels.wrap(user));
                        bundle.putParcelable("userInfo", Parcels.wrap(TimelineActivity.user));
                        composeDialogFragment.setArguments(bundle);
                        composeDialogFragment.show(fmt, "fragment");
                    }
                });


            }

            @Override
            public boolean onOptionsItemSelected(MenuItem menuItem) {
                Intent i = new Intent(DetailActivity.this, TimelineActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(i, 0);
                return true;
            }

}



