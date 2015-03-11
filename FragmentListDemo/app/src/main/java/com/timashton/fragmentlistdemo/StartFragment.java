package com.timashton.fragmentlistdemo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/*
 * Created by tim on 1/02/15.
 *
 */
public class StartFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = StartFragment.class.getName();

    private Button mStartButton;
    private StartDemoListener mStartDemoCallback;

    public StartFragment() {
    }

    /*
     * The StartDemoListener.startDemo() callback is implemented
     * in the MainActivity.
     */
    public interface StartDemoListener {
        public void startDemo();
    }

    /*
     * Start animating the fragments and adding things to the list as soon
     * as the button is clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.animate_button){
            Log.i(TAG, "Animate button clicked");
            mStartButton.setEnabled(false);
            mStartDemoCallback.startDemo();
        }
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
        View rootView = inflater.inflate(R.layout.fragment_start, container, false);

        mStartButton = (Button)rootView.findViewById(R.id.animate_button);
        mStartButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach()");

        // Throw exception if StartDemoListener is not implemented
        // where implementation is declared
        try {
            mStartDemoCallback = (StartDemoListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement StartDemoListener");
        }
    }


}
