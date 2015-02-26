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

    private static final String TAG = ResultBottomFragment.class.getName();
    private static final String ITEMS_LIST_TAG = "list_tag";

    private DemoListAdapter mDemoListAdapter;
    private ArrayList<DemoListItem> mItems;
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
        mBottomListFragment = inflater.inflate(R.layout.fragment_result_list, container, false);

        ScrollDetectingListView fragmentListView =
                (com.timashton.fragmentlistdemo.ScrollDetectingListView) mBottomListFragment.findViewById(R.id.fragment_list);

        if (savedInstanceState != null) {
            mItems = savedInstanceState.getParcelableArrayList(ITEMS_LIST_TAG);
        }
        else{
            mItems = new ArrayList<>();
        }

        mDemoListAdapter = new DemoListAdapter(getActivity(), mItems);
        fragmentListView.setAdapter(mDemoListAdapter);

        fragmentListView.setOnDetectScrollListener(new ScrollDetectingListView.OnDetectScrollListener() {
            @Override
            public void onUpScrolling() {
                Log.i(TAG, "onUpScrolling()");
                mDemoListAdapter.setScrollingUp(true);
                mDemoListAdapter.setScrollingDown(false);
            }

            @Override
            public void onDownScrolling() {
                Log.i(TAG, "onDownScrolling()");
                mDemoListAdapter.setScrollingUp(false);
                mDemoListAdapter.setScrollingDown(true);
            }
        });

        return mBottomListFragment;
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);

        ArrayList<DemoListItem> bundledListItems = (ArrayList)mDemoListAdapter.getList();
        savedState.putParcelableArrayList(ITEMS_LIST_TAG, bundledListItems);
    }


    public void updateListView(String text) {
        Log.i(TAG, "updateListView(String text)");
        DemoListItem item = new DemoListItem(text);
        mItems.add(item);
        mDemoListAdapter.notifyDataSetChanged();
    }
}
