package twitter.com.twitterclientsan.account.ui.contract;

/**
 * Created by Sangram Mohanty on 6/27/2018.
 * Contract
 */
public interface LoginContract {
    /**
     * Success Response
     */
    void onSuccess(String userName);

    /**
     * Failure response
     */
    void onFailure(String errorMessage);
}
