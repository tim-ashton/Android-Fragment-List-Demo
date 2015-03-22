package com.timashton.fragmentlistdemo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;


public class MainActivity extends Activity implements StartFragment.StartDemoListener
        , DemoTaskFragment.TaskCallbacks {

    private static final String TAG = MainActivity.class.getName();
    private static final String TAG_DEMO_TASK_FRAGMENT = "demo_task_fragment";
    private static final String TAG_RESULT_INFO_FRAGMENT = "result_info_fragment";
    private static final String TAG_RESULT_LIST_FRAGMENT = "result_list_fragment";

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
        // Otherwise a new one is required.
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
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    /*
     * Release the progress spinner.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
        mProgressSpinner = null;
    }


    /* startDemo()
     *
     * Implementation of the callback defined in StartFragment.
     *
     * Create the two result Fragments and set the slide in/out animations in
     * the FragmentManager(). Then start the dummy thread on the TaskFragment.
     */
    @Override
    public void startDemo() {
        Log.i(TAG, "startDemo()");

        ResultListFragment bottomFragment = ResultListFragment.newInstance();
        ResultInfoFragment topFragment = ResultInfoFragment.newInstance();

        getFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_in_left,
                        R.animator.slide_out_top,
                        R.animator.slide_in_bottom,
                        R.animator.slide_out_right)
                .replace(R.id.container, topFragment, TAG_RESULT_INFO_FRAGMENT)
                .add(R.id.container, bottomFragment, TAG_RESULT_LIST_FRAGMENT)
                .addToBackStack(null)
                .commit();

        mDemoTaskFragment.runAddItemsThread();
    }

    /*
     * showSpinner()
     *
     * Show the spinner progress dialog.
     */
    public void showSpinner() {
        Log.i(TAG, "showSpinner()");
        if (mProgressSpinner == null) {
            mProgressSpinner = this.createProgressDialog(this);
            mProgressSpinner.show();
        } else {
            mProgressSpinner.show();
        }
    }

    /* hideSpinner()
     *
     * Hide the spinner progress dialog.
     */
    public void hideSpinner() {
        Log.i(TAG, "hideSpinner()");
        if (mProgressSpinner != null) {
            mProgressSpinner.dismiss();
        }
    }

    /* createProgressDialog(Context mContext)
     *
     * Create a custom progress dialog that runs while items are being
     * added to the list fragment.
     */
    public ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_bar);
        return dialog;
    }

    /* addItemToListFragment(String text)
     *
     * Implementation of the callback declared in the DemoTaskFragment.
     *
     * Takes a String parameter passed from the caller. The caller is
     * a Handler object in DemoTaskFragment.
     *
     */
    @Override
    public void addItemToListFragment(String text) {
        Log.i(TAG, "addItemToListFragment(String text): " + text);

        ResultListFragment resultListFragment = (ResultListFragment) getFragmentManager()
                .findFragmentByTag("fragment_bottom");

        if (resultListFragment != null) {
            resultListFragment.updateListView(text);
        }
    }

    /* taskStarting()
     *
     * Implementation of the taskStarting callback declared in the DemoTaskFragment.
     */
    @Override
    public void taskStarting() {
        showSpinner();
    }

    /* taskStopping()
     *
     * Implementation of the taskStopping callback declared in the DemoTaskFragment.
     */
    @Override
    public void taskStopping() {
        hideSpinner();
    }
}
