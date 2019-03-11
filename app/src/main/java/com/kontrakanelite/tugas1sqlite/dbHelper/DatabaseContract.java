package com.kontrakanelite.tugas1sqlite.dbHelper;

import android.provider.BaseColumns;
public class DatabaseContract {
    static String TABLE_NASABAH = "table_nasabah";
    static String TABLE_TRANSACTION = "table_transaction";

    static final class NasabahColumns implements BaseColumns {

        // NasabahModel nama
        static String NAMA = "name";
        // NasabahModel nomor rekening
        static String NOREK = "noRek";
        // NasabahModel tabungan
        static String TABUNGAN = "tabungan";

    }

    static final class TransactionColumns implements BaseColumns {

        // TransactionModel nomor rekening
        static String REKENING = "noRek";
        // TransactionModel nominal
        static String NOMINAL = "nominal";
        // TransactionModel keterangan
        static String KETERANGAN = "keterangan";
    }
}
