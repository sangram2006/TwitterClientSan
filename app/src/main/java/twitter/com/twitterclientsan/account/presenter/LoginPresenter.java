package twitter.com.twitterclientsan.account.presenter;

import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import twitter.com.twitterclientsan.account.ui.contract.LoginContract;


public class LoginPresenter {
    private LoginContract view;

    public LoginPresenter(LoginContract view) {
        this.view = view;
    }

    public Callback<TwitterSession> doLogin() {
        return new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                view.onSuccess(session.getUserName());

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                view.onFailure(exception.getMessage());
                Log.d("Sangram", "" + exception);
            }
        };
    }

}
