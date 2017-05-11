package com.example.adiputra.bukapesanan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListPesananActivity extends AppCompatActivity {

    Context context;
    private List<Person> persons = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListPesananAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pesanan);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ImageButton backBtn = (ImageButton) findViewById(R.id.btnBack);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListPesananActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        pAdapter = new ListPesananAdapter(persons, context);
        recyclerView = (RecyclerView) findViewById(R.id.listPesanan);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ListPesananActivity.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);

//        recyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//                        // do whatever
//                    }
//
//                    @Override public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );

        initializeData();
    }

    private void initializeData(){
        persons.add(new Person("Tas Cantik", "100000-200000", R.drawable.tas));
        persons.add(new Person("Tas Cantik", "100000-200000", R.drawable.tas));
        persons.add(new Person("Tas Cantik", "100000-200000", R.drawable.tas));
        persons.add(new Person("Tas Cantik", "100000-200000", R.drawable.tas));
        persons.add(new Person("Tas Cantik", "100000-200000", R.drawable.tas));
        persons.add(new Person("Tas Cantik", "100000-200000", R.drawable.tas));
        persons.add(new Person("Tas Cantik", "100000-200000", R.drawable.tas));
        persons.add(new Person("Tas Cantik", "100000-200000", R.drawable.tas));
        pAdapter.notifyDataSetChanged();
    }
}

