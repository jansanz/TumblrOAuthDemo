package org.jfsd.tumblrdemo;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class TumblrDemoActivity extends Activity {

    private static final String TAG = "TumblrDemo";

    private static final String REQUEST_TOKEN_URL = "https://www.tumblr.com/oauth/request_token";
    private static final String ACCESS_TOKEN_URL = "https://www.tumblr.com/oauth/access_token";
    private static final String AUTH_URL = "https://www.tumblr.com/oauth/authorize";

    // Taken from Tumblr app registration
    private static final String CONSUMER_KEY = "get consumer key from tumblr!!!";
    private static final String CONSUMER_SECRET = "get consumer secret from tumblr!!";

    private static final String CALLBACK_URL = "tumblrdemo://tumblrdemo.com/ok";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // To get the oauth token after the user has granted permissions
        Uri uri = this.getIntent().getData();
        if (uri != null) {

            String token = uri.getQueryParameter("oauth_token");
            String verifier = uri.getQueryParameter("oauth_verifier");
            
            Log.v(TAG, "Token:" +token);
            Log.v(TAG, "Verifier:" +verifier);
        } else {

            CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
                    CONSUMER_SECRET);

            // It uses this signature by default
            // consumer.setMessageSigner(new HmacSha1MessageSigner());

            CommonsHttpOAuthProvider provider = new CommonsHttpOAuthProvider(
                    REQUEST_TOKEN_URL,
                    ACCESS_TOKEN_URL,
                    AUTH_URL);

            String authUrl;
            try {
                authUrl = provider.retrieveRequestToken(consumer, CALLBACK_URL);
                Log.v(TAG, "Auth url:" + authUrl);

                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(authUrl)));

            } catch (OAuthMessageSignerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (OAuthNotAuthorizedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (OAuthExpectationFailedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (OAuthCommunicationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        Log.v(TAG, "onResume");
    }

}
