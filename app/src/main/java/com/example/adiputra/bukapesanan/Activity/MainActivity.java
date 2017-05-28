package com.example.adiputra.bukapesanan.Activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.example.adiputra.bukapesanan.Fragment.FragmentBid;
import com.example.adiputra.bukapesanan.Fragment.FragmentPengaturan;
import com.example.adiputra.bukapesanan.Fragment.FragmentPesanan;
import com.example.adiputra.bukapesanan.Fragment.FragmentProyek;
import com.example.adiputra.bukapesanan.Model.ModelGetUserInfo;
import com.example.adiputra.bukapesanan.Model.ModelProduk;
import com.example.adiputra.bukapesanan.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aji Guna on 08/05/2017.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView = null;
    private Toolbar mToolbar;
    private RequestQueue requestQueue;
    private Gson gson;

    CardView cv;

    public static String USER_ID;
    public static String TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        USER_ID = getIntent().getStringExtra("userId");
        TOKEN = getIntent().getStringExtra("userToken");

        if(USER_ID==null && TOKEN==null){
            USER_ID = loadData("username");
            TOKEN = loadData("password");
        }

        Toast.makeText(MainActivity.this, USER_ID+" : "+TOKEN, Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rumah);

        cv = (CardView)findViewById(R.id.cv);
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
                            tvUserNameProfil.setText(filter[1]);
                            Glide.with(MainActivity.this)
                                    .load(filter[0])
                                    .into(ivAvatar);
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
                String creds = String.format("%s:%s",USER_ID,TOKEN);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        requestQueue.add(request);
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
        }else if(id == R.id.nav_logout){
            deleteData("username");
            deleteData("password");
            Intent i = new Intent( MainActivity.this , activityLogin.class);
            startActivity(i);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getMyData() {
        return USER_ID;
    }

    public void deleteData(String name){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(name, "");
        Log.d("Hapus Data:", "");
        editor.commit();
    }

    public String loadData(String name){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        String data = prefs.getString(name,"");
        Log.d(name + " keluar:", data);
        return data;
    }

    public boolean hasResponse(){
        return true;
    }
}
