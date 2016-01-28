package com.mingke.newmoduo.model.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.mingke.newmoduo.R;
import com.mingke.newmoduo.control.util.DimenUtil;

/**
 * 魔哆img占据的矩形
 * Created by ssthouse on 2016/1/28.
 */
public class ModuoRect {

    private Context mContext;

    //控件最小高度
    private int outerMinHeight;
    private int outMaxHeight;
    //按照宽度的倍数
    private static final int SCALE_BIG = 2;
    private static final int SCALE_SMALL = 8;

    //四周位置
    private int left;
    private int top;
    private int right;
    private int bottom;

    //宽高
    private int moduoWidth, moduoHeight;
    //中心位置
    private int centerX, centerY;

    //整个View的宽高
    private int outWidth, outHeight;

    private Bitmap bigModuo;

    private static final int MSG_WALK = 1000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //todo---走路
                /*
                改变ModuoRect四周位置
                 */
                case MSG_WALK:
                    break;
            }
        }
    };

    /**
     * 构造方法
     *
     * @param context
     * @param outWidth
     * @param outMaxHeight 只能从外部view获得的最大高度
     */
    public ModuoRect(Context context, int outWidth, int outMaxHeight, Bitmap moduoBitmap) {
        this.mContext = context;
        this.bigModuo = moduoBitmap;
        //初始化最小高度---最大高度---宽度
        outerMinHeight = DimenUtil.dp2px(mContext, 100);
        this.outMaxHeight = outMaxHeight;
        this.outHeight = outMaxHeight;
        this.outWidth = outWidth;
        //计算魔哆大小
        resetDimens();
        //初始化bitmap
        bigModuo = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.moduo);
    }

    //根据外部宽高---重新计算魔哆大小
    private void resetDimens() {
        //当前控件宽高---主要用高度
        float k = (float) (outHeight - outerMinHeight) / (outMaxHeight - outerMinHeight);
        float dividerNumber = 1.0f / SCALE_SMALL + (1.0f / SCALE_BIG - 1.0f / SCALE_SMALL) * k;
        moduoWidth = (int) (outWidth * dividerNumber);
        moduoHeight = (int) (bigModuo.getHeight() / ((float) bigModuo.getWidth() / moduoWidth));
    }

    //设置外围控件宽高
    public void setOuter(int outWidth, int outHeight) {
        this.outWidth = outWidth;
        this.outHeight = outHeight;
        //更新魔哆大小
        resetDimens();
    }

    public int getModuoWidth() {
        return moduoWidth;
    }

    public int getModuoHeight() {
        return moduoHeight;
    }

    public int getCenterX() {
        return (left + right) / 2;
    }

    public int getCenterY() {
        return (top + bottom) / 2;
    }

    //getter-------------------setter------------------------------

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getOutWidth() {
        return outWidth;
    }

    public void setOutWidth(int outWidth) {
        this.outWidth = outWidth;
    }

    public int getOutHeight() {
        return outHeight;
    }

    public void setOutHeight(int outHeight) {
        this.outHeight = outHeight;
    }
}
