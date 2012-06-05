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

public class SignUpFragment extends BaseFragment {

    AuthenticationActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_up_fragment, container, false);
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
                    getActivity().onBackPressed();
                    break;
                case R.id.sign_up:
                    if (haveInternet()) {
                        signUp();
                    } else {
                        showNoInternetDialog();
                    }
                    break;
            }
        }
    };

    private void signUp() {
        EditText txtUsername = (EditText) getView().findViewById(R.id.username);
        EditText txtPassword = (EditText) getView().findViewById(R.id.password);

        final String username = txtUsername.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();

        showProgressDialog();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!username.equals("") && !password.equals("")) {
                    try {
                        IdeasApi.register(username, password);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } finally {
                        dissmissProgressDialog();
                    }
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
