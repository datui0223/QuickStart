package com.lz.rxretrofit.mvp.ui.activity;

import androidx.fragment.app.Fragment;

import android.os.Process;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lz.rxretrofit.R;
import com.lz.rxretrofit.base.BaseMvpActivity;
import com.lz.rxretrofit.mvp.presenter.BasePresenter;
import com.lz.rxretrofit.bean.ExchangeMain;
import com.lz.rxretrofit.bean.TabEntity;
import com.lz.rxretrofit.mvp.ui.fragment.FindFrag;
import com.lz.rxretrofit.mvp.ui.fragment.HomeFrag;
import com.lz.rxretrofit.mvp.ui.fragment.MyFrag;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseMvpActivity {
    @BindView(R.id.main_tab)
    CommonTabLayout maintab;
    @BindView(R.id.fl_change)
    FrameLayout fl_change;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private int[] mIconUnselectIds;
    private int[] mIconSelectIds;
    private String[] mIconUnselectIdsUrl;
    private String[] mIconSelectIdsUrl;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initParams() {
        mTitles = new String[]{"首页", "分类", "发现", "我的"};
        mIconUnselectIds = new int[]{
                R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher};
        mIconSelectIds = new int[]{
                R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round,
                R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
        mIconSelectIdsUrl = new String[]{
                "https://img.alicdn.com/tfs/TB1nl3daYGYBuNjy0FoXXciBFXa-228-80.jpg", "https://github.githubassets.com/images/modules/octocats/supportcat.jpg",
                "https://www.baidu.com/img/bd_logo1.png", "https://cdn2.jianshu.io/assets/web/nav-logo-4c7bbafe27adc892f3046e6978459bac.png"};
//        mIconUnselectIdsUrl = new String[]{
//                "https://github.githubassets.com/images/modules/octocats/supportcat.jpg","https://www.baidu.com/img/bd_logo1.png",
//                "https://cdn2.jianshu.io/assets/web/nav-logo-4c7bbafe27adc892f3046e6978459bac.png","https://img.alicdn.com/tfs/TB1nl3daYGYBuNjy0FoXXciBFXa-228-80.jpg"};
//        for (int i=0;i<mTitles.length;i++) {
//            mFragments.add(new HomeFrag());
//        }
        mFragments.add(new HomeFrag());
        mFragments.add(new MyFrag());
        mFragments.add(new FindFrag());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i], mIconSelectIdsUrl[i], null));
        }
        maintab.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
//        maintab.showDot(2);
        maintab.showMsg(2,3);
        initLisener();
    }

    public void initLisener() {
        maintab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(position==2){
                    if(maintab.getMsgView(2).isCursorVisible()){
                        maintab.hideMsg(2);
                    }
                }
            }

            @Override
            public void onTabReselect(int position) {
                if(position==2){
                    if(maintab.getMsgView(2).isCursorVisible()){
                        maintab.hideMsg(2);
                    }
                }
            }
        });
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(ExchangeMain updateMain) {
        maintab.setCurrentTab(updateMain.getCurrentTab());
    }

    long lastCurrentTime = System.currentTimeMillis();
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-lastCurrentTime<1000){
//            System.exit(0);
            Process.killProcess(Process.myPid());
        }else {
            Toast.makeText(this,"再点一次退出",Toast.LENGTH_SHORT).show();
            lastCurrentTime = System.currentTimeMillis();
        }

    }
}
