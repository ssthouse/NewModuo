package com.mingke.newmoduo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.mingke.newmoduo.util.CanvasUtil;
import com.mingke.newmoduo.R;
import com.mingke.newmoduo.model.event.ModuoBigEvent;

import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 * Created by ssthouse on 2016/1/24.
 */
public class ModuoView extends View implements View.OnClickListener {

    //按照宽度的倍数
    private static final int SCALE_BIG = 2;
    private static final int SCALE_SMALL = 8;

    private Bitmap bigModuo = BitmapFactory.decodeResource(getResources(), R.drawable.moduo);

    //控件宽高
    private int width, height;
    //
    private int moduoWidth, moduoHeight;


    private enum State {
        STATE_BIG, STATE_SMALL;
    }

    //当前状态---初始为大!
    private State currentState = State.STATE_BIG;

    public ModuoView(Context context) {
        this(context, null);
    }

    public ModuoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
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
            Timber.e("发送变大事件");
        }
        invalidate();
    }

    //改变状态---会触发重绘
    public void changeState(State state) {
        if (currentState == state) {
            return;
        }
        Timber.e(state.name() + "-----------------------------------");
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
        width = getWidth();
        height = getHeight();
        if (currentState == State.STATE_BIG) {
            moduoWidth = width / SCALE_BIG;
            //缩小的倍数
            float dividerNumber = (float) bigModuo.getWidth() / moduoWidth;
            moduoHeight = (int) (bigModuo.getHeight() / dividerNumber);
        } else if (currentState == State.STATE_SMALL) {
            moduoWidth = width / SCALE_SMALL;
            //缩小的倍数
            float dividerNumber = (float) bigModuo.getWidth() / moduoWidth;
            moduoHeight = (int) (bigModuo.getHeight() / dividerNumber);
        }
    }

    private void drawBig(Canvas canvas) {
        resetDimens();
        CanvasUtil.drawBitmap(canvas, bigModuo, width / 2, height / 2, moduoWidth, moduoHeight);
        Timber.e("draw big");
    }

    private void drawSmall(Canvas canvas) {
        resetDimens();
        CanvasUtil.drawBitmap(canvas, bigModuo, width / 2, height / 2, moduoWidth, moduoHeight);
        Timber.e("draw small");
    }
}
