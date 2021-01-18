package com.jjs.zero.basecomponent;

import android.app.Activity;
import android.media.AudioManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.jjs.zero.basecomponent.databinding.ActivityAudioBinding;
import com.jjs.zero.baseviewlibrary.BaseActivity;
import com.jjs.zero.utilslibrary.utils.PermissionRequestUtils;
import com.jjs.zero.utilslibrary.utils.audio.AudioPlayer;
import com.jjs.zero.utilslibrary.utils.audio.AudioRecorder;
import com.jjs.zero.utilslibrary.utils.audio.IAudioRecordCallback;
import com.jjs.zero.utilslibrary.utils.audio.OnPlayListener;

import java.io.File;
import java.util.List;

public class AudioActivity extends BaseActivity<ActivityAudioBinding> implements IAudioRecordCallback, View.OnClickListener {

    // 语音
    protected AudioRecorder audioMessageHelper;
    public static final int MAX_VOICE_TIME = 60;
    private AudioPlayer mAudioPlayer;
    private String path;
    @Override
    public int layoutResId() {
        return R.layout.activity_audio;
    }

    @Override
    protected void initData() {
        viewBinding.setListener(this::onClick);
        if (mAudioPlayer == null) mAudioPlayer = new AudioPlayer(mContext);
        mAudioPlayer.setOnPlayListener(new OnPlayListener() {
            @Override
            public void onPrepared() {

            }

            @Override
            public void onCompletion() {

            }

            @Override
            public void onInterrupt() {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onPlaying(long curPosition) {

            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_init:
                initAudio();
                showToast("初始化完成");
                break;
            case R.id.tv_start:
                showToast("开始录音");
                if (PermissionRequestUtils.get().checkPermission(this,PermissionRequestUtils.ABS_MICROPHONE)) {
                    PermissionRequestUtils.get().requestPermission(this, new PermissionRequestUtils.OnPermissionResultListener() {
                        @Override
                        public void OnSuccessListener() {
                            Log.i("zero=============","权限添加成功：");

                            startAudio();
                        }

                        @Override
                        public void OnFailListener(List<String> permissions) {
                            Log.i("zero=============","权限添加失败：");
                        }
                    },PermissionRequestUtils.ABS_MICROPHONE);
                } else {
                    Log.i("zero=============","已有权限添加");
                    startAudio();
                }


                break;
            case R.id.tv_end:
                showToast("结束录音");
                stopAudio();
                break;
            case R.id.tv_play:
                showToast("播放");
                Log.i("zero=============","播放："+path);
                mAudioPlayer.setDataSource(path);
                mAudioPlayer.start(AudioManager.STREAM_MUSIC);
                break;
        }
    }



    private void startAudio() {
        audioMessageHelper.startRecord();
    }


    private void stopAudio() {
        if (audioMessageHelper != null) audioMessageHelper.completeRecord(true);
    }


    private void initAudio() {
        if (audioMessageHelper == null) audioMessageHelper = new AudioRecorder(mContext, MAX_VOICE_TIME, this);
    }

    private boolean isRecording() {
        return audioMessageHelper != null && audioMessageHelper.isRecording();
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopAudio();
        mAudioPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (audioMessageHelper != null) audioMessageHelper.destroyAudioRecorder();
    }

    @Override
    public void onRecordReady() {

    }

    @Override
    public void onRecordStart(File audioFile) {
        Log.i("zero=============","onRecordStart："+audioFile.getAbsolutePath());
        path = audioFile.getAbsolutePath();
    }

    @Override
    public void onRecordSuccess(File audioFile, int audioLength) {
        Log.i("zero=============","录音成功："+audioFile.getAbsolutePath()+"   length:"+audioLength);
        path = audioFile.getAbsolutePath();
    }

    @Override
    public void onRecordFail() {
        Log.i("zero=============","录音失败：");
    }

    @Override
    public void onRecordCancel() {

    }

    @Override
    public void onRecordReachedMaxTime(int maxTime) {
        Log.i("zers","录音超长结束：");
        audioMessageHelper.handleEndRecord(true,maxTime);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionRequestUtils.get().onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}