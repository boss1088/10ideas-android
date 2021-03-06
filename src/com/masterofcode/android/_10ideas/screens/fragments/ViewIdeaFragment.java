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
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ViewIdeaFragment extends BaseFragment {

    private static final String ID = "id";

    private String id;
    private DashboardActivity activity;
    private View view;

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
        view = inflater.inflate(R.layout.view_idea_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateTitle(view, R.string.view_idea);
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
                TextView text = (TextView) view.findViewById(R.id.text);
                text.setText(idea.getEssential());

                SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                SimpleDateFormat outFormat = new SimpleDateFormat("dd MMMM yyyy");
                String noteCreated = "";
                try {
                    noteCreated = outFormat.format(inFormat.parse(idea.getCreated_at()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TextView created = (TextView) view.findViewById(R.id.created);
                created.setText(noteCreated);

                Button button = (Button) view.findViewById(R.id.idea_button);
                TextView published = (TextView) view.findViewById(R.id.published);

                String userId = idea.getUser_id();
                if (userId.equals(PreferenceHelper.getUserId())) {
                    if (!idea.isPublic()) {
                        button.setText(activity.getString(R.string.publish));
                        button.setOnClickListener(publish);
                    } else {
                        button.setVisibility(View.GONE);
                        published.setVisibility(View.VISIBLE);
                    }
                } else {
                    button.setText(activity.getString(R.string.vote));
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
