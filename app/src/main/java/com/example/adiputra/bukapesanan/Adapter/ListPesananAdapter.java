package com.example.adiputra.bukapesanan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adiputra.bukapesanan.Activity.inputBidActivity;
import com.example.adiputra.bukapesanan.Model.ModelListPesanan;
import com.example.adiputra.bukapesanan.R;

import java.util.List;

/**
 * Created by adiputra on 5/11/2017.
 */

public class ListPesananAdapter extends RecyclerView.Adapter<ListPesananAdapter.ListPesananViewHolder> {

    private java.util.List listPesanan;
    final Context context;

    public class ListPesananViewHolder extends RecyclerView.ViewHolder {
        CardView cv;

        //ImageView ivListGambarPesanan;
        TextView tvListNamaPesanan;
        TextView tvListHargaPesanan;

        ListPesananViewHolder(final View itemView) {
            super(itemView);
            //ivListGambarPesanan = (ImageView)itemView.findViewById(R.id.ivListGambarPesanan);
            cv = (CardView) itemView.findViewById(R.id.cv);
            tvListNamaPesanan = (TextView)itemView.findViewById(R.id.tvListNamaPesanan);
            tvListHargaPesanan = (TextView)itemView.findViewById(R.id.tvListHargaPesanan);
        }
    }

    public ListPesananAdapter(List<ModelListPesanan> listPesanan, Context context) {
        this.listPesanan = (List) listPesanan;
        this.context = (Context) context;
    }

    @Override
    public int getItemCount() {
        return listPesanan.size();
    }

    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    @Override
    public ListPesananViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pesanan, viewGroup, false);
        ListPesananViewHolder pvh = new ListPesananViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ListPesananViewHolder listViewHolder, int position) {
        final ModelListPesanan l = (ModelListPesanan) listPesanan.get(position);
        //personViewHolder.ivListGambarPesanan.setImageResource(Integer.parseInt(l.getGambar()));
        listViewHolder.tvListNamaPesanan.setText(l.getNama());
        listViewHolder.tvListHargaPesanan.setText(l.getHarga());
        //        TODO(3) : (Completed) Lakukan Bid pada pesanan yang dipilih -> Buka layout pesanan
        listViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), inputBidActivity.class);
                i.putExtra("id", String.valueOf(l.getId()));
                i.putExtra("nama", String.valueOf(l.getNama()));
                i.putExtra("harga", String.valueOf(l.getHarga()));
                i.putExtra("lokasi", String.valueOf(l.getLokasi()));
                i.putExtra("deskripsi", String.valueOf(l.getDeskripsi()));
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
