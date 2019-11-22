package com.lee.base.mvp;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * Author ：le
 * Date ：2019-10-21 16:43
 * Description ：P层的基础类，处理和V层的绑定，以及内存释放的问题
 */
public class BasePresenter<V extends BaseContract.IView> implements BaseContract.IPresenter<V>, DefaultLifecycleObserver {

    private V v;
    private LifecycleOwner lifecycleOwner;

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        this.lifecycleOwner = owner;
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {}

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {}

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {}

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {}

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        detachView();
        if (lifecycleOwner != null) {
            lifecycleOwner.getLifecycle().removeObserver(this);
            lifecycleOwner = null;
        }
    }

    protected  <T> AutoDisposeConverter<T> bindLifecycle() {
        if (null == lifecycleOwner) {
            throw new NullPointerException("lifecycleOwner == null");
        }
        //默认指定在ON_DESTROY生命周期的时候解除订阅
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY));
    }

    @Override
    public V getView() {
        return v;
    }

    @Override
    public void attachView(V v) {
        this.v = v;
    }

    @Override
    public void detachView() {
        v = null;
    }
}
