package com.jjs.zero.viewlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/16
 * @Details: <功能描述>
 */
public class ClipView extends View {


    public ClipView(Context context) {
        this(context,null);
    }

    public ClipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.clipRect(50,50,150,150, Region.Op.DIFFERENCE);

    }
}
