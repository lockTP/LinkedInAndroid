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
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpGet get = new HttpGet("https://api.linkedin.com/v1/people/~");
//                HttpPost post = new HttpPost("https://api.linkedin.com/v1/people/~");
                try {
                    consumer.sign(get);
                } catch (OAuthMessageSignerException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (OAuthExpectationFailedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (OAuthCommunicationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } // here need the consumer for sign in for post the share
                get.setHeader("content-type", "text/XML");

                try {
                    String st = EntityUtils.toString(httpclient.execute(get).getEntity(), "UTF-8");
                    textView.setText(st);
                    Toast.makeText(TestActivity.this,
                            "Shared sucessfully", Toast.LENGTH_SHORT).show();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}
