package it.ilfuma.rc.casteldileva;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import it.ilfuma.rc.casteldileva.Database.AppShopDatabase;
import it.ilfuma.rc.casteldileva.Database.Shop;

abstract class VolleyShop implements Response.ErrorListener, Response.Listener<String> {
    private Context mContext;
    private AppShopDatabase mDB;

    VolleyShop(Context context, AppShopDatabase db) {
        mContext = context;
        mDB = db;
    }

    VolleyShop(Context context){
        mContext = context;
    }

    abstract void fill(List<Shop> snt);

    public void searchShopsByCategoryId(String id) {
        if (Integer.parseInt(id) < 10){
            String url = "https://my-json-server.typicode.com/fabiobf96/shopJson/shops?categoryId=%s";
            url = String.format(url, id);
            apiCall(url);
        }
        else {
            String url = "https://my-json-server.typicode.com/fabiobf96/shop1Json/shops?categoryId=%s";
            url = String.format(url, id);
            apiCall(url);
        }
    }

    private void apiCall(String url) {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this, this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // TO DO
    }

    @Override
    public void onResponse(String response) {
        Gson gson = new Gson();
        String shops = response.replaceAll("\n", "");
        try {
            Type listView = new TypeToken<List<Shop>>() {
            }.getType();
            List<Shop> snt = gson.fromJson(shops, listView);
            if (snt != null && snt.size() > 0) {
                Log.w("CA", "" + snt.size());
                mDB.shopDAO().insertAll(snt);
                fill(snt);
            }
        } catch (Exception e) {
            //TO DO
        }
    }
}
