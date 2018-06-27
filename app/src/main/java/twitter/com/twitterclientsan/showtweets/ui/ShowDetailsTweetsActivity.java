package twitter.com.twitterclientsan.showtweets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import twitter.com.twitterclientsan.R;
import twitter.com.twitterclientsan.account.ui.LoginActivity;
import twitter.com.twitterclientsan.common.ui.TwitterBaseActivity;

public class ShowDetailsTweetsActivity extends TwitterBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show_tweets_details);
        final FrameLayout myLayout
                = findViewById(R.id.bike_tweet);
        final long tweetId = getIntent().getLongExtra("tweetID", 0);

        //Load the tweet details and option to show the button(share, favorite)
        TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                final TweetView tweetView = new TweetView(ShowDetailsTweetsActivity.this, result.data,
                        R.style.tw__TweetDarkWithActionsStyle);
                tweetView.setOnActionCallback(actionCallback);
                myLayout.addView(tweetView);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(ShowDetailsTweetsActivity.this, "Please try again ", Toast.LENGTH_LONG).show();
            }
        });
    }

    //Callback for
    Callback<Tweet> actionCallback = new Callback<Tweet>() {
        @Override
        public void success(Result<Tweet> result) {
            //TODO add your work
        }

        @Override
        public void failure(TwitterException exception) {
            if (exception instanceof TwitterAuthException) {
                // launch custom login flow
                startActivity(new Intent(ShowDetailsTweetsActivity.this, LoginActivity.class));
            }
        }
    };
}
