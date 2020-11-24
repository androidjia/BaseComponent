package com.jjs.zero.viewlibrary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;

import com.jjs.zero.baseviewlibrary.BaseActivity;
import com.jjs.zero.utilslibrary.utils.StatusBarUtils;
import com.jjs.zero.viewlibrary.databinding.ActivityViewBinding;

public class ViewActivity extends BaseActivity<ActivityViewBinding>{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int layoutResId() {
        return R.layout.activity_view;
    }
    @Override
    protected void initData() {
//        ((ViewGroup)this.findViewById(android.R.id.content)).getChildAt(0).setBackgroundColor(Color.parseColor("#FF00FF"));
        /**
         * 1. viewgroup 中使用android:clipChildren="false" 不会裁剪子view
         * 2. text的drawable可以设置大小
         * 3. text SpannableString&SpannableStringBuilder定制文本
         *
         * 图片
         * ColorDrawable
         * NinePatchDrawable
         * ShapeDrawable
         * GradientDrawable
         * BitmapDrawable
         * InsertDrawable
         * ClipDrawable
         * RotateDrawable
         * AnimationDrawable
         * LayerDrawable
         * TransitionDrawable
         * LevelListDrawable
         * StateListDrawable
         *
         */

        Drawable[] drawables = viewBinding.tv12.getCompoundDrawables();
        drawables[0].setBounds(0,0,100,100);
        viewBinding.tv12.setCompoundDrawables(drawables[0],null,null,null);

        SpannableString span = new SpannableString("红色打电话斜体删除线绿色下划线图片:.");
        //1.设置背景色,setSpan时需要指定的flag,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)
        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //2.用超链接标记文本
        span.setSpan(new URLSpan("tel:4155551212"), 2, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //3.用样式标记文本（斜体）
        span.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //4.用删除线标记文本
        span.setSpan(new StrikethroughSpan(), 7, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //5.用下划线标记文本
        span.setSpan(new UnderlineSpan(), 10, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //6.用颜色标记
        span.setSpan(new ForegroundColorSpan(Color.GREEN), 10, 13,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //7.//获取Drawable资源
        Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        //8.创建ImageSpan,然后用ImageSpan来替换文本
        ImageSpan imgspan = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        span.setSpan(imgspan, 18, 19, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        viewBinding.tv12.setText(span);
        viewBinding.tv12.setSelected(false);
        viewBinding.tv12.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext,"长按点击",Toast.LENGTH_SHORT).show();

                return false;
            }
        });
        viewBinding.tv12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBinding.tv12.setSelected(true);
                startActivity(new Intent(mContext,View2Activity.class));
            }
        });

        //开启向上按钮，manifest中有父activity的配置 androidx 貌似没有效果
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        StatusBarUtils.setStatusBarColorDark(this,true);
//        mToolbar.setBackgroundColor(Color.BLUE);
//        setRootViewMarginTop(20);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}