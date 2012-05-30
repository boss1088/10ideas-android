package com.masterofcode.android._10ideas.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.masterofcode.android.R;
import com.masterofcode.android._10ideas.BaseFragment;
import com.masterofcode.android._10ideas.helpers.IdeasApi;
import com.masterofcode.android._10ideas.helpers.RestClient;
import com.masterofcode.android._10ideas.objects.Idea;
import com.masterofcode.android._10ideas.objects.Ideas;
import com.masterofcode.android._10ideas.screens.activities.DashboardActivity;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class PublicIdeasFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.public_ideas_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateTitle(getView(), R.string.my_ideas);

        Calendar cal = Calendar.getInstance();
        Integer day = cal.get(Calendar.DATE);
        Integer year = cal.get(Calendar.YEAR);

        Date date = new Date();
        String month = new SimpleDateFormat("MMMM").format(date);

        TextView dayTxt = (TextView) getView().findViewById(R.id.day);
        dayTxt.setText(day.toString());

        TextView monthYear = (TextView) getView().findViewById(R.id.month_year);
        monthYear.setText(String.format(getString(R.string.month_year), month, year));

        loadData();
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Ideas ideas = IdeasApi.getIdeas(RestClient.BASE_PUBLIC_IDEAS);
                    Vector items = ideas.getItems();

                    updateUi(items);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateUi(final Vector items) {
        final BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Object getItem(int position) {
                return items.elementAt(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {
                if (view == null) {
                    view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item, viewGroup, false);
                }

                Idea item = (Idea) getItem(position);
                TextView text = (TextView) view.findViewById(R.id.text1);
                text.setText(item.getEssential());
                return view;
            }
        };

        final ListView list = (ListView) getView().findViewById(R.id.list);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list.setAdapter(adapter);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Idea idea = (Idea) adapterView.getItemAtPosition(position);
                ViewIdeaFragment fragment = ViewIdeaFragment.newInstance(idea.getId());
                ((DashboardActivity) getActivity()).replaceFragment(fragment);
            }
        });

        ImageButton refresh = (ImageButton) getView().findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
    }

}
