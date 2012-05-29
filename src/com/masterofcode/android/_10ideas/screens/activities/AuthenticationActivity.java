package com.masterofcode.android._10ideas.screens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import com.masterofcode.android.R;
import com.masterofcode.android._10ideas.BaseActivity;
import com.masterofcode.android._10ideas.helpers.PreferenceHelper;
import com.masterofcode.android._10ideas.screens.fragments.SignInFragment;
import com.masterofcode.android._10ideas.screens.fragments.SignUpFragment;

public class AuthenticationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity);

        System.out.println("AuthenticationActivity.onCreate PreferenceHelper.isAuthenticated(): " + PreferenceHelper.isAuthenticated());

        if (savedInstanceState == null) {
            if (!PreferenceHelper.isAuthenticated()) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new SignUpFragment()).commit();
            } else {
                startActivity(new Intent(this, DashboardActivity.class));
            }
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}
