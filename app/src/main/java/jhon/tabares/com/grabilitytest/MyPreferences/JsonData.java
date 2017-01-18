package jhon.tabares.com.grabilitytest.MyPreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jhon on 14/01/2017.
 */

public class JsonData {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;
    private static String fileName = "JsonData";

    public JsonData(Context context){

        this.sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        this.prefsEditor = sharedPreferences.edit();
    }

    public void saveCategories(String key, String value){

        prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public String getCategories(String key){

        if (sharedPreferences != null){
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public void saveAppName(String key, String value){

        prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public String getAppName(String key){

        if (sharedPreferences != null){
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public void saveAppImage(String key, String value){

        prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public String getAppImage(String key){

        if (sharedPreferences != null){
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public void saveAppSummary(String key, String value){

        prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public String getAppSummary(String key){

        if (sharedPreferences != null){
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

}
