package com.example.adiputra.bukapesanan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adiputra.bukapesanan.Activity.inputBidActivity;
import com.example.adiputra.bukapesanan.Model.ModelBid;
import com.example.adiputra.bukapesanan.Model.ModelListPesanan;
import com.example.adiputra.bukapesanan.R;

import java.util.List;

/**
 * Created by rickReaper on 5/23/2017.
 */

public class ListTawaranAdapter extends RecyclerView.Adapter<ListTawaranAdapter.ListTawaranViewHolder> {

    private java.util.List listTawaran;
    final Context context;

    public class ListTawaranViewHolder extends RecyclerView.ViewHolder {
        CardView cv;

        TextView tvListNamaTawaran;
        TextView tvListHargaTawaran;

        ListTawaranViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cvTawaran);
            tvListNamaTawaran = (TextView)itemView.findViewById(R.id.tvListNamaTawaran);
            tvListHargaTawaran = (TextView)itemView.findViewById(R.id.tvListHargaTawaran);
        }
    }

    public ListTawaranAdapter(List<ModelBid> listTawaran, Context context) {
        this.listTawaran = (List) listTawaran;
        this.context = (Context) context;
    }

    @Override
    public int getItemCount() {
        return listTawaran.size();
    }

    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    @Override
    public ListTawaranViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_tawaran, viewGroup, false);
        ListTawaranViewHolder pvh = new ListTawaranViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ListTawaranViewHolder listViewHolder, int position) {
        final ModelBid l = (ModelBid) listTawaran.get(position);
        listViewHolder.tvListNamaTawaran.setText(l.getLokasi());
        listViewHolder.tvListHargaTawaran.setText(l.getHarga());
        //        TODO : Edit Bid
        listViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
