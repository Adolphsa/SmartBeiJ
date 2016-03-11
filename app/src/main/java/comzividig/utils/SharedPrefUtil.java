package comzividig.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreference工具类
 * Created by Administrator on 2016-02-25.
 */
public class SharedPrefUtil {
    public static final String PREF_NAME = "config";

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return sp.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context context, String key, boolean Value) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, Value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return sp.getString(key, defaultValue);
    }

    public static void setString(Context context, String key, String Value) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, Value).commit();
    }
}