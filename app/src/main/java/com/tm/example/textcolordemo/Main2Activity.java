package com.tm.example.textcolordemo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = Main2Activity.class.getSimpleName();

    private LinearLayout ll_cover;
    private View view_tailor;


    private Bitmap bitmapCover = null;
    private Bitmap bitmapTailor = null;

    private Paint paint;

    private Canvas mCanvas;

    private Rect rectTailor;
    private Rect rectCover;

    private float mDownY;
    private float mLastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        initView();
    }

//    private void initView() {
//
//        mCanvas = new Canvas();
//        rectCover = new Rect();
//        rectTailor = new Rect();
//        ll_cover = (LinearLayout) findViewById(R.id.ll_cover);
//        bitmapCover = BitmapUtils.convertViewToBitmap(ll_cover);
//
//        view_tailor = (View) findViewById(R.id.view_tailor);
//        bitmapTailor = BitmapUtils.convertViewToBitmap(view_tailor);
//
//        ll_cover.getDrawingRect(rectCover);
//
//        paint = new Paint();
//
//
//        if (rectCover == null) {
//            Log.e(TAG, "------------->>" + null);
//            return;
//        }
//
//        mCanvas.drawText("090809", 0, 0, paint);
//        mCanvas.drawBitmap(bitmapCover, 0, 0, paint);
////        mCanvas.clipRect(rectTailor);
////        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
////        mCanvas.drawBitmap(bitmapCover, rectTailor, rectTailor, paint);
//
//        ll_cover.setVisibility(View.INVISIBLE);
//        view_tailor.setVisibility(View.INVISIBLE);
//
//
//
//        view_tailor.setOnTouchListener(new View.OnTouchListener() {
//            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.e(TAG, "====ACTION_DOWN");
//                        rectTailor = view_tailor.getClipBounds();
//
//                        rectTailor.set(view_tailor.getLeft(), view_tailor.getTop(), view_tailor.getRight(), view_tailor.getBottom());
//                        mDownY = motionEvent.getY();
//                        mLastY = mDownY;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.e(TAG, "====ACTION_MOVE");
//                        float y = motionEvent.getY();
//                        int deltaY = (int) (y - mDownY);
//                        mLastY = y;
//                        view_tailor.layout(rectTailor.left, rectTailor.top + deltaY,
//                                rectTailor.right, rectTailor.bottom + deltaY);
//                        mCanvas.drawBitmap(bitmapCover, rectCover, rectTailor, paint);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_CANCEL:
//                        break;
//                }
//                return true;
//            }
//        });
//
//
//
//    }


}
