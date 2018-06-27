package twitter.com.twitterclientsan.showtweets.presenter;

import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import twitter.com.twitterclientsan.showtweets.contract.ShowTweetsContract;

public class ShowTweetsPresenter {
    private ShowTweetsContract view;

    public ShowTweetsPresenter(ShowTweetsContract view) {
        this.view = view;
    }
}
