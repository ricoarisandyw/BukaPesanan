package com.example.adiputra.bukapesanan.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.adiputra.bukapesanan.Adapter.ListProdukAdapter;
import com.example.adiputra.bukapesanan.Adapter.ListTawaranAdapter;
import com.example.adiputra.bukapesanan.Model.ModelBid;
import com.example.adiputra.bukapesanan.Model.ModelGetUserInfo;
import com.example.adiputra.bukapesanan.Model.ModelProduk;
import com.example.adiputra.bukapesanan.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class lapakkuActivity extends AppCompatActivity {
    Context context;
    private RequestQueue requestQueue;
    private Gson gson;

    private ListProdukAdapter adapter;

    String USER_ID;
    String TOKEN;

    private RecyclerView recyclerView;
    TextView dummy;

    private List<ModelProduk> modelProduk = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapakku);

        USER_ID = loadData("user_id");
        TOKEN = loadData("user_token");

        adapter = new ListProdukAdapter(modelProduk , context);
        recyclerView = (RecyclerView) findViewById(R.id.listProduk);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(lapakkuActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        lapakku();
    }

    public void lapakku(){
        //open --JSON--
        requestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        //USER INFO
        String USERINFO = "https://api.bukalapak.com/v2/products/mylapak.json";
        StringRequest request = new StringRequest(Request.Method.GET, USERINFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.i("Responsenya : ", response);
                            ModelProduk mp = gson.fromJson(response, ModelProduk.class);
                            for (int i = 0;i<mp.getProducts().size();i++) {
                                Log.i("Responsenya 2: ", mp.getProducts().get(i).getName());
                                modelProduk.add(new ModelProduk(
                                        mp.getProducts().get(i).getName(),
                                        mp.getProducts().get(i).getId(),
                                        mp.getProducts().get(i).getCity(),
                                        mp.getProducts().get(i).getPrice()
                                ));
                            }
                            adapter.notifyDataSetChanged();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("userInfo : ", error.toString());
                        Toast.makeText(lapakkuActivity.this, "Cek koneksi internet", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Content-Type", "application/json; charset=utf-8");
                String creds = String.format("%s:%s",USER_ID,TOKEN);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        requestQueue.add(request);
    }

    public String loadData(String name){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        String data = prefs.getString(name,"");
        Log.d(name + " keluar:", name + " > " +data);
        return data;
    }
}
