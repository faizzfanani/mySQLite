package com.kontrakanelite.tugas1sqlite;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kontrakanelite.tugas1sqlite.dbHelper.NasabahHelper;

import java.util.ArrayList;

public class NasabahActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText nama, noRek, tabungan;
    private Button tambah;
    //private ImageView btnBack;
    private NasabahAdapter nasabahAdapter;
    private NasabahHelper nasabahHelper;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nasabah);
        recyclerView = findViewById(R.id.recyclerview);
        nama = findViewById(R.id.edit_nama);
        noRek = findViewById(R.id.edit_noRek);
        tabungan = findViewById(R.id.edit_tabungan);
        tambah = findViewById(R.id.btn_tambah);
        //btnBack = findViewById(R.id.backToHome);


        nasabahHelper = new NasabahHelper(this);
        nasabahAdapter = new NasabahAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //btnBack.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {
              //  Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(intent);
            //}
        //});
        getAllData();

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tambah.getText().equals("update")) {


                } else {
                    insertData();
                    getAllData();
                }
            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                nasabahAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                nasabahAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
    private void insertData() {
        nasabahHelper.open();
        NasabahModel nasabah = new NasabahModel(nama.getText().toString(), noRek.getText().toString(), Integer.valueOf(tabungan.getText().toString()));
        nasabahHelper.insert(nasabah);
        nasabahHelper.close();
    }

    private void getAllData() {
        nasabahHelper.open();
        // Ambil semua data nasabah di database
        ArrayList<NasabahModel> nasabahModels = nasabahHelper.getAllData();
        nasabahHelper.close();
        nasabahAdapter.addItem(nasabahModels);
        recyclerView.setAdapter(nasabahAdapter);
    }
}
