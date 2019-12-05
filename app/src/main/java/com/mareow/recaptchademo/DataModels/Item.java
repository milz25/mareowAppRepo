package com.mareow.recaptchademo.DataModels;

public abstract class Item {

    public static final int HEADER_ITEM_TYPE = 0;
    public static final int GRID_ITEM_TYPE = 1;
    private String mItemTitle;
    private String mImagePath;

    public String getmImagePath() {
        return mImagePath;
    }

    public Item(String title,String imagePath) {
        mItemTitle = title;
        mImagePath=imagePath;
    }

    public String getItemTitle() {
        return mItemTitle;
    }

    public abstract int getItemType();

}
