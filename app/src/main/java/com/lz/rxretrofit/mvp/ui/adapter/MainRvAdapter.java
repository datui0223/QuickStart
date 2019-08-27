package com.lz.rxretrofit.mvp.ui.adapter;

import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lz.rxretrofit.R;
import com.lz.rxretrofit.bean.MarketGoodBean;

import java.util.List;

public class MainRvAdapter extends BaseQuickAdapter<MarketGoodBean, BaseViewHolder> {

    public MainRvAdapter(int layoutResId, @Nullable List<MarketGoodBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MarketGoodBean item) {
        helper.setText(R.id.tv_title,item.getGoods_name());
        helper.setText(R.id.tv_prince,item.getPrice());
        Glide.with(mContext).load(item.getOriginal()).into((ImageView) helper.getView(R.id.iv_image));
    }


}
