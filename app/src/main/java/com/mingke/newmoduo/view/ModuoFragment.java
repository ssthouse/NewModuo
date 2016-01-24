package com.mingke.newmoduo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ListView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.mingke.newmoduo.R;
import com.mingke.newmoduo.model.event.ModuoBigEvent;
import com.mingke.newmoduo.util.DimenUtil;
import com.mingke.newmoduo.widget.ModuoView;

import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 * Created by ssthouse on 2016/1/24.
 */
public class ModuoFragment extends Fragment {

    private ModuoView moduoView;
    private Button btnRecord;

    private ListView lvChat;

    private int bigModuoHeight;
    private int smallModuoHeight;

    private static final int ANIMATE_TWEEN = 500;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View rootView = inflater.inflate(R.layout.fragment_moduo, container, false);
        initView(rootView);
        initDimens();
        return rootView;
    }


    private void initDimens() {
        bigModuoHeight = DimenUtil.dp2px(getContext(), 400);
        smallModuoHeight = DimenUtil.dp2px(getContext(), 60);
        Timber.e(moduoView.getHeight()+"                我魔哆就这么高");
        Timber.e("big:  " + bigModuoHeight + "    small:   " + smallModuoHeight);
    }

    private void initView(View rootView) {
        lvChat = (ListView) rootView.findViewById(R.id.id_lv_chat);

        moduoView = (ModuoView) rootView.findViewById(R.id.id_moduo);

        btnRecord = (Button) rootView.findViewById(R.id.id_btn_record);
        btnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                animateToSmall();
                Timber.e("变小!!!!!!!!!!!!!!!");
                return true;
            }
        });
    }

    //魔哆变小
    private void animateToSmall() {
        lvChat.setVisibility(View.VISIBLE);
        ViewAnimator.animate(lvChat)
                .height(0, bigModuoHeight - smallModuoHeight)
                .andAnimate(moduoView)
                .height(bigModuoHeight, smallModuoHeight)
                .interpolator(new AccelerateDecelerateInterpolator())
                .duration(ANIMATE_TWEEN)
                .start();
    }

    //魔哆变大
    private void animateToBig() {
        ViewAnimator.animate(lvChat)
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
