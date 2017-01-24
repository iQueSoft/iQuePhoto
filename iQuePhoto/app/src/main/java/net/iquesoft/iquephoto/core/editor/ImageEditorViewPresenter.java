package net.iquesoft.iquephoto.core.editor;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.MotionEvent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.core.editor.model.Drawing;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

@InjectViewState
public class ImageEditorViewPresenter extends MvpPresenter<ImageEditorViewView> {
    private float mLastX;
    private float mLastY;

    private Paint mDrawingPaint;
    private Path mDrawingPath = new Path();

    private List<Drawing> mDrawings = new ArrayList<>();

    private PublishSubject<MotionEvent> mTouchSubject = PublishSubject.create();
    
    public ImageEditorViewPresenter() {
        Observable<MotionEvent> touchObservable = mTouchSubject.asObservable();
        initDrawingPaint();
        initMotionEventObservables(touchObservable);
    }

    public void viewTouched(MotionEvent event) {
        mTouchSubject.onNext(event);
    }

    private void initDrawingPaint() {
        mDrawingPaint = new Paint();
        mDrawingPaint.setStyle(Paint.Style.STROKE);
        mDrawingPaint.setColor(Color.BLUE);
        mDrawingPaint.setStrokeWidth(5);
    }

    private void initMotionEventObservables(Observable<MotionEvent> touchObservable) {
        initActionDownObservable(touchObservable);
        initActionMoveObservable(touchObservable);
        initActionUpObservable(touchObservable);
    }

    private void initActionDownObservable(Observable<MotionEvent> touchObservable) {
        Observable<MotionEvent> actionDownObservable =
                touchObservable.filter(event -> event.getActionMasked() == MotionEvent.ACTION_DOWN);
        actionDownObservable.subscribe(event -> {
            Log.i("Drawing", "Brush down");
            mLastX = event.getX();
            mLastY = event.getY();

            mDrawingPath.reset();

            mDrawingPath.moveTo(mLastX, mLastY);

            getViewState().brushDown(mDrawingPath);
        });
    }

    private void initActionMoveObservable(Observable<MotionEvent> touchObservable) {
        Observable<MotionEvent> actionMoveObservable =
                touchObservable.filter(event -> event.getActionMasked() == MotionEvent.ACTION_MOVE);
        actionMoveObservable.subscribe(event -> {
            Log.i("Drawing", "Brush move");

            float dX = event.getX() + mLastX;
            float dY = event.getY() + mLastY;

            mDrawingPath.quadTo(mLastX, mLastY, dX / 2, dY / 2);

            mLastX = event.getX();
            mLastY = event.getY();

            getViewState().brushMove(mDrawingPath);
        });
    }

    private void initActionUpObservable(Observable<MotionEvent> touchObservable) {
        Observable<MotionEvent> actionUpObservable =
                touchObservable.filter(event -> event.getActionMasked() == MotionEvent.ACTION_UP);
        actionUpObservable.subscribe(event -> {
            Log.i("Drawing", "Brush up");

            mDrawingPath.lineTo(mLastX, mLastY);
            mDrawings.add(new Drawing(new Paint(mDrawingPaint), new Path(mDrawingPath), null));

            mDrawingPath.reset();

            getViewState().brushUp(mDrawings);
        });
    }
}