package com.example.adiputra.bukapesanan.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.util.Base64;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.bukapesanan.Model.ModelGetCategories;
import com.example.adiputra.bukapesanan.R;
import com.example.adiputra.bukapesanan.Helper.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputPesananActivity extends AppCompatActivity implements View.OnClickListener{

    private RequestQueue requestQueue;
    private Gson gson;

    public String[] DayOfWeek;

    private ProgressDialog progress;

    //input pesanan
    public EditText etNamaPesanan;
    public Spinner mySpinner;
    public EditText etHarga;
    public EditText etDeskripsi;
    public Button btnBrowse;
    public ImageView ivGambarPesanan;
    private Bitmap bitmap;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    public EditText etLokasi;
    public Button btnSubmitPesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pesanan);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        requestQueue = Volley.newRequestQueue(InputPesananActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        fetchPosts();

        ImageButton btnMenuInputPesanan = (ImageButton) findViewById(R.id.btnMenuInputPesanan);
        btnMenuInputPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuBuilder menuBuilder =new MenuBuilder(InputPesananActivity.this);
                MenuInflater inflater = new MenuInflater(InputPesananActivity.this);
                inflater.inflate(R.menu.menu_input_pesanan, menuBuilder);
                MenuPopupHelper popup = new MenuPopupHelper(InputPesananActivity.this, menuBuilder, v);
                popup.setForceShowIcon(true);
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.setting:
                                Toast.makeText(InputPesananActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_LONG).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {}
                });
                popup.show();
            }
        });

        ImageButton backBtn = (ImageButton) findViewById(R.id.btnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(InputPesananActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        //INPUT
        etNamaPesanan = (EditText) findViewById(R.id.etNamaPesanan);
        mySpinner = (Spinner)findViewById(R.id.spinnerKategori);
        etHarga = (EditText) findViewById(R.id.etHargaPesanan);
        etDeskripsi = (EditText) findViewById(R.id.etDeskripsiBid);
        btnBrowse = (Button) findViewById(R.id.btnBrowse);
        btnBrowse.setOnClickListener(this);
        ivGambarPesanan = (ImageView) findViewById(R.id.ivGambarPesanan);
        etLokasi = (EditText) findViewById(R.id.etLokasiBid);
        btnSubmitPesanan = (Button) findViewById(R.id.btnSubmitPesanan);
        btnSubmitPesanan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnSubmitPesanan){
            if(etNamaPesanan.getText().toString().equals("")){
                Toast.makeText(this, "Nama pesanan masih kosong", Toast.LENGTH_SHORT).show();
            }else if(mySpinner.getSelectedItem().toString().equals("")){
                Toast.makeText(this, "Kategori masih kosong", Toast.LENGTH_SHORT).show();
            }else if(etHarga.getText().toString().equals("")){
                Toast.makeText(this, "Harga masih kosong", Toast.LENGTH_SHORT).show();
            }else if(etDeskripsi.getText().toString().equals("")){
                Toast.makeText(this, "Deskripsi masih kosong", Toast.LENGTH_SHORT).show();
            }else if(etLokasi.getText().toString().equals("")){
                Toast.makeText(this, "Lokasi masih kosong", Toast.LENGTH_SHORT).show();
            }else{
                progress=new ProgressDialog(this);
                progress.setMessage("Harap tunggu...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                postData();
            }
        }else if(v == btnBrowse){
            selectImage();
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(InputPesananActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(InputPesananActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    Toast.makeText(this, "permission error", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ivGambarPesanan.setImageBitmap(bitmap);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ivGambarPesanan.setImageBitmap(thumbnail);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void postData(){
        final String namaPesanan = etNamaPesanan.getText().toString().trim();
        final String kategori = mySpinner.getSelectedItem().toString().trim();
        final String harga = etHarga.getText().toString().trim();
        final String deskripsi = etDeskripsi.getText().toString().trim();
        final String gambar = getStringImage(bitmap);
        final String lokasi = etLokasi.getText().toString().trim();
        Bundle tvB = getIntent().getExtras();
        final String user_id = tvB.getString("user_id");
        //etNamaPesanan.setText(user_id);
        //TextView tvTitleBar = (TextView) findViewById(R.id.tvTitleBar);
        //tvTitleBar.setText(user_id);

        String INPUT_PESANAN_URL = "http://adiputra17.it.student.pens.ac.id/android/BukaPesanan/insert_pesanan.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, INPUT_PESANAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        etNamaPesanan.setText("");
                        etHarga.setText("");
                        etDeskripsi.setText("");
                        etLokasi.setText("");
                        progress.hide();
                        Toast.makeText(InputPesananActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InputPesananActivity.this,"Cek Koneksi Internet"+"\n"+gambar,Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("namaPesanan",namaPesanan);
                params.put("kategori",kategori);
                params.put("harga",harga);
                params.put("deskripsi",deskripsi);
                params.put("gambar",gambar);
                params.put("lokasi",lokasi);
                params.put("user_id",user_id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private static final String CATEGORIES = "https://api.bukalapak.com/v2/categories.json";
    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, CATEGORIES, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    protected final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("InputPesananActivity : ", error.toString());
        }
    };

    protected final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try{
                Log.i("PostActivity", response);
                //Toast.makeText(InputPesananActivity.this, response, Toast.LENGTH_SHORT).show();
                ModelGetCategories posts = gson.fromJson(response, ModelGetCategories.class);
                Log.i("PostActivity", String.valueOf(posts.status) + " " + posts.categories.toString());
                String filter = posts.categories.toString().replace("[","");
                filter = filter.replace("]","");
                DayOfWeek = filter.split(",");
                array();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public void array(){
        Spinner mySpinner = (Spinner)findViewById(R.id.spinnerKategori);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_kategory, R.id.weekofday, DayOfWeek);
        mySpinner.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}
