package twitter.com.twitterclientsan.account.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import twitter.com.twitterclientsan.R;
import twitter.com.twitterclientsan.common.Constants;
import twitter.com.twitterclientsan.home.ui.DashBoardActivity;
import twitter.com.twitterclientsan.account.presenter.LoginPresenter;
import twitter.com.twitterclientsan.account.ui.contract.LoginContract;
import twitter.com.twitterclientsan.common.ui.TwitterBaseActivity;

/**
 * Created by Sangram Mohanty on 6/27/2018.
 * Login screen, used twitter button for login
 */
public class LoginActivity extends TwitterBaseActivity implements LoginContract {

    TwitterLoginButton loginButton;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login_button);
        loginPresenter = new LoginPresenter(this);
        loginButton.setCallback(loginPresenter.doLogin());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(String userName) {
        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.putExtra(Constants.USER_NAME, userName);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
    }
}
