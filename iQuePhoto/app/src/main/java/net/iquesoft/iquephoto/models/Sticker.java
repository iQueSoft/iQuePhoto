package net.iquesoft.iquephoto.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

public class Sticker implements Parcelable {
    @DrawableRes
    private int mImage;

    public Sticker(@DrawableRes int image) {
        mImage = image;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }

    protected Sticker(Parcel in) {
        mImage = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mImage);
    }

    public static final Parcelable.Creator<Sticker> CREATOR = new Parcelable.Creator<Sticker>() {
        @Override
        public Sticker createFromParcel(Parcel in) {
            return new Sticker(in);
        }

        @Override
        public Sticker[] newArray(int size) {
            return new Sticker[size];
        }
    };
}