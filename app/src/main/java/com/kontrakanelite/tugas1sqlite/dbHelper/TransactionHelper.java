package com.kontrakanelite.tugas1sqlite.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.kontrakanelite.tugas1sqlite.TransactionModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.TransactionColumns.REKENING;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.TransactionColumns.NOMINAL;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.TransactionColumns.KETERANGAN;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.TABLE_TRANSACTION;
public class TransactionHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public TransactionHelper(Context context) {
        this.context = context;
    }

    public TransactionHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }


    public ArrayList<TransactionModel> getAllData() {
        Cursor cursor = database.query(TABLE_TRANSACTION, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        ArrayList<TransactionModel> arrayList = new ArrayList<>();
        TransactionModel transactionModel;
        if (cursor.getCount() > 0) {
            do {
                transactionModel = new TransactionModel();
                transactionModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                transactionModel.setNoRek(cursor.getString(cursor.getColumnIndexOrThrow(REKENING)));
                transactionModel.setKeterangan(cursor.getString(cursor.getColumnIndexOrThrow(KETERANGAN)));
                transactionModel.setNominal(cursor.getInt(cursor.getColumnIndexOrThrow(NOMINAL)));
                
                arrayList.add(transactionModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(TransactionModel transactionModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KETERANGAN, transactionModel.getKeterangan());
        initialValues.put(REKENING, transactionModel.getNoRek());
        initialValues.put(NOMINAL, transactionModel.getNominal());
        return database.insert(TABLE_TRANSACTION, null, initialValues);
    }


    public int update(TransactionModel transactionModel) {
        ContentValues args = new ContentValues();
        args.put(REKENING, transactionModel.getNoRek());
        args.put(NOMINAL, transactionModel.getNominal());
        args.put(KETERANGAN, transactionModel.getKeterangan());
        return database.update(TABLE_TRANSACTION, args, _ID + "= '" + transactionModel.getId() + "'", null);
    }


    public int delete(int id) {
        return database.delete(TABLE_TRANSACTION, _ID + " = '" + id + "'", null);
    }
}
