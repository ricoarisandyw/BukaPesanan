package com.example.adiputra.bukapesanan;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListPesananActivity extends AppCompatActivity {

    Context context;
    private List<Person> persons = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListPesananAdapter pAdapter;
    public static PopupWindow mPopupWindow;

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

    private void initializeData() {
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

//    public void showPopup() {
//    }

//    public PopupWindow pw;
//    public static void showPopup() {
//        try {
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.popup, (ViewGroup) findViewById(R.id.popup_1));
//            pw = new PopupWindow(layout, 300, 370, true);
//            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
//            Button Close = (Button) layout.findViewById(R.id.close_popup);
//            Close.setOnClickListener(cancel_button);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public View.OnClickListener cancel_button = new View.OnClickListener() {
//        public void onClick(View v) {
//            pw.dismiss();
//        }
//    };

}
