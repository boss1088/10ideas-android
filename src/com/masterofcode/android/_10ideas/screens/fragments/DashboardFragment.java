package com.masterofcode.android._10ideas.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.masterofcode.android.R;
import com.masterofcode.android._10ideas.BaseFragment;
import com.masterofcode.android._10ideas.screens.activities.DashboardActivity;

public class DashboardFragment extends BaseFragment {

    DashboardActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (DashboardActivity) getActivity();

        updateTitle(getView(), R.string.home);
        updateUi();
    }

    private void updateUi() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
                    activity.finish();
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
