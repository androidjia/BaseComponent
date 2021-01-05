package com.jjs.zero.baseviewlibrary;

import com.jjs.zero.baseviewlibrary.databinding.ViewBaseLoadingBinding;


/**
 * @Author: jiajunshuai
 * @CreateTime: 2020-01-06
 * @Details: <加载框>
 */
public class LoadingFragment extends BaseDialogFragment<ViewBaseLoadingBinding> {

//    private AVLoadingIndicatorView loadingView;

    @Override
    protected int layoutResId() {
        return R.layout.view_base_loading;
    }

    @Override
    protected void initData() {
        viewBinding.avloading.show();
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (getDialog()!=null){
//            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //无标题
//            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            getDialog().setCanceledOnTouchOutside(false);
////            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
////                @Override
////                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//////                    if (i == KeyEvent.KEYCODE_BACK) {
//////                        return true;
//////                    }
////                    return false;
////                }
////            });
//        }
//        View view  = inflater.inflate(R.layout.view_base_loading,container,false);
//        loadingView = view.findViewById(R.id.avloading);
//        loadingView.show();
//        return view;
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Window mWindow = getDialog().getWindow();
//        WindowManager.LayoutParams mLayoutParams = mWindow.getAttributes();
//        mLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        mLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        mLayoutParams.gravity = Gravity.CENTER;
//        mWindow.setAttributes(mLayoutParams);
//    }


    public void hide() {
        viewBinding.avloading.hide();
        dismissAllowingStateLoss();
    }
}
