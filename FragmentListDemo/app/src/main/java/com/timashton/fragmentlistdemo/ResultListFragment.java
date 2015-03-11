package com.timashton.fragmentlistdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;

/*
 * Created by tim on 1/02/15.
 */
public class ResultListFragment extends Fragment {

    private static final String TAG = ResultListFragment.class.getName();
    private static final String ITEMS_LIST_TAG = "list_tag";

    private DemoListAdapter mDemoListAdapter;
    private ArrayList<DemoListItem> mItems;
    private ListView mFragmentListView;
    private int mOldFirstVisibleItem;

    public ResultListFragment() {
    }

    /*
     * Create a new instance of ResultInfoFragment.
     */
    public static ResultListFragment newInstance() {
        return new ResultListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_result_list, container, false);

        mFragmentListView =
                (ListView) rootView.findViewById(R.id.fragment_list);

        if (savedInstanceState != null) {
            mItems = savedInstanceState.getParcelableArrayList(ITEMS_LIST_TAG);
        } else {
            mItems = new ArrayList<>();
        }

        mDemoListAdapter = new DemoListAdapter(getActivity(), mItems);
        mFragmentListView.setAdapter(mDemoListAdapter);


        mFragmentListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {

                final ListView lw = mFragmentListView;

                if (scrollState == 0)
                    Log.i(TAG, "scrolling stopped.");

                if (view.getId() == lw.getId()) {
                    final int currentFirstVisibleItem = lw.getFirstVisiblePosition();

                    if (currentFirstVisibleItem > mOldFirstVisibleItem) {
                        Log.i(TAG, "Scrolling down");
                        mDemoListAdapter.setScrollingUp(false);
                        mDemoListAdapter.setScrollingDown(true);
                    } else if (currentFirstVisibleItem < mOldFirstVisibleItem) {
                        Log.i(TAG, "Scrolling up.");
                        mDemoListAdapter.setScrollingUp(true);
                        mDemoListAdapter.setScrollingDown(false);
                    }

                    mOldFirstVisibleItem = currentFirstVisibleItem;
                }
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        ArrayList<DemoListItem> bundledListItems = (ArrayList) mDemoListAdapter.getList();
        savedState.putParcelableArrayList(ITEMS_LIST_TAG, bundledListItems);
    }


    public void updateListView(String text) {
        Log.i(TAG, "updateListView(String text)");
        DemoListItem item = new DemoListItem(text);
        mItems.add(item);
        mDemoListAdapter.notifyDataSetChanged();
    }

}
