package com.example.adiputra.bukapesanan.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.example.adiputra.bukapesanan.Activity.InputPesananActivity;
import com.example.adiputra.bukapesanan.Activity.ListPesananActivity;
import com.example.adiputra.bukapesanan.Activity.ListPesanankuActivity;
import com.example.adiputra.bukapesanan.Activity.MainActivity;
import com.example.adiputra.bukapesanan.R;
import com.google.gson.Gson;

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
        Button btnListPesananku = (Button) view.findViewById(R.id.btnListPesananku);
//        Button btnProyekku = (Button) view.findViewById(R.id.btnProyekku);
//        Button btnTawaranku = (Button) view.findViewById(R.id.btnTawaranku);

        MainActivity activity = (MainActivity) getActivity();
        final String USER_ID = activity.getMyData();
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
        btnListPesananku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplication(), ListPesanankuActivity.class);
                i.putExtra("user_id",USER_ID);
                startActivity(i);
            }
        });
        return view;
    }
}