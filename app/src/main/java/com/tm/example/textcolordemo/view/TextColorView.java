package com.tm.example.textcolordemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Tian on 2016/7/13.
 */
public class TextColorView extends View {
    private static final String TAG = TextColorView.class.getSimpleName();

    private static final int TEXT_SIZE = 60;

    private Paint mPaintFloor;
    private Paint mPaintOver;

    private Rect rectDec;

    private float mDownY;
    private float mLastY;


    public TextColorView(Context context) {
        super(context);
        init(context);
    }

    public TextColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaintFloor = new Paint();
        mPaintFloor.setTextSize(TEXT_SIZE);
        mPaintFloor.setColor(Color.BLACK);
        mPaintOver = new Paint();
        mPaintOver.setTextSize(TEXT_SIZE);
        mPaintOver.setColor(Color.WHITE);
        rectDec = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        Log.e(TAG, "==onLayout ---->>" + left + "---->>" + top + "---->>" + right + "---->>" + bottom);
        rectDec.set(left, top, right, top + TEXT_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.drawText("Hello World!!!!!", 0, 60, mPaintFloor);
        canvas.drawText("Hello World!!!!!", 0, 120, mPaintFloor);
        canvas.drawText("Hello World!!!!!", 0, 180, mPaintFloor);
        canvas.drawText("Hello World!!!!!", 0, 240, mPaintFloor);
        canvas.drawText("Hello World!!!!!", 0, 300, mPaintFloor);
        canvas.drawText("Hello World!!!!!", 0, 360, mPaintFloor);
        canvas.restore();

        canvas.save();
        canvas.clipRect(rectDec);
        canvas.drawText("Hello World!!!!!", 0, 60, mPaintOver);
        canvas.drawText("Hello World!!!!!", 0, 120, mPaintOver);
        canvas.drawText("Hello World!!!!!", 0, 180, mPaintOver);
        canvas.drawText("Hello World!!!!!", 0, 240, mPaintOver);
        canvas.drawText("Hello World!!!!!", 0, 300, mPaintOver);
        canvas.drawText("Hello World!!!!!", 0, 360, mPaintOver);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = event.getY();
                rectDec.set(rectDec.left, (int) mDownY, rectDec.right, (int) mDownY + TEXT_SIZE);
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = event.getY();
                rectDec.set(rectDec.left, (int) mLastY, rectDec.right, (int) mLastY + TEXT_SIZE);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        invalidate();
        return true;
    }

}
