package com.mingke.newmoduo.view.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingke.newmoduo.R;
import com.mingke.newmoduo.control.util.AudioPlayer;
import com.mingke.newmoduo.control.util.DimenUtil;
import com.mingke.newmoduo.view.adapter.MsgBean;

/**
 * 用在xml里面
 * Created by ssthouse on 2016/1/25.
 */
public class AudioPlayButton extends TextView implements IAudioPlayBtnControl {

    //最长20秒对应的margin
    private int MAX_MARGIN_LEFT;
    private int MAX_AUDIO_LENGTH = 20;

    private MsgBean audioMsg;
    private State currentState;


    enum State {
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
        //最大距离宽度
        MAX_MARGIN_LEFT = DimenUtil.dp2px(getContext(), 100);
        //初始状态
        currentState = State.STATE_NORMAL;
    }

    //更新控件宽度
    @Override
    public void updateView() {
        if (audioMsg == null) {
            return;
        }
        //画出默认背景
        stopAnim();
        //改变长度
        int currentMargin;
        if (audioMsg.getAudioDuration() > 20) {
            currentMargin = MAX_MARGIN_LEFT;
        } else {
            currentMargin = (int) (audioMsg.getAudioDuration() / 20.0f * MAX_MARGIN_LEFT);
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        layoutParams.setMargins(currentMargin, 0, 0, 0);
        setLayoutParams(layoutParams);
    }

    @Override
    public void onClick() {
        if (audioMsg == null) {
            return;
        }
        if (currentState == State.STATE_NORMAL) {
            //切换为播放状态
            currentState = State.STATE_PLAYING;
            //尝试播放
            AudioPlayer.getInstance(getContext()).playAudio(audioMsg.getAudioFilePath(), AudioPlayButton.this);
            //播放动画
            playAnim();
        } else if (currentState == State.STATE_PLAYING) {
            currentState = State.STATE_NORMAL;
            //暂停
            AudioPlayer.getInstance(getContext()).pausePlayer();
            stopAnim();
        }
    }


    @Override
    public void playAnim() {
        //播放动画
        if (audioMsg.isFromModuo()) {
            setBackgroundResource(R.drawable.anim_audio_play_btn_left);
            AnimationDrawable animationDrawable = (AnimationDrawable) getBackground();
            animationDrawable.start();
        } else {
            setBackgroundResource(R.drawable.anim_audio_play_btn_right);
            AnimationDrawable animationDrawable = (AnimationDrawable) getBackground();
            animationDrawable.start();
        }
    }

    @Override
    public void stopAnim() {
        currentState = State.STATE_NORMAL;
        //背景图片
        if (audioMsg.isFromModuo()) {
            setBackgroundResource(R.drawable.ic_chat_voice_left);
        } else {
            setBackgroundResource(R.drawable.ic_chat_voice_right);
        }
    }

    //getter---------------setter-----------------------------------------------

    public MsgBean getAudioMsg() {
        return audioMsg;
    }

    public void setAudioMsg(MsgBean audioMssg) {
        this.audioMsg = audioMssg;
    }
}
