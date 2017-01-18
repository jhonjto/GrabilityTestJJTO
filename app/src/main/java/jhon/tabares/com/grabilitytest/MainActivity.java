package jhon.tabares.com.grabilitytest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import jhon.tabares.com.grabilitytest.Adapters.ListCategoryAdapter;
import jhon.tabares.com.grabilitytest.ConstantsStrings.OkHttpConnections;
import jhon.tabares.com.grabilitytest.Models.Categorys;
import jhon.tabares.com.grabilitytest.MyPreferences.JsonData;
import jhon.tabares.com.grabilitytest.WService.ApiCall;

public class MainActivity extends AppCompatActivity {

    private JsonData jsonData;
    private ProgressDialog pDialog;
    //private ListView listView;
    AbsListView absListView;
    private ArrayAdapter<Categorys> categoryAdapter;
    private ArrayList<Categorys> categorysList = new ArrayList<>();

    Context ctx;

    String response, TAG = MainActivity.class.getSimpleName(), key = "JsonData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;

        absListView = (AbsListView) findViewById(R.id.listViewP);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new loadContent().execute();
            }
        });

        categoryAdapter = new ListCategoryAdapter(MainActivity.this, R.layout.item_abslistview, categorysList);
        absListView.setAdapter(categoryAdapter);

        jsonData = new JsonData(ctx);

    }

    private class loadContent extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
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
                        JSONObject jsonLabel = null;
                        JSONObject jsonName = null;
                        String imCategoryName = null;
                        String imNameLabel = null;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String feed;
                            feed = jsonObject.getString("feed");
                            //Log.d("Object 1", feed);
                            JSONObject jsonObject1 = new JSONObject(feed);
                            //String entry;
                            //entry = jsonObject1.getString("entry");
                            //Log.d("Object 2", entry);
                            jsonArray = jsonObject1.getJSONArray("entry");
                            System.out.println("*****JARRAY*****" + jsonArray.length());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = null;
                            try {
                                jsonObject2 = jsonArray.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String imCategory = null;
                            try {
                                imCategory = jsonObject2.getString("category");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                jsonLabel = new JSONObject(imCategory);
                                imCategoryName = jsonLabel.getString("attributes");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Log.d("Name", imCategoryName);
                            try{
                                jsonName = new JSONObject(imCategoryName);
                                imNameLabel = jsonName.getString("label");
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                            Log.d("Attributes", imNameLabel);
                            jsonData.saveCategories("category", imNameLabel);

                            final Categorys categorys = new Categorys();
                            categorys.setCategorys(imNameLabel);
                            //Log.d("Category", imCategory);
                            categorysList.add(categorys);

                        }
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
