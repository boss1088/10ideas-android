package com.masterofcode.android._10ideas.screens.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
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

    private static DashboardFragment Instance = null;

    private DashboardActivity activity;
    private View view;
    private int count;
    private boolean restore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Instance = this;

        activity = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateTitle(view, R.string.home);

        if (restore) {
            updateUi();
            updateCount(count);
            restore = false;
        } else {
            updateUi();
            loadData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        restore = true;
    }

    private void loadData() {
        showProgressIndicator();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Ideas ideas = IdeasApi.getIdeas(RestClient.BASE_IDEAS);
                    count = ideas.getTotal();
                } catch (UnsupportedEncodingException e) {
                    showErrorDialog();
                } catch (NullPointerException npe) {
                    showErrorDialog("Network connection problem");
                } finally {
                    hideProgressIndicator();
                }
            }
        }).start();
    }

    private void showProgressIndicator() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.findViewById(R.id.count).setVisibility(View.GONE);
                view.findViewById(R.id.progress).setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideProgressIndicator() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.findViewById(R.id.count).setVisibility(View.VISIBLE);
                view.findViewById(R.id.progress).setVisibility(View.GONE);

                updateCount(count);
            }
        });
    }

    private void updateCount(int total) {
        TextView count = (TextView) view.findViewById(R.id.count);
        count.setText(String.format(getString(R.string.count), total));
    }

    private void updateUi() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView username = (TextView) view.findViewById(R.id.username);
                username.setText(PreferenceHelper.getUserEmail());

                Button signOut = (Button) view.findViewById(R.id.sign_out);
                signOut.setOnClickListener(btnClickListener);

                Button myIdeas = (Button) view.findViewById(R.id.home_btn_my);
                myIdeas.setOnClickListener(btnClickListener);

                Button publicIdeas = (Button) view.findViewById(R.id.home_btn_public);
                publicIdeas.setOnClickListener(btnClickListener);

                Button addIdea = (Button) view.findViewById(R.id.home_btn_add);
                addIdea.setOnClickListener(btnClickListener);

                Button topIdeas = (Button) view.findViewById(R.id.home_btn_top);
                topIdeas.setOnClickListener(btnClickListener);
            }
        });
    }

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sign_out:
                    PreferenceHelper.clearAuthentication();
                    getActivity().startActivity(new Intent(getActivity(), AuthenticationActivity.class));
                    getActivity().finish();
                    break;
                case R.id.home_btn_my:
                    if (haveInternet()) {
                        ((DashboardActivity) getActivity()).replaceFragment(new MyIdeasFragment());
                    } else {
                        showNoInternetDialog();
                    }
                    break;
                case R.id.home_btn_public:
                    if (haveInternet()) {
                        ((DashboardActivity) getActivity()).replaceFragment(new PublicIdeasFragment());
                    } else {
                        showNoInternetDialog();
                    }
                    break;
                case R.id.home_btn_add:
                    if (haveInternet()) {
                        ((DashboardActivity) getActivity()).replaceFragment(new EditIdeaFragment());
                    } else {
                        showNoInternetDialog();
                    }
                    break;
                case R.id.home_btn_top:
                    showComingSoonDialog();
                    break;
            }
        }
    };

    public static class BroadcastListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (Instance == null) {
                return;
            }

            if (Intents.isIdeaCreatedBroadcastIntent(intent)) {
                Instance.loadData();
            }
        }
    }
}
