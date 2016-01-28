package com.mingke.newmoduo.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.mingke.newmoduo.R;
import com.mingke.newmoduo.control.util.CanvasUtil;
import com.mingke.newmoduo.model.bean.ModuoRect;

/**
 * 魔哆:
 * 只在xml中引用
 * 最小的时候是100dp
 * Created by ssthouse on 2016/1/24.
 */
public class ModuoView extends View implements View.OnClickListener {

    //当前魔哆位置
    private ModuoRect moduoRect;

    private Bitmap bigModuo = BitmapFactory.decodeResource(getResources(), R.drawable.moduo);

    //当前状态---初始为大!
    private State currentState = State.STATE_BIG;

    public enum State {
        STATE_BIG, STATE_SMALL
    }

    public ModuoView(Context context) {
        this(context, null);
    }

    public ModuoView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        //获取最大高度
        post(new Runnable() {
            @Override
            public void run() {
                //将魔哆范围管理类初始化
                moduoRect = new ModuoRect(ModuoView.this, getWidth(), getHeight(), bigModuo);
                invalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //更新外部长宽
        if (moduoRect != null) {
            moduoRect.setOuter(w, h);
            //根据长宽切换模式
            if (w < h) {
                changeState(State.STATE_BIG);
            } else {
                changeState(State.STATE_SMALL);
            }
        }

    }

    @Override
    public void onClick(View v) {
        // TODO: 2016/1/28 是不是点击的魔哆---现在魔哆的状态是大是小
        if (currentState == State.STATE_SMALL) {
            // TODO: 2016/1/28 暂时改为走路试试
            moduoRect.startWalk();
//            EventBus.getDefault().post(new ModuoBigEvent());
        }else{

        }
        invalidate();
    }

    //改变状态---会触发重绘
    public void changeState(State state) {
        if (currentState == state) {
            return;
        }
        currentState = state;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawModuo(canvas);
    }

    /**
     * 重绘
     */
    private void drawModuo(Canvas canvas) {
        if (moduoRect == null) {
            return;
        }
//        Timber.e("正在画魔哆");
        switch (currentState) {
            case STATE_BIG:
                drawBig(canvas);
                break;
            case STATE_SMALL:
                drawSmall(canvas);
                break;
        }
    }

    private void drawBig(Canvas canvas) {
//        Timber.e("draw big");
        CanvasUtil.drawBitmap(canvas, bigModuo, moduoRect.getOutWidth() / 2, moduoRect.getOutHeight() / 2,
                moduoRect.getModuoWidth(), moduoRect.getModuoHeight());
    }

    private void drawSmall(Canvas canvas) {
//        Timber.e("draw small");
        CanvasUtil.drawBitmap(canvas, bigModuo, moduoRect.getCenterX(), moduoRect.getCenterY(),
                moduoRect.getModuoWidth(), moduoRect.getModuoHeight());
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    //getter-------------------setter--------------------------------------------------
}
