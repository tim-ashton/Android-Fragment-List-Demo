package com.timashton.fragmentlistdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/*
 * Created by tim on 5/02/15.
 */
public class ScrollDetectingListView extends ListView {

    private static final String TAG = ScrollDetectingListView.class.getName();

    private OnScrollListener mOnScrollListener;
    private OnDetectScrollListener mOnDetectScrollListener;

    public ScrollDetectingListView(Context context) {
        super(context);
        setListeners();
    }

    public ScrollDetectingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setListeners();
    }

    public ScrollDetectingListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setListeners();
    }

    /*
     *private void setListeners()
     *
     * Assign the OnScrollListener and OnDetectScrollListener to scroll events of this
     * View.
      */
    private void setListeners() {
        super.setOnScrollListener(new OnScrollListener() {

            private int oldTop;
            private int oldFirstVisibleItem;

            /*
             * Callback method to be invoked while the list view or grid view is being scrolled.
             * If the view is being scrolled, this method will be called before the next frame
             * of the scroll is rendered. In particular, it will be called before any calls
             * to getView(int, View, ViewGroup).
             *
             * Override to call the OnScrollListener if implemented.
             */
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mOnScrollListener != null) {
                    mOnScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            /*
             * Callback method to be invoked when the list or grid has been scrolled.
             * This will be called after the scroll has completed
             *
             * Override to call the OnScrollListener if implemented.
             *
             * Override to call the OnDetectScrollListener if implemented.
             */
            @Override
            public void onScroll(
                    AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mOnScrollListener != null) {
                    mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
                if (mOnDetectScrollListener != null) {
                    onDetectedListScroll(view, firstVisibleItem);
                }
            }

            /*
             *
             */
            private void onDetectedListScroll(AbsListView absListView, int firstVisibleItem) {
                View view = absListView.getChildAt(0);
                int top = (view == null) ? 0 : view.getTop();

                if (firstVisibleItem == oldFirstVisibleItem) {
                    if (top > oldTop) {
                        mOnDetectScrollListener.onUpScrolling();
                    }
                    else if (top < oldTop) {
                        mOnDetectScrollListener.onDownScrolling();
                    }
                 }
                else {
                    if (firstVisibleItem < oldFirstVisibleItem) {
                        mOnDetectScrollListener.onUpScrolling();
                    }
                    else if (firstVisibleItem > oldFirstVisibleItem) {
                        mOnDetectScrollListener.onDownScrolling();
                    }
                }

                oldTop = top;
                oldFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    public void setOnDetectScrollListener(OnDetectScrollListener onDetectScrollListener) {
        mOnDetectScrollListener = onDetectScrollListener;
    }


    public interface OnDetectScrollListener {

        void onUpScrolling();

        void onDownScrolling();
    }

}
