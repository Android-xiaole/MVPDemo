package com.lee.base.view;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lee.base.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Author ：le
 * Date ：2019-10-21 16:43
 * Description ：这是一个通用的Progress进度条弹窗显示
 */
public class BaseProgress extends BaseFragmentDialog {

    private Dialog dialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (dialog == null){
            if(getContext()!=null){
                dialog = new Dialog(getContext(),R.style.base_style_dialog_progress);
            }else{
                return super.onCreateDialog(savedInstanceState);
            }
            View contentView = View.inflate(getContext(),R.layout.base_layout_progress,null);
            dialog.setContentView(contentView);
            ImageView iv_loading = contentView.findViewById(R.id.iv_loading);

            iv_loading.setImageResource(R.drawable.base_animation_progress);
            AnimationDrawable animationDrawable = (AnimationDrawable) iv_loading.getDrawable();
            if (animationDrawable!=null)animationDrawable.start();
        }
        return dialog;
    }

//    public BaseProgress(Context context) {
//        super(context,R.style.base_dialog_progress);
//        View contentView = LayoutInflater.from(context).inflate(R.layout.base_progress_dialog, null);
//        setContentView(contentView);
//        ImageView iv_loading = contentView.findViewById(R.id.iv_loading);
//        tv_msg = contentView.findViewById(R.id.tv_msg);
//
//        iv_loading.setImageResource(R.drawable.base_progress_loading);
//        AnimationDrawable animationDrawable = (AnimationDrawable) iv_loading.getDrawable();
//        if (animationDrawable!=null)animationDrawable.start();
        //一下方式也可以实现动画效果，不用animation-list的方式
//        AnimationDrawable mAnimation = new AnimationDrawable();
//        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_01),50);
//        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_02),50);
//        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_03),50);
//        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_04),50);
//        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_05),50);
//        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_06),50);
//        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_07),50);
//        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_08),50);
//        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_09),50);
//        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_10),50);
//        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_11),50);
//        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.loading_12),50);
//        mAnimation.setOneShot(false);
//        iv_loading.setBackground(mAnimation);
//        if (mAnimation != null && !mAnimation.isRunning()) {
//            mAnimation.start();
//        }
//    }

}
