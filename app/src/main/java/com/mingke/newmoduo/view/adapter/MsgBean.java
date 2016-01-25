package com.mingke.newmoduo.view.adapter;

/**
 * 消息数据bean
 * Created by ssthouse on 2016/1/25.
 */
public class MsgBean {

    //消息类型枚举
    public enum MsgType {
        MODUO_TEXT, MODUO_AUDIO, MODUO_IMAGE,
        USER_TEXT, USER_AUDIO, USER_IMAGE
    }

    //消息发送状态
    public enum MsgState {
        STATE_FILED,
        STATE_SENT,
        STATE_SENDING,
        STATE_RECEIPT
    }

    //消息类型
    private MsgType msgType;
    //消息状态
    private MsgState msgState;

    //文字
    private String text;
    //音频数据
    private String audioFilePath;
    //图片数据
    private String imgFilePath;

    //是否是魔哆发送的
    private boolean isFromModuo;

    //空 构造方法
    private  MsgBean() {
    }

    /**
     * 获取一个实例
     *
     */
    public static MsgBean getInstance(MsgType msgType, MsgState msgState, String content) {
        MsgBean bean = new MsgBean();
        bean.setMsgType(msgType);
        bean.setMsgState(msgState);
        switch (msgType) {
            case MODUO_TEXT:
                bean.setFromModuo(true);
                bean.setText(content);
                break;
            case MODUO_AUDIO:
                bean.setFromModuo(true);
                bean.setAudioFilePath(content);
                break;
            case MODUO_IMAGE:
                bean.setFromModuo(true);
                bean.setImgFilePath(content);
                break;
            case USER_TEXT:
                bean.setFromModuo(false);
                bean.setText(content);
                break;
            case USER_AUDIO:
                bean.setFromModuo(false);
                bean.setAudioFilePath(content);
                break;
            case USER_IMAGE:
                bean.setFromModuo(false);
                bean.setImgFilePath(content);
                break;
        }
        return bean;
    }

    //getter-------------------setter-----------------------------------------------------

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public MsgState getMsgState() {
        return msgState;
    }

    public void setMsgState(MsgState msgState) {
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
