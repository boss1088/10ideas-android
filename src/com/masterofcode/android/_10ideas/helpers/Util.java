package com.masterofcode.android._10ideas.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created with IntelliJ IDEA.
 * User: boss1088
 * Date: 6/4/12
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util {

    /**
     * Check if Internet connection available
     * @param context
     * @return true is internet connected
     */
    public static boolean haveInternet(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr == null) {
            // can't tell if we have internet or not, so no
            return false;
        }

        NetworkInfo info = mgr.getActiveNetworkInfo();

        if (info == null) {
            // no active network, so no network
            return false;
        }

        if (!info.isConnectedOrConnecting()) {
            return false;
        }

        if (info.isRoaming()) {
            // here is the roaming option you can change it if you want to disable
            // internet while roaming, just return false
            return true;
        }

        return true;
    }
}
