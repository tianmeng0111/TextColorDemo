package com.tm.example.textcolordemo.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.tm.example.textcolordemo.R;

/**
 * Created by Tian on 2016/7/14.
 */
public class TextSwitchButton extends View {

    private static final String TAG = TextSwitchButton.class.getSimpleName();

    private boolean canSwitch = false;

    private int mTouchSlop;

    private static final String TEXT_LEFT = "左页";
    private static final String TEXT_RIGHT = "右页";

    private Paint paintBackFill;
    private Paint paintText;
    private Paint paintTextCover;
    private Paint paintThumb;

    private RectF rectBack;
    private RectF rectThumb;
    private RectF rectSwitch;//边距是mPadding
    private RectF rectLeft;
    private RectF rectRight;

    private int backColor;

    private int textFloorColor;
    private int textCoverColor;

    private Path path;

    private float centerLeft;
    private float centerRight;
    private float mCenterThumb;
    private float mLastCenterThumb;
    private float mProgress = 0;
    private float mLastProgress;

    private float mDownX;
    private float mDownY;
    private float mLastX;

    private ValueAnimator slideAnimator;

    public enum CheckedState {
        Left,
        Right
    }

    private CheckedState mCheckState = CheckedState.Left;
    /**
     * 绘图属性
     */
    private int mWidthSize;
    private int mHeightSize;
    private String mStrLeft = TEXT_LEFT;
    private String mStrRight = TEXT_RIGHT;

    private float mPadding;
    private float mTextSize = 30;

    public TextSwitchButton(Context context) {
        super(context);
        init(context, null);
    }

    public TextSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextSwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TextSwitchButton);
            if (typedArray != null) {
                mPadding = typedArray.getDimension(R.styleable.TextSwitchButton_ThumbPadding, 4);
                backColor = typedArray.getColor(R.styleable.TextSwitchButton_BackColor, Color.WHITE);

                mStrLeft = typedArray.getString(R.styleable.TextSwitchButton_TextLeft);
                mStrRight = typedArray.getString(R.styleable.TextSwitchButton_TextRight);

                textFloorColor = typedArray.getColor(R.styleable.TextSwitchButton_TextColorFloor, Color.parseColor("#FF4081"));
                textCoverColor = typedArray.getColor(R.styleable.TextSwitchButton_TextColorCover, Color.WHITE);
                if (mStrLeft == null) {
                    mStrLeft = "";
                }
                if (mStrRight == null) {
                    mStrRight = "";
                }

                mTextSize = typedArray.getDimension(R.styleable.TextSwitchButton_TextSize, 20);
            }
        }

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        slideAnimator = ValueAnimator.ofFloat(mProgress, 1f).setDuration(200);
        slideAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLastCenterThumb = (float) slideAnimator.getAnimatedValue();
                rectThumb.offset(mLastCenterThumb - mCenterThumb, 0);
                Log.e(TAG, "mLastCenterThumb--->>" + mLastCenterThumb);
                mCenterThumb = mLastCenterThumb;
                invalidate();
            }
        });

        rectBack = new RectF();
        rectThumb = new RectF();
        rectSwitch = new RectF();
        rectLeft = new RectF();
        rectRight = new RectF();

        paintBackFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBackFill.setAntiAlias(true);
        paintBackFill.setColor(backColor);
        paintBackFill.setStyle(Paint.Style.FILL_AND_STROKE);

        paintThumb = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintThumb.setColor(textFloorColor);
        paintThumb.setAntiAlias(true);

        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(textFloorColor);
        paintText.setTextSize(mTextSize);
        paintText.setAntiAlias(true);
        paintText.setTextAlign(Paint.Align.CENTER);

        paintTextCover = new Paint();
        paintTextCover.setColor(textCoverColor);
        paintTextCover.setTextSize(mTextSize);
        paintTextCover.setAntiAlias(true);
        paintTextCover.setTextAlign(Paint.Align.CENTER);

        path = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        mHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Log.e(TAG, "mWidthSize----->>" + mWidthSize);
        Log.e(TAG, "widthMode----->>" + widthMode);

        if (widthMode == MeasureSpec.EXACTLY) {//如果给定宽高 或match_parent

        } else if (widthMode == MeasureSpec.AT_MOST) {//wrap_content
            mWidthSize = 200;//默认
        }

        if (heightMode == MeasureSpec.EXACTLY) {//如果给定宽高 或match_parent

        } else if (heightMode == MeasureSpec.AT_MOST) {//wrap_content
            mHeightSize = 50;//默认
        }

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        rectBack.set(0, 0, mWidthSize, mHeightSize);
        rectThumb.set(mPadding, mPadding, mWidthSize / 2 - mPadding, mHeightSize - mPadding);
        rectSwitch.set(mPadding, mPadding, mWidthSize - mPadding, mHeightSize - mPadding);
        rectLeft.set(mPadding, mPadding, mWidthSize / 2 - mPadding, mHeightSize - mPadding);
        rectRight.set(mWidthSize / 2 + mPadding, mPadding, mWidthSize - mPadding, mHeightSize - mPadding);
        centerLeft = rectLeft.centerX();
        centerRight = rectRight.centerX();
        mCenterThumb = rectThumb.centerX();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRoundRect(rectBack, mHeightSize / 2, mHeightSize / 2, paintBackFill);
        canvas.drawRoundRect(rectThumb, mHeightSize / 2, mHeightSize / 2, paintThumb);
//        canvas.restore();
//        canvas.save();
        Paint.FontMetricsInt fontMetricsInt = paintText.getFontMetricsInt();
        float baseline = (rectBack.top + rectBack.bottom - fontMetricsInt.top - fontMetricsInt.bottom) / 2;
        canvas.drawText(mStrLeft, centerLeft, baseline, paintText);
        canvas.drawText(mStrRight, centerRight, baseline, paintText);
//        canvas.restore();
//        canvas.save();
//        rectThumb.offset(mProgress * (centerRight - centerLeft), 0);
//        canvas.clipRect(rectThumb);
        path = new Path();
        path.addRoundRect(rectThumb, mHeightSize, mHeightSize, Path.Direction.CW);
        canvas.clipPath(path);
        canvas.drawText(mStrLeft, centerLeft, baseline, paintTextCover);
        canvas.drawText(mStrRight, centerRight, baseline, paintTextCover);
//        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                catchView();//
                mDownX = event.getX();
                mDownY = event.getY();
                mLastX = mDownX;
                setPressed(true);
                if (rectThumb.contains(mDownX, mDownY)) {//可以拖动thumb
                    canSwitch = true;
                } else {//点击处 不在Thumb范围内  所以不可拖动Thumb
                    canSwitch = false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float tinyX = x - mLastX;
                if (canSwitch) {
                    if (rectThumb.centerX() + tinyX <= centerRight
                            && rectThumb.centerX() + tinyX >= centerLeft) {
                        rectThumb.offset(tinyX, 0);
                    } else {//出现这种情况  检查
                        Log.e(TAG, "---------------");
                    }
                    mLastX = x;
                    invalidate();
                    if (rectLeft.contains(rectThumb)) {
                        setCheckedState(CheckedState.Left);
                    }
                    if (rectRight.contains(rectThumb)) {
                        setCheckedState(CheckedState.Right);
                    }
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                setPressed(false);
                float deltaX = mLastX - mDownX;
                mCenterThumb = rectThumb.centerX();
                mProgress = (mCenterThumb - centerLeft) / (centerRight - centerLeft);
                if (Math.abs(deltaX) > mTouchSlop) {
                    if (deltaX > 0) {
                        animCheck(CheckedState.Right);
                    }else {
                        animCheck(CheckedState.Left);
                    }
                } else if (Math.abs(deltaX) < mTouchSlop){
                    performClick();
                }
                break;

            default:
                break;
        }

        return true;
    }

    private void catchView() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override
    public boolean performClick() {
        if (rectRight.contains(mDownX, mDownY) && mCheckState == CheckedState.Left) {
            animCheck(CheckedState.Right);
        }
        if (rectLeft.contains(mDownX, mDownY) && mCheckState == CheckedState.Right) {
            animCheck(CheckedState.Left);
        }
        return super.performClick();
    }

    private void animCheck(CheckedState checkedState) {
        switch (checkedState) {
            case Right:
                slideAnimator.setFloatValues(mCenterThumb, centerRight);
                slideAnimator.start();
                setCheckedState(CheckedState.Right);
                break;
            case Left:
                slideAnimator.setFloatValues(mCenterThumb, centerLeft);
                slideAnimator.start();
                setCheckedState(CheckedState.Left);
                break;
        }

    }

    private void setCheckedState(CheckedState checkedState) {
        mCheckState = checkedState;
        if (listener != null) {
            listener.stateChangeListener(mCheckState);
        }
    }

    /**
     * 外部调用 修改选中项 并带有动画
     * @param checkedState
     */
    public void setChangeCheckedState(CheckedState checkedState) {
        animCheck(checkedState);
    }

    public CheckedState getCheckedState() {
        return  mCheckState;
    }

    public interface CheckedStateChangeListener {
        void stateChangeListener(CheckedState checkedState);
    }

    public CheckedStateChangeListener listener;

    public void setCheckedStateChangeListener(CheckedStateChangeListener listener) {
        this.listener = listener;
    }

}
