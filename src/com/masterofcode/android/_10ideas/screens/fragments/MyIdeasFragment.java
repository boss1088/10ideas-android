package com.masterofcode.android._10ideas.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterofcode.android.R;
import com.masterofcode.android._10ideas.BaseFragment;
import com.masterofcode.android._10ideas.helpers.IdeasApi;
import com.masterofcode.android._10ideas.helpers.PreferenceHelper;
import com.masterofcode.android._10ideas.helpers.RestClient;
import com.masterofcode.android._10ideas.objects.Ideas;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

public class MyIdeasFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_ideas_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateTitle(getView(), R.string.my_ideas);

        loadData();
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*Ideas ideas = */IdeasApi.getIdeas(RestClient.BASE_IDEAS);
                    /*System.out.println("MyIdeasFragment.loadData ideas.getTotal()" + ideas.getTotal());
                    Vector items = ideas.getItems();*/
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
