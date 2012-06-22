package com.masterofcode.android._10ideas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.masterofcode.android.R;
import com.masterofcode.android._10ideas.helpers.Util;

public class BaseFragment extends Fragment {

    private ProgressDialog progressDialog = null;
    private boolean showProgressDialog;

    protected void updateTitle(View view, int strId, boolean showActions) {
        TextView title = (TextView) view.findViewById(R.id.title);
        if (title != null) {
            title.setText(strId);
        }

        if (showActions) {
            ImageButton btnDate = (ImageButton) view.findViewById(R.id.btn_date);
            btnDate.setVisibility(View.VISIBLE);
        }
    }

    public boolean haveInternet() {
        return Util.haveInternet(getActivity());
    }

    public void showErrorDialog(final String error) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setCancelable(false)
                        .setTitle("Error!")
                        .setMessage(error)
                        .setPositiveButton("Ok", null).show();
            }
        });
    }

    public void showErrorDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setCancelable(false)
                        .setTitle("Sign in failed")
                        .setMessage("Something went wrong %)")
                        .setPositiveButton("Ok", null).show();
            }
        });
    }

    public void showComingSoonDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setCancelable(false)
                        .setTitle("Coming soon")
                        .setMessage("Sorry. This feature not available now.")
                        .setPositiveButton("Ok", null).show();
            }
        });
    }

    public void showNoInternetDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setCancelable(false)
                        .setTitle("No Connection")
                        .setMessage("Sorry, server is unreachable. Please check you connection and/or try again.")
                        .setPositiveButton("Ok", null).show();
            }
        });
    }

    public void showProgressDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = ProgressDialog.show(getActivity(), "Loading", "loading", false, false);
            }
        });
    }

    public void dissmissProgressDialog() {
        if (progressDialog != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });
            showProgressDialog = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            showProgressDialog = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (progressDialog != null && showProgressDialog) {
            progressDialog.show();
        }
    }
}
