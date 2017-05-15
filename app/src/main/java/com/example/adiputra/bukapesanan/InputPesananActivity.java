package com.example.adiputra.bukapesanan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InputPesananActivity extends AppCompatActivity implements View.OnClickListener{

    private RequestQueue requestQueue;
    private Gson gson;

    public String[] DayOfWeek;

    private ProgressDialog progress;

    public EditText etNamaPesanan;
    public Spinner mySpinner;
    public EditText etDeskripsi;
    public Button btnSubmitPesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pesanan);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        requestQueue = Volley.newRequestQueue(InputPesananActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        fetchPosts();

        ImageButton btnMenuInputPesanan = (ImageButton) findViewById(R.id.btnMenuInputPesanan);
        btnMenuInputPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuBuilder menuBuilder =new MenuBuilder(InputPesananActivity.this);
                MenuInflater inflater = new MenuInflater(InputPesananActivity.this);
                inflater.inflate(R.menu.menu_input_pesanan, menuBuilder);
                MenuPopupHelper popup = new MenuPopupHelper(InputPesananActivity.this, menuBuilder, v);
                popup.setForceShowIcon(true);
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.setting:
                                Toast.makeText(InputPesananActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_LONG).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {}
                });
                popup.show();
            }
        });

        ImageButton backBtn = (ImageButton) findViewById(R.id.btnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InputPesananActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        //INPUT
        etNamaPesanan = (EditText) findViewById(R.id.etNamaPesanan);
        mySpinner = (Spinner)findViewById(R.id.spinnerKategori);
        etDeskripsi = (EditText) findViewById(R.id.etDeskripsi);
        btnSubmitPesanan = (Button) findViewById(R.id.btnSubmitPesanan);
        btnSubmitPesanan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnSubmitPesanan){
            if(etNamaPesanan.getText().toString().equals("")){
                Toast.makeText(this, "Nama pesanan masih kosong", Toast.LENGTH_SHORT).show();
            }else if(mySpinner.getSelectedItem().toString().equals("")){
                Toast.makeText(this, "Kategori masih kosong", Toast.LENGTH_SHORT).show();
            }else if(etDeskripsi.getText().toString().equals("")){
                Toast.makeText(this, "Deskripsi masih kosong", Toast.LENGTH_SHORT).show();
            }else{
                progress=new ProgressDialog(this);
                progress.setMessage("Harap tunggu...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.show();
                registerUser();
            }
        }
    }

    private void registerUser(){
        final String namaPesanan = etNamaPesanan.getText().toString().trim();
        final String kategori = mySpinner.getSelectedItem().toString().trim();
        final String deskripsi = etDeskripsi.getText().toString().trim();

        String INPUT_PESANAN_URL = "http://adiputra17.it.student.pens.ac.id/android/BukaPesanan/insert_pesanan.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, INPUT_PESANAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        etNamaPesanan.setText("");
                        etDeskripsi.setText("");
                        progress.hide();
                        Toast.makeText(InputPesananActivity.this,"Data berhasil dimasukkan",Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InputPesananActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("namaPesanan",namaPesanan);
                params.put("kategori",kategori);
                params.put("deskripsi",deskripsi);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private static final String CATEGORIES = "https://api.bukalapak.com/v2/categories.json";
    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, CATEGORIES, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    protected final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("InputPesananActivity : ", error.toString());
        }
    };

    protected final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try{
                Log.i("PostActivity", response);
                //Toast.makeText(InputPesananActivity.this, response, Toast.LENGTH_SHORT).show();
                ModelGetCategories posts = gson.fromJson(response, ModelGetCategories.class);
                Log.i("PostActivity", String.valueOf(posts.status) + " " + posts.categories.toString());
                String filter = posts.categories.toString().replace("[","");
                DayOfWeek = filter.split(",");
                array();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public void array(){
        Spinner mySpinner = (Spinner)findViewById(R.id.spinnerKategori);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_kategory, R.id.weekofday, DayOfWeek);
        mySpinner.setAdapter(adapter);
    }
}
