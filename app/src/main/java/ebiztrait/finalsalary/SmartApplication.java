package ebiztrait.finalsalary;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import java.io.IOException;

public class SmartApplication extends Application {

    public static SmartApplication REF_SMART_APPLICATION;

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        REF_SMART_APPLICATION = this;

        sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
    }


    /**
     * This method will return instance of <b>SharedPreferences</b> generated by
     * SmartFramework. Framework will use SharedPreference name as given in
     * <b>ApplicationConfiguration</b> for generation of SharedPreference.
     * <b>Note</b> : SharedPreference Mode will be private whenever generated by
     * SmartFramework.
     *
     * @return sharedPreferences = Instance of SharedPreferences created by
     * SmartFramework.
     */
    public SharedPreferences readSharedPreferences() {
        return sharedPreferences;
    }

    /**
     * This method will write to <b>SharedPreferences</b>.
     *
     * @param key   = String <b>key</b> to store in <b>SharedPreferences</b>.
     * @param value = String <b>value</b> to store in <b>SharedPreferences</b>.
     */
    public void writeSharedPreferences(String key, String value) {
        SharedPreferences.Editor editor = readSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * This method will write to <b>SharedPreferences</b>.
     *
     * @param key   = String <b>key</b> to store in <b>SharedPreferences</b>.
     * @param value = boolean <b>value</b> to store in <b>SharedPreferences</b>.
     */
    public void writeSharedPreferences(String key, boolean value) {
        SharedPreferences.Editor editor = readSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * This method will write to <b>SharedPreferences</b>.
     *
     * @param key   = String <b>key</b> to store in <b>SharedPreferences</b>.
     * @param value = float <b>value</b> to store in <b>SharedPreferences</b>.
     */
    public void writeSharedPreferences(String key, float value) {
        SharedPreferences.Editor editor = readSharedPreferences().edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * This method will write to <b>SharedPreferences</b>.
     *
     * @param key   = String <b>key</b> to store in <b>SharedPreferences</b>.
     * @param value = int <b>value</b> to store in <b>SharedPreferences</b>.
     */
    public void writeSharedPreferences(String key, int value) {
        SharedPreferences.Editor editor = readSharedPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * This method will write to <b>SharedPreferences</b>.
     *
     * @param key   = String <b>key</b> to store in <b>SharedPreferences</b>.
     * @param value = long <b>value</b> to store in <b>SharedPreferences</b>.
     */
    public void writeSharedPreferences(String key, long value) {
        SharedPreferences.Editor editor = readSharedPreferences().edit();
        editor.putLong(key, value);
        editor.commit();
    }
}