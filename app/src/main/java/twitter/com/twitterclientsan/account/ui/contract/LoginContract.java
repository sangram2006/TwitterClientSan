package twitter.com.twitterclientsan.account.ui.contract;

public interface LoginContract {
    /**
     * Success Response
     */
    void onSucess(String userName);

    /**
     * Failure response
     */
    void onFailure(String errorMessage);
}
