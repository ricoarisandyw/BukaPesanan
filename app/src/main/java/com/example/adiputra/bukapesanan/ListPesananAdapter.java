package com.example.adiputra.bukapesanan;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by adiputra on 5/11/2017.
 */

public class ListPesananAdapter extends RecyclerView.Adapter<ListPesananAdapter.PersonViewHolder> {

    private java.util.List persons;
    final Context context;
    //private RelativeLayout mRelativeLayout;

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Position:" + Integer.toString(getPosition())+ "Name: "+getItemViewType(), Toast.LENGTH_SHORT).show();
                    //showPopup();
                }
            });
        }
    }

    public ListPesananAdapter(java.util.List persons, Context context) {
        this.persons = persons;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pesanan, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int position) {
        ModelListPesanan l = (ModelListPesanan) persons.get(position);
        personViewHolder.personName.setText(l.getName());
        personViewHolder.personAge.setText(l.getAge());
        personViewHolder.personPhoto.setImageResource(l.getPhotoId());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
