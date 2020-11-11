package com.jjs.zero.viewlibrary;


import android.widget.Toast;

import com.jjs.zero.baseviewlibrary.BaseActivity;
import com.jjs.zero.viewlibrary.databinding.ActivityViewTwoBinding;

public class View2Activity extends BaseActivity<ActivityViewTwoBinding> {

    @Override
    public int layoutResId() {
        return R.layout.activity_view_two;
    }

    @Override
    protected void initData() {
        viewBinding.chronometer.setOnChronometerTickListener(chronometer ->{
            String time = chronometer.getText().toString();
            if(time.equals("00:00")){
                Toast.makeText(View2Activity.this,"时间到了~",Toast.LENGTH_SHORT).show();
            }
        });
        viewBinding.btnStart.setOnClickListener(view -> {
            viewBinding.chronometer.start();
        });
        viewBinding.btnEnd.setOnClickListener(view -> {
            viewBinding.chronometer.stop();
        });

    }
}