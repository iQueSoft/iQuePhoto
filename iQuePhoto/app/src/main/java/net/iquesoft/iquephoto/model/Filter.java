package net.iquesoft.iquephoto.model;

import android.graphics.ColorMatrix;

import java.util.Arrays;
import java.util.List;

public class Filter {

    private String mTitle;

    private ColorMatrix mColorMatrix;

    public static List<Filter> getFiltersList() {
        return Arrays.asList(filters);
    }

    private static Filter[] filters = {

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

            new Filter("F01", new ColorMatrix(new float[]{
                    2, -1, 0, 0, 0,
                    -1, 2, 0, 0, 0,
                    0, -1, 2, 0, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F12", new ColorMatrix(new float[]{ // TechColor mMatrix
                    1.9125277891456083f, -0.8545344976951645f, -0.09155508482755585f, 0, 11.793603434377337f,
                    -0.3087833385928097f, 1.7658908555458428f, -0.10601743074722245f, 0, -70.35205161461398f,
                    -0.231103377548616f, -0.7501899197440212f, 1.847597816108189f, 0, 30.950940869491138f,
                    0, 0, 0, 1, 0})),

            new Filter("F04", new ColorMatrix(new float[]{
                    1, 0, 0, 0.2f, 0,
                    0, 1, 0, 0, 0,
                    0, 0, 1, 0.2f, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F05", new ColorMatrix(new float[]{
                    1, 0, 0, 0, 0,
                    0, 1.25f, 0, 0, 0,
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

            new Filter("F08", new ColorMatrix(new float[]{ // Polaroid mMatrix
                    1.438f, -0.062f, -0.062f, 0, 0,
                    -0.122f, 1.378f, -0.122f, 0, 0,
                    -0.016f, -0.016f, 1.483f, 0, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F09", new ColorMatrix(new float[]{ // CodaChrome mMatrix
                    1.1285582396593525f, -0.3967382283601348f, -0.03992559172921793f, 0, 63.72958762196502f,
                    -0.16404339962244616f, 1.0835251566291304f, -0.05498805115633132f, 0, 24.732407896706203f,
                    -0.16786010706155763f, -0.5603416277695248f, 1.6014850761964943f, 0, 35.62982807460946f,
                    0, 0, 0, 1, 0})),
            
            new Filter("F10", new ColorMatrix(new float[]{ // LSD mMatrix
                    2, -0.4f, 0.5f, 0, 0,
                    -0.5f, 2, -0.4f, 0, 0,
                    -0.4f, -0.5f, 3, 0, 0,
                    0, 0, 0, 1, 0})),

            new Filter("F11", new ColorMatrix(new float[]{ // Vintage mMatrix
                    0.6279345635605994f, 0.3202183420819367f, -0.03965408211312453f, 0, 9.651285835294123f,
                    0.02578397704808868f, 0.6441188644374771f, 0.03259127616149294f, 0, 7.462829176470591f,
                    0.0466055556782719f, -0.0851232987247891f, 0.5241648018700465f, 0, 5.159190588235296f,
                    0, 0, 0, 1, 0})),

            new Filter("F13", new ColorMatrix(new float[]{ // Browni mMatrix
                    0.5997023498159715f, 0.34553243048391263f, -0.2708298674538042f, 0, 47.43192855600873f,
                    -0.037703249837783157f, 0.8609577587992641f, 0.15059552388459913f, 0, -36.96841498319127f,
                    0.24113635128153335f, -0.07441037908422492f, 0.44972182064877153f, 0, -7.562075277591283f,
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

    public ColorMatrix getColorMatrix() {
        return mColorMatrix;
    }
}
