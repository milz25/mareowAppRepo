package com.mareow.recaptchademo.DataModels;

public class HeaderItem extends Item{

    public HeaderItem(String title,String imagepath) {
        super(title,imagepath);
    }

    @Override
    public int getItemType() {
        return HEADER_ITEM_TYPE;
    }
}
