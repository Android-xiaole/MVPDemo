package com.lee.base.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.lee.base.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * Author ：le
 * Date ：2019-10-21 16:43
 * Description ：外部可以传入布局ID和styleId的DialogFragment
 */
public class CustomFragmentDialog extends BaseFragmentDialog {

    private Dialog dialog;

    public void show(Context context, int layoutId) {
        if (context instanceof FragmentActivity) {
            FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
            super.show(manager);
            if (dialog == null) dialog = new Dialog(context, R.style.base_style_dialog_custom);
            dialog.setContentView(layoutId);
        } else {
            throw new RuntimeException("Please use FragmentActivity to show DialogFragment");
        }
    }

    public void show(Context context, int layoutId, int styleId) {
        if (context instanceof FragmentActivity) {
            FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
            super.show(manager);
            if (dialog == null) dialog = new Dialog(context, styleId);
            dialog.setContentView(layoutId);
        } else {
            throw new RuntimeException("Please use FragmentActivity to show DialogFragment");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (dialog != null) {
            return dialog;
        }
        return super.onCreateDialog(savedInstanceState);
    }

    /**
     * 外部调用此方法获取dialog对象，再通过dialog.findViewById()对控件进行操作
     */
    @Override
    public Dialog getDialog() {
        return dialog;
    }

}
