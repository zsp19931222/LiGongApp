package com.example.app4.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.app4.base.BaseFragment;
import com.example.smartclass.eventbus.MessageEvent;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import yh.app.appstart.lg.R;

/**
 * Created by Administrator on 2018/4/16 0016.
 * <p>
 * 消息fragment
 */

public class MessageFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.home_message_title_left)
    Button homeMessageTitleLeft;
    @BindView(R.id.home_message_title_right)
    Button homeMessageTitleRight;
    @BindView(R.id.fragment_message_frame)
    FrameLayout fragmentMessageFrame;


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private MessageListFragment messageListFragment;
    private AddressListFragment addressListFragment;

    private Context context;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        fragmentManager = getActivity().getSupportFragmentManager();
        context = getContext();
        initMessageListFragment();
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 隐藏-显示首页fragment
     */
    private void initMessageListFragment() {
        try {
            fragmentTransaction = fragmentManager.beginTransaction();
            hideFragment(fragmentTransaction);
            if (messageListFragment == null) {
                messageListFragment = new MessageListFragment();
                fragmentTransaction.add(R.id.fragment_message_frame, messageListFragment);
            } else {
                fragmentTransaction.show(messageListFragment);
            }
            fragmentTransaction.commit();
        } catch (Exception ignored) {
        }
    }


    /**
     * 隐藏-显示消息fragment
     */
    private void initAddressListFragment() {
        try {
            fragmentTransaction = fragmentManager.beginTransaction();
            hideFragment(fragmentTransaction);
            if (addressListFragment == null) {
                addressListFragment = new AddressListFragment();
                fragmentTransaction.add(R.id.fragment_message_frame, addressListFragment);
            } else {
                fragmentTransaction.show(addressListFragment);
            }
            fragmentTransaction.commit();
        } catch (Exception ignored) {

        }
    }

    /**
     * 去除（隐藏）所有的Fragment
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (messageListFragment != null) {
            //隐藏方法
            transaction.hide(messageListFragment);
        }
        if (addressListFragment != null) {
            transaction.hide(addressListFragment);
        }
    }

    @OnClick({R.id.home_message_title_left, R.id.home_message_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_message_title_left:
                homeMessageTitleLeft.setBackgroundResource(R.drawable.d_biankuang_soild_touming_buttom_2dp_333333);
                homeMessageTitleRight.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                initMessageListFragment();
                break;
            case R.id.home_message_title_right:
                homeMessageTitleRight.setBackgroundResource(R.drawable.d_biankuang_soild_touming_buttom_2dp_333333);
                homeMessageTitleLeft.setBackgroundColor(context.getResources().getColor(R.color.white));
                initAddressListFragment();
                break;
        }
    }
}
