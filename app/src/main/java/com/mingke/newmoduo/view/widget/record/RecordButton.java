package com.mingke.newmoduo.view.widget.record;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.mingke.newmoduo.R;

/**
 * 录音按钮
 * Created by ssthouse on 2016/1/23.
 */
public class RecordButton extends Button {

    //dialog管理器
    private DialogManager mDialogManager;
    //音频管理器
    private AudioManager mAudioManager;

    //目前按钮状态
    private State currentState = State.STATE_NORMAL;

    private enum State {
        //正常    正在录音    想取消
        STATE_NORMAL, STATE_RECORDING, STATE_WANT_CANCEL
    }

    /**
     * 只能在xml用的构造方法
     * @param context
     * @param attrs
     */
    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mDialogManager = new DialogManager(getContext());
        mAudioManager = AudioManager.getmInstance();
        mAudioManager.setmListener(new AudioManager.AudioStateListener() {
            @Override
            public void wellPrepared() {

            }
        });


    }

    //改变按钮状态
    private void changeState(State newState) {
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
                mDialogManager.showRecordingDialog();
                setBackgroundResource(R.drawable.btn_record_recording);
                break;
            case STATE_WANT_CANCEL:
                setText(R.string.str_record_want_cancel);
                setBackgroundResource(R.drawable.btn_record_recording);
                mDialogManager.showWantCancelDialog();
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
                break;
        }

        return super.onTouchEvent(event);
    }

    //判断是否是要取消
    private boolean isWantCancel(int x, int y) {
        return x < 0 || x > getWidth() || y < -50 || y > getHeight() + 50;
    }
}
