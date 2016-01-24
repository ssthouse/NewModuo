package com.mingke.newmoduo.view.widget.record;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 音频管理器
 * Created by ssthouse on 2016/1/24.
 */
public class AudioManager {

    private MediaRecorder mMediaRecorder;

    private String mDir = Environment.getExternalStorageState() + "/NewModuo";
    private String mCurrentFilePath;


    private boolean isPrepared = false;

    public interface AudioStateListener {
        void wellPrepared();
    }

    private AudioStateListener mListener;

    private AudioManager() {

    }

    private static AudioManager mInstance;

    public static AudioManager getmInstance() {
        if (mInstance == null) {
            mInstance = new AudioManager();
        }
        return mInstance;
    }

    public void prepareAudio() {
        isPrepared = false;

        File file = new File(mDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = generateFileName();
        File outputFile = new File(mDir, fileName);
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setOutputFile(outputFile.getPath());
        try {
            //配置录音参数
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            isPrepared = true;
            mCurrentFilePath = outputFile.getAbsolutePath();
            //准备完毕
            if (mListener != null) {
                mListener.wellPrepared();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateFileName() {
        return UUID.randomUUID().toString() + ".amr";
    }

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


    public void setmListener(AudioStateListener mListener) {
        this.mListener = mListener;
    }

    public void release() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    public void cancel() {
        release();
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            file.deleteOnExit();
            mCurrentFilePath = null;
        }
    }
}
