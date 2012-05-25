package com.masterofcode.android._10ideas.helpers;

import android.text.TextUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: boss1088
 * Date: 5/25/12
 * Time: 7:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestClient {

    final static public String BASE_URL = "http://stage.masterofcode.com:10101/";
    final static public String BASE_REGISTER = "users.json";

    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static JSONObject connect(String url)
    {
        String result;
        JSONObject json = null;

        HttpClient httpclient = new DefaultHttpClient();

        // Prepare a request object
        HttpGet httpget = new HttpGet(url);

        // Execute the request
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            // Examine the response status

            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release

            if (entity != null) {

                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                result= convertStreamToString(instream);

                // A Simple JSONObject Creation
                if (result.equals("null\n") || TextUtils.isEmpty(result)){
                    json = null;
                } else {
                    json=new JSONObject(result);
                }


                // Closing the input stream will trigger connection release
                instream.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static String sendPut(String url, String id, String author){

        String result = null;
        try{

            HttpClient httpclient = new DefaultHttpClient();

            // Prepare a request object
            HttpPut httpput = new HttpPut(url);
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("author", new StringBody(author));
            httpput.setEntity(reqEntity);
            // Execute the request
            HttpResponse response;
            try {
                response = httpclient.execute(httpput);
                // Examine the response status
                result = String.valueOf(response.getStatusLine().getStatusCode());

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public static JSONObject register(String url, String[] params){
        String result;
        JSONObject json = null;

        if(!TextUtils.isEmpty(url))
            try {
                HttpClient client = new DefaultHttpClient();
                client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
                HttpPost post = new HttpPost(url);

                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                reqEntity.addPart("user[email]", new StringBody(params[0]));
                reqEntity.addPart("user[password]", new StringBody(params[1]));

                post.setEntity(reqEntity);
                HttpResponse response = client.execute(post);
                HttpEntity resEntity = response.getEntity();

                InputStream instream = resEntity.getContent();
                result= convertStreamToString(instream);
                json = new JSONObject(result);

                return json;
            } catch (Exception ex){
                ex.printStackTrace();
            }

        return null;
    }

}
