package com.masterofcode.android._10ideas.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.masterofcode.android.R;
import com.masterofcode.android._10ideas.BaseFragment;
import com.masterofcode.android._10ideas.helpers.IdeasApi;
import com.masterofcode.android._10ideas.objects.Idea;

public class ViewIdeaFragment extends BaseFragment {

    private static final String ID = "id";

    private String id;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                Idea idea = IdeasApi.getIdeaById(id);
                updateUi(idea);
            }
        }).start();
    }

    private void updateUi(final Idea idea) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView text = (TextView) getView().findViewById(R.id.text);
                text.setText(idea.getEssential());
            }
        });
    }
}
