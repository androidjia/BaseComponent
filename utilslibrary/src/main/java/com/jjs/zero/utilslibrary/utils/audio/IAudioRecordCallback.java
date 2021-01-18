package com.jjs.zero.utilslibrary.utils.audio;


import java.io.File;

/**
 * Created by shawpaul on 2019/5/27
 */
public interface IAudioRecordCallback {

    /**
     * 录音器已就绪，提供此接口用于在录音前关闭本地音视频播放（可选）
     */
    void onRecordReady();

    /**
     * 开始录音回调
     * @param audioFile 录音文件
     */
    void onRecordStart(File audioFile);

    /**
     * 录音结束，成功
     * @param audioFile 录音文件
     * @param audioLength 录音时间长度
     */
    void onRecordSuccess(File audioFile, int audioLength);

    /**
     * 录音结束，出错
     */
    void onRecordFail();

    /**
     * 录音结束， 用户主动取消录音
     */
    void onRecordCancel();

    /**
     * 到达指定的最长录音时间
     * @param maxTime 录音文件时间长度限制
     */
    void onRecordReachedMaxTime(int maxTime);

}
