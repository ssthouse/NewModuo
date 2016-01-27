package com.mingke.newmoduo.view.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mingke.newmoduo.R;
import com.mingke.newmoduo.view.widget.AudioPlayButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * 魔哆聊天adapter
 * Created by ssthouse on 2016/1/24.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.Holder> {

    private List<MsgBean> msgList;

    private RecyclerView mRecyclerView;
    private Context mContext;

    //判断view的类型(选择左右的xml文件)
    public static final int TYPE_LEFT = 1000;
    public static final int TYPE_RIGHT = 1001;

    public MainAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        msgList = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder;
        if (viewType == TYPE_LEFT) {
            holder = new Holder(LayoutInflater.from(mContext)
                    .inflate(R.layout.view_chat_left, parent, false), TYPE_LEFT);
        } else {
            holder = new Holder(LayoutInflater.from(mContext)
                    .inflate(R.layout.view_chat_right, parent, false), TYPE_RIGHT);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        //UI填充
        switch (msgList.get(position).getMsgType()) {
            case MsgBean.TYPE_MODUO_TEXT:
                bindTextMsg(holder, position);
                break;
            case MsgBean.TYPE_MODUO_AUDIO:
                bindAudioMsg(holder, position);
                break;
            case MsgBean.TYPE_MODUO_IMAGE:
                break;
            case MsgBean.TYPE_USER_TEXT:
                bindTextMsg(holder, position);
                break;
            case MsgBean.TYPE_USER_AUDIO:
                bindAudioMsg(holder, position);
                break;
            case MsgBean.TYPE_USER_IMAGE:
                break;
        }
    }

    //填充AudioMsg到view
    private void bindAudioMsg(Holder holder, int position) {
        holder.conventLayout.removeAllViews();
        //添加语音播放按钮
        View contentView;
        if (msgList.get(position).isFromModuo()) {
            contentView = View.inflate(mContext, R.layout.view_chat_item_left_audio_layout, null);
        } else {
            contentView = View.inflate(mContext, R.layout.view_chat_item_right_audio_layout, null);
        }
        //填充音频播放按钮
        final AudioPlayButton playButton = (AudioPlayButton) contentView.findViewById(R.id.id_btn_audio_play);
        playButton.setAudioFilePath(msgList.get(position).getAudioFilePath());
        playButton.setFromModuo(msgList.get(position).isFromModuo());
        //播放音频的点击事件
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.e("click!!!!!!!!!!!!!!!!");
                playButton.onClick();
            }
        });
        //填充音频时长
        TextView tvAudioLength = (TextView) contentView.findViewById(R.id.id_tv_audio_length);
        MediaPlayer mediaPlayer = MediaPlayer.create(mContext,
                Uri.fromFile(new File(msgList.get(position).getAudioFilePath())));
        if (mediaPlayer != null) {
            tvAudioLength.setText(mediaPlayer.getDuration() / 1000 + "''");
        }
        holder.conventLayout.addView(contentView);
    }

    //填充TextMsg到View
    private void bindTextMsg(Holder holder, int position) {
        holder.conventLayout.removeAllViews();
        if (msgList.get(position).isFromModuo()) {
            holder.conventLayout.addView(View.inflate(mContext, R.layout.view_chat_item_left_text_layout, null));
        } else {
            holder.conventLayout.addView(View.inflate(mContext, R.layout.view_chat_item_right_text_layout, null));
        }
        TextView tv = (TextView) holder.conventLayout.findViewById(R.id.id_tv_chat_content);
        tv.setText(msgList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    //增加消息
    public void addMsg(MsgBean msgBean) {
        msgList.add(msgBean);
        notifyDataSetChanged();
        mRecyclerView.smoothScrollBy(0, msgList.size() * 200);
    }

    @Override
    public int getItemViewType(int position) {
        int msgType = msgList.get(position).getMsgType();
        switch (msgType) {
            case MsgBean.TYPE_MODUO_TEXT:
                return TYPE_LEFT;
            case MsgBean.TYPE_MODUO_AUDIO:
                return TYPE_LEFT;
            case MsgBean.TYPE_MODUO_IMAGE:
                return TYPE_LEFT;
            case MsgBean.TYPE_USER_TEXT:
                return TYPE_RIGHT;
            case MsgBean.TYPE_USER_AUDIO:
                return TYPE_RIGHT;
            case MsgBean.TYPE_USER_IMAGE:
                return TYPE_RIGHT;
        }
        return TYPE_LEFT;
    }

    /**
     * 单独一个Msg View的承接类---只管保管当前View就行
     */
    class Holder extends RecyclerView.ViewHolder {

        ImageView avatarView;
        TextView timeView;
        TextView nameView;
        LinearLayout conventLayout;
        FrameLayout statusLayout;
        ProgressBar progressBar;
        TextView statusView;
        ImageView errorView;

        public Holder(View itemView, int viewType) {
            super(itemView);
            //找到控件
            if (viewType == TYPE_LEFT) {
                avatarView = (ImageView) itemView.findViewById(R.id.chat_left_iv_avatar);
                timeView = (TextView) itemView.findViewById(R.id.chat_left_tv_time);
                nameView = (TextView) itemView.findViewById(R.id.chat_left_tv_name);
                conventLayout = (LinearLayout) itemView.findViewById(R.id.chat_left_layout_content);
                statusLayout = (FrameLayout) itemView.findViewById(R.id.chat_left_layout_status);
                statusView = (TextView) itemView.findViewById(R.id.chat_left_tv_status);
                progressBar = (ProgressBar) itemView.findViewById(R.id.chat_left_progressbar);
                errorView = (ImageView) itemView.findViewById(R.id.chat_left_tv_error);
            } else {
                avatarView = (ImageView) itemView.findViewById(R.id.chat_right_iv_avatar);
                timeView = (TextView) itemView.findViewById(R.id.chat_right_tv_time);
                nameView = (TextView) itemView.findViewById(R.id.chat_right_tv_name);
                conventLayout = (LinearLayout) itemView.findViewById(R.id.chat_right_layout_content);
                statusLayout = (FrameLayout) itemView.findViewById(R.id.chat_right_layout_status);
                progressBar = (ProgressBar) itemView.findViewById(R.id.chat_right_progressbar);
                errorView = (ImageView) itemView.findViewById(R.id.chat_right_tv_error);
                statusView = (TextView) itemView.findViewById(R.id.chat_right_tv_status);
            }
        }
    }
}
