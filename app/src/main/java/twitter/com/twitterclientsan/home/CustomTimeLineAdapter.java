package twitter.com.twitterclientsan.home;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetLinkClickListener;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

/**
 * Created by Sangram Mohanty on 6/27/2018.
 * Custom adapter for on item click listener
 */
public class CustomTimeLineAdapter extends TweetTimelineRecyclerViewAdapter {
    AdapterListener adapterListener;

    public CustomTimeLineAdapter(Context context, Timeline<Tweet> timeline, AdapterListener adapterListener) {
        super(context, timeline);
        this.adapterListener = adapterListener;
    }

    CustomTimeLineAdapter(Context context, Timeline<Tweet> timeline, int styleResId, Callback<Tweet> cb) {
        super(context, timeline, styleResId, cb);
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Tweet tweet = new TweetBuilder().build();
        final CompactTweetView compactTweetView = new CompactTweetView(context, tweet, styleResId);
        compactTweetView.setOnActionCallback(actionCallback);
        compactTweetView.setTweetLinkClickListener(new TweetLinkClickListener() {
            @Override
            public void onLinkClick(Tweet tweet, String url) {
                adapterListener.showDetails(tweet.getId());
                Log.d("TweetLinkClicked", "tweet = " + tweet.text + "url = " + url);
            }
        });
        return new TweetViewHolder(compactTweetView);
    }

}
