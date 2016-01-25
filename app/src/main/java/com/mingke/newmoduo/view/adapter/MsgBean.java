package com.mingke.newmoduo.view.adapter;

/**
 * 消息数据bean
 * Created by ssthouse on 2016/1/25.
 */
public class MsgBean {

    //消息类型枚
    public static final int TYPE_MODUO_TEXT = 1000;
    public static final int TYPE_MODUO_AUDIO = 1001;
    public static final int TYPE_MODUO_IMAGE = 1002;
    public static final int TYPE_USER_TEXT = 1003;
    public static final int TYPE_USER_AUDIO = 1004;
    public static final int TYPE_USER_IMAGE = 1005;

    //消息状态
    public static final int STATE_FILED = 1006;
    public static final int STATE_SENT = 1007;
    public static final int STATE_SENDING = 1008;
    public static final int STATE_RECEIPT = 1009;

    //消息类型
    private int msgType;
    //消息状态
    private int msgState;

    //文字
    private String text;
    //音频数据
    private String audioFilePath;
    //图片数据
    private String imgFilePath;

    //是否是魔哆发送的
    private boolean isFromModuo;

    //空 构造方法
    private MsgBean() {
    }

    /**
     * 获取一个实例
     */
    public static MsgBean getInstance(int msgType, int msgState, String content) {
        //新建msg---初始化类型, 状态
        MsgBean bean = new MsgBean();
        bean.setMsgType(msgType);
        bean.setMsgState(msgState);
        switch (msgType) {
            case TYPE_MODUO_TEXT:
                bean.setFromModuo(true);
                bean.setText(content);
                break;
            case TYPE_MODUO_AUDIO:
                bean.setFromModuo(true);
                bean.setAudioFilePath(content);
                break;
            case TYPE_MODUO_IMAGE:
                bean.setFromModuo(true);
                bean.setImgFilePath(content);
                break;
            case TYPE_USER_TEXT:
                bean.setFromModuo(false);
                bean.setText(content);
                break;
            case TYPE_USER_AUDIO:
                bean.setFromModuo(false);
                bean.setAudioFilePath(content);
                break;
            case TYPE_USER_IMAGE:
                bean.setFromModuo(false);
                bean.setImgFilePath(content);
                break;
        }
        return bean;
    }

    //getter-------------------setter-----------------------------------------------------

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getMsgState() {
        return msgState;
    }

    public void setMsgState(int msgState) {
        this.msgState = msgState;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    public String getImgFilePath() {
        return imgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        this.imgFilePath = imgFilePath;
    }

    public boolean isFromModuo() {
        return isFromModuo;
    }

    public void setFromModuo(boolean fromModuo) {
        isFromModuo = fromModuo;
    }
}
