package com.example.adiputra.bukapesanan;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aji Guna on 12/05/2017.
 */

public class FragmentPesanan extends Fragment {

    private RequestQueue requestQueue;
    private Gson gson;

    public String user_id;

    public FragmentPesanan(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pesanan, container, false);
        final Button btnPesanan = (Button) view.findViewById(R.id.btnPesanan);
        Button btnListPesanan = (Button) view.findViewById(R.id.btnListPesanan);

        //open --JSON--
        requestQueue = Volley.newRequestQueue(getActivity().getApplication());
        //requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String AUTH = "https://api.bukalapak.com/v2/authenticate.json";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AUTH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final ModelGetUser mgu = gson.fromJson(response, ModelGetUser.class);
                        Toast.makeText(getActivity().getApplication(),"ID : "+mgu.getUser_id(),Toast.LENGTH_LONG).show();
                        user_id = String.valueOf(mgu.getUser_id());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplication(),"Fragment Pesanan : "+error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Content-Type", "application/json; charset=utf-8");
                String creds = String.format("%s:%s","adiputra_utama","adiputra17");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        requestQueue.add(stringRequest);

        btnPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplication(), InputPesananActivity.class);
                i.putExtra("user_id",user_id);
                startActivity(i);
            }
        });

        btnListPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplication(), ListPesananActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}
