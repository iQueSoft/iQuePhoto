package net.iquesoft.iquephoto.model;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

/**
 * @author Sergey Belenkiy
 *         Filter model class how works with {@link ColorMatrixColorFilter}.
 */
public class Filter {

    private int title;
    private int image;
    private boolean selected;
    private ColorMatrixColorFilter matrixColorFilter;

    /**
     * @return list with all editor tools for adapter usage;
     */
    public static List<Filter> getFiltersList() {
        return Arrays.asList(filters);
    }

    /**
     * Array with all filters;
     */
    public static Filter[] filters = {

            new Filter(R.string.filter_original, R.drawable.original),

            new Filter(R.string.filter_sunny, R.drawable.sunny, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1.5f, 0, 0, 0, 0,
                    0, 1.5f, 0, 0, 0,
                    0, 0, 1.5f, 0, 0,
                    0, 0, 0, 1, 0}))),

            new Filter(R.string.filter_darken, R.drawable.darknen, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    0.5f, 0, 0, 0, 0,
                    0, 0.5f, 0, 0, 0,
                    0, 0, 0.5f, 0, 0,
                    0, 0, 0, 1, 0}))),

            // Todo: Change filters name
            new Filter(R.string.filter_darken, R.drawable.darknen, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    2, -1, 0, 0, 0,
                    -1, 2, 0, 0, 0,
                    0, -1, 2, 0, 0,
                    0, 0, 0, 1, 0}))),

            new Filter(R.string.filter_purple, R.drawable.purple, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0.50f, 0, 1, 0, 0,
                    0, 0, 0, 1, 0}))),

            new Filter(R.string.filter_darken, R.drawable.darknen, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1.5f, 0, 0, 0, -40,
                    0, 1.5f, 0, 0, -40,
                    0, 0, 1.5f, 0, -40,
                    0, 0, 0, 1, 0}))),

            /*new Filter(R.string.filter_polaroid, R.drawable.purple, false, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1.438f, -0.062f, -0.062f, 0, 0,
                    -0.122f, 1.378f, -0.122f, 0, 0,
                    -0.016f, -0.016f, 1.483f, 0, 0,
                    -0.03f, 0.05f, -0.02f, 0, 1}))),*/

            new Filter(R.string.filter_purple, R.drawable.purple, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1, 0, 0, 0.2f, 0,
                    0, 1, 0, 0, 0,
                    0, 0, 1, 0.2f, 0,
                    0, 0, 0, 1, 0}))),



            /*new Filter(R.string.filter_morning, R.drawable.buldog, false, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    0, 0, 0, 1, 0}))),*/

            new Filter(R.string.filter_lime, R.drawable.lime, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 1.65f, 0, 0, 0,
                    0, 0, 0, 0.5f, 0,
                    0, 0, 0, 1, 0}))),

            /*new Filter(R.string.filter_goldie, R.drawable.buldog, false, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1, 0.3f, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0, 0.3f, 0, 0, 0,
                    0, 0, 0, 1, 0}))),*/

            new Filter(R.string.filter_peachy, R.drawable.peachy, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 0.5f, 0, 0, 0,
                    0, 0, 0, 0.5f, 0,
                    0, 0, 0, 1, 0}))),

            new Filter(R.string.filter_magenta, R.drawable.magenta, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    0, 0, 1, 1, 0,
                    0, 0, 0, 1, 0}))),

            /*new Filter(R.string.filter_sepia, R.drawable.buldog, false, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    0.393f, 0.769f, 0.189f, 0, 0,
                    0.349f, 0.686f, 0.168f, 0, 0,
                    0.272f, 0.0534f, 0.131f, 0, 0,
                    0, 0, 0, 1, 0}))),*/

            new Filter(R.string.filter_light_gray, R.drawable.light_grey, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    1, 0, 0, 0, 0,
                    1, 0, 0, 0, 0,
                    0, 0, 0, 1, 0}))),

            new Filter(R.string.filter_mid_grey, R.drawable.mid_grey, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    0, 1, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0, 0, 0, 1, 0}))),

            new Filter(R.string.filter_dark_grey, R.drawable.dark_grey, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    0, 0, 1, 0, 0,
                    0, 0, 1, 0, 0,
                    0, 0, 1, 0, 0,
                    0, 0, 0, 1, 0}))),
    };

    public Filter() {

    }

    public Filter(int title, int image) {
        this.title = title;
        this.image = image;
        this.selected = selected;
    }

    public Filter(int title, int image, ColorMatrixColorFilter matrixColorFilter) {
        this.title = title;
        this.image = image;
        this.selected = selected;
        this.matrixColorFilter = matrixColorFilter;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ColorMatrixColorFilter getMatrixColorFilter() {
        return matrixColorFilter;
    }

    public void setMatrixColorFilter(ColorMatrixColorFilter matrixColorFilter) {

        this.matrixColorFilter = matrixColorFilter;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
