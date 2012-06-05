package com.masterofcode.android._10ideas;

import android.content.Context;
import android.content.Intent;
import com.masterofcode.android._10ideas.screens.activities.DashboardActivity;

public class Intents {

    public static final String ACTION_IDEA_CREATED = "com.masterofcode.android._10ideas.ACTION_IDEA_CREATED";

    public static Intent getDashboardIntent(Context context) {
        Intent i = new Intent(context, DashboardActivity.class);
        return i;
    }

    public static Intent getIdeaCreatedBroadcastIntent() {
        Intent intent = new Intent(ACTION_IDEA_CREATED);
        return intent;
    }

    public static boolean isIdeaCreatedBroadcastIntent(Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return false;
        }

        return intent.getAction().equals(ACTION_IDEA_CREATED);
    }

}
