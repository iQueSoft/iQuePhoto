package net.iquesoft.iquephoto.mvp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private int mId;
    private String mPath;

    public Image(int id, String path) {
        mId = id;
        mPath = path;
    }

    public int getId() {
        return mId;
    }

    public String getPath() {
        return mPath;
    }


    protected Image(Parcel in) {
        mId = in.readInt();
        mPath = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mPath);
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}