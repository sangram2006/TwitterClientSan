package twitter.com.twitterclientsan.account.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import twitter.com.twitterclientsan.R;
import twitter.com.twitterclientsan.Home.ui.DashBoardActivity;
import twitter.com.twitterclientsan.account.presenter.LoginPresenter;
import twitter.com.twitterclientsan.account.ui.contract.LoginContract;
import twitter.com.twitterclientsan.common.ui.TwitterBaseActivity;

public class LoginActivity extends TwitterBaseActivity implements LoginContract {

    TwitterLoginButton loginButton;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login_button);
        loginPresenter = new LoginPresenter(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.doLogin();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSucess(String userName) {
        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.putExtra("username", userName);
        startActivity(intent);
    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
    }
}
