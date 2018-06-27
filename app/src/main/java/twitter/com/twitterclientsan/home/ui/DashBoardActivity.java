package twitter.com.twitterclientsan.home.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;

import twitter.com.twitterclientsan.common.Constants;
import twitter.com.twitterclientsan.home.AdapterListener;
import twitter.com.twitterclientsan.home.CustomTimeLineAdapter;
import twitter.com.twitterclientsan.R;
import twitter.com.twitterclientsan.common.ui.TwitterBaseActivity;
import twitter.com.twitterclientsan.showtweets.ui.ShowDetailsTweetsActivity;

public class DashBoardActivity extends TwitterBaseActivity implements AdapterListener, View.OnClickListener {
    TextView textView;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeLayout;
    CustomTimeLineAdapter tweetTimelineRecyclerViewAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
        String userName = getIntent().getStringExtra(Constants.USER_NAME);
        textView = findViewById(R.id.welcome_text);
        //Welcome Text
        textView.setText(getString(R.string.welcome_text) + userName);

        recyclerView = findViewById(R.id.recycleTweets);
        //Set layout manager for recycle view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeLayout = findViewById(R.id.swipeContainer);

        //Create timeline builder for twitter and max request 10
        final SearchTimeline timeline = new SearchTimeline.Builder()
                .query("#twitter")
                .maxItemsPerRequest(10)
                .build();

        //set the custom adapter
        tweetTimelineRecyclerViewAdapter = new CustomTimeLineAdapter(this, timeline, this);
        recyclerView.setAdapter(tweetTimelineRecyclerViewAdapter);

        //set refreshLayout on adapter. on update of data twitter SDK will take care of notify data chages
        swipeLayout.setOnRefreshListener(pullToRefresh());

        FloatingActionButton compose = findViewById(R.id.compose);
        compose.setOnClickListener(this);
    }

    @Override
    public void showDetails(long id) {
        Intent intent = new Intent(this, ShowDetailsTweetsActivity.class);
        intent.putExtra("tweetId", id);
        startActivity(intent);
    }

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
                        Toast.makeText(DashBoardActivity.this, "Something wrong", Toast.LENGTH_LONG);
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
}
