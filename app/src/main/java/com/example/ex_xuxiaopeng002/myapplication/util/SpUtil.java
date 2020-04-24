package com.example.ex_xuxiaopeng002.myapplication.util;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * @Date: 2020-04-22
 * @author: ex-xuxiaopeng002
 * @Description:
 */

import com.example.ex_xuxiaopeng002.myapplication.app.MyApp;

import java.util.Map;

public class SpUtil {
    private static final String COMMON_FILE = "common_file";
    private static final String LANGUAGE_FILE_NAME = "language_file";
    private static final String SUPER_CONFIG_FILE_NAME = "super_config_file";
    private static SPImp spImp = new SPImp();

    public static SPImp mCommonSp() {
        return spImp.getSPImp(COMMON_FILE, Context.MODE_PRIVATE);
    }

    public static SPImp languageSp() {
        return spImp.getSPImp(LANGUAGE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static SPImp superConfigSp() {
        return spImp.getSPImp(SUPER_CONFIG_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static class SPImp {
        private SharedPreferences sp;

        SPImp getSPImp(String name, int mode) {
            sp = MyApp.INSTANCE.getSharedPreferences(name, mode);
            return this;
        }

        public synchronized void put(String key, Object object) {
            synchronized (sp) {
                SharedPreferences.Editor editor = sp.edit();

                if (object instanceof String) {
                    editor.putString(key, (String) object);
                } else if (object instanceof Integer) {
                    editor.putInt(key, (Integer) object);
                } else if (object instanceof Boolean) {
                    editor.putBoolean(key, (Boolean) object);
                } else if (object instanceof Float) {
                    editor.putFloat(key, (Float) object);
                } else if (object instanceof Long) {
                    editor.putLong(key, (Long) object);
                } else {
                    editor.putString(key, object.toString());
                }
                editor.apply();
            }
        }

        public Object get(String key, Object defaultObject) {
            if (defaultObject instanceof String) {
                return sp.getString(key, (String) defaultObject);
            } else if (defaultObject instanceof Integer) {
                return sp.getInt(key, (Integer) defaultObject);
            } else if (defaultObject instanceof Boolean) {
                return sp.getBoolean(key, (Boolean) defaultObject);
            } else if (defaultObject instanceof Float) {
                return sp.getFloat(key, (Float) defaultObject);
            } else if (defaultObject instanceof Long) {
                return sp.getLong(key, (Long) defaultObject);
            }

            return null;
        }

        public String getString(String key, String defaultObject) {
            return sp.getString(key, defaultObject);
        }

        public Integer getInt(String key, Integer defaultObject) {
            return sp.getInt(key, defaultObject);
        }

        public Boolean getBoolean(String key, Boolean defaultObject) {
            return sp.getBoolean(key, defaultObject);
        }

        public Float getFloat(String key, Float defaultObject) {
            return sp.getFloat(key, defaultObject);
        }

        public Long getLong(String key, Long defaultObject) {
            return sp.getLong(key, defaultObject);
        }

        /**
         * 移除某个key值已经对应的值
         *
         * @param key
         */
        public void remove(String key) {
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.apply();
        }

        /**
         * 清除所有数据
         */
        public void clear() {
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.apply();
        }

        /**
         * 查询某个key是否已经存在
         *
         * @param key
         * @return
         */
        public boolean contains(String key) {
            return sp.contains(key);
        }

        /**
         * 返回所有的键值对
         *
         * @return
         */
        public Map<String, ?> getAll() {
            return sp.getAll();
        }
    }
}