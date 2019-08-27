package com.lz.rxretrofit.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hjq.toast.ToastUtils;
import com.lz.rxretrofit.R;
import com.lz.rxretrofit.base.BaseMvpFragment;
import com.lz.rxretrofit.bean.AllProType;
import com.lz.rxretrofit.bean.BannerBean;
import com.lz.rxretrofit.bean.BaseErrorBean;
import com.lz.rxretrofit.bean.ExchangeMain;
import com.lz.rxretrofit.bean.HomeProListBean;
import com.lz.rxretrofit.bean.LoginBean;
import com.lz.rxretrofit.bean.MarketGoodBean;
import com.lz.rxretrofit.bean.MiHomeBean;
import com.lz.rxretrofit.mvp.contract.HomeContract;
import com.lz.rxretrofit.mvp.presenter.HomePresenter;
import com.lz.rxretrofit.mvp.ui.activity.GuideActivity;
import com.lz.rxretrofit.mvp.ui.adapter.HomeTopRvAdapter;
import com.lz.rxretrofit.mvp.ui.adapter.MainRvAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shehuan.statusview.StatusView;
import com.shehuan.statusview.StatusViewBuilder;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import okhttp3.ResponseBody;

public class HomeFrag extends BaseMvpFragment<HomePresenter> implements HomeContract.HomeView {
    @BindView(R.id.rv_main_top)
    RecyclerView rv_main_top;
    @BindView(R.id.rv_main)
    RecyclerView rv_main;
    @BindView(R.id.sfl_main)
    SmartRefreshLayout sfl_main;
    @BindView(R.id.sbs_main)
    NestedScrollView sbs_main;
    @BindView(R.id.tv_get)
    TextView tv_get;
    @BindView(R.id.tv_post)
    TextView tv_post;
    @BindView(R.id.tv_put)
    TextView tv_put;
    @BindView(R.id.tv_load)
    TextView tv_load;
    @BindView(R.id.banner)
    XBanner banner;
    @BindView(R.id.ll_bar)
    FrameLayout ll_bar;
    @BindView(R.id.sv_home)
    StatusView sv;
    @BindView(R.id.fa_top)
    FloatingActionButton fa_top;
    @BindView(R.id.sb_home)
    AppCompatSeekBar sb_home;

    ImageView iv_image;
    private MainRvAdapter adapter;
    List<MarketGoodBean> lsGoods = new ArrayList<>();
    private HomeTopRvAdapter homeTopRvAdapter;
    private List<AllProType> lsapt = new ArrayList<>();
    private int page_no = 1;
    View header;
    boolean isCanLoading = true;
    private MiHomeBean miHomeBean;
    private List<BannerBean> banners;
    private boolean isFirstIn = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomePresenter getPresenter() {
        return new HomePresenter();
    }

    @SuppressLint({"WrongConstant", "RestrictedApi"})
    @Override
    public void initParams() {
        rv_main.setFocusable(false);
        rv_main.setNestedScrollingEnabled(false);
        header = LayoutInflater.from(mContext).inflate(R.layout.rv_main_header, null);
        iv_image = header.findViewById(R.id.iv_image);
        adapter = new MainRvAdapter(R.layout.main_item, lsGoods);
        adapter.addHeaderView(header);
        homeTopRvAdapter = new HomeTopRvAdapter(R.layout.item_home_top,lsapt);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rv_main.setLayoutManager(gridLayoutManager);
        rv_main.setAdapter(adapter);
        GridLayoutManager topGridLayoutManager = new GridLayoutManager(mContext, 2);
        topGridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        rv_main_top.setLayoutManager(topGridLayoutManager);
        rv_main_top.setAdapter(homeTopRvAdapter);
        initLisener();
        getData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            mPresenter.setStop(true);
        }
    }

    public void initLisener() {
        fa_top.setOnClickListener(v -> sbs_main.scrollTo(0,0));
        tv_get.setOnClickListener(v -> sbs_main.scrollTo(0,0));
        tv_post.setOnClickListener(v -> doLogin());
        tv_put.setOnClickListener(v -> putSth());
        iv_image.setOnClickListener(v->EventBus.getDefault().post(new ExchangeMain(2)));
        tv_load.setOnClickListener(v -> startActivitySample(GuideActivity.class));
        sfl_main.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        sfl_main.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (isCanLoading) {
                    page_no++;
                    getGoods();
                }
            }
        });
        sbs_main.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == 0) {
                ll_bar.setVisibility(View.GONE);
            }else {
                ll_bar.setVisibility(View.VISIBLE);
            }
            if (scrollY > 2000) {
                fa_top.setVisibility(View.VISIBLE);
            }else {
                fa_top.setVisibility(View.GONE);
            }
//            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                if (isCanLoading) {
//                    page_no++;
//                    getGoods();
//                }
//            }
        });
        adapter.setOnItemClickListener((adapter, view1, position) -> ToastUtils.show(position));
        //设置广告图片点击事件
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                ToastUtils.show(position);
            }
        });
        //加载广告图片
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(mContext).load(miHomeBean.getBanners().get(position).getPoster()).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into((ImageView) view);
            }
        });
        //设置切换布局
        sv.config(new StatusViewBuilder.Builder()
                .setOnErrorRetryClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sv.showContentView();
                        getData();
                    }
                })
                .build()
        );
        //设置seekbar与recyclerveiw互动
        rv_main_top.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //显示区域的高度。
                int extent = rv_main_top.computeHorizontalScrollExtent();
                //整体的高度，注意是整体，包括在显示区域之外的。
                int range = rv_main_top.computeHorizontalScrollRange();
                //已经向下滚动的距离，为0时表示已处于顶部。
                int offset = rv_main_top.computeHorizontalScrollOffset();
                //设置可滚动区域
                sb_home.setMax((range - extent));
                if (dx == 0) {
                    sb_home.setProgress(0);
                } else if (dx > 0) {
                    sb_home.setProgress(offset);
                } else if (dx < 0) {
                    sb_home.setProgress(offset);
                }
            }
        });
        sb_home.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void putSth() {
        showProgress();
        mPresenter.putSth();
    }

    public void getGoods() {
        isCanLoading = false;
        mPresenter.getGoods(page_no);
    }

    public void getData() {
        mPresenter.getData();
    }

    public void doLogin() {
        showProgress();
        mPresenter.setStop(false);
        mPresenter.doLogin();
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        banner.stopAutoPlay();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(ExchangeMain exchangeMain) {
        adapter.removeHeaderView(header);
    }

    @Override
    public void onSuccess(HomeProListBean content) {
        isCanLoading = true;
        if (page_no == 1) {
            lsGoods.clear();
            sfl_main.finishRefresh();
        }
        if (content.getData().size() > 0) {
            lsGoods.addAll(content.getData());
            adapter.notifyDataSetChanged();
            sfl_main.finishLoadMore();
            if (content.getData().size() != 6) {
                isCanLoading = false;
                sfl_main.finishLoadMoreWithNoMoreData();
            }
        } else {
            isCanLoading = false;
            sfl_main.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onFail(BaseErrorBean baseErrorBean) {
        ToastUtils.show(baseErrorBean.getCode() + baseErrorBean.getMessage());
        sfl_main.finishRefresh();
        sfl_main.finishLoadMore(false);
        isCanLoading = true;
        page_no--;
    }
    @Override
    public void onDataSuccess(MiHomeBean content) {
        isFirstIn = false;
        page_no = 1;
        getGoods();
        miHomeBean = content;

//        lsapt = content.getCategorysSlide();
        lsapt.addAll(content.getCategorysSlide());
        homeTopRvAdapter.notifyDataSetChanged();

        banners = content.getBanners();
        //刷新数据之后，需要重新设置是否支持自动轮播
        banner.setAutoPlayAble(banners.size() > 1);
        banner.setBannerData(banners);
    }

    @Override
    public void onDataFail(BaseErrorBean baseErrorBean) {
        if (isFirstIn) {
            isFirstIn = false;
            sv.showErrorView();
        }
        sfl_main.finishRefresh(false);
        ToastUtils.show(baseErrorBean.getCode() + baseErrorBean.getMessage());
    }

    @Override
    public void onLoginSuccess(LoginBean content) {
        hideProgress();
        ToastUtils.show(content.toString());
    }

    @Override
    public void onSthSuccess(ResponseBody content) {
        hideProgress();
        ToastUtils.show(content.toString());
    }

    @Override
    public void onCommonFail(BaseErrorBean baseErrorBean) {
        hideProgress();
        ToastUtils.show(baseErrorBean.getCode() + baseErrorBean.getMessage());
    }
}
