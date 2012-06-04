package com.masterofcode.android._10ideas;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import com.masterofcode.android.R;
import com.masterofcode.android._10ideas.helpers.Util;

public class BaseFragment extends Fragment {

    protected void updateTitle(View view, int strId) {
        TextView title = (TextView) view.findViewById(R.id.title);
        if (title != null) {
            title.setText(strId);
        }
    }

    public boolean haveInternet() {
        return Util.haveInternet(getActivity());
    }

    public void showErrorDialog() {
        new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setTitle("Sign in failed")
                .setMessage("Something went wrong %)")
                .setPositiveButton("Ok", null).show();
    }

    public void showNoInternetDialog() {
        new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setTitle("Sign in failed")
                .setMessage("Check you connection")
                .setPositiveButton("Ok", null).show();
    }
}
