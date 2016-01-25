package com.mingke.newmoduo.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.mingke.newmoduo.R;
import com.mingke.newmoduo.util.AudioHelper;

/**
 * 用在xml里面
 * todo:
 * 动画的实现...
 * Created by ssthouse on 2016/1/25.
 */
public class AudioPlayButton extends TextView {

    private String audioFilePath;
    private State currentState;

    enum State{
        STATE_NORMAL, STATE_PLAYING
    }

    public AudioPlayButton(Context context) {
        this(context, null);
    }

    public AudioPlayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        currentState = State.STATE_NORMAL;

        //背景图片
        setBackgroundResource(R.drawable.adj);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audioFilePath == null){
                    return;
                }
                if(currentState == State.STATE_NORMAL){
                    //尝试播放
                    AudioHelper.getInstance(getContext()).playAudio(audioFilePath, AudioPlayButton.this);
                    //切换为播放状态
                    currentState = State.STATE_PLAYING;
                }else if (currentState == State.STATE_PLAYING){
                    //暂停
                    AudioHelper.getInstance(getContext()).pausePlayer();
                }
            }
        });
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }
}
