package com.jjs.zero.basecomponent.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/18
 * @Details: <翻转布局>
 */
public class FrameLayoutTo3d  extends FrameLayout {

    private View view1;
    private View view2;
    private ObjectAnimator objectAnimator ;
    private View currentView;
    private int currentIndex = 0;
    private boolean isChange = false;
    private FrameLayout.LayoutParams layoutParams;

    public FrameLayoutTo3d(@NonNull Context context) {
        this(context,null);
    }

    public FrameLayoutTo3d(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FrameLayoutTo3d(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

//        if (view1 != null) {
//            addView(view1,0,layoutParams);
//            currentView = view1;
//            currentIndex = 0;
//        }
//        if (view2 != null) {
//            addView(view2,1,layoutParams);
//            view2.setVisibility(GONE);
//        }

    }

    public void setView1(View view1) {
        this.view1 = view1;
        addView(view1,0,layoutParams);
        currentView = view1;
        currentIndex = 0;
        getObjectAnimator().setTarget(currentView);
    }

    public void setView2(View view2) {
        if (view1 == null) {
            throw  new RuntimeException("第一个view 为空!");
        }
        addView(view2,1,layoutParams);
        this.view2 = view2;
        getChildAt(1).setVisibility(GONE);
    }


    private ObjectAnimator getObjectAnimator(){

        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(currentView,"rotationX",0.0f,360.0f);
            objectAnimator.setDuration(2000);

            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                    Log.i("zero","value:"+(float)valueAnimator.getAnimatedValue()+"  isChange:"+isChange);
                    float va = (float)valueAnimator.getAnimatedValue();
                    if (!isChange && va>=180) {
                        isChange = true;
                        for (int i = 0; i < getChildCount(); i++) {
                            if (getChildAt(i).getVisibility() == View.VISIBLE) {
                                getChildAt(i).setVisibility(View.GONE);
                            } else {
//                                Log.i("zero","onAnimationUpdate:"+i);
                                getChildAt(i).setVisibility(View.VISIBLE);
                                currentView = getChildAt(i);
                                currentIndex = i;
                            }
                        }
                        objectAnimator.end();
                    }
                }
            });
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    isChange = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
        }
        return objectAnimator;
    }


    public void startRotation(){
        this.post(new Runnable() {
            @Override
            public void run() {
                getObjectAnimator().setTarget(currentView);
                getObjectAnimator().start();
            }
        });
    }

    public void endRotaion() {
        if (objectAnimator != null && objectAnimator.isRunning()) objectAnimator.end();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }


    public void onDestroy(){
        if (objectAnimator !=null && objectAnimator.isRunning()) objectAnimator.cancel();
        objectAnimator = null;
    }

}
