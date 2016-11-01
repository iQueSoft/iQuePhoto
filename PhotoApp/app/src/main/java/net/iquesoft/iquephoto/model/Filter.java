package net.iquesoft.iquephoto.model;

import android.graphics.ColorMatrix;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

public class Filter {

    private String mTitle;

    private boolean mIsSelected;

    private ColorMatrix mColorMatrix;

    public static List<Filter> getFiltersList() {
        return Arrays.asList(filters);
    }

    private static Filter[] filters = {
            new Filter("F01", new ColorMatrix(new float[]{
                    2, -1, 0, 0, 0,
                    -1, 2, 0, 0, 0,
                    0, -1, 2, 0, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F02", new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0.50f, 0, 1, 0, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F03", new ColorMatrix(new float[]{
                    1.5f, 0, 0, 0, -40,
                    0, 1.5f, 0, 0, -40,
                    0, 0, 1.5f, 0, -40,
                    0, 0, 0, 1, 0})),

            new Filter("F04", new ColorMatrix(new float[]{
                    1, 0, 0, 0.2f, 0,
                    0, 1, 0, 0, 0,
                    0, 0, 1, 0.2f, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F05", new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 1.65f, 0, 0, 0,
                    0, 0, 0, 0.5f, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F06", new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 0.5f, 0, 0, 0,
                    0, 0, 0, 0.5f, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F07", new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    0, 0, 1, 1, 0,
                    0, 0, 0, 1, 0})),

            new Filter("BW01", new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    1, 0, 0, 0, 0,
                    1, 0, 0, 0, 0,
                    0, 0, 0, 1, 0})),

            new Filter("BW02", new ColorMatrix(new float[]{
                    0, 1, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0, 0, 0, 1, 0})),

            new Filter("BW03", new ColorMatrix(new float[]{
                    0, 0, 1, 0, 0,
                    0, 0, 1, 0, 0,
                    0, 0, 1, 0, 0,
                    0, 0, 0, 1, 0})),
    };

    private Filter() {

    }

    private Filter(String title, ColorMatrix colorMatrix) {
        mTitle = title;
        mColorMatrix = colorMatrix;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }

    public ColorMatrix getColorMatrix() {
        return mColorMatrix;
    }

    public void setColorMatrix(ColorMatrix colorMatrix) {
        this.mColorMatrix = colorMatrix;
    }
}
