package com.mingke.newmoduo.widget;

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

    private DialogManager mDialogManager;

    /**
     * 按钮状态变化监听事件
     */
    public abstract class ButtonStateListener {
        //正在录音
        public abstract void onRecording();

        //想要上滑取消
        public abstract void onWantCancel();

        //音量变化
        public abstract void onVolumnChanged();
    }

    //目前按钮状态
    private State currentState = State.STATE_NORMAL;

    private enum State {
        //正常    正在录音    想取消
        STATE_NORMAL, STATE_RECORDING, STATE_WANT_CANCEL
    }

    public RecordButton(Context context) {
        this(context, null);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mDialogManager = new DialogManager(getContext());
    }

    private void showRecordingDialog() {
        mDialogManager.showRecordingDialog();
    }

    private void showWantCancelDialog() {
        mDialogManager.showWantCancelDialog();

    }

    /**
     * 改变按钮状态
     *
     * @param newState
     */
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
                showRecordingDialog();
                setBackgroundResource(R.drawable.btn_record_recording);
                break;
            case STATE_WANT_CANCEL:
                setText(R.string.str_record_want_cancel);
                setBackgroundResource(R.drawable.btn_record_recording);
                showWantCancelDialog();
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

    /**
     * 判断是否是要取消
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isWantCancel(int x, int y) {
        return x < 0 || x > getWidth() || y < -50 || y > getHeight() + 50;
    }
}
