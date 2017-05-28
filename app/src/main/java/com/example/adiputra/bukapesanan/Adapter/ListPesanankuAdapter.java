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

public class ListPesanankuAdapter extends RecyclerView.Adapter<ListPesanankuAdapter.ListPesanankuViewHolder> {

    private java.util.List listPesananku;
    final Context context;

    public class ListPesanankuViewHolder extends RecyclerView.ViewHolder {
        CardView cv2;
        //ImageView ivListGambarPesanan;
        TextView tvListNamaPesananku;
        TextView tvListKategoriPesananku;
        TextView tvListHargaPesananku;
        TextView tvListDeskripsiPesananku;
        TextView tvListLokasiPesananku;
        TextView tvListTanggalPesananku;

        ListPesanankuViewHolder(View itemView) {
            super(itemView);
            cv2 = (CardView)itemView.findViewById(R.id.cv2);
            //ivListGambarPesanan = (ImageView)itemView.findViewById(R.id.ivListGambarPesanan);
            tvListNamaPesananku = (TextView)itemView.findViewById(R.id.tvNamaProduk);
            tvListKategoriPesananku = (TextView)itemView.findViewById(R.id.tvKategoriProduk);
            tvListHargaPesananku = (TextView)itemView.findViewById(R.id.tvHargaProduk);
            tvListDeskripsiPesananku = (TextView)itemView.findViewById(R.id.tvDeskripsiPesanan2);
            tvListLokasiPesananku = (TextView)itemView.findViewById(R.id.tvLokasiPesanan2);
            tvListTanggalPesananku = (TextView)itemView.findViewById(R.id.tvTanggalPesanan2);

            cv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Position:" + Integer.toString(getPosition())+ "\nName: "+getItemViewType(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public ListPesanankuAdapter(List<ModelListPesanan> listPesanan, Context context) {
        this.listPesananku = (List) listPesanan;
        this.context = (Context) context;
    }

    @Override
    public int getItemCount() {
        return listPesananku.size();
    }

    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    @Override
    public ListPesanankuAdapter.ListPesanankuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pesananku, viewGroup, false);
        ListPesanankuAdapter.ListPesanankuViewHolder pvh = new ListPesanankuAdapter.ListPesanankuViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ListPesanankuAdapter.ListPesanankuViewHolder listViewHolder, int position) {
        ModelListPesanan l = (ModelListPesanan) listPesananku.get(position);
        //personViewHolder.ivListGambarPesanan.setImageResource(Integer.parseInt(l.getGambar()));
        listViewHolder.tvListNamaPesananku.setText(l.getNama());
        listViewHolder.tvListKategoriPesananku.setText(l.getKategori());
        listViewHolder.tvListHargaPesananku.setText(l.getHarga());
        listViewHolder.tvListDeskripsiPesananku.setText(l.getDeskripsi());
        listViewHolder.tvListLokasiPesananku.setText(l.getLokasi());
        listViewHolder.tvListTanggalPesananku.setText(l.getCreated_at());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
