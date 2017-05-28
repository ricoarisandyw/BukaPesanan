package com.example.adiputra.bukapesanan.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adiputra.bukapesanan.Model.ModelProduk;
import com.example.adiputra.bukapesanan.R;

import java.util.List;

/**
 * Created by rickReaper on 5/27/2017.
 */

public class ListProdukAdapter extends RecyclerView.Adapter<ListProdukAdapter.ListProdukViewHolder> {

    private java.util.List listProduk;
    final Context context;

    public class ListProdukViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvListNamaProduk;
        TextView tvListHargaProduk;

        ListProdukViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cvLapakku);
            tvListNamaProduk = (TextView)itemView.findViewById(R.id.tvNamaProduk);
            tvListHargaProduk = (TextView)itemView.findViewById(R.id.tvHargaProduk);
        }
    }

    public ListProdukAdapter(List<ModelProduk> listProduk, Context context) {
        this.listProduk = (List) listProduk;
        this.context = (Context) context;
    }

    @Override
    public int getItemCount() {
        return listProduk.size();
    }

    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    @Override
    public ListProdukAdapter.ListProdukViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_lapakku, viewGroup, false);
        ListProdukAdapter.ListProdukViewHolder pvh = new ListProdukAdapter.ListProdukViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ListProdukAdapter.ListProdukViewHolder listViewHolder, int position) {
        final ModelProduk l = (ModelProduk) listProduk.get(position);
        listViewHolder.tvListNamaProduk.setText(l.getProducts().get(0).getName());
        listViewHolder.tvListHargaProduk.setText(Integer.toString(l.getProducts().get(0).getPrice()));
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
