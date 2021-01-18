package com.jjs.zero.utilslibrary.utils.audio;

/**
 * Created by shawpaul on 2019/5/27
 */
public interface OnPlayListener {
    /**
     * 文件解码完成，准备播放
     */
    public void onPrepared();

    /**
     * 播放完成
     */
    public void onCompletion();

    /**
     * 中断播放
     */
    public void onInterrupt();

    /**
     * 出错
     *
     * @param error
     *            错误原因
     */
    public void onError(String error);

    /**
     * 播放过程
     *
     * @param curPosition
     *            音频当前播放位置
     */
    public void onPlaying(long curPosition);
}
