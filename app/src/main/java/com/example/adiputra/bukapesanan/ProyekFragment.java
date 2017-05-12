package com.example.adiputra.bukapesanan;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Aji Guna on 12/05/2017.
 */

public class ProyekFragment extends Fragment {

    public ProyekFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_proyek, container, false);
    }
}
