package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

public class ShowAllMachineViewHolder extends RecyclerView.ViewHolder {

    public AppCompatImageView machineImage;
    public AppCompatTextView machineName;
    public AppCompatTextView mMFG;
    public AppCompatTextView mModel;
    public AppCompatTextView mfgYear;
    public AppCompatTextView locateAt;
    public AppCompatTextView mAvailability;
    public AppCompatImageButton mFavoarite;
    public AppCompatButton mRating;
    public AppCompatImageButton mVerified;

    public CardView btnTile;
    public ShowAllMachineViewHolder(@NonNull View itemView) {
        super(itemView);

        machineImage=(AppCompatImageView)itemView.findViewById(R.id.renter_sub_within_sub_image);
        machineName=(AppCompatTextView)itemView.findViewById(R.id.renter_sub_within_sub_text);

        mMFG=(AppCompatTextView)itemView.findViewById(R.id.renter_sub_within_sub_make);
        mModel=(AppCompatTextView)itemView.findViewById(R.id.renter_sub_within_sub_model);
        mfgYear=(AppCompatTextView)itemView.findViewById(R.id.renter_sub_within_sub_manuyear);
        locateAt=(AppCompatTextView)itemView.findViewById(R.id.renter_sub_within_sub_curentlocation);
        mAvailability=(AppCompatTextView)itemView.findViewById(R.id.renter_sub_within_sub_availability);
        mFavoarite=(AppCompatImageButton)itemView.findViewById(R.id.renter_sub_within_sub_favorite);
        mRating=(AppCompatButton)itemView.findViewById(R.id.renter_sub_within_sub_rating);
        mVerified=(AppCompatImageButton)itemView.findViewById(R.id.renter_sub_within_sub_verified);


        btnTile=(CardView)itemView.findViewById(R.id.renter_sub_within_sub_tile);
    }
}
