package com.masterofcode.android._10ideas.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.masterofcode.android.R;
import com.masterofcode.android._10ideas.BaseFragment;
import com.masterofcode.android._10ideas.screens.activities.AuthenticationActivity;

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
                TextView text1 = (TextView) getView().findViewById(R.id.text1);
                text1.setText(activity.getString(R.string._10_ideas).toUpperCase());

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
                    activity.onBackPressed();
                    break;
                case R.id.sign_up:
                    // TODO Sign Up button click action
                    break;
            }
        }
    };
}
