package com.example.adiputra.bukapesanan;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aji Guna on 08/05/2017.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView = null;
    private Toolbar mToolbar;
    private RequestQueue requestQueue;
    private Gson gson;
    public String userId;
    public String userToken;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rumah);

        progress=new ProgressDialog(this);
        progress.setMessage("Please Wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        //Set the fragment initially
        FragmentPesanan fragment = new FragmentPesanan();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        mToolbar = (Toolbar)findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_main);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        final TextView tvUserNameProfil = (TextView) header.findViewById(R.id.tvUserNameProfil);
        final ImageView ivAvatar = (ImageView) header.findViewById(R.id.ivAvatar);

        //open --JSON--
        requestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String AUTH = "https://api.bukalapak.com/v2/authenticate.json";
        StringRequest req = new StringRequest(Request.Method.POST, AUTH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.i("Response : ", response);
                            ModelGetUser mgu = gson.fromJson(response, ModelGetUser.class);
                            tvUserNameProfil.setText(mgu.getUser_name());
                            userId = String.valueOf(mgu.getUser_id());
                            userToken = mgu.getToken();

                            //USER INFO
                            String USERINFO = "https://api.bukalapak.com/v2/users/info.json";
                            StringRequest request = new StringRequest(Request.Method.GET, USERINFO,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try{
                                                Log.i("Response : ", response);
                                                ModelGetUserInfo mgui1 = gson.fromJson(response, ModelGetUserInfo.class);
                                                String filter[] = mgui1.getUser().toString().split(",");
                                                Toast.makeText(MainActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                                //Toast.makeText(MainActivity.this, filter[0]+"\n"+filter[1]+"\n"+filter[2], Toast.LENGTH_SHORT).show();
                                                Glide.with(MainActivity.this)
                                                        .load(filter[0])
                                                        .into(ivAvatar);
                                                progress.hide();
                                            }catch(Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e("userInfo : ", error.toString());
                                            Toast.makeText(MainActivity.this, "Cek koneksi internet", Toast.LENGTH_LONG).show();
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
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("GetUser : ", error.toString());
                        Toast.makeText(MainActivity.this, "Cek koneksi internet", Toast.LENGTH_LONG).show();
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
        requestQueue.add(req);
        //close --JSON--
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_pesanan){
            FragmentPesanan fragment = new FragmentPesanan();
            FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_bid) {
            FragmentBid fragment = new FragmentBid();
            FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_proyek) {
            FragmentProyek fragment = new FragmentProyek();
            FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_settings) {
            FragmentPengaturan fragment = new FragmentPengaturan();
            FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
