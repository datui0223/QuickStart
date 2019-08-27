package com.lz.rxretrofit.mvp.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.hjq.toast.ToastUtils;
import com.lz.rxretrofit.R;
import com.lz.rxretrofit.base.BaseApplication;
import com.lz.rxretrofit.base.BaseMvpActivity;
import com.lz.rxretrofit.bean.BaseErrorBean;
import com.lz.rxretrofit.bean.UpdateBean;
import com.lz.rxretrofit.mvp.contract.SplashContract;
import com.lz.rxretrofit.mvp.presenter.SplashPresenter;
import com.lz.rxretrofit.utils.SPmanager;
import com.lz.rxretrofit.utils.AppUtil;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;

import java.io.File;

import androidx.appcompat.app.AlertDialog;

public class SplashActivity extends BaseMvpActivity<SplashPresenter> implements SplashContract.SplashView {
    private DownloadManager manager;
    boolean isForceUpdate = false;
    private ProgressDialog dialogProgress;
    private UpdateConfiguration configuration;
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initParams() {
        configuration = new UpdateConfiguration()
                //输出错误日志
                .setEnableLog(true)
                //设置自定义的下载
                //.setHttpManager()
                //下载完成自动跳动安装页面
                .setJumpInstallPage(true)
                //设置对话框背景图片 (图片规范参照demo中的示例图870*423)
                //.setDialogImage(R.drawable.ic_dialog)
                //设置按钮的颜色
                //.setDialogButtonColor(Color.parseColor("#E743DA"))
                //设置按钮的文字颜色
                //.setDialogButtonTextColor(Color.WHITE)
                //支持断点下载
                //.setBreakpointDownload(false)
                //设置是否显示通知栏进度
                .setShowNotification(true)
                //设置是否提示后台下载toast
                .setShowBgdToast(false)
                //设置强制更新
                .setForcedUpgrade(false)
                //设置对话框按钮的点击监听
                .setButtonClickListener(new OnButtonClickListener() {
                    @Override
                    public void onButtonClick(int id) {
                    }
                })
                //设置下载过程的监听
                .setOnDownloadListener(new OnDownloadListener() {
                    @Override
                    public void start() {

                    }

                    @Override
                    public void downloading(int max, int progress) {
                        if (isForceUpdate) {
                            dialogProgress.setProgress(Integer.valueOf(progress * 100 / max));
                        }
                    }

                    @Override
                    public void done(File apk) {
                        if(isForceUpdate){
                            dialogProgress.dismiss();
                        }
//                        else {
//                            finish();
//                        }
                    }

                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void error(Exception e) {

                    }
                });
        isUpdate();
    }

    private void isUpdate() {
        mPresenter.isUpdate();
    }

    private void conmmenUpdate(String apkName, String apkUrl, boolean isForceUpdate) {
        if(isForceUpdate){
            new AlertDialog.Builder(SplashActivity.this)
                    .setTitle("发现新版本")
                    .setMessage("1.支持断点下载\n2.支持Android N\n3.支持Android O\n4.支持自定义下载过程\n5.支持 设备>=Android M 动态权限的申请\n6.支持通知栏进度条展示(或者自定义显示进度)")
                    .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialogProgress = new ProgressDialog(SplashActivity.this);
                            dialogProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            dialogProgress.setMessage("下载中...");
                            dialogProgress.setIndeterminate(false);
                            dialogProgress.setCancelable(false);
                            dialogProgress.show();
                            manager = DownloadManager.getInstance(SplashActivity.this);
                            manager.setApkName(apkName)
                                    .setApkUrl(apkUrl)
                                    .setDownloadPath(Environment.getExternalStorageDirectory() + "/AppUpdate")
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setConfiguration(configuration)
                                    .download();
                        }
                    })
                    .setCancelable(false)
                    .create().show();
        }else {
            new AlertDialog.Builder(SplashActivity.this)
                    .setTitle("发现新版本")
                    .setMessage("1.支持断点下载\n2.支持Android N\n3.支持Android O\n4.支持自定义下载过程\n5.支持 设备>=Android M 动态权限的申请\n6.支持通知栏进度条展示(或者自定义显示进度)")
                    .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SoulPermission.getInstance()
                                    .checkAndRequestPermissions(Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                            , new CheckRequestPermissionsListener() {
                                                @Override
                                                public void onAllPermissionOk(Permission[] allPermissions) {
                                                    manager = DownloadManager.getInstance(BaseApplication.appContext);
                                                    manager.setApkName(apkName)
                                                            .setApkUrl(apkUrl)
                                                            .setDownloadPath(Environment.getExternalStorageDirectory() + "/AppUpdate")
                                                            .setSmallIcon(R.mipmap.ic_launcher)
                                                            .setConfiguration(configuration)
                                                            .download();
                                                    startActivityTo();
                                                }

                                                @Override
                                                public void onPermissionDenied(Permission[] refusedPermissions) {
                                                    ToastUtils.show("未授权文件下载权限");
                                                    SoulPermission.getInstance().goPermissionSettings();
                                                }
                                            });
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startActivityTo();
                        }
                    })
                    .setCancelable(false)
                    .create().show();
        }

    }

    void startActivityTo(){
        SoulPermission.getInstance().checkAndRequestPermissions(Permissions.build(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
                ),
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        SoulPermission.getInstance().checkAndRequestPermissions(Permissions.build(
                                Manifest.permission.ACCESS_COARSE_LOCATION
                                ,Manifest.permission.ACCESS_FINE_LOCATION),
                                new CheckRequestPermissionsListener() {
                                    @Override
                                    public void onAllPermissionOk(Permission[] allPermissions) {
                                        Go();
                                    }

                                    @Override
                                    public void onPermissionDenied(Permission[] refusedPermissions) {
                                        Go();
                                    }
                                });
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        ToastUtils.show("请设置允许读写权限以及手机状态权限，以提供给您更优的服务");
                        SoulPermission.getInstance().goPermissionSettings();
                        System.exit(0);
                    }
                });
    }

    private void Go(){
        if(dialogProgress!=null){
            if("".equals(SPmanager.getSharedClass("user").getValueFromKey("firstIn",""))){
                SPmanager.getSharedClass("user").putSingleData("firstIn","no");
                startActivitySample(GuideActivity.class);
            }else {
                startActivitySample(MainActivity.class);
            }
        }else {
            if("".equals(SPmanager.getSharedClass("user").getValueFromKey("firstIn",""))){
                SPmanager.getSharedClass("user").putSingleData("firstIn","no");
                startActivitySample(GuideActivity.class);
                finish();
            }else {
                startActivitySample(MainActivity.class);
                finish();
            }
        }
    }

    @Override
    public SplashPresenter getPresenter() {
        return new SplashPresenter();
    }

    @Override
    public void onSuccess(UpdateBean updateBean) {
        if (AppUtil.compareVersion(updateBean.getMaxVersion(), AppUtil.getVersion()) > 0) {
            isForceUpdate = updateBean.isForceUpdate();
            conmmenUpdate("app.apk", updateBean.getUpdateUrl(), isForceUpdate);
        }else {
            startActivityTo();
        }
    }

    @Override
    public void onFail(BaseErrorBean baseErrorBean) {
        ToastUtils.show(baseErrorBean.getMessage());
    }
}
