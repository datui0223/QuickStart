package com.lz.rxretrofit.utils;

import android.content.SharedPreferences;

import com.lz.rxretrofit.base.BaseApplication;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SPmanager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static Map<String, SPmanager> sharedMap = new ConcurrentHashMap<>();

    public static SPmanager getSharedClass(String NAME) {
        if (!sharedMap.containsKey(NAME)) {
            sharedMap.put(NAME, new SPmanager(NAME));
        }
        return sharedMap.get(NAME);
    }

    public static String getToken() {
        return BaseApplication.appContext.getSharedPreferences("user", BaseApplication.appContext.MODE_PRIVATE).getString("token", "");
    }

    public SPmanager(String FILENAME) {
        sharedPreferences = BaseApplication.appContext.getSharedPreferences(FILENAME, BaseApplication.appContext.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putSingleData(String key, Object value) {
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        editor.commit();
    }

    public void putSetData(String key, Set<String> stringSet) {
        editor.putStringSet(key, stringSet);
        editor.commit();
    }

    public Set<String> getSetData(String key, Set<String> stringSet) {
        return sharedPreferences.getStringSet(key, stringSet);
    }

    public Object getValueFromKey(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return sharedPreferences.getString(key, null);
        }
    }

    public void removeFromKey(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否存在
     */
    public Boolean isExist(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * 获取所有的键值对
     */
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }
}













