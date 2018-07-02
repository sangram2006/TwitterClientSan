# TwitterClientSan

This is sample app for  Twitter Client application using #Twitter SDK #MVP

# Getting Started
Generate your Twitter API keys through the Twitter developer apps dashboard.
Install Twitter Kit using instructions below.

```javascript
repositories {
  jcenter()
}

dependencies {
    implementation 'com.twitter.sdk.android:twitter:3.1.1'
    implementation 'com.twitter.sdk.android:twitter-core:3.1.1'
  }
} 
```

Below are the highlights for sample.

- App using Android Architecture Patterns Model-View-Presenter

- Login: Connect with twitter using API Keys

- On Login success  make request for 10 tweets in his/her feed

- Display specific tweet details from recycleview tweets item using passing twitterID screen: author, number of likes and retweets, date
- Pull to refresh for update the new data
- Save the data using file can be used at the time of offline.(Note: can be implemented by using DB)
- Compose new tweets using internal api from Twitter SDK exmpale

```java
final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                    .getActiveSession();
            final Intent intent = new ComposerActivity.Builder(DashBoardActivity.this)
                    .session(session)
                    .text("Love where you work")
                    .hashtags("#twitter")
                    .createIntent();
            startActivity(intent);
 ```   
 
 # Setup
 ```java
Use callback url as twittersdk:// on app.twitter.com settings
Form app side add app key on string resorunce file
<string name="com.twitter.sdk.android.CONSUMER_KEY">consumer key</string>
<string name="com.twitter.sdk.android.CONSUMER_SECRET"consumer secret</string>
 ```
 
![alt text](https://github.com/sangram2006/TwitterClientSan/blob/master/flow.png)    
 
 ### Images
 ![alt text](https://github.com/sangram2006/TwitterClientSan/blob/master/Login.png)  
 ![alt text](https://github.com/sangram2006/TwitterClientSan/blob/master/UserAuth.png)  
 ![alt text](https://github.com/sangram2006/TwitterClientSan/blob/master/home_screen.png)  
 ![alt text](https://github.com/sangram2006/TwitterClientSan/blob/master/Compose.png)  
