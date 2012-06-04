package com.masterofcode.android._10ideas.helpers;

/**
 * Created with IntelliJ IDEA.
 * User: boss1088
 * Date: 5/25/12
 * Time: 9:03 PM
 * To change this template use File | Settings | File Templates.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.Map;

public class PreferenceHelper {

    public static final String AUTH_TOKEN = "auth_token";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_ID = "user_id";
    public static final String ERROR = "error";

    static Context mContext = null;
    static SharedPreferences prefs = null;

    public static void init(Context applicationContext) {
        if (mContext != null) {
            return;
        }

        mContext = applicationContext;
        prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

    /**
     * Get all preferences
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        return prefs.getAll();
    }

    /**
     * Update an int valued shared preference. This method induces file system
     * access. Validate values in setters before calling this method.
     *
     * @param key   to associate with value
     * @param value to store
     */
    private static void updatePref(String key, int value) {
        Editor edit = prefs.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    /**
     * Update a String valued shared preference. This method induces file system
     * access. Validate values in setters before calling this method.
     *
     * @param key   to associate with value
     * @param value to store
     */
    private static void updatePref(String key, String value) {
        Editor edit = prefs.edit();
        edit.putString(key, value);
        edit.commit();
    }

    /**
     * Update a boolean valued shared preference. This method induces file system
     * access. Validate values in setters before calling this method.
     *
     * @param key   to associate with value
     * @param value to store
     */
    private static void updatePref(String key, boolean value) {
        Editor edit = prefs.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    /**
     * Update an long valued shared preference. This method induces file system
     * access. Validate values in setters before calling this method.
     *
     * @param key   to associate with value
     * @param value to store
     */
    private static void updatePref(String key, long value) {
        Editor edit = prefs.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public static void setAuthToken(String authToken) {
        updatePref(AUTH_TOKEN, authToken);
    }

    public static String getAuthToken() {
        return prefs.getString(AUTH_TOKEN, "");
    }

    public static void setUserPass(String userPass) {
        updatePref(USER_PASSWORD, userPass);
    }

    public static String getUserPass() {
        return prefs.getString(USER_PASSWORD, "");
    }

    public static void setUserEmail(String userEmail) {
        updatePref(USER_EMAIL, userEmail);
    }

    public static String getUserEmail() {
        return prefs.getString(USER_EMAIL, "");
    }

    public static void setUserId(String userId) {
        updatePref(USER_ID, userId);
    }

    public static String getUserId() {
        return prefs.getString(USER_ID, "");
    }

    public static void setError(String error) {
        updatePref(ERROR, error);
    }

    public static String getError() {
        return prefs.getString(ERROR, "");
    }

    public static void clearAuthentication() {
        updatePref(AUTH_TOKEN, "");
        updatePref(USER_PASSWORD, "");
        updatePref(USER_EMAIL, "");
        updatePref(USER_ID, "");

    }

    public static boolean isAuthenticated() {
        if (getAuthToken().equals("") && getUserPass().equals("")) {
            return false;
        } else {
            return true;
        }
    }

}
