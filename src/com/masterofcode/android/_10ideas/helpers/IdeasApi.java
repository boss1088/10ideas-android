package com.masterofcode.android._10ideas.helpers;

import com.masterofcode.android._10ideas.objects.Idea;
import com.masterofcode.android._10ideas.objects.Ideas;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: boss1088
 * Date: 5/25/12
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class IdeasApi {

    public static void register(String userName, String pass) throws UnsupportedEncodingException {
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

        reqEntity.addPart("user[email]", new StringBody(userName));
        reqEntity.addPart("user[password]", new StringBody(pass));

        JSONObject json = RestClient.post(RestClient.BASE_URL + RestClient.BASE_USERS, reqEntity);

        PreferenceHelper.setUserEmail(userName);
        PreferenceHelper.setUserPass(pass);
        PreferenceHelper.setAuthToken(json.optString("auth_token"));
        PreferenceHelper.setUserId(json.optString("user_id"));

        //TODO rebuild if need some return
    }

    public static void sign_in(String userName, String pass) throws UnsupportedEncodingException, Exception {
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

        reqEntity.addPart("user[email]", new StringBody(userName));
        reqEntity.addPart("user[password]", new StringBody(pass));

        JSONObject json = RestClient.post(RestClient.BASE_URL + RestClient.BASE_USERS_SIGN_IN, reqEntity);

        if (json == null) {
            throw new Exception();
        }

        PreferenceHelper.setUserEmail(userName);
        PreferenceHelper.setUserPass(pass);
        PreferenceHelper.setAuthToken(json.optString("auth_token"));
        PreferenceHelper.setUserId(json.optString("user_id"));
    }

    public static void create(String essential, Boolean isPublic) throws UnsupportedEncodingException {
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

        reqEntity.addPart("user[email]", new StringBody(PreferenceHelper.getUserEmail()));
        reqEntity.addPart("user[password]", new StringBody(PreferenceHelper.getUserPass()));
        reqEntity.addPart("auth_token", new StringBody(PreferenceHelper.getAuthToken()));
        reqEntity.addPart("idea[essential]", new StringBody(essential));
        reqEntity.addPart("idea[user_id]", new StringBody(PreferenceHelper.getUserId()));
        if (isPublic) {
            reqEntity.addPart("idea[public]", new StringBody("1"));
        } else {
            reqEntity.addPart("idea[public]", new StringBody("0"));
        }

        RestClient.post(RestClient.BASE_URL + RestClient.BASE_IDEAS, reqEntity);

        //TODO rebuild if need some return
    }

    public static Ideas getIdeas(String wichIdeas) throws UnsupportedEncodingException {

        Ideas ideas = null;
        JSONArray jsonArray = RestClient.get(RestClient.BASE_URL + wichIdeas
                                                + "?auth_token=" + PreferenceHelper.getAuthToken());

        try {
           ideas = Ideas.fromJson(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ideas;
    }

    public static Idea getIdeaById(String id) {
        JSONObject json = RestClient.getObject(RestClient.BASE_URL + "ideas/" + id + ".json"
                + "?auth_token=" + PreferenceHelper.getAuthToken());

        return new Idea(json);
    }

    public static void vote(String id) throws UnsupportedEncodingException {
        RestClient.sendPut(RestClient.BASE_URL + RestClient.BASE_IDEAS + "/" + id + "/vote.json");
    }

    public static void publish(String id) throws UnsupportedEncodingException {
        RestClient.sendPut(RestClient.BASE_URL + RestClient.BASE_IDEAS + "/" + id + "/publish.json");
    }
}
