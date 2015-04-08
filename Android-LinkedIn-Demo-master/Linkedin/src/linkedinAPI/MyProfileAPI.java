package linkedinAPI;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import beans.MyProfile;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 * Created by Weichuan on 4/8/2015.
 */
public class MyProfileAPI {
    public MyProfile testAPI(OAuthConsumer consumer){
        String st = null;
        MyProfile myProfile = null;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet("https://api.linkedin.com/v1/people/~?format=json");
//                HttpPost post = new HttpPost("https://api.linkedin.com/v1/people/~format=json");
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
            st = EntityUtils.toString(httpclient.execute(get).getEntity(), "UTF-8");
            JSONObject obj = new JSONObject(st);
            myProfile = new MyProfile();
            myProfile.setFirstName(obj.getString("firstName"));
            myProfile.setHeadline(obj.getString("headline"));
            myProfile.setId(obj.getString("id"));
            myProfile.setLastName(obj.getString("lastName"));
            myProfile.setUrl(obj.getJSONObject("siteStandardProfileRequest").getString("url"));

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myProfile;
    }
}
