package com.masterofcode.android._10ideas.helpers;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

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

    public static void sign_in(String userName, String pass) throws UnsupportedEncodingException {
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

        reqEntity.addPart("user[email]", new StringBody(userName));
        reqEntity.addPart("user[password]", new StringBody(pass));

        JSONObject json = RestClient.post(RestClient.BASE_URL + RestClient.BASE_USERS_SIGN_IN, reqEntity);

        PreferenceHelper.setUserEmail(userName);
        PreferenceHelper.setUserPass(pass);
        PreferenceHelper.setAuthToken(json.optString("auth_token"));
        PreferenceHelper.setUserId(json.optString("user_id"));

        //TODO rebuild if need some return
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

    public static void getIdeas(String wichIdeas) throws UnsupportedEncodingException {

        JSONArray jsonArray = RestClient.get(RestClient.BASE_URL + wichIdeas
                                                + "?auth_token=" + PreferenceHelper.getAuthToken());

        //TODO rebuild if need some return
    }
}
