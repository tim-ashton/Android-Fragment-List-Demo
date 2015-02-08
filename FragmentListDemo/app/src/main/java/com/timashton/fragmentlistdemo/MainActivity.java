package com.timashton.fragmentlistdemo;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;


public class MainActivity extends Activity implements StartFragment.AnimateFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new StartFragment())
                    .commit();
        }
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

        ft.replace(R.id.container, topFragment, "fragment");
        ft.add(R.id.container, bottomFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}
