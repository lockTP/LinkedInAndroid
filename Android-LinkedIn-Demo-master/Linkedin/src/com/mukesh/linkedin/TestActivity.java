package com.mukesh.linkedin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linkedin.R;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import beans.MyProfile;
import beans.NetworkUpdate;
import linkedinAPI.NetworkUpdateAPI;
import linkedinAPI.TestAPI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 * Created by Weichuan on 4/6/2015.
 */
public class TestActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        Button testAPI = (Button) findViewById(R.id.button);
        Intent intent = this.getIntent();
        final OAuthConsumer consumer = (OAuthConsumer) intent.getSerializableExtra("consumer");
        final TextView textView = (TextView) findViewById(R.id.textView);
        testAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkUpdateAPI testAPI = new NetworkUpdateAPI();
                NetworkUpdate st = testAPI.NetwordSearchAPI(consumer,null,null,null,null);
//                textView.setText(st.getTitle());

            }
        });
    }
}
