package com.jjs.zero.basecomponent;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jjs.zero.basecomponent.adapter.AdapterFragment;
import com.jjs.zero.basecomponent.adapter.AdapterLazFragment;
import com.jjs.zero.basecomponent.databinding.ActivityMainBinding;
import com.jjs.zero.basecomponent.fragment.FragmentData;
import com.jjs.zero.basecomponent.fragment.FragmentHome;
import com.jjs.zero.basecomponent.fragment.FragmentMine;
import com.jjs.zero.basecomponent.fragment.FragmentService;
import com.jjs.zero.basecomponent.model.MainViewModel;
import com.jjs.zero.basecomponent.model.ViewModelMain2;
import com.jjs.zero.baseviewlibrary.BaseActivity;
import com.jjs.zero.baseviewlibrary.BaseFragment;
import com.jjs.zero.baseviewlibrary.commonmodel.CommonViewModelFactory;
import com.jjs.zero.servicelibrary.TestActivity;
import com.jjs.zero.utilslibrary.utils.PermissionRequestUtils;
import com.jjs.zero.utilslibrary.utils.PermissionUtils;
import com.jjs.zero.utilslibrary.utils.StatusBarUtils;
import com.jjs.zero.viewlibrary.ViewBaseActivity;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private String[]    tab_names = new String[]{"读心", "数据", "服务", "我的"};
    private int[]       tab_img = new int[]{R.drawable.selector_tabbar_home,R.drawable.selector_tabbar_data,R.drawable.selector_tabbar_service,R.drawable.selector_tabbar_mine};

    private int progress = 0;
    private List<BaseFragment> fragmentList = new ArrayList<>();
//    private AdapterFragment adapterFragment;
    private AdapterLazFragment adapterLazFragment;
    private MainViewModel mainViewModel;

    @Override
    public int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mainViewModel = createViewModel(MainViewModel.class);
        mainViewModel.setText("年后干嘛");

        mainViewModel.getText().observe(this, s -> {
            Log.i("zero","数据改变:"+mainViewModel.getText().getValue());
        });
        getIvBack().setVisibility(View.GONE);
        fragmentList.add(new FragmentHome());
        fragmentList.add(new FragmentData());
        fragmentList.add(new FragmentService());
        fragmentList.add(new FragmentMine());

        adapterLazFragment = new AdapterLazFragment(getSupportFragmentManager(),fragmentList);
        viewBinding.viewPager.setAdapter(adapterLazFragment);
        viewBinding.viewPager.setOffscreenPageLimit(2);
        viewBinding.tabsLayout.setupWithViewPager(viewBinding.viewPager);
        for (int i=0;i<4;i++){
            View inflateHome = getLayoutInflater().inflate(R.layout.tab_template, null);
            ((TextView) inflateHome.findViewById(R.id.tab_text)).setText(tab_names[i]);
            ((ImageView)inflateHome.findViewById(R.id.tab_icon)).setImageResource(tab_img[i]);
            viewBinding.tabsLayout.getTabAt(i).setCustomView(inflateHome);
        }

//        adapterFragment = new AdapterFragment(this,fragmentList);
//        viewBinding.viewPager.setAdapter(adapterFragment);
        //        viewBinding.viewPager.setPageTransformer(new SimplePageTransform());

//        viewBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
////                viewBinding.tabsLayout.getTabAt(position).select();
//                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewBinding.viewPager,"rotationX",0f,360f);
//                objectAnimator.setDuration(200);
//                objectAnimator.start();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                super.onPageScrollStateChanged(state);
//            }
//        });
//        viewBinding.viewPager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
//
//            }
//        });




//        new TabLayoutMediator(viewBinding.tabsLayout, viewBinding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                viewBinding.viewPager.setCurrentItem(position);
//                tab.setText(tab_names[position]);
//                tab.setIcon(tab_img[position]);
//            }
//        }).attach();
//        viewBinding.tabsLayout.setScrollPosition(0,0,true);
//        viewBinding.viewPager.setCurrentItem(0);
//        默认显示最后一个页面,待优化


//        NotificationUtils.getInstance(this).createNotification("这是标题","jisdfjlksjfklsdjflksjdflksjfklsjfdlksdjflksjflksdjlksdfd士大夫撒旦法发达的第三方士大夫fsdfsdfsdff",
//                NotificationUtils.NotificationStatus.LARGE,MainActivity2.class,1);


//        int noId = 100;
//        NotificationCompat.Builder builder = NotificationUtils.getInstance(this).getProgressBuilder("下载标题","下载内容",100);

        viewBinding.tv.setOnClickListener(view -> {
//            NotificationUtils.getInstance(this).createNotification("这是第二个标题","gengduododsfdklasdjfsdjfklsdjfklsjf",
//                    NotificationUtils.NotificationStatus.LARGE,MainActivity2.class,2);


            startActivity(new Intent(mContext,MainActivity2.class));
            //创建进度条通知
//            NotificationUtils.getInstance(this).createProgressNotification(builder,noId,100,progress);


//            progress += 10;
//            NotificationUtils.getInstance(this).createCustomNotification(this,"biaoti","benbds");
//            Log.i("zero","点击了"+progress);
        });

        viewBinding.tvService.setOnClickListener(view -> {
            startActivity(new Intent(this, TestActivity.class));
        });

        viewBinding.tvView.setOnClickListener(view -> {
            startActivity(new Intent(this, ViewBaseActivity.class));
        });


        if (PermissionRequestUtils.getInstance().checkPermission(this, PermissionUtils.ABS_CAMERA)) {
            PermissionRequestUtils.getInstance().requestPermission(this, new PermissionRequestUtils.OnPermissionResultListener() {
                @Override
                public void OnSuccessListener() {
                }

                @Override
                public void OnFailListener() {
                }
            },PermissionUtils.ABS_CAMERA);
        }

//          状态栏设置
        StatusBarUtils.setStatusBarColorDark(this,true);
//        StatusBarUtils.setStatusBarColor(this, Color.BLUE);
        setTitle("statusBar:"+StatusBarUtils.statusBarHeight(this)+"  title:"+StatusBarUtils.titleBarHeight(this));



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionRequestUtils.getInstance().onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    public class SimplePageTransform implements ViewPager2.PageTransformer {

        @Override
        public void transformPage(@NonNull View view, float position) {
            int width = view.getWidth();
            int pivotX = 0;
            if (position <= 1 && position > 0) {// right scrolling
                pivotX = 0;
            } else if (position == 0) {

            } else if (position < 0 && position >= -1) {// left scrolling
                pivotX = width;
            }
            //设置x轴的锚点
            view.setPivotX(pivotX);
            //设置绕Y轴旋转的角度
            view.setRotationX(90f * position);
        }
    }

}