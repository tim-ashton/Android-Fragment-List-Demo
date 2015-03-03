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

    private Button mAnimateButton;
    private AnimateFragmentListener mAnimateFragmentCallback;

    public StartFragment() {
    }

    /*
     * The AnimateFragmentListener.animateFragments() callback is implemented
     * in the MainActivity.
     */
    public interface AnimateFragmentListener {
        public void animateFragments();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.animate_button){
            Log.i(TAG, "Animate button clicked");
            mAnimateButton.setEnabled(false);
            mAnimateFragmentCallback.animateFragments();
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

        mAnimateButton = (Button)rootView.findViewById(R.id.animate_button);
        mAnimateButton.setOnClickListener(this);
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
        Log.i(TAG, "onCreateView()");

        // Throw exception if AnimateFragmentListener is not implemented
        // where implementation is declared
        try {
            mAnimateFragmentCallback = (AnimateFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AnimateFragmentListener");
        }
    }


}
