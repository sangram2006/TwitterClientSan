package twitter.com.twitterclientsan.home.presenter;

import android.content.Context;

import com.twitter.sdk.android.core.models.Tweet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import twitter.com.twitterclientsan.common.Constants;
import twitter.com.twitterclientsan.connectivity.ConnectionManager;

import static twitter.com.twitterclientsan.storage.InternalStorage.readObject;

public class DashBoardPresenter {
    Context context;

    public DashBoardPresenter(Context context) {
        this.context = context;
    }

    public boolean isConnectionAvailable() {
        if (ConnectionManager.isNetworkAvailable(context)) {
            return true;
        }
        if (getTweets() != null) {
            return false;
        }
        return true;
    }

    public List<Tweet> getTweets() {
        List<Tweet> list = null;
        try {
            list = (ArrayList<Tweet>) readObject(context, Constants.STORE_KEY);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
