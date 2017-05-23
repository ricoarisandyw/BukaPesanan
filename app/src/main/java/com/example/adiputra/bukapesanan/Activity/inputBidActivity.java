package com.example.adiputra.bukapesanan.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class inputBidActivity extends AppCompatActivity {

    TextView txtDetail;
    EditText etHarga, etDeskripsi, etLokasi;
    Button btnBid;

    public String[] DayOfWeek;

    private RequestQueue requestQueue;
    private Gson gson;

    Bundle tvB = getIntent().getExtras();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_bid);

        txtDetail = (TextView) findViewById(R.id.txtDetailPesanan);
        etHarga = (EditText) findViewById(R.id.etHargaBid);
        etDeskripsi = (EditText) findViewById(R.id.etDeskripsiBid);
        etLokasi = (EditText) findViewById(R.id.etLokasiBid);
        btnBid =  (Button) findViewById(R.id.btnSubmitBid);

//        txtDetail.setText("Nama > " + nama);
//        txtDetail.append("Harga  > " + nama);
//        txtDetail.setText("Deskripsi > " + nama);
    }

    private void postData(){
        final String harga = etHarga.getText().toString().trim();
        final String deskripsi = etDeskripsi.getText().toString().trim();
        final String lokasi = etLokasi.getText().toString().trim();
        final String namaPesanan = "";
//        final String gambar = getStringImage(bitmap);
        final String id = tvB.getString("id");
        final String user_id = tvB.getString("user_id");
        //etNamaPesanan.setText(user_id);
        //TextView tvTitleBar = (TextView) findViewById(R.id.tvTitleBar);
        //tvTitleBar.setText(user_id);

        requestQueue = Volley.newRequestQueue(super.getApplication());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String INPUT_PESANAN_URL = "http://adiputra17.it.student.pens.ac.id/android/BukaPesanan/insert_pesanan.php";
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
                params.put("id",lokasi);
                params.put("harga",namaPesanan);
                params.put("deskripsi",deskripsi);
                params.put("lampiran",harga);
                params.put("lokasi",lokasi);
                params.put("user_id",user_id);
                params.put("pesanan_id",user_id);
                params.put("created_at",user_id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private static final String CATEGORIES = "https://api.bukalapak.com/v2/categories.json";
}
