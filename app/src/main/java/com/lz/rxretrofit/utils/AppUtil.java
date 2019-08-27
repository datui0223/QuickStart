package com.lz.rxretrofit.utils;

import android.content.pm.PackageManager;

import com.lz.rxretrofit.base.BaseApplication;

public class AppUtil {
    public static String getVersion(){
        String version = "";
        try {
            version = BaseApplication.appContext.getPackageManager().getPackageInfo(BaseApplication.appContext.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    // 版本判断
    public static int compareVersion(String version1, String version2) {
        String[] versionArray1 = version1.split("\\.");// 注意此处为正则匹配，不能用"."；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);// 取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length()
                - versionArray2[idx].length()) == 0// 先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {// 再比较字符
            ++idx;
        }
        // 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }
}
