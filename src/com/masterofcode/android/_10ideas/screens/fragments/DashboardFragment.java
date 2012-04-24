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

    DashboardActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity = (DashboardActivity) getActivity();

        updateUi();
    }

    private void updateUi() {
        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button signOut = (Button) getView().findViewById(R.id.sign_out);
                signOut.setOnClickListener(btnClickListener);
            }
        });
    }

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sign_out:
                    parentActivity.finish();
                    break;
            }
        }
    };
}
