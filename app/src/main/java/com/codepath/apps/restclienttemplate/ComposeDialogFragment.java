package com.codepath.apps.restclienttemplate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeDialogFragment extends DialogFragment {
    public  static final String TAG="ComposeDialogFragment";
    public  static final  int MAX_TWEET_LENGTH=140;
    public static final String key ="keyboard";
    TwitterClient client;
    EditText etCompose;
    Button btnTweet;
    Context context;
    ImageButton image_button;
    ImageView profile;
    TextView nom;
    TextView username;




    public ComposeDialogFragment() {

    }

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
        return inflater.inflate(R.layout.fragement, container);
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
        client = TwitterApp.getRestClient(getContext());


        String title = getArguments().getString("title", "Enter your text");

        getDialog().setTitle(title);
        // Show soft keybord automatically and request focus to field
        etCompose.requestFocus();

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().getWindow().setLayout(800,900);




        Bundle bundle=getArguments();
        User user= Parcels.unwrap(bundle.getParcelable("userInfo"));


//        nom.setText(user.name);
//        username.setText("@" + user.screenName);




        Glide.with(this)
                .load(user.profileImageUrl)
                .transform(new CircleCrop())
                .into(profile);

        // draft
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String draft = preferences.getString(key,"");

        if(draft.isEmpty()){
            etCompose.setText(draft);
        }

        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open();
            }
        });

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent=etCompose.getText().toString();
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

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG,"onFailure to publish tweet",throwable);
                    }
                });
                dismiss();
            }
        });
    }

    public void open(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Save Draft");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Save();
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void Save(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username",etCompose.getText().toString());
        editor.commit();
        dismiss();
    }


}

