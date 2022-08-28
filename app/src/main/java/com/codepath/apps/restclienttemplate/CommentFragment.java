package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class CommentFragment extends DialogFragment {
    public  static final String TAG="ComposeFragment";
    public  static final  int MAX_TWEET_LENGTH=140;

    TwitterClient client;
    EditText etCompose;
    Button btnTweet;
    Context context;
    ImageButton image_button;
    ImageView profile;
    TextView nom;
    TextView username;
    TextView ic_reply;

    public CommentFragment (){}


    public static ComposeDialogFragment newInstance(String title) {

        ComposeDialogFragment frag = new ComposeDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.comment_fragment, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etCompose  = (EditText) view.findViewById(R.id.etCompose);
        btnTweet = (Button) view.findViewById(R.id.btnTweet);
        image_button = (ImageButton) view.findViewById(R.id.close);
        profile = (ImageView) view.findViewById(R.id.profile);
        nom =(TextView)view.findViewById(R.id.nom) ;
        username =(TextView)view.findViewById(R.id.username) ;
        ic_reply =(TextView)view.findViewById(R.id.ic_reply);


        Bundle bundle=getArguments();
        Tweet tweet = Parcels.unwrap(bundle.getParcelable("tweets"));
        User user= Parcels.unwrap(bundle.getParcelable("userInfo"));



        // a retire eremetel nan compose dialogue la sil pa mache
        nom.setText(user.name);
        username.setText("@" + user.screenName);


        String title = getArguments().getString("title", "Enter your text");

        getDialog().setTitle(title);
        // Show soft keybord automatically and request focus to field
        etCompose.requestFocus();




        etCompose.setHint("Reply to " + tweet.user.getName());

        etCompose.setText("@"+ tweet.user.getScreenName());


        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().getWindow().setLayout(800,1500);



        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ic_reply.setText("reply to " + tweet.user.getScreenName() );
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent= etCompose.getText().toString();
                if(tweetContent.isEmpty()){

                    Toast.makeText(getContext(),"Sorry your tweet cannot be empty",Toast.LENGTH_LONG).show();
                    return;
                }
                if(tweetContent.length() >MAX_TWEET_LENGTH){
                    Toast.makeText(getContext(),"Sorry your tweet is too long",Toast.LENGTH_LONG).show();
                    return;

                }

                Toast.makeText(getContext(),tweetContent,Toast.LENGTH_LONG).show();
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG,"onSuccess to publish tweet");
                        try {
                            Tweet tweet=Tweet.fromJson(json.jsonObject);
                            Log.i(TAG," published tweet says : " + tweet);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dismiss();

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG,"onFailure to publish tweet",throwable);
                    }
                });
            }
        });
    }



    }
