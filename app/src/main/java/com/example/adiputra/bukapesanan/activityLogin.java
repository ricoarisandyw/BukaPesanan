package com.example.adiputra.bukapesanan;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class activityLogin extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView = null;
    private Toolbar mToolbar;
    private RequestQueue requestQueue;
    private Gson gson;
    public String userId;
    public String userToken;

    private ProgressDialog progress;

    EditText nametxt;
    EditText passtxt;
    Button btnlogin;

    public boolean STATUS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        nametxt = (EditText) findViewById(R.id.nameText);
        passtxt = (EditText) findViewById(R.id.passText);
        btnlogin = (Button) findViewById(R.id.btnLogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekLog(nametxt.getText().toString(), passtxt.getText().toString());
                Log.d("Cek STATUS : ", Boolean.toString(getSTATUS()));
                if (getSTATUS()) {
                    Intent i = new Intent(activityLogin.this, MainActivity.class);
                    Log.d("pindah Acitivty : ", Boolean.toString(getSTATUS()));
                    //Mengirim Data ke Activity lain.
                    try {
                        i.putExtra(MainActivity.USER_NAME, nametxt.getText().toString());
                        i.putExtra(MainActivity.PASSWORD, passtxt.getText().toString());
                    }catch (Exception e){
                        Toast.makeText(activityLogin.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                    startActivity(i);
                } else {
                    Log.d("pindah Acitivty : ", Boolean.toString(getSTATUS()));
                }
            }
        });
    }


    public boolean cekLog(final String uname, final String pass){
        //open --JSON--
        requestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String AUTH = "https://api.bukalapak.com/v2/authenticate.json";
        StringRequest req = new StringRequest(Request.Method.POST, AUTH,methodRes()
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("GetUser : ", error.toString());
                        Toast.makeText(activityLogin.this, "Cek koneksi internet", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Content-Type", "application/json; charset=utf-8");
                String creds = String.format("%s:%s",uname,pass);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);

                return headers;
            }
        };
        requestQueue.add(req);
        Log.i("isiSTATUS : ", Boolean.toString(STATUS));
        return this.STATUS;
        //close --JSON--
    }
    public void becomeTrue(){
        Log.i("Berjalan : ", Boolean.toString(STATUS));
        this.STATUS = true;
    }
    public Response.Listener<String> methodRes(){
        Response.Listener<String> resList = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Log.i("Response POST : ", response);
                    ModelGetUser mgu = gson.fromJson(response, ModelGetUser.class);
                    userId = String.valueOf(mgu.getUser_id());
                    userToken = mgu.getToken();

                    //USER INFO
                    String USERINFO = "https://api.bukalapak.com/v2/users/info.json";
                    StringRequest request = new StringRequest(Request.Method.GET, USERINFO,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{
                                        Log.i("Response GET : ", response);
                                        ModelGetUserInfo mgui1 = gson.fromJson(response, ModelGetUserInfo.class);
                                        String filter[] = mgui1.getUser().toString().split(",");
                                        Toast.makeText(activityLogin.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                        becomeTrue();
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("userInfo : ", error.toString());
                                    Toast.makeText(activityLogin.this, "Cek koneksi internet", Toast.LENGTH_LONG).show();
                                }
                            }
                    ){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/x-www-form-urlencoded");
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            String creds = String.format("%s:%s",userId,userToken);
                            String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                            headers.put("Authorization", auth);
                            return headers;
                        }
                    };
                    requestQueue.add(request);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        return resList;
    }
    public boolean getSTATUS(){
        return this.STATUS;
    }
}