package com.example.adiputra.bukapesanan.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

        ListPesananViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            //ivListGambarPesanan = (ImageView)itemView.findViewById(R.id.ivListGambarPesanan);
            tvListNamaPesanan = (TextView)itemView.findViewById(R.id.tvListNamaPesanan);
            tvListHargaPesanan = (TextView)itemView.findViewById(R.id.tvListHargaPesanan);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Position:" + Integer.toString(getPosition())+ "\nName: "+getItemViewType(), Toast.LENGTH_SHORT).show();
                }
            });
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
    public void onBindViewHolder(ListPesananViewHolder listViewHolder, int position) {
        ModelListPesanan l = (ModelListPesanan) listPesanan.get(position);
        //personViewHolder.ivListGambarPesanan.setImageResource(Integer.parseInt(l.getGambar()));
        listViewHolder.tvListNamaPesanan.setText(l.getNama());
        listViewHolder.tvListHargaPesanan.setText(l.getHarga());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
