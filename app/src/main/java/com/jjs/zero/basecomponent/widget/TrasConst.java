package com.jjs.zero.basecomponent.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jjs.zero.utilslibrary.utils.DensityUtils;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/12/2
 * @Details: <功能描述>
 */
public class TrasConst extends View {

    private Paint paint;
    private Path path;
    private int count = 5;//分段
    private int current = 2;//选中段落
    private int width;
    private int height;
    private float transTopY = 0;
    public TrasConst(@NonNull Context context) {
        this(context,null);
    }

    public TrasConst(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TrasConst(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#00D46A"));
        paint.setStrokeWidth(2);
        path = new Path();
        transTopY = DensityUtils.dp2px(13);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
//        Log.i("zero=====","onDraw:"+width+" h:"+height);
        //9圆角
        path.moveTo(0,transTopY);
        float top = (float)(current-0.5)*(float) width/count;
        Log.i("zero=====","onDraw:"+width+" h:"+height+" top:"+top);
        path.lineTo(top-DensityUtils.dp2px(3),transTopY);
        path.lineTo(top,0);
        path.lineTo(top+DensityUtils.dp2px(3),transTopY);
        path.lineTo(width,transTopY);
        canvas.drawPath(path,paint);

    }
}
