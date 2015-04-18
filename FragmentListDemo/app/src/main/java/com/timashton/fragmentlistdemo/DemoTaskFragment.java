package com.timashton.fragmentlistdemo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class DemoTaskFragment extends Fragment {

    private static final String TAG = DemoTaskFragment.class.getName();

    private static Handler mHandler;
    private static TaskCallbacks mCallbacks;
    private static DemoThread mThread;

    public DemoTaskFragment() {
    }

    /*
 * Interface to be implemented where this fragment is attached.
 *
 * This fragment is attached to the main activity.
 */
    static interface TaskCallbacks {

        void addItemToListFragment(String text);

        void taskStarting();

        void taskStopping();
    }

    @Override
    public void onAttach(Activity activity) {
        Log.i(TAG, "onAttach(Activity)");
        super.onAttach(activity);

        if (!(activity instanceof TaskCallbacks)) {
            throw new IllegalStateException("Activity must implement the TaskCallbacks interface.");
        }

        // get a reference
        mCallbacks = (TaskCallbacks) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle)");
        setRetainInstance(true);

        if (savedInstanceState == null) {
            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    mCallbacks.addItemToListFragment(msg.obj.toString());
                }
            };
        }
    }

    /*
     * Set the callback to null so not to accidentally leak the
     * Activity instance.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach()");
        mCallbacks = null;
    }

    /*
     * Call the onPause() of the DemoThread to pause execution
     * of the thread.
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");

        if ((mThread != null) && !mThread.mFinished) {
            mThread.onPause();
        }
    }

    /*
     * Call the onResume() of the DemoThread to resume execution
     * of the thread.
     */
    @Override
    public void onResume() {
        Log.i(TAG, "onResume()");
        super.onResume();

        if ((mThread != null) && !mThread.mFinished) {
            mThread.onResume();
        }
    }


    public void runAddItemsThread() {
        Log.i(TAG, "runAddItemsThread()");
        mCallbacks.taskStarting();
        mThread = new DemoThread();
        mThread.start();
    }


    /*
     * Class DemoThread
     *
     * Inner thread class to run the dummy thread by overriding the thread.run method.
     * Make static because there should only ever be one of these.
     *
     * Allows parent to pause and restart the thread by calling the appropriate methods.
     *
     */
    static class DemoThread extends Thread {

        private static final String TAG = DemoThread.class.getName();

        private final Object mPauseLock;
        private boolean mPaused;
        private boolean mFinished;

        public DemoThread() {
            Log.i(TAG, "DemoRunnable()");
            mPauseLock = new Object();
            mPaused = false;
            mFinished = false;
        }


        @Override
        public void run() {
            Log.i(TAG, "run()");

            try {
                int i = 0;
                while (!mFinished) {
                    String countText = "Item: " + i++ + " added to list!";
                    Message msg = new Message();
                    msg.obj = countText;
                    mHandler.sendMessage(msg);

                    //Add an item every 1/2 second
                    Thread.sleep(500);

                    if (i == 25) {
                        mFinished = true;
                    }

                    synchronized (mPauseLock) {
                        while (mPaused) {
                            try {
                                mPauseLock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mCallbacks.taskStopping();
        }

        /*
         * Called on fragment pause.
         *
         * Pauses the thread and removes the spinner.
         */
        public void onPause() {
            Log.i(TAG, "onPause()");
            synchronized (mPauseLock) {
                mPaused = true;
            }
            mCallbacks.taskStopping();
        }

        /*
         * Called on fragment resume.
         *
         * Resumes the thread and the spinner.
         */
        public void onResume() {
            Log.i(TAG, "onResume()");
            synchronized (mPauseLock) {
                mPaused = false;
                mPauseLock.notifyAll();
            }
            mCallbacks.taskStarting();
        }
    }
}