package com.example.adiputra.bukapesanan.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.bukapesanan.Adapter.ListPesananAdapter;
import com.example.adiputra.bukapesanan.Model.ModelListPesanan;
import com.example.adiputra.bukapesanan.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListPesananActivity extends AppCompatActivity {

    Context context;
    private List<ModelListPesanan> modelListPesanan = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListPesananAdapter adapter;
    public static PopupWindow mPopupWindow;
    private RequestQueue requestQueue;
    private Gson gson;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pesanan);
        ImageButton backBtn = (ImageButton) findViewById(R.id.btnBack);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPesananActivity.this.finish();
            }
        });

        progress = new ProgressDialog(ListPesananActivity.this);
        progress.setMessage("Please Wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        adapter = new ListPesananAdapter(modelListPesanan, context);
        recyclerView = (RecyclerView) findViewById(R.id.listPesanan);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ListPesananActivity.this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String GETALLDATA = "http://adiputra17.it.student.pens.ac.id/android/BukaPesanan/show_all_pesanan.php";

        StringRequest req = new StringRequest(Request.Method.GET, GETALLDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.i("Response : ", response);
                            List<ModelListPesanan> posts = Arrays.asList(gson.fromJson(response, ModelListPesanan[].class));
                            Log.i("PostActivity", posts.size() + " posts loaded.");
                            for (ModelListPesanan post : posts) {
                                Log.i("PostActivity", post + ": " + post.getNama());
                                modelListPesanan.add(new ModelListPesanan(
                                    post.getId(),
                                    post.getNama(),
                                    post.getHarga(),
                                    post.getLokasi(),
                                    post.getDeskripsi()
                                ));
                            }
                            progress.hide();
                            adapter.notifyDataSetChanged();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Get Data : ", error.toString());
                    }
                }
        );
        requestQueue.add(req);
        //initializeData();
    }
}
