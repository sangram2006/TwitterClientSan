package twitter.com.twitterclientsan.splash.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import twitter.com.twitterclientsan.R;
import twitter.com.twitterclientsan.account.ui.LoginActivity;
import twitter.com.twitterclientsan.common.Constants;
import twitter.com.twitterclientsan.common.ui.TwitterBaseActivity;

/**
 * Created by Sangram Mohanty on 6/26/2018.
 */

public class SplashActivity extends TwitterBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeTwitter();
        setContentView(R.layout.layout_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, Constants.SPLASH_TIME_OUT);
    }
}
