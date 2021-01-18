package com.jjs.zero.utilslibrary.utils.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by shawpaul on 2019/5/27
 */
public class AudioRecorder {

    public static final int DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND = 120;
    private static final int MSG_START_RECORD = 1;
    private static final int MSG_STOP_RECORD = 2;
    private static final int MSG_END_RECORD = 3; // 录音超时等异常结束

    private static final int RECORD_FAILED = 1;
    private static final int RECORD_READY = 2;
    private static final int RECORD_START = 3;
    private static final int RECORD_SUCCESS = 4;
    private static final int RECORD_CANCELED = 5;
    private static final String TAG = "zero=============";

    private MediaRecorder mAudioRecorder;
    private AudioManager audioManager;

    private Context context;

    private File audioFile;
    private int maxDuration;
    private AtomicBoolean isRecording = new AtomicBoolean(false);
    private AtomicBoolean cancelRecord = new AtomicBoolean(false);
    private IAudioRecordCallback cb;

    private RecordHandler mHandler;
    private Handler mEventHandler = new Handler(Looper.getMainLooper());
    private HandlerThread handlerThread;
    private DateTime recoedStart;

    private class RecordHandler extends Handler {

        public RecordHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_RECORD:
                    onStartRecord();
                    break;
                case MSG_STOP_RECORD:
                    boolean cancel = (Boolean) msg.obj;
                    onCompleteRecord(cancel);
                    break;
                case MSG_END_RECORD:
                    boolean success = (Boolean) msg.obj;
                    int duration = msg.arg1;
                    onHandleEndRecord(success, duration);
                    break;
            }
        }
    }

    /**
     * 构造函数
     *
     * @param context     上下文
     * @param maxDuration 最长录音时长，到该长度后，会自动停止录音
     * @param cb          录音过程回调
     */
    public AudioRecorder(
            Context context,
            int maxDuration,
            IAudioRecordCallback cb
    ) {
        this.context = context.getApplicationContext();
        if (maxDuration <= 0) {
            this.maxDuration = DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND;
        } else {
            this.maxDuration = maxDuration;
        }
        this.cb = cb;

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        handlerThread = new HandlerThread("audio_recorder");
        handlerThread.start();
        mHandler = new RecordHandler(handlerThread.getLooper());
    }

    /**
     * 启动(开始)录音，如果成功，会按照顺序回调onRecordReady和onRecordStart
     *
     * @return 操作是否成功
     */
    public void startRecord() {
        // 移除队列中的 开始任务
        mHandler.removeMessages(MSG_START_RECORD);
        mHandler.obtainMessage(MSG_START_RECORD).sendToTarget();
    }

    private void onStartRecord() {
        audioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (isRecording.get()) {
            Log.d(TAG, " startRecord false, as current state is isRecording");
            callBackRecordState(RECORD_FAILED);
            return;
        }


        String outputFilePath = context.getFilesDir() + File.separator + "audio" + File.separator + UUID.randomUUID() + ".aac";


        cancelRecord.set(false);

        try {
            audioFile = new File(outputFilePath);
            if (!audioFile.getParentFile().exists()) {
                audioFile.getParentFile().mkdirs();
                boolean newFile = audioFile.createNewFile();
                Log.d(TAG, "newFile:" + newFile);
            }


            mAudioRecorder = new MediaRecorder();
            //配置采集方式，这里用的是麦克风的采集方式
            mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
            //配置输出方式，这里用的是MP4，
            mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mAudioRecorder.setMaxDuration(maxDuration * 1000);
            //配置采样频率，频率越高月接近原始声音，Android所有设备都支持的采样频率为44100
            mAudioRecorder.setAudioSamplingRate(44100);
            //配置文件的编码格式,AAC是比较通用的编码格式
            mAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            //配置码率，这里一般通用的是96000
            mAudioRecorder.setAudioEncodingBitRate(96000);
            //配置录音文件的位置
            mAudioRecorder.setOutputFile(audioFile.getAbsolutePath());
            mAudioRecorder.setOnInfoListener(infoListener);
            mAudioRecorder.prepare();
            if (!cancelRecord.get()) {
                callBackRecordState(RECORD_READY);
                mAudioRecorder.start();
                isRecording.set(true);
                callBackRecordState(RECORD_START);
                recoedStart = DateTime.now();
            }
        } catch (Exception e) {
            e.printStackTrace();
            onCompleteRecord(false);
        }
        if (!isRecording.get()) {
            callBackRecordState(RECORD_FAILED);
        }
    }

    /**
     * 完成(结束)录音，根据参数cancel，做不同的回调。
     * 如果cancel为true，回调onRecordCancel, 为false，回调onRecordSuccess
     *
     * @param cancel 是正常结束还是取消录音
     */
    public void completeRecord(boolean cancel) {
        Message message = mHandler.obtainMessage(MSG_STOP_RECORD);
        message.obj = cancel;
        message.sendToTarget();
    }

    private void onCompleteRecord(boolean cancel) {
        if (!isRecording.get()) {
            return;
        }
        cancelRecord.set(cancel);
        audioManager.abandonAudioFocus(null);
        try {
            if (mAudioRecorder != null) {
                mAudioRecorder.stop();
                mAudioRecorder.release();
                Period period = new Period(recoedStart, DateTime.now());
                onHandleEndRecord(true, period.getSeconds());
                recoedStart = null;
                mAudioRecorder = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * release资源
     */
    public void destroyAudioRecorder() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (handlerThread != null) {
            handlerThread.getLooper().quit();
        }
    }

    /**
     * 是否正在录音
     */
    public boolean isRecording() {
        return isRecording.get();
    }

    public void handleEndRecord(boolean isSuccess, int duration) {
        Message message = mHandler.obtainMessage(MSG_END_RECORD);
        message.obj = isSuccess;
        message.arg1 = duration;
        message.sendToTarget();
    }

    private void onHandleEndRecord(boolean isSuccess, final int duration) {
        if (cancelRecord.get()) {
            // cancel
            //            AttachmentStore.deleteOnExit(audioFile.getAbsolutePath());

            callBackRecordState(RECORD_CANCELED);
        } else if (!isSuccess) {
            // failed
            //            AttachmentStore.deleteOnExit(audioFile.getAbsolutePath());
            callBackRecordState(RECORD_FAILED);
        } else {
            // error
            if (audioFile == null || !audioFile.exists() || audioFile.length() <= 0) {
                callBackRecordState(RECORD_FAILED);
            } else {
                // success
                mEventHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        cb.onRecordSuccess(audioFile, duration);
                    }
                });
            }
        }
        isRecording.set(false);
    }

    private void callBackRecordState(final int recordState) {
        mEventHandler.post(new Runnable() {
            @Override
            public void run() {
                switch (recordState) {
                    case RECORD_FAILED:
                        cb.onRecordFail();
                        break;
                    case RECORD_READY:
                        cb.onRecordReady();
                        break;
                    case RECORD_START:
                        cb.onRecordStart(audioFile);
                        break;
                    case RECORD_CANCELED:
                        cb.onRecordCancel();
                    default:
                        break;
                }
            }
        });
    }


    private void handleReachedMaxRecordTime(int duration) {
        cb.onRecordReachedMaxTime(duration);
    }


    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {

        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                isRecording.set(false);
                if (mAudioRecorder != null) {
                    mAudioRecorder.stop();
                    mAudioRecorder.release();
                    mAudioRecorder = null;
                }
                mEventHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        handleReachedMaxRecordTime(maxDuration);
                    }
                });

            }
        }

    };
}
