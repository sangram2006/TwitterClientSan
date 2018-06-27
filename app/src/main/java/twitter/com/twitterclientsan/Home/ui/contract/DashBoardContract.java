package twitter.com.twitterclientsan.home.ui.contract;

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
