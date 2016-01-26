package com.mingke.newmoduo.view.widget.record;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mingke.newmoduo.R;

/**
 * 语音按钮Dialog管理类
 * Created by ssthouse on 2016/1/24.
 */
public class AudioDialogManager {

    private Context mContext;

    private Dialog mDialog;
    private LinearLayout llRecording;
    private LinearLayout llWantCancel;
    private LinearLayout llTooShort;

    public AudioDialogManager(Context mContext) {
        this.mContext = mContext;
        mDialog = new Dialog(mContext, R.style.RecordDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contentVIew = inflater.inflate(R.layout.dialog_recording, null);
        mDialog.setContentView(contentVIew);
        //找到控件
        llRecording = (LinearLayout) mDialog.findViewById(R.id.id_ll_recording);
        llWantCancel = (LinearLayout) mDialog.findViewById(R.id.id_ll_want_cancel);
        llTooShort = (LinearLayout) mDialog.findViewById(R.id.id_ll_too_short);
    }

    /**
     * 显示正在说话Dialog
     * 需要显示音量
     *
     * @param volumeLevel
     */
    public void showRecordingDialog(int volumeLevel) {
        llRecording.setVisibility(View.VISIBLE);
        llWantCancel.setVisibility(View.GONE);
        llTooShort.setVisibility(View.GONE);
        //改变音量View
        ImageView ivColumnLevel = (ImageView) llRecording.findViewById(R.id.id_iv_volume_level);
        int id = mContext.getResources().getIdentifier("ic_v" + volumeLevel, "drawable", "com.mingke.newmoduo");
        if (id > 0) {
            ivColumnLevel.setImageResource(id);
        }
        mDialog.show();
    }

    public void showWantCancelDialog() {
        llRecording.setVisibility(View.GONE);
        llWantCancel.setVisibility(View.VISIBLE);
        llTooShort.setVisibility(View.GONE);
        mDialog.show();
    }

    public void showTooSHort() {
        llRecording.setVisibility(View.GONE);
        llWantCancel.setVisibility(View.GONE);
        llTooShort.setVisibility(View.VISIBLE);
    }

    public void dismiss() {
        mDialog.dismiss();
    }
}
