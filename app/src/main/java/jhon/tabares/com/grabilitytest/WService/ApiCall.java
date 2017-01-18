package jhon.tabares.com.grabilitytest.WService;

import android.content.Context;
import android.provider.SyncStateContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import jhon.tabares.com.grabilitytest.ConstantsStrings.OkHttpConnections;
import jhon.tabares.com.grabilitytest.MyPreferences.JsonData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jhon on 12/01/2017.
 */

public class ApiCall {

    static OkHttpClient client = new OkHttpClient();

    //GET network request
    public static String GET(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();

        /*Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e("onFailure ", call.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e("onResponse", response.body().toString());
            }
        });*/

        return response.body().string();
    }

    //POST network request
    public static String POST(OkHttpClient client, String url, RequestBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
