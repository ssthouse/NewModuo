package com.mingke.newmoduo.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.mingke.newmoduo.R;
import com.mingke.newmoduo.model.event.ModuoBigEvent;
import com.mingke.newmoduo.control.util.CanvasUtil;
import com.mingke.newmoduo.control.util.DimenUtil;

import de.greenrobot.event.EventBus;

/**
 * 魔哆:
 * 只在xml中引用
 * 最小的时候是100dp
 * Created by ssthouse on 2016/1/24.
 */
public class ModuoView extends View implements View.OnClickListener {

    //当前控件大小
    private int currentWidth;
    private int currentHeight;

    //控件最小高度
    private int minHeight;
    private int maxHeight;

    //按照宽度的倍数
    private static final int SCALE_BIG = 2;
    private static final int SCALE_SMALL = 8;

    private Bitmap bigModuo = BitmapFactory.decodeResource(getResources(), R.drawable.moduo);

    //魔哆宽高
    private int moduoWidth, moduoHeight;

    //当前状态---初始为大!
    private State currentState = State.STATE_BIG;

    public enum State {
        STATE_BIG, STATE_SMALL
    }

    public ModuoView(Context context) {
        this(context, null);
    }

    public ModuoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        //初始化最小高度
        minHeight = DimenUtil.dp2px(getContext(), 100);
        //获取最大高度
        post(new Runnable() {
            @Override
            public void run() {
                maxHeight = getHeight();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //更新控件大小
        currentWidth = w;
        currentHeight = h;
        //根据长宽切换模式
        if (w < h) {
            changeState(State.STATE_BIG);
        } else {
            changeState(State.STATE_SMALL);
        }
    }

    @Override
    public void onClick(View v) {
        if (currentState == State.STATE_SMALL) {
            EventBus.getDefault().post(new ModuoBigEvent());
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
        resetDimens();
        switch (currentState) {
            case STATE_BIG:
                drawBig(canvas);
                break;
            case STATE_SMALL:
                drawSmall(canvas);
                break;
        }
    }

    //重新计算参数
    private void resetDimens() {
        if (bigModuo == null) {
            return;
        }
        //当前控件宽高---主要用高度
        currentWidth = getWidth();
        currentHeight = getHeight();
        float k = (float) (currentHeight - minHeight) / (maxHeight - minHeight);
        float dividerNumber = 1.0f / SCALE_SMALL + (1.0f / SCALE_BIG - 1.0f / SCALE_SMALL) * k;
        moduoWidth = (int) (currentWidth*dividerNumber);
        moduoHeight = (int)(bigModuo.getHeight()/((float)bigModuo.getWidth()/moduoWidth));
    }

    private void drawBig(Canvas canvas) {
        CanvasUtil.drawBitmap(canvas, bigModuo, currentWidth / 2, currentHeight / 2, moduoWidth, moduoHeight);
    }

    private void drawSmall(Canvas canvas) {
        CanvasUtil.drawBitmap(canvas, bigModuo, currentWidth / 2, currentHeight / 2, moduoWidth, moduoHeight);
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    //getter-------------------setter--------------------------------------------------
    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getModuoWidth() {
        return moduoWidth;
    }

    public void setModuoWidth(int moduoWidth) {
        this.moduoWidth = moduoWidth;
    }

    public int getModuoHeight() {
        return moduoHeight;
    }

    public void setModuoHeight(int moduoHeight) {
        this.moduoHeight = moduoHeight;
    }
}
