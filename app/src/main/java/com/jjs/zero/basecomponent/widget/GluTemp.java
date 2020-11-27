package com.jjs.zero.basecomponent.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.jjs.zero.basecomponent.R;
import com.jjs.zero.utilslibrary.utils.DensityUtils;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/25
 * @Details: <血压计连续设置百分比要去除动画>
 */
public class GluTemp extends View {
    private int width;
    private int height;
    private ObjectAnimator animator;
    private int percentage = 0;//百分比
    private Bitmap drawBitmapBg;
    private Paint mPaint;

    private int marginLeft = 0;
    private int marginTop = 0;
    private int marginRight = 0;
    private int marginBottom = 0;
    private int paddingLeft = 0;
    private int paddingRight = 0;
    private int paddingTop = 0;
    private int paddingBottom = 0;
    public GluTemp(Context context) {
        this(context,null);
    }

    public GluTemp(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GluTemp(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        marginLeft = DensityUtils.dp2px(20f);
        marginTop = DensityUtils.dp2px(8f);
        marginRight = marginBottom = DensityUtils.dp2px(12f);
        paddingLeft = marginLeft + DensityUtils.dp2px(27f);
        paddingRight = marginRight + DensityUtils.dp2px(25f);
        paddingTop = marginTop + DensityUtils.dp2px(39f);
        paddingBottom = marginBottom + DensityUtils.dp2px(15f);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.parseColor("#E93A6A"));
        drawBitmapBg = BitmapFactory.decodeResource(getResources(), R.drawable.xyj);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = drawBitmapBg.getWidth()+marginLeft+marginRight;
        height = drawBitmapBg.getHeight()+marginTop+marginBottom;
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        width = getWidth();
//        height = getHeight();
//        if ((width-marginLeft-marginRight)<drawBitmapBg.getWidth()) width = drawBitmapBg.getWidth()+marginLeft+marginRight;
//        if ((height-marginTop-marginBottom)<drawBitmapBg.getHeight()) height = drawBitmapBg.getHeight()+marginTop+marginBottom;
//        Log.i("zero========","onDrawwidth:"+width+" height:"+height);
        RectF rectFBg = new RectF(marginLeft,marginTop,width-marginRight,height-marginBottom);
        canvas.drawBitmap(drawBitmapBg,null,rectFBg,mPaint);
        int rx = (width-paddingLeft-paddingRight-marginLeft-marginRight)/2;
//        Log.i("zero========","rx:"+rx);
        int top = paddingTop+marginTop;
        int percent = height-paddingBottom-marginBottom;
        int current =percent - (percent-top)*percentage/100;
//        Log.i("zero========","top:"+top+" percent:"+percent+" current:"+current);
        canvas.drawRoundRect(paddingLeft+marginLeft,current,width-paddingRight-marginRight,percent,rx,rx,mPaint);

    }

    private void startAnimation() {
        if (animator == null) {
            animator = ObjectAnimator.ofInt(this,"percentage",0,percentage);
            animator.addUpdateListener(valueAnimator -> {
                invalidate();
            });
            animator.setDuration(2000);
            animator.start();
        } else {
            if (!animator.isRunning()) {
                animator.start();
            }
        }
    }

    public void stopAnimation(){
        if (animator !=null && animator.isRunning()) {
            animator.end();
        }
    }

    public boolean isRunning() {
        if (animator==null) {
            return false;
        }
        return animator.isRunning();
    }

    public void setPercentage(int percentaget) {
        this.percentage = percentaget;
        startAnimation();
        invalidate();
    }

    public int getPercentage() {
        return percentage;
    }

    public void onDestory() {
        if (isRunning()) stopAnimation();
        if (drawBitmapBg != null) drawBitmapBg.recycle();
    }
}
