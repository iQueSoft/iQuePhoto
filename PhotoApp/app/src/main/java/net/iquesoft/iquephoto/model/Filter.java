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
            new Filter(R.string.filter_original, R.drawable.image_1),

            new Filter(R.string.filter_morning, R.drawable.image_1, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    0, 0, 0, 1, 0}))),

            new Filter(R.string.filter_purple, R.drawable.image_1, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1, 0, 0, 0.2f, 0,
                    0, 1, 0, 0, 0,
                    0, 0, 1, 0.2f, 0,
                    0, 0, 0, 1, 0}))),

            new Filter(R.string.filter_goldie, R.drawable.image_1, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    0.55f, 0, 0, 0.34f, 0,
                    0, 0.25f, 0, 0.2f, 0,
                    0, 0, 0.11f, 0, 0,
                    0, 0, 0, 2.85f, 0}))),

            new Filter(R.string.filter_neon, R.drawable.image_1, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    1, 0.3f, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0, 0.3f, 0, 0, 0,
                    0, 0, 0, 1, 0}))),

            new Filter(R.string.filter_grayscale, R.drawable.image_1, new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                    0.3f, 0.75f, 0.11f, 0, 1,
                    0.3f, 0.75f, 0.11f, 0, 1,
                    0.3f, 0.75f, 0.11f, 0, 1,
                    0, 0, 0, 1.3f, 0}))),
    };

    public Filter() {

    }

    public Filter(int title, int image) {
        this.title = title;
        this.image = image;
    }

    public Filter(int title, int image, ColorMatrixColorFilter matrixColorFilter) {
        this.title = title;
        this.image = image;
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
}
