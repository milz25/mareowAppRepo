package com.mareow.recaptchademo.DataModels;

public class GridItem extends Item {
    private String mPosition;
    private String mSubTitle;


    public GridItem(String title, String position,String mImagePath) {
        super(title,mImagePath);
        mPosition = position;
    }

    public String getSubTitle() {
        return mSubTitle;
    }

    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
    }

    public String getPosition() {
        return mPosition;
    }

    @Override
    public int getItemType() {
        return GRID_ITEM_TYPE;
    }



}
