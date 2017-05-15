package com.example.adiputra.bukapesanan;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Aji Guna on 12/05/2017.
 */

public class FragmentPesanan extends Fragment {

    public FragmentPesanan(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pesanan, container, false);
        Button btnPesanan = (Button) view.findViewById(R.id.btnPesanan);
        Button btnListPesanan = (Button) view.findViewById(R.id.btnListPesanan);

        btnPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplication(), InputPesananActivity.class);
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
