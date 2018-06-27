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
            
