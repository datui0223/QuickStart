package com.lz.rxretrofit.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lz.rxretrofit.R;
import com.lz.rxretrofit.base.BaseApplication;
import com.lz.rxretrofit.bean.AllProType;
import com.lz.rxretrofit.utils.ScreenUtil;
import com.lz.rxretrofit.utils.StringUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HomeTopRvAdapter extends BaseQuickAdapter<AllProType,BaseViewHolder> {


    public HomeTopRvAdapter(int layoutResId, @Nullable List<AllProType> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AllProType item) {
        ViewGroup.LayoutParams layoutParams = helper.getView(R.id.rl_home).getLayoutParams();
        layoutParams.width = ScreenUtil.getScreenWidthPixels(BaseApplication.appContext) / 5;
        helper.getView(R.id.rl_home).setLayoutParams(layoutParams);
        helper.setText(R.id.tv_name,item.getName());
        if(StringUtil.isValue(item.getLogo())){
            Glide.with(BaseApplication.appContext).load(item.getLogo()).into((ImageView) helper.getView(R.id.iv_logo));
        }
    }
}
