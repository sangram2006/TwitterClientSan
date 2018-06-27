package twitter.com.twitterclientsan.account.ui.contract;


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
