package com.udacity.gradle.builditbigger.paid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.artamonov.jokes.Jokes;
import com.udacity.gradle.builditbigger.GoogleEndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Button button = root.findViewById(R.id.btn_tellJoke);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jokes jokes = new Jokes();
                String wizardJoke = jokes.getJoke();
                new GoogleEndpointsAsyncTask().execute(new Pair<Context, String>(getActivity(), wizardJoke));
            }
        });
        return root;
    }

}
