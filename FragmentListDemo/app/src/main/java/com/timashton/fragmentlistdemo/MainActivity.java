package com.timashton.fragmentlistdemo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;


public class MainActivity extends Activity implements StartFragment.AnimateFragmentListener
        , DemoTaskFragment.TaskCallbacks {

    private final static String TAG = MainActivity.class.getName();
    private static final String TAG_DEMO_TASK_FRAGMENT = "demo_task_fragment";

    private ProgressDialog mProgressSpinner;
    private DemoTaskFragment mDemoTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();

        if (savedInstanceState == null) {
            fm.beginTransaction()
                    .add(R.id.container, new StartFragment())
                    .commit();
        }

        mDemoTaskFragment = (DemoTaskFragment) fm.findFragmentByTag(TAG_DEMO_TASK_FRAGMENT);

        // If the Fragment is non-null, then it is being retained
        // over a configuration change.
        if (mDemoTaskFragment == null) {
            mDemoTaskFragment = new DemoTaskFragment();
            fm.beginTransaction().add(mDemoTaskFragment, TAG_DEMO_TASK_FRAGMENT).commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onResume() {
        super.onPause();
        Log.i(TAG, "onResume()");
    }


    @Override
    public void animateFragments() {
        Log.i(TAG, "animateFragments()");

        ResultListFragment bottomFragment = ResultListFragment.newInstance();
        ResultTopFragment topFragment = ResultTopFragment.newInstance();

        FragmentTransaction ft = getFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_in_left,
                        R.animator.slide_out_top,
                        R.animator.slide_in_bottom,
                        R.animator.slide_out_right);

        ft.replace(R.id.container, topFragment, "fragment_top");
        ft.add(R.id.container, bottomFragment, "fragment_bottom");
        ft.addToBackStack(null);
        ft.commit();

        mDemoTaskFragment.runAddItemsThread();
    }

    public void showSpinner() {
        Log.i(TAG, "showSpinner()");
        if (mProgressSpinner == null) {
            mProgressSpinner = this.createProgressDialog(this);
            mProgressSpinner.show();
        } else {
            mProgressSpinner.show();
        }
    }

    public void hideSpinner() {
        Log.i(TAG, "hideSpinner()");
        if (mProgressSpinner == null) {
            return;
        } else {
            mProgressSpinner.dismiss();
        }
    }

    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_bar);
        // dialog.setMessage(Message);
        return dialog;
    }

    @Override
    public void addItemToListFragment(String text) {
        Log.i(TAG, "addItemToListFragment(String text): " + text);

        //TODO - Move this to ..
        ResultListFragment resultListFragment = (ResultListFragment) getFragmentManager()
                .findFragmentByTag("fragment_bottom");

        if (resultListFragment != null) {
            resultListFragment.updateListView(text);
        }
    }

    @Override
    public void taskStarting() {
        showSpinner();
    }

    @Override
    public void taskStopping() {
        hideSpinner();
    }
}
