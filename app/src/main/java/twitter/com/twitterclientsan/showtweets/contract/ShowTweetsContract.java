package twitter.com.twitterclientsan.showtweets.contract;

/**
 * Created by Sangram Mohanty on 6/26/2018.
 */
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
