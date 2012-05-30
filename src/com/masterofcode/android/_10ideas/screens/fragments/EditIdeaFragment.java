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
import com.masterofcode.android._10ideas.helpers.IdeasApi;

import java.io.UnsupportedEncodingException;

public class EditIdeaFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_idea_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateTitle(getView(), R.string.edit_idea);

        Button save = (Button) getView().findViewById(R.id.save);
        Button cancel = (Button) getView().findViewById(R.id.cancel);

        save.setOnClickListener(listener);
        cancel.setOnClickListener(listener);
    }

    private void save() {
        EditText txtTitle = (EditText) getView().findViewById(R.id.text);
        final String username = txtTitle.getText().toString().trim();
        final CheckBox checkBox = (CheckBox) getView().findViewById(R.id.public_chk);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    IdeasApi.create(username, checkBox.isChecked());
                    
                    goBack();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void goBack() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
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
