package net.iquesoft.iquephoto.models;

import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

public class ParcelablePaint implements Parcelable {
    private Paint mPaint;

    public ParcelablePaint(@NonNull Paint paint) {
        mPaint = paint;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setAlpha(@IntRange(from = 0, to = 255) int value) {
        mPaint.setAlpha(value);
    }

    protected ParcelablePaint(Parcel in) {
        mPaint = (Paint) in.readValue(Paint.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mPaint);
    }

    public static final Parcelable.Creator<ParcelablePaint> CREATOR = new Parcelable.Creator<ParcelablePaint>() {
        @Override
        public ParcelablePaint createFromParcel(Parcel in) {
            return new ParcelablePaint(in);
        }

        @Override
        public ParcelablePaint[] newArray(int size) {
            return new ParcelablePaint[size];
        }
    };
}