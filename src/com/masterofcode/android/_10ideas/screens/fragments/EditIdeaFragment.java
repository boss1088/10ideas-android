package com.masterofcode.android._10ideas.screens.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.masterofcode.android.R;
import com.masterofcode.android._10ideas.BaseFragment;
import com.masterofcode.android._10ideas.Intents;
import com.masterofcode.android._10ideas.helpers.IdeasApi;
import com.masterofcode.android._10ideas.screens.activities.DashboardActivity;

import java.io.UnsupportedEncodingException;

public class EditIdeaFragment extends BaseFragment {

    private DashboardActivity activity;
    private View view;
    private boolean restore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        activity = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_idea_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateTitle(view, R.string.edit_idea);

        Button save = (Button) view.findViewById(R.id.save);
        Button cancel = (Button) view.findViewById(R.id.cancel);

        save.setOnClickListener(listener);
        cancel.setOnClickListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        restore = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        restore = false;
    }

    private void save() {
        EditText txtTitle = (EditText) view.findViewById(R.id.text);
        final String username = txtTitle.getText().toString().trim();
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.public_chk);

        showProgressDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    IdeasApi.create(username, checkBox.isChecked());

                    goBack();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } finally {
                    dissmissProgressDialog();
                }
            }
        }).start();
    }

    private void goBack() {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                getActivity().sendBroadcast(Intents.getIdeaCreatedBroadcastIntent());
                Toast.makeText(getActivity(), getString(R.string.idea_saved), Toast.LENGTH_SHORT);
                getActivity().onBackPressed();
            }
        });
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.save:
                    save();
                    break;
                case R.id.cancel:
                    getActivity().onBackPressed();
                    break;
            }
        }
    };
}
