package twitter.com.twitterclientsan.splash.presenter;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import twitter.com.twitterclientsan.splash.ui.contract.SplashContract;

/**
 * Created by Sangram Mohanty on 6/26/2018.
 * Splash screen presenter
 */
public class SplashPresenter {
    SplashContract view;

    public SplashPresenter(SplashContract splashContract) {
        this.view = splashContract;
    }

    /*
     * Check session active or not
     */
    public void isSessionActive() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (session == null) {
            view.launchLogin();
        } else {
            view.launchDashboard(session.getUserName());
        }
    }
}
