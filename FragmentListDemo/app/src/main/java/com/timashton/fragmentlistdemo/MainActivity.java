package com.timashton.fragmentlistdemo;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;


public class MainActivity extends Activity implements StartFragment.AnimateFragmentListener{

    Handler mHandler;
    private ProgressDialog mProgressSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new StartFragment())
                    .commit();
        }

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                addItemToListFragment(msg.obj.toString());
            }
        };

    }


    @Override
    public void animateFragments() {

        ResultBottomFragment bottomFragment = ResultBottomFragment.newInstance();
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

        runAddItemsThread();
    }


    private void runAddItemsThread(){
        showSpinner();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //add 25 items to the list, 1 each 1/2 second
                    for (int i = 0; i < 25; i++) {
                        String countText = "Item: " + i + " added to list!";
                        Message msg = new Message();
                        msg.obj = countText;
                        mHandler.sendMessage(msg);
                        Thread.sleep(500);
                    }

                    hideSpinner();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void addItemToListFragment(String text) {

        ResultBottomFragment resultBottomFragment = (ResultBottomFragment) getFragmentManager()
                .findFragmentByTag("fragment_bottom");

        if (resultBottomFragment != null) {
            resultBottomFragment.updateListView(text);
        }
    }


    private void showSpinner(){
        if (mProgressSpinner == null) {
            mProgressSpinner = this.createProgressDialog(this);
            mProgressSpinner.show();
        }
        else {
            mProgressSpinner.show();
        }
    }

    private void hideSpinner(){
        if (mProgressSpinner == null) {
            return;
        }
        else {
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

}
