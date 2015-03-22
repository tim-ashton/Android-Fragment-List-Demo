package com.timashton.fragmentlistdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 * Created by Tim Ashton on 1/02/15.
 */
public class ResultInfoFragment extends Fragment {

    private static final String TAG = ResultInfoFragment.class.getName();

    public ResultInfoFragment() {
    }

    /*
     * Factory for creating a new instance of ResultInfoFragment.
     */
    public static ResultInfoFragment newInstance() {
        ResultInfoFragment f = new ResultInfoFragment();

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_result_info, container, false);
        return rootView;
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "onPause()");
    }

}
