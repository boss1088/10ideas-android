package com.masterofcode.android._10ideas.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.masterofcode.android.R;
import com.masterofcode.android._10ideas.BaseFragment;
import com.masterofcode.android._10ideas.helpers.IdeasApi;
import com.masterofcode.android._10ideas.helpers.PreferenceHelper;
import com.masterofcode.android._10ideas.objects.Idea;
import com.masterofcode.android._10ideas.screens.activities.DashboardActivity;

import java.io.UnsupportedEncodingException;

public class ViewIdeaFragment extends BaseFragment {

    private static final String ID = "id";

    private String id;
    private DashboardActivity activity;

    public static ViewIdeaFragment newInstance(String id) {
        ViewIdeaFragment f = new ViewIdeaFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (DashboardActivity) getActivity();

        Bundle args = getArguments();
        if (args != null) {
            id = args.getString(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_idea_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateTitle(getView(), R.string.view_idea);
        loadData();
    }

    private void loadData() {
        showProgressDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Idea idea = IdeasApi.getIdeaById(id);
                    updateUi(idea);
                } catch (NullPointerException e) {
                    showErrorDialog("Network connection problem");
                } finally {
                    dissmissProgressDialog();
                }
            }
        }).start();
    }

    private void updateUi(final Idea idea) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView text = (TextView) getView().findViewById(R.id.text);
                text.setText(idea.getEssential());

                Button button = (Button) getView().findViewById(R.id.idea_button);
                String userId = idea.getUser_id();
                if (userId.equals(PreferenceHelper.getUserId()) && !idea.isPublic()) {
                    button.setText("Publish");
                    button.setOnClickListener(publish);
                } else {
                    button.setText("Vote");
                    button.setOnClickListener(vote);
                }
            }
        });
    }

    private boolean isSuccess(String code) {
        if (code.equals("200") || code.equals("201")) {
            return true;
        } else {
            return false;
        }
    }

    View.OnClickListener vote = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String response = IdeasApi.vote(id);
                        if (isSuccess(response)) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "success", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            showErrorDialog();
                        }
                    } catch (UnsupportedEncodingException e) {
                        showErrorDialog();
                    }
                }
            }).start();
        }
    };

    View.OnClickListener publish = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String response = IdeasApi.publish(id);
                        if (isSuccess(response)) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "success", Toast.LENGTH_LONG).show();
                                    loadData();
                                }
                            });
                        } else {
                            showErrorDialog();
                        }
                    } catch (UnsupportedEncodingException e) {
                        showErrorDialog();
                    }
                }
            }).start();
        }
    };
}
