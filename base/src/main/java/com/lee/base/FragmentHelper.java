package com.lee.base;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Author ：le
 * Date ：2019-11-19 15:36
 * Description ：实现activity对fragment add/show/hide/remove等操作的帮助类
 */
public class FragmentHelper<T> {

    private FragmentManager fragmentManager;
    //存储fragment的集合
    private List<Fragment> fragmentList;
    //容器id
    private int containerId;
    //当前fragment的坐标
    private int currentIndex;

    /**
     * 如果是构建是无参的fragment，list直接传path；如果构建有参的fragment，list就传fragment
     *
     * @param context     加载fragment的载体
     * @param list        可以是ARouter注解的path路径集合，用来反射其对应的类对象，也可以是存储fragment的集合
     * @param containerId 加载fragmrent的容器id
     */
    public FragmentHelper(@NonNull Object context, @NonNull List<T> list, @IdRes int containerId) {
        fragmentManager = getFragmentManager(context);
        this.containerId = containerId;
        fragmentList = new ArrayList<>();
        addFragment(list);
        //检测参数是否合法
        checkInit();
        currentIndex = -1;
        //默认展示第一个fragment
        switchFragment(0);
    }

    /**
     * 这个构造方法的初始化的时候不会添加fragment集合，需要手动调用addFragment方法
     * 最后还需要调用switchFragment方法显示
     */
    public FragmentHelper(@NonNull Object context, @IdRes int containerId) {
        fragmentManager = getFragmentManager(context);
        this.containerId = containerId;
        fragmentList = new ArrayList<>();
        //检测参数是否合法
        checkInit();
        currentIndex = -1;
    }

    /**
     * @param showIndex 即将切换展示的fragment的坐标
     */
    public void switchFragment(@IntRange(from = 0) int showIndex) {
        if (showIndex == currentIndex) return;
        Fragment showFragment = getFragment(showIndex);
        if (showFragment == null) {
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment currentFragment = getFragment(currentIndex);
        if (currentFragment == null) {//当前没有Fragment就表示这是第一次展示fragment
            fragmentTransaction.add(containerId, showFragment);
        } else {//当前存在Fragment，就需要隐藏当前的，展示或者添加将要展示的
            fragmentManager.executePendingTransactions();//这个方法是确保fragment.isAdded()值同步
            fragmentTransaction.hide(currentFragment);
            if (showFragment.isAdded()) {
                fragmentTransaction.show(showFragment);
            } else {
                fragmentTransaction.add(containerId, showFragment);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
        currentIndex = showIndex;
    }

    /**
     * 直接添加对象，类型由泛型参数决定
     * @param t path或fragment对象
     */
    public void addFragment(@NonNull T t) {
        if (t instanceof String) {
            //这里通过ARouter创建fragment对象
            fragmentList.add((Fragment) ARouter.getInstance().build((String) t).navigation());
        } else if (t instanceof Fragment) {
            fragmentList.add((Fragment) t);
        }
    }

    /**
     * 直接添加一个集合，这个集合可以是String类型也可以是fragment类型，由泛型参数决定
     * @param list 存储path或fragment的集合
     */
    public void addFragment(@NonNull List<T> list) {
        for (T t : list) {
            if (t instanceof String){
                //这里通过ARouter创建fragment对象
                fragmentList.add((Fragment) ARouter.getInstance().build((String) t).navigation());
            }else if (t instanceof Fragment){
                fragmentList.add((Fragment) t);
            }
        }
    }

    /**
     * 写这个方法是为了处理如果出现同时添加无参和有参fragment的情况
     *
     * @param fragment 集合里面添加一个fragment对象
     */
    public void addFragment(@NonNull Fragment fragment) {
        fragmentList.add(fragment);
    }

    /**
     * 同上
     * @param path 通过ARouter创建一个fragment对象添加到集合里
     */
    public void addFragment(@NonNull String path) {
        fragmentList.add((Fragment) ARouter.getInstance().build(path).navigation());
    }

    /**
     * 从集合中获取fragment对象
     */
    private Fragment getFragment(int index) {
        if (index >= 0 && index < fragmentList.size()) {
            return fragmentList.get(index);
        }
        LogUtils.e("跳转的fragment不在集合里面");
        return null;
    }

    /**
     * 获取FragmentManager对象
     */
    private FragmentManager getFragmentManager(Object context) {
        if (context instanceof FragmentActivity) {
            return ((FragmentActivity) context).getSupportFragmentManager();
        } else if (context instanceof Fragment) {
            return ((Fragment) context).getChildFragmentManager();
        }
        return null;
    }

    /**
     * 检测初始化是否正常
     */
    private void checkInit() {
        if (fragmentManager == null || fragmentList == null || containerId <= 0) {
            throw new IllegalArgumentException("FragmentHelper check init fail");
        }
    }

}
