package com.jjs.zero.basecomponent.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.core.content.res.TypedArrayUtils;

import com.jjs.zero.basecomponent.R;
import com.jjs.zero.basecomponent.databinding.FragmentMineBinding;
import com.jjs.zero.basecomponent.dialog.PickImgSelectDialogFragment;
import com.jjs.zero.baseviewlibrary.BaseFragment;
import com.jjs.zero.utilslibrary.utils.PermissionRequestUtils;

import java.util.List;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/18
 * @Details: <功能描述>
 */
public class FragmentMine extends BaseFragment<FragmentMineBinding> {
    @Override
    protected int layoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData() {
        PropertyValuesHolder valueHolder_1 = PropertyValuesHolder.ofFloat(
                "scaleX", 1f, 0.7f);
        PropertyValuesHolder valuesHolder_2 = PropertyValuesHolder.ofFloat(
                "scaleY", 1f, 0.7f);

        PropertyValuesHolder valueHolder_3 = PropertyValuesHolder.ofFloat(
                "scaleX", 0.7f, 1f);
        PropertyValuesHolder valuesHolder_4 = PropertyValuesHolder.ofFloat(
                "scaleY", 0.7f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(viewBinding.ivHeader,valueHolder_1,valuesHolder_2);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(viewBinding.ivHeader,valueHolder_3,valuesHolder_4);
        animatorSet.playSequentially(objectAnimator1,objectAnimator2);
        animatorSet.setDuration(200);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.i("zero","动画结束");
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        viewBinding.ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                animatorSet.start();
                Log.i("zero","click:"+isFastClick());
//                CrashReport.testJavaCrash();


                if (PermissionRequestUtils.get().checkPermission((Activity) mContext, PermissionRequestUtils.ABS_CAMERA)) {
                    PermissionRequestUtils.get().requestPermission(FragmentMine.this, new PermissionRequestUtils.OnPermissionResultListener() {
                        @Override
                        public void OnSuccessListener() {
                            new PickImgSelectDialogFragment().addPickImgListener(path -> {
                                Log.i("zero=========","拍照回调=============mPath:"+path);
                                    }
                            ).show(getFragmentManager(),"");
                        }

                        @Override
                        public void OnFailListener(List<String> permissions) {
                            Log.i("zero=========","拍照回调=============mPath:"+permissions.size());
                        }
                    },PermissionRequestUtils.ABS_CAMERA);
                } else {
                    new PickImgSelectDialogFragment().addPickImgListener(path -> {
                                Log.i("zero=========","拍照回调=============mPath:"+path);
                            }
                    ).show(getFragmentManager(),"");
                }
            }
        });


;
//        viewBinding.tvPlus.setRadius(10);
//        viewBinding.tvPlus.setRadiusWithStroke(10,Color.BLUE,4);
        viewBinding.tvPlus.setBgRadiusWithStroke(20,Color.RED,0,4);
        viewBinding.tvPlus.setText("你好");
        viewBinding.tvPlus.setShadowLayer(10,20,10,Color.RED);

    }
    private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("zero=========","onRequestPermissionsResult=============:"+permissions.length+"     grantResults:"+grantResults.length);
        PermissionRequestUtils.get().onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

}
