package com.example.adiputra.bukapesanan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListPesanankuActivity extends AppCompatActivity {

    Context context;
    private List<ModelListPesanan> modelListPesananku = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListPesanankuAdapter adapter;
    public static PopupWindow mPopupWindow;
    private RequestQueue requestQueue;
    private Gson gson;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pesananku);

        ImageButton backBtn = (ImageButton) findViewById(R.id.btnBack);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListPesanankuActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        spinner=(ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        adapter = new ListPesanankuAdapter(modelListPesananku, context);
        recyclerView = (RecyclerView) findViewById(R.id.listPesananku);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        Bundle tvB = getIntent().getExtras();
        final String user_id = tvB.getString("user_id");
        //Toast.makeText(ListPesanankuActivity.this, "USER_ID : "+user_id, Toast.LENGTH_SHORT).show();
        String GETALLMYDATA = "http://adiputra17.it.student.pens.ac.id/android/BukaPesanan/show_all_pesananku.php?user_id=";
        String url = GETALLMYDATA+user_id;
        StringRequest req = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.i("Response : ", response);
                            //Toast.makeText(ListPesanankuActivity.this, response, Toast.LENGTH_LONG).show();
                            List<ModelListPesanan> posts = Arrays.asList(gson.fromJson(response, ModelListPesanan[].class));

                            Log.i("PostActivity", posts.size() + " posts loaded.");
                            for (ModelListPesanan post : posts) {
                                Log.i("PostActivity", post + ": " + post.getNama());
                                //Toast.makeText(ListPesanankuActivity.this, post.getNama()+" "+post.getUser_id(), Toast.LENGTH_SHORT).show();
                                modelListPesananku.add(new ModelListPesanan(
                                        //post.getId(),
                                        post.getNama(),
                                        post.getKategori(),
                                        post.getHarga(),
                                        post.getDeskripsi(),
                                        //post.getGambar(),
                                        post.getLokasi(),
                                        post.getCreated_at()
                                ));
                            }
                            spinner.setVisibility(View.GONE);
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
                        Toast.makeText(ListPesanankuActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id",user_id);
                return params;
            }
        };
        requestQueue.add(req);
    }
}
