package com.jjs.zero.basecomponent.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.jjs.zero.basecomponent.R;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/12/7
 * @Details: <动态设置圆角的TextView>
 */
public class TextViewPlus extends AppCompatTextView {

    private float radiusAll;
    private float leftTR;
    private float rightTR;
    private float rightBR;
    private float leftBR;
    private int strokeWidth;

    private @ColorInt int colorBg;
    private @ColorInt int strokeColor;

    public TextViewPlus(@NonNull Context context) {
        this(context,null);
    }

    public TextViewPlus(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextViewPlus(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
        radiusAll = array.getDimension(R.styleable.TextViewPlus_radiusAll,0f);
        leftTR = array.getDimension(R.styleable.TextViewPlus_leftTopRadius,0f);
        leftBR = array.getDimension(R.styleable.TextViewPlus_leftBottomRadius,0f);
        rightTR = array.getDimension(R.styleable.TextViewPlus_rightTopRadius,0f);
        rightBR = array.getDimension(R.styleable.TextViewPlus_rightBottomRadius,0f);
        colorBg = array.getColor(R.styleable.TextViewPlus_bgColor,Color.WHITE);
        strokeColor = array.getColor(R.styleable.TextViewPlus_strokeColor,Color.WHITE);
        strokeWidth = array.getDimensionPixelSize(R.styleable.TextViewPlus_strokeWidths,0);
        array.recycle();
        if (radiusAll>0) {
            setBg(radiusAll,radiusAll,radiusAll,radiusAll,colorBg,strokeColor,strokeWidth);
        } else if (leftTR>0 || rightTR>0 || rightBR>0 || leftBR>0 || strokeWidth>0){
            setBg(leftTR,rightTR,rightBR,leftBR,colorBg,strokeColor,strokeWidth);
        }

    }

    public void setRadius(@Dimension float radiusAll) {
        setRadiusWithStroke(radiusAll,0,0);
    }

    public void setRadiusWithStroke(float radiusAll,@ColorInt int strokeColor,int strokeWidth) {
        setRadiusWithStroke(radiusAll,radiusAll,radiusAll,radiusAll,strokeColor,strokeWidth);
    }

    public void setRadiusWithStroke(float leftTopRadius, float rightTopRadius, float rightBottomRadius,float leftBottomRadius,@ColorInt int strokeColor,int strokeWidth) {
        setBgRadiusWithStroke(leftTopRadius,rightTopRadius,rightBottomRadius,leftBottomRadius,Color.WHITE,strokeColor,strokeWidth);
    }

    public void setBgRadius(float radiusAll, @ColorInt int colorBg) {
        setBgRadius(radiusAll,radiusAll,radiusAll,radiusAll,colorBg);
    }
    public void setBgRadius(float leftTopRadius, float rightTopRadius, float rightBottomRadius,float leftBottomRadius, @ColorInt int colorBg) {
        setBg(leftTopRadius,rightTopRadius,rightBottomRadius,leftBottomRadius,colorBg,0,0);
    }

    public void setBgRadiusWithStroke(float radiusAll, @ColorInt int colorBg,@ColorInt int strokeColor,int strokeWidth) {
        setBgRadiusWithStroke(radiusAll,radiusAll,radiusAll,radiusAll,colorBg,strokeColor,strokeWidth);
    }

    public void setBgRadiusWithStroke(float leftTopRadius, float rightTopRadius, float rightBottomRadius,float leftBottomRadius, @ColorInt int colorBg,@ColorInt int strokeColor,int strokeWidth) {
        setBg(leftTopRadius,rightTopRadius,rightBottomRadius,leftBottomRadius,colorBg,strokeColor,strokeWidth);
    }

    /**
     *
     * @param leftTopRadius
     * @param rightTopRadius
     * @param rightBottomRadius
     * @param leftBottomRadius
     * @param colorBg               背景色
     * @param strokeColor           边框线颜色
     * @param strokeWidth           线宽
     */
    private void setBg(float leftTopRadius, float rightTopRadius, float rightBottomRadius,float leftBottomRadius,@ColorInt int colorBg,@ColorInt int strokeColor,int strokeWidth){
        this.leftTR = leftTopRadius;
        this.rightTR = rightTopRadius;
        this.rightBR = rightBottomRadius;
        this.leftBR = leftBottomRadius;
        this.colorBg = colorBg;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
        GradientDrawable gradientDrawable  = new GradientDrawable();
        gradientDrawable.setColor(colorBg);
//        if (isAllRadius) {
//            gradientDrawable.setCornerRadius(leftTopRadius);
//        } else {
        if (leftTopRadius >0 || rightTopRadius >0 || rightBottomRadius > 0 || leftBottomRadius > 0){
            float[] outRadii = new float[] { leftTopRadius, leftTopRadius,rightTopRadius, rightTopRadius,
                    rightBottomRadius,rightBottomRadius,leftBottomRadius,leftBottomRadius};
            gradientDrawable.setCornerRadii(outRadii);
        }

        if (strokeWidth>0) gradientDrawable.setStroke(strokeWidth,strokeColor);

        this.setBackground(gradientDrawable);
    }


}
