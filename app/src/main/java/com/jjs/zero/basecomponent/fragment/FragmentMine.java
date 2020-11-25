package com.jjs.zero.basecomponent.fragment;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jjs.zero.basecomponent.R;
import com.jjs.zero.basecomponent.databinding.FragmentMineBinding;
import com.jjs.zero.basecomponent.dialog.PickImgSelectDialogFragment;
import com.jjs.zero.baseviewlibrary.BaseFragment;
import com.jjs.zero.utilslibrary.utils.PermissionRequestUtils;
import com.jjs.zero.utilslibrary.utils.PermissionUtils;

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

        viewBinding.ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PermissionRequestUtils.getInstance().checkPermission((Activity) mContext, PermissionUtils.ABS_CAMERA)) {
                    PermissionRequestUtils.getInstance().requestPermission(FragmentMine.this, new PermissionRequestUtils.OnPermissionResultListener() {
                        @Override
                        public void OnSuccessListener() {
                            new PickImgSelectDialogFragment().addPickImgListener(
                                    new PickImgSelectDialogFragment.OnPickImgListener() {
                                        @Override
                                        public void onPickImgPath(String path) {
                                            Log.i("zero=========","拍照回调=============mPath:"+path);
                                        }
                                    }
                            ).show(getFragmentManager(),"");
                        }

                        @Override
                        public void OnFailListener() {

                        }
                    },PermissionUtils.ABS_CAMERA);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionRequestUtils.getInstance().onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

}
