package com.timashton.fragmentlistdemo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class DemoTaskFragment extends Fragment {

    private static final String TAG = DemoTaskFragment.class.getName();

    private Handler mHandler;
    private TaskCallbacks mCallbacks;
    private DemoThread mThread;


    public DemoTaskFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle)");
        setRetainInstance(true);

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                mCallbacks.addItemToListFragment(msg.obj.toString());
            }
        };
    }

    @Override
    public void onAttach(Activity activity) {
        Log.i(TAG, "onAttach(Activity)");
        super.onAttach(activity);
        if (!(activity instanceof TaskCallbacks)) {
            throw new IllegalStateException("Activity must implement the TaskCallbacks interface.");
        }

        mCallbacks = (TaskCallbacks) activity;
    }

    /**
     * Set the callback to null so not to leak the
     */
    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach()");
        mCallbacks = null;
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "onPause()");

        if((mThread != null) && !mThread.mFinished){
            mThread.onPause();
        }
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume()");
        super.onResume();

        if((mThread != null) && !mThread.mFinished){
            mThread.onResume();
        }
    }


    /*
    Interface to be implemented where this fragment is attached.
     */
    static interface TaskCallbacks {
        void addItemToListFragment(String text);
        void taskStarting();
        void taskStopping();
    }


    public void runAddItemsThread() {
        Log.i(TAG, "runAddItemsThread()");
        mCallbacks.taskStarting();
        mThread = new DemoThread();
        mThread.start();
    }


    class DemoThread extends Thread {

        private final String TAG = DemoThread.class.getName();

        private Object mPauseLock;
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
                    //mCallbacks.addItemToListFragment(countText);
                    mHandler.sendMessage(msg);
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

        /**
         * Call this on pause.
         */
        public void onPause() {
            Log.i(TAG, "onPause()");
            synchronized (mPauseLock) {
                mPaused = true;
            }
            mCallbacks.taskStopping();
        }

        /**
         * Call this on resume.
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



    /************************/
    /***** LOGS & STUFF *****/
    /************************/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated(Bundle)");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart()");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");

    }

}