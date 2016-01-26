package com.mingke.newmoduo.control.util;

import android.media.MediaRecorder;
import android.os.Environment;

import com.mingke.newmoduo.model.event.AudioPreparedEvent;

import java.io.File;
import java.io.IOException;

import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 * 音频管理器
 * Created by ssthouse on 2016/1/24.
 */
public class AudioRecorder {

    private MediaRecorder mMediaRecorder;

    private String mDir = Environment.getExternalStorageDirectory() + "/NewModuo";
    private String mCurrentFilePath;

    private boolean isPrepared = false;

    private AudioRecorder() {

    }

    private static AudioRecorder mInstance;

    public static AudioRecorder getmInstance() {
        if (mInstance == null) {
            mInstance = new AudioRecorder();
        }
        return mInstance;
    }

    public void prepareAudio() {
        isPrepared = false;
        //初始化文件夹
        File file = new File(mDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = generateFileName();
        File outputFile = new File(mDir, fileName);
        mCurrentFilePath = outputFile.getAbsolutePath();
        if(mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }else{
            mMediaRecorder.reset();
            mMediaRecorder.release();
        }
        try {
            //配置录音参数
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(mCurrentFilePath);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            isPrepared = true;
            //准备完毕
            EventBus.getDefault().post(new AudioPreparedEvent());
        } catch (IOException e) {
            e.printStackTrace();
            Timber.e("我跪了!!!!!!!!!!!!!!!!!!!");
        }
    }

    //获取随机文件名
    private String generateFileName() {
        return System.currentTimeMillis() + ".amr";
    }

    //音量等级
    public int getLevel(int maxLevel) {
        if (!isPrepared) {
            return 1;
        }
        try {
            return maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void release() {
        if(mMediaRecorder == null){
            return;
        }
        isPrepared = false;
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    //取消录制的文件
    public void cancel() {
        release();
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            file.deleteOnExit();
            mCurrentFilePath = null;
        }
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    public String getmCurrentFilePath() {
        return mCurrentFilePath;
    }

    public void setmCurrentFilePath(String mCurrentFilePath) {
        this.mCurrentFilePath = mCurrentFilePath;
    }

    public void setPrepared(boolean prepared) {
        isPrepared = prepared;
    }
}
