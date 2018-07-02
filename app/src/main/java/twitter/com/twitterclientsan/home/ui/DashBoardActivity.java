package twitter.com.twitterclientsan.home.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import twitter.com.twitterclientsan.R;
import twitter.com.twitterclientsan.common.Constants;
import twitter.com.twitterclientsan.common.ui.TwitterBaseActivity;
import twitter.com.twitterclientsan.home.AdapterListener;
import twitter.com.twitterclientsan.home.presenter.DashBoardPresenter;
import twitter.com.twitterclientsan.showtweets.ui.ShowDetailsTweetsActivity;
import twitter.com.twitterclientsan.storage.InternalStorage;

/**
 * Created by Sangram Mohanty on 6/27/2018.
 * Dash board activity show the max 10 list per request , Pull to refresh for new items, used
 * searchTimeLine for server response and FixedTimeLine for stored data.
 */

public class DashBoardActivity extends TwitterBaseActivity implements AdapterListener, View.OnClickListener {
    TextView textView, tweets, followers, following;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeLayout;
    TweetTimelineRecyclerViewAdapter tweetTimelineRecyclerViewAdapter;
    SearchTimeline timeline;
    FixedTweetTimeline fixedTweetTimeline;
    DashBoardPresenter dashBoardPresenter;
    ImageView imageView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
        String userName = getIntent().getStringExtra(Constants.USER_NAME);
        //get Resource
        textView = findViewById(R.id.welcome_text);
        tweets = findViewById(R.id.tweets_count);
        followers = findViewById(R.id.followers_count);
        following = findViewById(R.id.following_count);

        imageView = findViewById(R.id.user_profile_image_view);

        //Initialize presenter
        dashBoardPresenter = new DashBoardPresenter(this);
        //Welcome Text
        textView.setText(getString(R.string.welcome_text) + userName);

        recyclerView = findViewById(R.id.recycleTweets);
        //Set layout manager for recycle view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeLayout = findViewById(R.id.swipeContainer);

        //Create timeline builder for twitter and max request 10
        timeline = getSearchTimeLine();
        registerTimeLine();

        //get data from storage if no network
        if (!dashBoardPresenter.isConnectionAvailable()) {
            fixedTweetTimeline = getFixedTimeline();
            findViewById(R.id.count_details).setVisibility(View.GONE);
        } else {
            findViewById(R.id.count_details).setVisibility(View.VISIBLE);
        }

        //create adapter for RecyclerView
        tweetTimelineRecyclerViewAdapter = new TweetTimelineRecyclerViewAdapter.Builder(this)
                .setTimeline(dashBoardPresenter.isConnectionAvailable() ? timeline : fixedTweetTimeline)
                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                .build();
        //Set adapter
        recyclerView.setAdapter(tweetTimelineRecyclerViewAdapter);

        //set refreshLayout on adapter. on update of data twitter SDK will take care of notify data chages
        swipeLayout.setOnRefreshListener(pullToRefresh());

        FloatingActionButton compose = findViewById(R.id.compose);
        compose.setOnClickListener(this);
        fetchTwitterImage();
    }

    /*
     * Store tweets for offline use
     */
    private void registerTimeLine() {
        final ArrayList<Tweet> tweets = new ArrayList<>();
        timeline.next(null, new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                tweets.addAll(result.data.items);
                try {
                    InternalStorage.writeObject(DashBoardActivity.this, Constants.STORE_KEY, tweets);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(TwitterException exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void showDetails(long id) {
        Intent intent = new Intent(this, ShowDetailsTweetsActivity.class);
        intent.putExtra(Constants.TWITTER_ID, id);
        startActivity(intent);
    }

    /*
     * Listener for pull to refresh
     */
    private SwipeRefreshLayout.OnRefreshListener pullToRefresh() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                tweetTimelineRecyclerViewAdapter.refresh(new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        swipeLayout.setRefreshing(false);
                    }

                    @SuppressLint("ShowToast")
                    @Override
                    public void failure(TwitterException exception) {
                        Toast.makeText(DashBoardActivity.this, R.string.error_msg, Toast.LENGTH_LONG);
                    }
                });
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.compose) {
            // Start the Twitter SDK composeActivity
            final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                    .getActiveSession();
            final Intent intent = new ComposerActivity.Builder(DashBoardActivity.this)
                    .session(session)
                    .text("Love where you work")
                    .hashtags("#twitter")
                    .createIntent();
            startActivity(intent);
        }
    }

    /*
     * Get Search time with query #twitter this is set for server response
     * @return searchTimeLine
     */
    private SearchTimeline getSearchTimeLine() {
        return new SearchTimeline.Builder()
                .query("#twitter")
                .maxItemsPerRequest(10)
                .languageCode(Locale.ENGLISH.getLanguage())
                .build();
    }


    /*
     * Get FixedTweetTimeline with query #twitter this is set for local data
     * @return FixedTweetTimeline
     */
    private FixedTweetTimeline getFixedTimeline() {
        return new FixedTweetTimeline.Builder()
                .setTweets(dashBoardPresenter.getTweets())
                .build();
    }

    /**
     * call Verify Credentials API when Twitter Auth is successful else it will go in exception block
     * this metod will provide you User model which contain all user information
     */
    public void fetchTwitterImage() {
        //check if user is already authenticated or not
        if (getTwitterSession() != null) {

            //fetch twitter image with other information if user is already authenticated

            //initialize twitter api client
            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();

            Call<User> call = twitterApiClient.getAccountService().verifyCredentials(true, false, true);
            call.enqueue(new Callback<User>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void success(Result<User> result) {
                    User user = result.data;
                    textView.setText(" " + user.name + "\n@" + user.screenName);

                    tweets.setText(getString(R.string.tweets) + user.statusesCount);
                    following.setText(getString(R.string.following) + user.friendsCount);
                    followers.setText(getString(R.string.followers) + user.followersCount);
                    String imageProfileUrl = user.profileImageUrl;
                    Log.e(Constants.TAG, "Data : " + imageProfileUrl);

                    imageProfileUrl = imageProfileUrl.replace("_normal", "");

                    ///load image using Picasso

                    Picasso.with(DashBoardActivity.this).load(imageProfileUrl)
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.drawable.tw__composer_logo_blue)
                            .into(imageView, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                                    RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                                    imageDrawable.setCircular(true);
                                    imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                                    imageView.setImageDrawable(imageDrawable);
                                }

                                @Override
                                public void onError() {
                                    imageView.setImageResource(R.drawable.tw__composer_logo_blue);
                                }
                            });
                }

                @Override
                public void failure(TwitterException exception) {
                    Toast.makeText(DashBoardActivity.this, R.string.try_again, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, R.string.authenticate, Toast.LENGTH_SHORT).show();
        }

    }

}
