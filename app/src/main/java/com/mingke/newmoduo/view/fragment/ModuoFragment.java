package com.mingke.newmoduo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.florent37.viewanimator.ViewAnimator;
import com.mingke.newmoduo.R;
import com.mingke.newmoduo.control.util.DbHelper;
import com.mingke.newmoduo.control.util.DimenUtil;
import com.mingke.newmoduo.model.event.ModuoBigEvent;
import com.mingke.newmoduo.view.adapter.MainAdapter;
import com.mingke.newmoduo.view.adapter.MsgBean;
import com.mingke.newmoduo.view.widget.ModuoView;
import com.mingke.newmoduo.view.widget.record.RecordButton;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 魔哆主界面
 * Created by ssthouse on 2016/1/24.
 */
public class ModuoFragment extends Fragment {

    //魔哆图案
    @Bind(R.id.id_moduo)
    ModuoView moduoView;

    //录音按钮
    @Bind(R.id.id_btn_record)
    RecordButton btnRecord;

    //聊天列表
    @Bind(R.id.id_swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.id_recycle_chat)
    RecyclerView recycleChat;
    MainAdapter mAdapter;

    private int bigModuoHeight;
    private int smallModuoHeight;
    private static final int ANIMATE_TWEEN = 500;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View rootView = inflater.inflate(R.layout.fragment_moduo, container, false);
        ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }

    private void initDimens() {
        bigModuoHeight = moduoView.getHeight();
        smallModuoHeight = DimenUtil.dp2px(getContext(), 100);
    }

    private void initView(View rootView) {
        //聊天列表
        recycleChat.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MainAdapter(getContext(), recycleChat);
        recycleChat.setAdapter(mAdapter);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: 2016/2/1 刷新加载数据
                Observable.just(mAdapter.getMsgList().get(0))
                        .map(new Func1<MsgBean, List<MsgBean>>() {
                            @Override
                            public List<MsgBean> call(MsgBean msgBean) {
                                return DbHelper.getLastTenMsgBean(msgBean);
                            }
                        })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<MsgBean>>() {
                            @Override
                            public void call(List<MsgBean> msgList) {
                                mAdapter.addMgList(msgList);
                                //清除刷新状态
                                swipeLayout.setRefreshing(false);
                            }
                        });
            }
        });

        moduoView.post(new Runnable() {
            @Override
            public void run() {
                initDimens();
            }
        });
    }

    //魔哆变小
    private void animateToSmall() {
        if (moduoView.getCurrentState() == ModuoView.State.STATE_SMALL) {
            return;
        }
        swipeLayout.setVisibility(View.VISIBLE);
        ViewAnimator.animate(swipeLayout)
                .height(0, bigModuoHeight - smallModuoHeight)
                .andAnimate(moduoView)
                .height(bigModuoHeight, smallModuoHeight)
                .interpolator(new AccelerateDecelerateInterpolator())
                .duration(ANIMATE_TWEEN)
                .start();
    }

    //魔哆变大
    private void animateToBig() {
        if (moduoView.getCurrentState() == ModuoView.State.STATE_BIG) {
            return;
        }
        ViewAnimator.animate(swipeLayout)
                .height(bigModuoHeight - smallModuoHeight, 0)
                .andAnimate(moduoView)
                .height(smallModuoHeight, bigModuoHeight)
                .interpolator(new AccelerateDecelerateInterpolator())
                .duration(ANIMATE_TWEEN)
                .start();
    }

    //魔哆变大事件回调
    public void onEventMainThread(ModuoBigEvent event) {
        animateToBig();
    }

    //添加msgBen回调
    public void onEventMainThread(MsgBean msgBean) {
        if (msgBean == null) {
            return;
        }
        //魔哆变小
        animateToSmall();
        //添加数据到对话框
        mAdapter.addMsg(msgBean);
        //保存到数据库
        DbHelper.saveMsgBean(msgBean);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
