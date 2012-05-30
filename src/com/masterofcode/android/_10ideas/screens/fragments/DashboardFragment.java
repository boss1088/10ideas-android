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
import com.masterofcode.android._10ideas.Intents;
import com.masterofcode.android._10ideas.helpers.IdeasApi;
import com.masterofcode.android._10ideas.helpers.PreferenceHelper;
import com.masterofcode.android._10ideas.helpers.RestClient;
import com.masterofcode.android._10ideas.objects.Ideas;
import com.masterofcode.android._10ideas.screens.activities.AuthenticationActivity;
import com.masterofcode.android._10ideas.screens.activities.DashboardActivity;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

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
        loadData();
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Ideas ideas = IdeasApi.getIdeas(RestClient.BASE_IDEAS);

                    updateUi(ideas.getTotal());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateUi(final int total) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView count = (TextView) getView().findViewById(R.id.count);
                count.setText(String.format(getString(R.string.count), total));

                TextView username = (TextView) getView().findViewById(R.id.username);
                username.setText(PreferenceHelper.getUserEmail());

                Button signOut = (Button) getView().findViewById(R.id.sign_out);
                signOut.setOnClickListener(btnClickListener);

                Button myIdeas = (Button) getView().findViewById(R.id.home_btn_my);
                myIdeas.setOnClickListener(btnClickListener);

                Button publicIdeas = (Button) getView().findViewById(R.id.home_btn_public);
                publicIdeas.setOnClickListener(btnClickListener);

                Button addIdea = (Button) getView().findViewById(R.id.home_btn_add);
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
                case R.id.home_btn_my:
                    activity.replaceFragment(new MyIdeasFragment());
                    break;
                case R.id.home_btn_public:
                    activity.replaceFragment(new PublicIdeasFragment());
                    break;
                case R.id.home_btn_add:
                    activity.replaceFragment(new EditIdeaFragment());
                    break;
            }
        }
    };
}
