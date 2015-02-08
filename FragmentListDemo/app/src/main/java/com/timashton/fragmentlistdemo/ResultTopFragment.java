package com.timashton.fragmentlistdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 * Created by tim on 1/02/15.
 */
public class ResultTopFragment extends Fragment {

    private final String TAG = this.getClass().getName();

    public ResultTopFragment() {
    }

    /*
     * Factory for creating a new instance of ResultTopFragment.
     */
    public static ResultTopFragment newInstance() {
        ResultTopFragment f = new ResultTopFragment();

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
        View rootView = inflater.inflate(R.layout.fragment_result_top, container, false);
        return rootView;
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "onPause()");
    }

}
