package twitter.com.twitterclientsan.home.presenter;

import android.content.Context;

import com.twitter.sdk.android.core.models.Tweet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import twitter.com.twitterclientsan.common.Constants;
import twitter.com.twitterclientsan.connectivity.ConnectionManager;

import static twitter.com.twitterclientsan.storage.InternalStorage.readObject;

/**
 * Created by Sangram Mohanty on 6/27/2018.
 * Dash board presenter
 */
public class DashBoardPresenter {
    Context context;

    public DashBoardPresenter(Context context) {
        this.context = context;
    }

    /*
     * get connection available or not
     * @return boolean
     */
    public boolean isConnectionAvailable() {
        if (ConnectionManager.isNetworkAvailable(context)) {
            return true;
        }
        return false;
    }

    /*
     * get the stored tweet data
     * @return list of tweet
     */
    public List<Tweet> getTweets() {
        ArrayList<Tweet> list = null;
        try {
            list = readObject(context, Constants.STORE_KEY);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
