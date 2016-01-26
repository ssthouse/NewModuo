package com.mingke.newmoduo.view.widget.record;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import com.mingke.newmoduo.R;
import com.mingke.newmoduo.model.event.AudioPreparedEvent;
import com.mingke.newmoduo.util.AudioRecorder;
import com.mingke.newmoduo.view.adapter.MsgBean;

import de.greenrobot.event.EventBus;

/**
 * 录音按钮
 * Created by ssthouse on 2016/1/23.
 */
public class RecordButton extends Button {

    //dialog管理器
    private DialogManager mDialogManager;
    //音频管理器
    private AudioRecorder mAudioRecorder;

    //手指按下的时间
    private long touchDownTime;

    //目前按钮状态
    private State currentState = State.STATE_NORMAL;

    public enum State {
        //正常    正在录音    想取消
        STATE_NORMAL, STATE_RECORDING, STATE_WANT_CANCEL
    }

    /**
     * 只能在xml用的构造方法
     */
    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mDialogManager = new DialogManager(getContext());
        mAudioRecorder = AudioRecorder.getmInstance();
    }

    /**
     * 录音准备完毕事件
     *
     * @param event
     */
    public void onEventMainThread(AudioPreparedEvent event) {
        //显示正在录音状态
        changeState(State.STATE_RECORDING);
    }


    //改变按钮状态
    public void changeState(State newState) {
        if (currentState == newState) {
            return;
        }
        //更新状态
        currentState = newState;
        switch (newState) {
            case STATE_NORMAL:
                setText(R.string.str_record_normal);
                setBackgroundResource(R.drawable.btn_record_normal);
                mDialogManager.dismiss();
                break;
            case STATE_RECORDING:
                setText(R.string.str_record_recording);
                if (mAudioRecorder.isPrepared()) {
                    mDialogManager.showRecordingDialog();
                }
                setBackgroundResource(R.drawable.btn_record_recording);
                break;
            case STATE_WANT_CANCEL:
                if (mAudioRecorder.isPrepared()) {
                    setText(R.string.str_record_want_cancel);
                    setBackgroundResource(R.drawable.btn_record_recording);
                    mDialogManager.showWantCancelDialog();
                }
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //手指摸下时就开始准备录音
                mAudioRecorder.prepareAudio();
                touchDownTime = System.currentTimeMillis();
                changeState(State.STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isWantCancel(x, y)) {
                    changeState(State.STATE_WANT_CANCEL);
                } else {
                    changeState(State.STATE_RECORDING);
                }
                break;
            case MotionEvent.ACTION_UP:
                changeState(State.STATE_NORMAL);
                if (isWantCancel(x, y)) {
                    mAudioRecorder.cancel();
                } else {
                    if ((System.currentTimeMillis() - touchDownTime) < 500) {
                        mAudioRecorder.cancel();
                        Toast.makeText(getContext(), "多说几句吧", Toast.LENGTH_SHORT).show();
                    } else {
                        mAudioRecorder.release();
                        //添加一条消息
                        EventBus.getDefault().post(MsgBean.getInstance(MsgBean.TYPE_USER_AUDIO,
                                MsgBean.STATE_SENDING, mAudioRecorder.getmCurrentFilePath()));
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    //判断是否是要取消
    private boolean isWantCancel(int x, int y) {
        return x < 0 || x > getWidth() || y < -50 || y > getHeight() + 50;
    }

}
