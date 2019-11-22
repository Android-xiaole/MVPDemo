package com.lee.base.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * Author ：le
 * Date ：2019-11-21 18:03
 * Description ：这是一个可以捕获到用户点击物理返回键回调的Fragment
 */
public abstract class BackHandledFragment extends Fragment {

    private BackHandledInterface mBackHandledInterface;

    /**
     * 定义一个接口类，让FragmentActivity实现，从而拿到Fragment实例
     */
    public interface BackHandledInterface{
        void setShowFragment(BackHandledFragment showFragment);
    }

    /**
     * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
     * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
     */
    protected abstract boolean onBackPressed();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!(getActivity() instanceof BackHandledInterface)){
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        }else{
            this.mBackHandledInterface = (BackHandledInterface)getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //告诉FragmentActivity，当前Fragment在栈顶
        mBackHandledInterface.setShowFragment(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            mBackHandledInterface.setShowFragment(this);
        }
    }
}
