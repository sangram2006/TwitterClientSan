package twitter.com.twitterclientsan.Home.ui.contract;

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
