package linkedinAPI;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import beans.NetworkUpdate;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 * Created by xiaotingli on 4/8/15.
 */
public class NetworkUpdateAPI {
    public NetworkUpdate networkSearchAPI(OAuthConsumer consumer, String timeBefore, String timeAfter, String count, String keyword){
        String st = null;
        ArrayList<NetworkUpdate> networkUpdates = new ArrayList<NetworkUpdate>();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String url = "https://api.linkedin.com/v1/people/~/network/updates?format=json";
//        String baseURL = "https://api.l        inkedin.com/v1/people/~/network/updates?";
//        String url = baseURL + "count=" + count + "&" + "after=" + timeAfter + "&" + "before" + timeBefore + "&format=json";

        HttpGet get = new HttpGet(url);

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
            JSONArray values = obj.getJSONArray("values");
            for (int i = 0; i < values.length(); i++){
                JSONObject value = values.getJSONObject(i);
                NetworkUpdate network = null;
                if(value.getJSONObject("updateContent") != null){
                    if (value.getJSONObject("updateContent").has("company")){
                        network = new NetworkUpdate();
                        if(value.getJSONObject("updateContent").getJSONObject("company").has("name")){
                            network.setCompanyName(value.getJSONObject("updateContent").getJSONObject("company").getString("name"));
                        }
                    }
                    if (value.getJSONObject("updateContent").has("companyJobUpdate")){
                            if(value.getJSONObject("updateContent").getJSONObject("companyJobUpdate").has("job")){
                                if(value.getJSONObject("updateContent").getJSONObject("companyJobUpdate").getJSONObject("job").has("description")){
                                    network.setDescription(value.getJSONObject("updateContent").getJSONObject("companyJobUpdate").getJSONObject("job").getString("description"));
                                }
                                if(value.getJSONObject("updateContent").getJSONObject("companyJobUpdate").getJSONObject("job").has("position")){
                                    if(value.getJSONObject("updateContent").getJSONObject("companyJobUpdate").getJSONObject("job").getJSONObject("position").has("title")){
                                        network.setTitle(value.getJSONObject("updateContent").getJSONObject("companyJobUpdate").getJSONObject("job").getJSONObject("position").getString("title"));
                                    }
                                }
                            }
                        networkUpdates.add(network);
                        }
                }

            }
            networkUpdates = searchResults(networkUpdates, "truck");

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
        return null;
    }



    public ArrayList<NetworkUpdate> searchResults(ArrayList<NetworkUpdate> networkUpdates, String keyword){
        ArrayList<NetworkUpdate> newNetworkUpdates = new ArrayList<NetworkUpdate>();
        keyword = keyword.toLowerCase();
        for (int i = 0; i < networkUpdates.size(); i++){
            if (networkUpdates.get(i).getTitle().toLowerCase().contains(keyword)||networkUpdates.get(i).getDescription().toLowerCase().contains(keyword)){
                newNetworkUpdates.add(networkUpdates.get(i));
            }
        }
        return newNetworkUpdates;
    }
}
