package com.jjs.zero.basecomponent.dialog;

import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;

import com.jjs.zero.basecomponent.R;
import com.jjs.zero.basecomponent.databinding.DialogFragmentPickImgSelectBinding;
import com.jjs.zero.baseviewlibrary.BaseDialogFragment;
import com.jjs.zero.utilslibrary.utils.CameraUtils;

import static android.app.Activity.RESULT_OK;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/25
 * @Details: <照片选择和拍照>
 */
public class PickImgSelectDialogFragment extends BaseDialogFragment<DialogFragmentPickImgSelectBinding> implements View.OnClickListener {
    public PickImgSelectDialogFragment() {
        super(Gravity.BOTTOM,true);
    }
    private OnPickImgClickListener onPickImgClickListener;



    public interface OnPickImgClickListener{
        void onClickPickerListener();//选择图片
        void onClickCameraListener();//拍照
    }
    private OnPickImgListener onPickImgListener;
    public interface OnPickImgListener {
        void onPickImgPath(String path);
//        void onPickImgUri(Uri path);
    }

    public PickImgSelectDialogFragment addPickImgListener(OnPickImgClickListener onPickImgClickListener) {
        this.onPickImgClickListener = onPickImgClickListener;
        return this;
    }


    public PickImgSelectDialogFragment addPickImgListener(OnPickImgListener onPickImgListener) {
        this.onPickImgListener = onPickImgListener;
        return this;
    }

    @Override
    protected int layoutResId() {
        return R.layout.dialog_fragment_pick_img_select;
    }

    @Override
    protected void initData() {
        viewBinding.setListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_camare:
                if (onPickImgListener != null) CameraUtils.takePhoto(PickImgSelectDialogFragment.this);

                if (onPickImgClickListener != null) {
                    dismissAllowingStateLoss();
                    onPickImgClickListener.onClickCameraListener();
                }
                break;
            case R.id.tv_photo:
                if (onPickImgListener != null) CameraUtils.choosePhoto(PickImgSelectDialogFragment.this);

                if (onPickImgClickListener != null) {
                    dismissAllowingStateLoss();
                    onPickImgClickListener.onClickPickerListener();
                }
                break;
            case R.id.tv_quit:
                dismissAllowingStateLoss();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK ){
            switch (requestCode) {
                case CameraUtils.CAMERA_REQUEST_CODE:
                    Uri imageUris = CameraUtils.imageUri;
                    String imgPaths = CameraUtils.getRealPathFromUri(mContext,imageUris);
                    if (onPickImgListener != null) {
                        onPickImgListener.onPickImgPath(imgPaths);
//                        onPickImgListener.onPickImgUri(imageUris);
                        dismissAllowingStateLoss();
                    }
                    break;
                case CameraUtils.GALLERY_REQUEST_CODE:
                    Uri imageUri = data.getData();
                    if (imageUri==null) return;
                    String imgPath = CameraUtils.getRealPathFromUri(mContext,imageUri);
                    if (onPickImgListener != null) {
                        onPickImgListener.onPickImgPath(imgPath);
//                        onPickImgListener.onPickImgUri(imageUri);
                        dismissAllowingStateLoss();
                    }

                    break;
            }
        }
    }
}
