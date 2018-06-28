package twitter.com.twitterclientsan.splash.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import twitter.com.twitterclientsan.R;
import twitter.com.twitterclientsan.account.ui.LoginActivity;
import twitter.com.twitterclientsan.common.Constants;
import twitter.com.twitterclientsan.common.ui.TwitterBaseActivity;
import twitter.com.twitterclientsan.home.ui.DashBoardActivity;
import twitter.com.twitterclientsan.splash.presenter.SplashPresenter;
import twitter.com.twitterclientsan.splash.ui.contract.SplashContract;

/**
 * Created by Sangram Mohanty on 6/26/2018.
 * Splash screen as per active session route to login or dashboard screen
 */

public class SplashActivity extends TwitterBaseActivity implements SplashContract {

    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);

        splashPresenter = new SplashPresenter(this);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer.
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                splashPresenter.isSessionActive();
                // close this activity
                finish();
            }
        }, Constants.SPLASH_TIME_OUT);
    }

    /*
     * Move to login screen
     */
    @Override
    public void launchLogin() {
        Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    /*
     * Move Dashboard screen
     */
    @Override
    public void launchDashboard(String userName) {
        Intent dashboardIntent = new Intent(SplashActivity.this, DashBoardActivity.class);
        dashboardIntent.putExtra(Constants.USER_NAME, userName);
        startActivity(dashboardIntent);
    }
}
