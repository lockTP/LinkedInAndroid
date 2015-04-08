package com.mukesh.linkedin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.linkedin.R;

import beans.NetworkUpdate;
import linkedinAPI.NetworkUpdateAPI;
import oauth.signpost.OAuthConsumer;

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
                NetworkUpdate st = testAPI.networkSearchAPI(consumer,null,null,null,null);
//                textView.setText(st.getTitle());

            }
        });
    }
}
