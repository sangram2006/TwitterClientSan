package twitter.com.twitterclientsan.common.app;

import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import twitter.com.twitterclientsan.R;

/**
 * Created by Sangram Mohanty on 6/27/2018.
 * Application initialize the twitter instance
 */
public class TwitterClientApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Create Config file, enable debug and pass key for auth
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();

        // initialize twitter with created configs
        Twitter.initialize(config);
    }
}
