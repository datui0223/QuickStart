package com.lz.rxretrofit.mvp.ui.activity;

import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.lz.rxretrofit.R;
import com.lz.rxretrofit.base.BaseMvpActivity;
import com.lz.rxretrofit.mvp.presenter.GuidePresenter;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.LocalImageInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * XBanner 使用在引导页控件
 */
public class GuideActivity extends BaseMvpActivity<GuidePresenter> {
    @BindView(R.id.xbanner)
    XBanner mXBanner;
    @BindView(R.id.btn)
    Button mBtnEnter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initParams() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        List<LocalImageInfo> localImageInfoList=new ArrayList<>();
        localImageInfoList.add(new LocalImageInfo(R.mipmap.ic_launcher));
        localImageInfoList.add(new LocalImageInfo(R.mipmap.ic_launcher));
        localImageInfoList.add(new LocalImageInfo(R.mipmap.ic_launcher_round));
        mXBanner.setBannerData(localImageInfoList);
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                ((ImageView) view).setImageResource(((LocalImageInfo) model).getXBannerUrl());
            }
        });
        initLisener();
    }

    public void initLisener() {
        mXBanner.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == mXBanner.getRealCount() - 1) {
                    mBtnEnter.setVisibility(View.VISIBLE);
                } else {
                    mBtnEnter.setVisibility(View.GONE);
                }
            }
        });
        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivitySample(MainActivity.class);
                finish();
            }
        });
    }

    @Override
    public GuidePresenter getPresenter() {
        return new GuidePresenter();
    }

}
