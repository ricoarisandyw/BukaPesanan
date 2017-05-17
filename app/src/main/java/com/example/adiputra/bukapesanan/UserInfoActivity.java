package com.example.adiputra.bukapesanan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        TextView tvUserId = (TextView) findViewById(R.id.tvUserId);
        TextView tvUserToken = (TextView) findViewById(R.id.tvUserToken);
        final TextView tvAvatar = (TextView) findViewById(R.id.tvAvatar);

        final Bundle tvB = getIntent().getExtras();
        tvUserId.setText(tvB.getString("UserId"));
        tvUserToken.setText(tvB.getString("UserToken"));

        requestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String URL = "https://api.bukalapak.com/v2/users/info.json";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("user_id", tvB.getString("UserId"));
        params.put("token", tvB.getString("UserToken"));

//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("user_id", tvB.getString("UserId"));
//            obj.put("token", tvB.getString("UserToken"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if (statusCode < 200 || statusCode > 405) {
//            throw new IOException();
//        }

        StringRequest req = new StringRequest(Request.Method.GET, URL,
        //JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(
                //params),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.i("Response : ", response);
                            ModelGetUserInfo.User mgui = gson.fromJson(response, ModelGetUserInfo.User.class);
                            Log.i("Response : ", mgui.toString());
                            //Toast.makeText(UserInfoActivity.this, "Avatar : "+mgui.getAvatar(), Toast.LENGTH_SHORT).show();
                            //tvAvatar.setText(response.toString());
                            //tvAvatar.setText(mgui.getAvatar());
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("userInfo : ", error.toString());
                    }
                }
        ){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Content-Type", "application/json; charset=utf-8");
                String creds = String.format("%s:%s",tvB.getString("UserId"),tvB.getString("UserToken"));
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        requestQueue.add(req);
    }
}
