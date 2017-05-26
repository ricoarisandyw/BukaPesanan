package com.example.adiputra.bukapesanan.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.bukapesanan.Model.ModelGetCategories;
import com.example.adiputra.bukapesanan.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class inputBidActivity extends AppCompatActivity {

    TextView txtDetail;
    EditText etHarga, etDeskripsi, etLokasi;
    Button btnBid;

    public String[] DayOfWeek;

    private RequestQueue requestQueue;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_bid);
        txtDetail = (TextView) findViewById(R.id.txtDetailPesanan);
        etHarga = (EditText) findViewById(R.id.etHargaBid);
        etDeskripsi = (EditText) findViewById(R.id.etDeskripsiBid);
        etLokasi = (EditText) findViewById(R.id.etLokasiBid);
        btnBid =  (Button) findViewById(R.id.btnSubmitBid);
        String idBid = getIntent().getStringExtra("id");
        String namaBid = getIntent().getStringExtra("nama");
        String lokasiBid = getIntent().getStringExtra("lokasi");
        String hargaBid = getIntent().getStringExtra("harga");
        String deskripsi = getIntent().getStringExtra("deksripsi");
        Toast.makeText(inputBidActivity.this, idBid, Toast.LENGTH_LONG).show();
        txtDetail.setText("id > " + idBid);
        txtDetail.append("\nNama  > " + namaBid);
        txtDetail.append("\nHarga > " + hargaBid);
        txtDetail.append("\nLokasi > " + lokasiBid);
        txtDetail.append("\nDeskripsi > " + deskripsi);

        btnBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etHarga.getText().toString().equals("")){
                    Toast.makeText(inputBidActivity.this, "Something Missing", Toast.LENGTH_LONG).show();
                }else{
                    postData();
                    Intent i = new Intent(inputBidActivity.this, listTawaranku.class);
                    startActivity(i);
                }
            }
        });
    }
    //        TODO(4) : Masukkan data ke daftar tawaranku > Bisa Ubah,Hapus.
    private void postData(){
        final String harga = etHarga.getText().toString().trim();
        final String deskripsi = etDeskripsi.getText().toString().trim();
        final String lokasi = etLokasi.getText().toString().trim();
        final String namaPesanan = "";
        final String user_id = "";
//        final String gambar = getStringImage(bitmap);

        requestQueue = Volley.newRequestQueue(super.getApplication());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String INPUT_PESANAN_URL = "http://adiputra17.it.student.pens.ac.id/android/BukaPesanan/create_bid.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, INPUT_PESANAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(inputBidActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(inputBidActivity.super.getApplicationContext(),"Cek Koneksi Internet"+"\n",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                String user_id = loadData("user_id");
                Date date = new Date();
                String now = Integer.toString(date.getDate());
                params.put("id","");
                params.put("harga",harga);
                params.put("deskripsi",deskripsi);
                params.put("lampiran",harga);
                params.put("lokasi",lokasi);
                params.put("user_id",user_id);
                params.put("pesanan_id",user_id);
                params.put("created_at",now);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public String loadData(String name){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        String data = prefs.getString(name,"");
        Log.d(name + " keluar:", data);
        return data;
    }
}
