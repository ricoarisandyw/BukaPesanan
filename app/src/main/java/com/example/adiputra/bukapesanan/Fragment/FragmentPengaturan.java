package com.example.adiputra.bukapesanan.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adiputra.bukapesanan.R;

/**
 * Created by Aji Guna on 12/05/2017.
 */

public class FragmentPengaturan extends Fragment {

    public FragmentPengaturan(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);



        return view;
    }
}
