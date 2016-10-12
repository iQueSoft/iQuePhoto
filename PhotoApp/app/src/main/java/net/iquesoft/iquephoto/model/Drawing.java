package net.iquesoft.iquephoto.model;

import android.graphics.Paint;
import android.graphics.Path;

public class Drawing {
    private Paint paint;
    private Path path;

    public Drawing(Paint paint, Path path) {
        this.paint = paint;
        this.path = path;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
