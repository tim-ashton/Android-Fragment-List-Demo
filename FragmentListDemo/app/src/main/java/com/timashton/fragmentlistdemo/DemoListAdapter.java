package com.timashton.fragmentlistdemo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/*
 * Created by tim on 17/01/15.
 */
public class DemoListAdapter extends BaseAdapter {

    private final String TAG = DemoListAdapter.class.getName();

    private Activity mContext;
    private List<DemoListItem> mList;
    private boolean mIsScrollingUp = false;
    private boolean mIsScrollingDown = false;


    public DemoListAdapter(Activity context, List<DemoListItem> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int pos) {
        return mList.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DemoListItem item = mList.get(position);
        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.mListItemText = (TextView) convertView.findViewById(R.id.list_item_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mListItemText.setText(item.getItemText());

        Animation animation;

        if (item.isNewItem() && !mIsScrollingUp && !mIsScrollingDown) {
            animation = AnimationUtils.loadAnimation(mContext, R.anim.flip_down);
            item.setIsNewItem(false);

            convertView.startAnimation(animation);
        }

        if (mIsScrollingUp || mIsScrollingDown) {
            animation = AnimationUtils.loadAnimation(mContext, R.anim.flip_down);

            convertView.startAnimation(animation);
        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView mListItemText;

    }

    public void setScrollingUp(boolean scrollingUp){
        mIsScrollingUp = scrollingUp;
    }

    public void setScrollingDown(boolean scrollingDown){
        mIsScrollingDown = scrollingDown;
    }

    public List<DemoListItem> getList(){
        return mList;
    }

}
