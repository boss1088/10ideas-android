package com.masterofcode.android._10ideas.screens.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.masterofcode.android.R;
import com.masterofcode.android._10ideas.BaseFragment;
import com.masterofcode.android._10ideas.helpers.IdeasApi;
import com.masterofcode.android._10ideas.helpers.PreferenceHelper;
import com.masterofcode.android._10ideas.screens.activities.AuthenticationActivity;
import com.masterofcode.android._10ideas.screens.activities.DashboardActivity;

import java.io.UnsupportedEncodingException;

public class SignInFragment extends BaseFragment {

    AuthenticationActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_in_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (AuthenticationActivity) getActivity();

        updateUi();
    }

    private void updateUi() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button login = (Button) getView().findViewById(R.id.login);
                Button signUp = (Button) getView().findViewById(R.id.sign_up);
                login.setOnClickListener(btnClickListener);
                signUp.setOnClickListener(btnClickListener);
            }
        });
    }

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.login:
                    /*startActivity(new Intent(getActivity(), DashboardActivity.class));*/
                    if (haveInternet()) {
                        signIn();
                    } else {
                        showNoInternetDialog();
                    }
                    break;
                case R.id.sign_up:
                    activity.replaceFragment(new SignUpFragment());
                    break;
            }
        }
    };

    private void signIn() {
        EditText txtUsername = (EditText) getView().findViewById(R.id.username);
        EditText txtPassword = (EditText) getView().findViewById(R.id.password);

        final String username = txtUsername.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();

        if (username.equals("") || password.equals("")) {
            showErrorDialog();
            return;
        }

        showProgressDialog();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    IdeasApi.sign_in(username, password);
                } catch (UnsupportedEncodingException e) {
                    //showErrorDialog();
                    e.printStackTrace();
                } finally {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dissmissProgressDialog();
                        }
                    });
                }

                if (PreferenceHelper.isAuthenticated()) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getActivity().finish();
                            startActivity(new Intent(getActivity(), DashboardActivity.class));
                        }
                    });
                } else if (!PreferenceHelper.getError().equals("false")) {
                    showErrorDialog(PreferenceHelper.getError());
                    PreferenceHelper.setError("false");
                } else {
                    showErrorDialog();
                }
            }
        }).start();
    }


}
