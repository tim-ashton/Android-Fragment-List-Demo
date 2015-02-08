package com.timashton.fragmentlistdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/*
 * Created by tim on 1/02/15.
 */
public class ResultBottomFragment extends Fragment {

    private final String TAG = this.getClass().getName();

    private FragmentListAdapter mFragmentListAdapter;
    private ArrayList<ListItem> mItems;
    View mBottomListFragment;

    public ResultBottomFragment(){
    }

    /*
     * Create a new instance of ResultTopFragment.
     */
    public static ResultBottomFragment newInstance() {
        ResultBottomFragment f = new ResultBottomFragment();

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
        mBottomListFragment = inflater.inflate(R.layout.fragment_result_bottom_list, container, false);

        ScrollDetectingListView mFragmentListView =
                (com.timashton.fragmentlistdemo.ScrollDetectingListView) mBottomListFragment.findViewById(R.id.fragment_list);

        mItems = new ArrayList<>();
        mFragmentListAdapter = new FragmentListAdapter(getActivity(), mItems);
        mFragmentListView.setAdapter(mFragmentListAdapter);

        mFragmentListView.setOnDetectScrollListener(new ScrollDetectingListView.OnDetectScrollListener() {
            @Override
            public void onUpScrolling() {
                Log.i(TAG, "onUpScrolling()");
                mFragmentListAdapter.setScrollingUp(true);
                mFragmentListAdapter.setScrollingDown(false);
            }

            @Override
            public void onDownScrolling() {
                Log.i(TAG, "onDownScrolling()");
                mFragmentListAdapter.setScrollingUp(false);
                mFragmentListAdapter.setScrollingDown(true);
            }
        });

        return mBottomListFragment;
    }

    public void updateListView(String text) {
        Log.i(TAG, "updateListView(String text)");
        ListItem item = new ListItem(text);
        mItems.add(item);
        mFragmentListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "onPause()");
    }
}
