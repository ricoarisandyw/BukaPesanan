package com.example.adiputra.bukapesanan.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.bukapesanan.Activity.ListPesananActivity;
import com.example.adiputra.bukapesanan.Activity.MainActivity;
import com.example.adiputra.bukapesanan.Adapter.ListPesananAdapter;
import com.example.adiputra.bukapesanan.Model.ModelListPesanan;
import com.example.adiputra.bukapesanan.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Aji Guna on 12/05/2017.
 */

public class FragmentBid extends Fragment{

    Context context;
    private List<ModelListPesanan> modelListPesanan = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListPesananAdapter adapter;
    public static PopupWindow mPopupWindow;
    private RequestQueue requestQueue;
    private Gson gson;
    private ProgressBar spinner;

    public FragmentBid(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bid, container, false);
        //        TODO(1) : (Complete) Tampilkan daftar semua pesanan dan bisa di klik
        TextView message = (TextView) view.findViewById(R.id.fragmentBidMessage);
        message.setText("Loading . . .");
        adapter = new ListPesananAdapter(modelListPesanan, context);
        recyclerView = (RecyclerView) view.findViewById(R.id.listPesananBid);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String GETALLDATA = "http://adiputra17.it.student.pens.ac.id/android/BukaPesanan/show_all_pesanan.php";

        StringRequest req = new StringRequest(Request.Method.GET, GETALLDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.i("Response : ", response);
                            List<ModelListPesanan> posts = Arrays.asList(gson.fromJson(response, ModelListPesanan[].class));
                            Log.i("PostActivity", posts.size() + " posts loaded.");
                            for (ModelListPesanan post : posts) {
                                Log.i("PostActivity", post + ": " + post.getNama());
                                modelListPesanan.add(new ModelListPesanan(
                                        post.getId(),
                                        post.getNama(),
                                        post.getHarga()
                                ));
                            }
                            adapter.notifyDataSetChanged();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Get Data : ", error.toString());
                    }
                }
        );
        requestQueue.add(req);

//        TODO(2) : Filter Pesanan

        message.setText("1. Pilih Pesanan yang kamu sanggupi.");
        return view;
    }
}