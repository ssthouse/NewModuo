package com.mingke.newmoduo.view.widget.record;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.mingke.newmoduo.R;

/**
 * Created by ssthouse on 2016/1/24.
 */
public class DialogManager {

    private Context mContext;

    private Dialog mDialog;
    private LinearLayout llRecording;
    private LinearLayout llWantCancel;
    private LinearLayout llTooShort;

    public DialogManager(Context mContext) {
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


    public void showRecordingDialog() {
        llRecording.setVisibility(View.VISIBLE);
        llWantCancel.setVisibility(View.GONE);
        llTooShort.setVisibility(View.GONE);
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
