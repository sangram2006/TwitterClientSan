package twitter.com.twitterclientsan.common.app;

import android.app.Application;

import com.twitter.sdk.android.core.Twitter;

/**
 * Created by Sangram Mohanty on 6/26/2018.
 */
public class TwitterClientApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Twitter.initialize(this);
    }
}
