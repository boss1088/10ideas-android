package com.masterofcode.android._10ideas;

import android.app.Application;
import com.masterofcode.android._10ideas.helpers.PreferenceHelper;

/**
 * Created with IntelliJ IDEA.
 * User: boss1088
 * Date: 5/26/12
 * Time: 12:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class IdeasApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceHelper.init(this);
    }
}
