package twitter.com.twitterclientsan.showtweets.contract;

public interface ShowTweetsContract {
    /**
     * Success Response
     */
    void onSucess(String userName);

    /**
     * Failure response
     */
    void onFailure(String errorMessage);
}
