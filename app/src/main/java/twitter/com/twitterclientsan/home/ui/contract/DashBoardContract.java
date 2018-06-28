package twitter.com.twitterclientsan.home.ui.contract;

/**
 * Created by Sangram Mohanty on 6/27/2018.
 * Dash board contract
 */
public interface DashBoardContract {
    /**
     * Success Response
     */
    void onSucess(String userName);

    /**
     * Failure response
     */
    void onFailure(String errorMessage);
}
