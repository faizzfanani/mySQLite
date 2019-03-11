package com.kontrakanelite.tugas1sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button nasabah, transaksi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nasabah = findViewById(R.id.btnNasabah);
        transaksi = findViewById(R.id.btnTransaksi);

        transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
                startActivity(intent);

            }
        });

        nasabah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NasabahActivity.class);
                startActivity(intent);

            }
        });
    }

}
