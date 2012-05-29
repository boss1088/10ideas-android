package com.masterofcode.android._10ideas.screens.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.masterofcode.android.R;
import com.masterofcode.android._10ideas.BaseFragment;
import com.masterofcode.android._10ideas.helpers.PreferenceHelper;
import com.masterofcode.android._10ideas.screens.activities.AuthenticationActivity;
import com.masterofcode.android._10ideas.screens.activities.DashboardActivity;

public class DashboardFragment extends BaseFragment {

    DashboardActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateTitle(getView(), R.string.home);
        updateUi();
    }

    private void updateUi() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView username = (TextView) getView().findViewById(R.id.username);
                username.setText(PreferenceHelper.getUserEmail());

                Button signOut = (Button) getView().findViewById(R.id.sign_out);
                signOut.setOnClickListener(btnClickListener);

                Button myIdeas = (Button) getView().findViewById(R.id.my_ideas);
                myIdeas.setOnClickListener(btnClickListener);

                Button addIdea = (Button) getView().findViewById(R.id.add_idea);
                addIdea.setOnClickListener(btnClickListener);
            }
        });
    }

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sign_out:
                    // TODO sign out
                    break;
                case R.id.my_ideas:
                    activity.replaceFragment(new MyIdeasFragment());
                    break;
                case R.id.add_idea:
                    activity.replaceFragment(new EditIdeaFragment());
                    break;
            }
        }
    };
}
