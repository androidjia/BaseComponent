package com.jjs.zero.basecomponent;

import com.jjs.zero.basecomponent.databinding.ActivityMain2Binding;
import com.jjs.zero.basecomponent.model.ViewModelMain2;
import com.jjs.zero.baseviewlibrary.BaseActivity;

public class MainActivity2 extends BaseActivity<ActivityMain2Binding> {

    private ViewModelMain2 viewModel;

    @Override
    public int layoutResId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initData() {
        /**
         * fragments中数据共享使用getActivity()
         * 例如：new ViewModelProvider(getActivity(),new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(m);
         * ViewModel的onCleared():
         * 由于屏幕旋转导致的Activity重建，该方法不会被调用
         * activity销毁时会自动调用onCleared()方法
         *
         *使用ViewModel的时候，不能将任何含有Context引用的对象传入ViewModel，因为这可能会导致内存泄露。但如果你希望在ViewModel中使用Context怎么办呢？
         * 我们可以使用AndroidViewModel类，
         * 它继承自ViewModel，并且接收Application作为Context，既然是Application作为Context，
         * 也就意味着，我们能够明确它的生命周期和Application是一样的，这就不算是一个内存泄露了。
         *
         *
         * LiveData 和 MutableLiveData 的区别：
         * 1.MutableLiveData的父类是LiveData
         * 2.LiveData在实体类里可以通知指定某个字段的数据更新.
         * 3.MutableLiveData则是完全是整个实体类或者数据类型变化后才通知.不会细节到某个字段
         *
         *  postValue()
         * 　　可能你已经在上面看到几次调用此方法了。postValue的特性如下：
         * 　　1.此方法可以在其他线程中调用
         * 　　2.如果在主线程执行发布的任务之前多次调用此方法，则仅将分配最后一个值。
         * 　　3.如果同时调用 .postValue(“a”)和.setValue(“b”)，一定是值b被值a覆盖。
         *
         *  setValue()
         * 　　setValue()的特性如下：
         * 　　1.此方法只能在主线程里调用
         *
         *  getValue()
         * 　　返回当前值。 注意，在后台线程上调用此方法并不能保证将接收到最新的值。
         */

        viewModel = createViewModel(new ViewModelMain2(this));
        viewModel.init();



        viewBinding.setVm(viewModel);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}