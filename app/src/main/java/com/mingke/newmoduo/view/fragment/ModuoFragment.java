package com.mingke.newmoduo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.florent37.viewanimator.ViewAnimator;
import com.mingke.newmoduo.R;
import com.mingke.newmoduo.model.event.ModuoBigEvent;
import com.mingke.newmoduo.util.DimenUtil;
import com.mingke.newmoduo.view.adapter.MainAdapter;
import com.mingke.newmoduo.view.adapter.MsgBean;
import com.mingke.newmoduo.view.widget.ModuoView;
import com.mingke.newmoduo.view.widget.record.RecordButton;

import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 * 魔哆主界面
 * Created by ssthouse on 2016/1/24.
 */
public class ModuoFragment extends Fragment {

    private ModuoView moduoView;
    private RecordButton btnRecord;

    private RecyclerView recycleChat;
    private MainAdapter mAdapter;

    private int bigModuoHeight;
    private int smallModuoHeight;

    private static final int ANIMATE_TWEEN = 500;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View rootView = inflater.inflate(R.layout.fragment_moduo, container, false);
        initView(rootView);
        return rootView;
    }

    private void initDimens() {
        bigModuoHeight = moduoView.getHeight();
        smallModuoHeight = DimenUtil.dp2px(getContext(), 100);
        Timber.e(moduoView.getHeight() + "                我魔哆就这么高");
        Timber.e("big:  " + bigModuoHeight + "    small:   " + smallModuoHeight);
    }

    private void initView(View rootView) {
        //聊天列表
        mAdapter = new MainAdapter(getContext());
        recycleChat = (RecyclerView) rootView.findViewById(R.id.id_recycle_chat);
        recycleChat.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleChat.setAdapter(mAdapter);
        for (int i = 0; i < 10; i++) {
            mAdapter.addMsg(MsgBean.getInstance(MsgBean.TYPE_MODUO_TEXT, MsgBean.STATE_SENDING, "我在发送一条消息"));
        }

        moduoView = (ModuoView) rootView.findViewById(R.id.id_moduo);
        moduoView.post(new Runnable() {
            @Override
            public void run() {
                initDimens();
            }
        });

        btnRecord = (RecordButton) rootView.findViewById(R.id.id_btn_record);
        btnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                animateToSmall();
                return true;
            }
        });
    }

    //魔哆变小
    private void animateToSmall() {
        recycleChat.setVisibility(View.VISIBLE);
        ViewAnimator.animate(recycleChat)
                .height(0, bigModuoHeight - smallModuoHeight)
                .andAnimate(moduoView)
                .height(bigModuoHeight, smallModuoHeight)
                .interpolator(new AccelerateDecelerateInterpolator())
                .duration(ANIMATE_TWEEN)
                .start();
    }

    //魔哆变大
    private void animateToBig() {
        ViewAnimator.animate(recycleChat)
                .height(bigModuoHeight - smallModuoHeight, 0)
                .andAnimate(moduoView)
                .height(smallModuoHeight, bigModuoHeight)
                .interpolator(new AccelerateDecelerateInterpolator())
                .duration(ANIMATE_TWEEN)
                .start();
    }

    /**
     * 魔哆变大事件回调
     *
     * @param event
     */
    public void onEventMainThread(ModuoBigEvent event) {
        animateToBig();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
