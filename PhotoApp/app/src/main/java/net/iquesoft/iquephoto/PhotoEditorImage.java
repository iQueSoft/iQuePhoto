package net.iquesoft.iquephoto;

import android.graphics.Bitmap;

public class PhotoEditorImage {
    private float maxScaleSize = 4;
    private int id;
    private Bitmap bitmap;
    private float left = 0;
    private float top = 0;
    private float scale = 1f;
    private int width;
    private int height;
    private int maxLeft;
    private int maxTop;
    private int minLeft;
    private int minTop;
    private boolean centerHorizontal;
    private boolean centerVertical;
    private float roationDegrees;
    private String path;
    private float minScaleSize = 1;

    public float getMinScaleSize() {
        return minScaleSize;
    }

    public void setMinScaleSize(float minScaleSize) {
        this.minScaleSize = minScaleSize;
    }

    public void setMaxScaleSize(float maxScaleSize) {
        this.maxScaleSize = maxScaleSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public float getLeft() {

        if (left < getMinLeft()) {
            left = getMinLeft();
        } else if (left > getMaxLeft()) {
            left = getMaxLeft();
        }
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public int getMaxLeft() {
        return maxLeft;
    }

    public void setMaxLeft(int maxLeft) {
        if (maxLeft >= 0)
            this.maxLeft = maxLeft;
    }

    public int getMaxTop() {
        return maxTop;
    }

    public void setMaxTop(int maxTop) {
        if (maxTop >= 0)
            this.maxTop = maxTop;

    }

    public float getTop() {
        if (top < getMinTop()) {
            top = getMinTop();
        } else if (top > getMaxTop()) {
            top = getMaxTop();
        }
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        if (scale >= minScaleSize && scale < maxScaleSize) {
            this.scale = scale;
        } else if (scale < minScaleSize) {
            this.scale = minScaleSize;
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        if (bitmap != null) {
            width = bitmap.getWidth();
            height = bitmap.getHeight();
        } else {
            width = 0;
            height = 0;
        }
    }

    public boolean isCenterHorizontal() {
        return centerHorizontal;
    }

    public void setCenterHorizontal(boolean centerHorizontal) {
        this.centerHorizontal = centerHorizontal;
    }

    public boolean isCenterVertical() {
        return centerVertical;
    }

    public void setCenterVertical(boolean centerVertical) {
        this.centerVertical = centerVertical;
    }

    public float getRoationDegrees() {
        return roationDegrees;
    }

    public void setRoationDegrees(float roationDegrees) {
        this.roationDegrees = roationDegrees;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GiantSquaresImage{");
        sb.append("id=").append(id);
        sb.append(", bitmap=").append(bitmap);
        sb.append(", left=").append(left);
        sb.append(", top=").append(top);
        sb.append(", scale=").append(scale);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", maxLeft=").append(maxLeft);
        sb.append(", maxTop=").append(maxTop);
        sb.append(", centerHorizontal=").append(centerHorizontal);
        sb.append(", centerVertical=").append(centerVertical);
        sb.append(", roationDegrees=").append(roationDegrees);
        sb.append('}');
        return sb.toString();
    }

    public int getMinLeft() {
        return minLeft;
    }

    public void setMinLeft(int minLeft) {
        this.minLeft = minLeft;
    }

    public int getMinTop() {
        return minTop;
    }

    public void setMinTop(int minTop) {
        this.minTop = minTop;
    }

    public void setFreeScale(float scale) {
        if (scale > 0 && scale < maxScaleSize) {
            this.scale = scale;
        }
    }

    public float getMaxScale() {
        return maxScaleSize;
    }
}
