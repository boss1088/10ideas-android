package com.masterofcode.android._10ideas;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import com.masterofcode.android.R;

public class BaseFragment extends Fragment {

    protected void updateTitle(View view, int strId) {
        TextView title = (TextView) view.findViewById(R.id.title);
        if (title != null) {
            title.setText(strId);
        }
    }
}
