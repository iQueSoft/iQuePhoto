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

            new Filter("Original"),

            new Filter("F01", new ColorMatrix(new float[]{
                    1.5f, 0, 0, 0, 0,
                    0, 1.5f, 0, 0, 0,
                    0, 0, 1.5f, 0, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F02", new ColorMatrix(new float[]{
                    0.5f, 0, 0, 0, 0,
                    0, 0.5f, 0, 0, 0,
                    0, 0, 0.5f, 0, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F03", new ColorMatrix(new float[]{
                    2, -1, 0, 0, 0,
                    -1, 2, 0, 0, 0,
                    0, -1, 2, 0, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F04", new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0.50f, 0, 1, 0, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F05", new ColorMatrix(new float[]{
                    1.5f, 0, 0, 0, -40,
                    0, 1.5f, 0, 0, -40,
                    0, 0, 1.5f, 0, -40,
                    0, 0, 0, 1, 0})),

            /*new Filter(R.string.filter_polaroid, R.drawable.purple, false, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1.438f, -0.062f, -0.062f, 0, 0,
                    -0.122f, 1.378f, -0.122f, 0, 0,
                    -0.016f, -0.016f, 1.483f, 0, 0,
                    -0.03f, 0.05f, -0.02f, 0, 1}))),*/

            new Filter("F06", new ColorMatrix(new float[]{
                    1, 0, 0, 0.2f, 0,
                    0, 1, 0, 0, 0,
                    0, 0, 1, 0.2f, 0,
                    0, 0, 0, 1, 0})),



            /*new Filter(R.string.filter_morning, R.drawable.buldog, false, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    0, 0, 0, 1, 0}))),*/

            new Filter("F07", new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 1.65f, 0, 0, 0,
                    0, 0, 0, 0.5f, 0,
                    0, 0, 0, 1, 0})),

            /*new Filter(R.string.filter_goldie, R.drawable.buldog, false, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1, 0.3f, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0, 0.3f, 0, 0, 0,
                    0, 0, 0, 1, 0}))),*/

            new Filter("F08", new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 0.5f, 0, 0, 0,
                    0, 0, 0, 0.5f, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F09", new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    0, 0, 1, 1, 0,
                    0, 0, 0, 1, 0})),

            /*new Filter(R.string.filter_sepia, R.drawable.buldog, false, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    0.393f, 0.769f, 0.189f, 0, 0,
                    0.349f, 0.686f, 0.168f, 0, 0,
                    0.272f, 0.0534f, 0.131f, 0, 0,
                    0, 0, 0, 1, 0}))),*/

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

    private Filter(String title) {
        mTitle = title;
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
