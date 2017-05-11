package com.example.adiputra.bukapesanan;

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

public class InputPesananActivity extends AppCompatActivity {

    private static final String CATEGORIES = "https://api.bukalapak.com/v2/categories.json";
    private RequestQueue requestQueue;
    private Gson gson;

//    String[] DayOfWeek = {"Sunday", "Monday", "Tuesday",
//            "Wednesday", "Thursday", "Friday", "Saturday"};

    public String[] DayOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pesanan);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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

        requestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        fetchPosts();
    }

    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, CATEGORIES, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("InputPesananActivity", error.toString());
            //tvList.append("Please Check Internet Connection\n");
        }
    };

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);
            ModelGetCategories posts = gson.fromJson(response, ModelGetCategories.class);
            Log.i("PostActivity", String.valueOf(posts.status) + " " + posts.categories.toString());
            String filter = posts.categories.toString().replace("[","");
            DayOfWeek = filter.split(",");
            array();
        }

    };

    public void array(){
        Spinner mySpinner = (Spinner)findViewById(R.id.spinnerKategori);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_kategory, R.id.weekofday, DayOfWeek);
        mySpinner.setAdapter(adapter);
    }
}
