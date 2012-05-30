package com.masterofcode.android._10ideas;

import android.content.Context;
import android.content.Intent;
import com.masterofcode.android._10ideas.screens.activities.DashboardActivity;

public class Intents {

    public static Intent getDashboardIntent(Context context) {
        Intent i = new Intent(context, DashboardActivity.class);
        return i;
    }
}
