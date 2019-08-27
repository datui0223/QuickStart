package com.lz.rxretrofit.mvp.presenter;

import com.lz.rxretrofit.bean.BaseErrorBean;
import com.lz.rxretrofit.bean.TestBean;
import com.lz.rxretrofit.mvp.contract.MyContract;
import com.lz.rxretrofit.mvp.model.MyModel;
import com.lz.rxretrofit.mvp.model.callback.BaseCallBack;
import com.lz.rxretrofit.mvp.model.callback.MyCallBack;

import java.util.List;

public class MyPresenter extends BasePresenter<MyContract.MyView> implements MyContract.MyPresenter{
    private MyModel myModel = new MyModel();
    @Override
    public void findById(String uid) {
        myModel.findById(uid, new BaseCallBack<TestBean>() {
            @Override
            public void onSuccess(TestBean response) {
                mView.onFindByIdSuccess(response);
            }

            @Override
            public void onFail(BaseErrorBean baseErrorBean) {
                mView.onFail(baseErrorBean);
            }
        });
    }

    @Override
    public void findAll() {
        myModel.findAll(new BaseCallBack<List<TestBean>>() {
            @Override
            public void onSuccess(List<TestBean> response) {
                mView.onFindAllSuccess(response);
            }

            @Override
            public void onFail(BaseErrorBean baseErrorBean) {
                mView.onFail(baseErrorBean);
            }
        });
    }

    @Override
    public void deleteById(String uid) {
        myModel.deleteById(uid, new MyCallBack() {
            @Override
            public void onSuccess(boolean b) {
                mView.onDeleteByIdSuccess(b);
            }

            @Override
            public void onFail(BaseErrorBean baseErrorBean) {
                mView.onFail(baseErrorBean);
            }
        });
    }

    @Override
    public void add(TestBean testBean) {
        myModel.add(testBean, new MyCallBack() {
            @Override
            public void onSuccess(boolean b) {
                mView.onAddSuccess(b);
            }

            @Override
            public void onFail(BaseErrorBean baseErrorBean) {
                mView.onFail(baseErrorBean);
            }
        });
    }
}
