package com.kontrakanelite.tugas1sqlite.dbHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.TABLE_NASABAH;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.NasabahColumns.NAMA;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.NasabahColumns.NOREK;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.NasabahColumns.TABUNGAN;

import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.TABLE_TRANSACTION;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.TransactionColumns.KETERANGAN;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.TransactionColumns.NOMINAL;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.TransactionColumns.REKENING;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static String CREATE_TABLE_NASABAH = "create table " + TABLE_NASABAH +
            " (" + _ID + " integer primary key autoincrement, " +
            NAMA + " text not null, " +
            NOREK + " text not null, "+
            TABUNGAN + " integer not null );";
    public static String CREATE_TABLE_TRANSACTION = "create table " + TABLE_TRANSACTION +
            " (" + _ID + " integer primary key autoincrement, " +
            REKENING + " text not null, " +
            NOMINAL + " integer not null, "+
            KETERANGAN + " text not null );";

    private static String DATABASE_NAME = "dbbank";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NASABAH);
        db.execSQL(CREATE_TABLE_TRANSACTION);
    }

    /*
    Method onUpgrade akan di panggil ketika terjadi perbedaan versi
    Gunakan method onUpgrade untuk melakukan proses migrasi data
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
         */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NASABAH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(db);
    }


}