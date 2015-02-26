package com.timashton.fragmentlistdemo;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/*
 * Created by tim on 4/02/15.
 */
public class DemoListItem implements Parcelable {

    private static final String TAG = DemoListItem.class.getName();
    private static final String NEW_ITEM_TAG = "new_item";
    private static final String TEXT_TAG = "item_text";

    private boolean mNewItem;
    private String mText;

    public DemoListItem(String text){
        mNewItem = true;
        mText = text;
    }

    public DemoListItem(boolean newItem, String text){
        mNewItem = newItem;
        mText = text;
    }

    private DemoListItem(Parcel in) {
        Bundle bundle = in.readBundle();
        mNewItem = bundle.getBoolean(NEW_ITEM_TAG);
        mText = bundle.getString(TEXT_TAG);
    }

    public boolean isNewItem(){
        return mNewItem;
    }

    public String getItemText(){
        return mText;
    }

    public void setIsNewItem(boolean newItem){
        mNewItem = newItem;
    }

    public void setItemText(String text){
        mText = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(NEW_ITEM_TAG, mNewItem);
        bundle.putString(TEXT_TAG, mText);
        dest.writeBundle(bundle);
    }

    public static final Parcelable.Creator<DemoListItem> CREATOR
            = new Parcelable.Creator<DemoListItem>() {
        public DemoListItem createFromParcel(Parcel in) {
            return new DemoListItem(in);
        }

        public DemoListItem[] newArray(int size) {
            return new DemoListItem[size];
        }
    };
}
