package jhon.tabares.com.grabilitytest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import jhon.tabares.com.grabilitytest.Adapters.AppCategoryAdapter;
import jhon.tabares.com.grabilitytest.Adapters.ListCategoryAdapter;
import jhon.tabares.com.grabilitytest.ConstantsStrings.OkHttpConnections;
import jhon.tabares.com.grabilitytest.Models.Apps;
import jhon.tabares.com.grabilitytest.Models.Categorys;
import jhon.tabares.com.grabilitytest.MyPreferences.JsonData;
import jhon.tabares.com.grabilitytest.WService.ApiCall;

public class ListApps extends AppCompatActivity {

    private JsonData jsonData;
    private ProgressDialog pDialog;
    //private ListView listView;
    AbsListView absListView;
    private ArrayAdapter<Apps> categoryAdapter;
    private ArrayList<Apps> categorysList = new ArrayList<>();

    Context ctx;

    String response, TAG = MainActivity.class.getSimpleName(), key = "JsonData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        ctx = this;

        absListView = (AbsListView) findViewById(R.id.list_apps);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new loadContent().execute();
            }
        });

        categoryAdapter = new AppCategoryAdapter(this, R.layout.item_ablistview_smartphone, categorysList);
        absListView.setAdapter(categoryAdapter);

        jsonData = new JsonData(ctx);

    }

    private class loadContent extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            pDialog = new ProgressDialog(ListApps.this);
            pDialog.setMessage("Cargando...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {

            try {
                response = ApiCall.GET(OkHttpConnections.wsConnect);
                // first Try Catch
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Parse the response string here
            if (response != null) {
                JSONArray jsonArray = null;
                JSONArray jsonArray1 = null;
                //JSONObject jsonLabel;
                JSONObject jsonName;
                String imImage = null;
                String imImage2 = null, imImage3 = null, imImage4 = null;
                String imSummary = null, imSummary2 = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String feed;
                    feed = jsonObject.getString("feed");
                    //Log.d("Object 1", feed);
                    JSONObject jsonObject1 = new JSONObject(feed);
                    /*String entry;
                    entry = jsonObject1.getString("entry");*/
                    //Log.d("Object 2", entry);
                    jsonArray = jsonObject1.getJSONArray("entry");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = null;
                    JSONObject jsonObject3;
                    JSONObject jsonObject4;
                    try {
                        jsonObject2 = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String imName = null;
                    String imName2 = null;
                    try {
                        imName = jsonObject2.getString("im:name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try{
                        jsonObject3 = new JSONObject(imName);
                        imName2 = jsonObject3.getString("label");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    try {
                        //jsonLabel = new JSONObject(imName);
                        imImage = jsonObject2.getString("im:image");
                        Log.d("imImage", imImage);
                        jsonArray1 = new JSONArray(imImage);
                        Log.d("jsonArray1", jsonArray1.toString());
                        for (int i1 = 0; i1 < jsonArray1.length(); i1++) {
                            //JSONArray childJsonArray = jsonArray1.getJSONArray(i1);
                            JSONObject object = jsonArray1.getJSONObject(i1);
                            imImage2 = object.getString("label");
                            //imImage3 = object.getString("label");
                            //imImage4 = object.getString("label");
                            Log.d("img url", imImage2);
                            //Log.d("img url", imImage3);
                            //Log.d("img url", imImage4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Log.d("Name", imCategoryName);
                    try{
                        //jsonName = new JSONObject(imCategoryName);
                        imSummary = jsonObject2.getString("summary");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    try{
                        jsonObject4 = new JSONObject(imSummary);
                        imSummary2 = jsonObject4.getString("label");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    Log.d("Name", imName2);
                    //Log.d("Image", imImage);
                    Log.d("Summary", imSummary2);
                    jsonData.saveAppName("name", imName2);
                    jsonData.saveAppImage("image", imImage2);
                    jsonData.saveAppSummary("summary", imSummary2);

                    final Apps apps = new Apps();
                    apps.setName(imName2);
                    apps.setImage(imImage2);
                    apps.setSummary(imSummary2);
                    //Log.d("Category", imCategory);
                    categorysList.add(apps);

                }
                        /*runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                categoryAdapter.notifyDataSetChanged();
                            }
                        });*/
                //categoryAdapter.notifyDataSetChanged();
                // end of If
            }else {
                Log.e(TAG, "No se pudo leer el Json");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ctx, "No se pudieron cargar los datos", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();

            categoryAdapter.notifyDataSetChanged();

        }

    }

    private void hidePDialog(){

        if (pDialog != null){
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        hidePDialog();
    }

}
