package com.lz.rxretrofit.mvp.contract;

import com.lz.rxretrofit.bean.BaseErrorBean;
import com.lz.rxretrofit.bean.TestBean;

import java.util.List;

public interface MyContract{
    interface MyPresenter{
        void findById(String uid);
        void findAll();
        void deleteById(String uid);
        void add(TestBean testBean);
    }

    interface MyView{
        void onFindAllSuccess(List<TestBean> lstb);
        void onFindByIdSuccess(TestBean testBean);
        void onDeleteByIdSuccess(boolean b);
        void onAddSuccess(boolean b);
        void onFail(BaseErrorBean baseErrorBean);
    }

}
