package com.mingke.newmoduo.widget;

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

    public DialogManager(Context mContext) {
        this.mContext = mContext;
        mDialog = new Dialog(mContext, R.style.RecordDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contentVIew = inflater.inflate(R.layout.dialog_recording, null);
        mDialog.setContentView(contentVIew);

        llRecording = (LinearLayout) mDialog.findViewById(R.id.id_ll_recording);
        llWantCancel = (LinearLayout) mDialog.findViewById(R.id.id_ll_want_cancel);
    }

    private LinearLayout llRecording;
    private LinearLayout llWantCancel;

    public void showRecordingDialog() {
        llRecording.setVisibility(View.VISIBLE);
        llWantCancel.setVisibility(View.GONE);
        mDialog.show();
    }

    public void showWantCancelDialog() {
        llRecording.setVisibility(View.GONE);
        llWantCancel.setVisibility(View.VISIBLE);
        mDialog.show();
    }

    public void showTooSHort(){

    }

    public void dismiss(){
        mDialog.dismiss();
    }
}
