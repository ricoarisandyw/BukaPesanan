package com.example.adiputra.bukapesanan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String AUTH = "https://api.bukalapak.com/v2/authenticate.json";
    private RequestQueue requestQueue;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sentBtn = (Button) findViewById(R.id.btnLogin);
        Button formBtn = (Button) findViewById(R.id.btnForm);
        Button listBtn = (Button) findViewById(R.id.btnList);
        final TextView tvUser = (TextView) findViewById(R.id.tvUser);

        sentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        formBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InputPesananActivity.class);
                startActivity(i);
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListPesananActivity.class);
                startActivity(i);
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        //fetchPosts();

        final String URL = "https://api.bukalapak.com/v2/authenticate.json";
        // Post params to be sent to the server
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("username","");
//        params.put("password","");
        //params.put("adiputra_utama:adiputra17", "adiputra_utama:adiputra17");

        StringRequest req = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.i("Response", response);
                        ModelGetUser mgu = gson.fromJson(response, ModelGetUser.class);
                        tvUser.setText("SELAMAT DATANG : "+ mgu.getUser_name());
                        Toast.makeText(MainActivity.this, "Token : "+mgu.getToken(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("ERROR","error => "+error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s","adiputra_utama","adiputra17");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<String, String>();
//                //headers.put("username:password", "adiputra_utama:adiputra17");
////                headers.put("adiputra_utama", "username");
////                headers.put("adiputra17", "password");
//                //params.put("adiputra_utama","adiputra17");
//                headers.put("username", "adiputra_utama");
//                headers.put("password", "adiputra17");
//                return headers;
//            }
        };

//        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
//            new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        Log.i("Response : ", response.toString(4));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.i("Error : ", error.getMessage());
//                }
//            }){
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("username", "adiputra_utama");
//                headers.put("password", "adiputra17");
//                return headers;
//            }
//
//        };
        requestQueue.add(req);
    }
}
