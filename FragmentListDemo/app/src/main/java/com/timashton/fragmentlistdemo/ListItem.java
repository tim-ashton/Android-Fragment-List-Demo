package com.timashton.fragmentlistdemo;

/*
 * Created by tim on 4/02/15.
 */
public class ListItem {
    private boolean mNewItem;
    private String mText;

    public ListItem(String text){
        mNewItem = true;
        mText = text;
    }

    public ListItem(boolean newItem, String text){
        mNewItem = newItem;
        mText = text;
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
}
