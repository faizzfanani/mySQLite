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

import com.kontrakanelite.tugas1sqlite.dbHelper.TransactionHelper;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText rekening, nominal, keterangan;
    private Button tambah;
    //private ImageView btnBack;
    private TransactionAdapter transactionAdapter;
    private TransactionHelper transactionHelper;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction);
        recyclerView = findViewById(R.id.recyclerview);
        rekening = findViewById(R.id.edit_rekening);
        nominal = findViewById(R.id.edit_nominal);
        keterangan = findViewById(R.id.edit_keterangan);
        tambah = findViewById(R.id.btn_tambah);
        //btnBack = findViewById(R.id.backToHome);

        transactionHelper = new TransactionHelper(this);
        transactionAdapter = new TransactionAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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
                transactionAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                transactionAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
    private void insertData() {
        transactionHelper.open();
        TransactionModel transaction = new TransactionModel(rekening.getText().toString(), Integer.valueOf(nominal.getText().toString()), keterangan.getText().toString());
        transactionHelper.insert(transaction);
        transactionHelper.close();
    }

    private void getAllData() {
        transactionHelper.open();
        // Ambil semua data transaction di database
        ArrayList<TransactionModel> transactionModels = transactionHelper.getAllData();
        transactionHelper.close();
        transactionAdapter.addItem(transactionModels);
        recyclerView.setAdapter(transactionAdapter);
    }
}
