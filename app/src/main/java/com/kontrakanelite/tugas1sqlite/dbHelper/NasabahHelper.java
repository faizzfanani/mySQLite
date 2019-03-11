package com.kontrakanelite.tugas1sqlite.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.kontrakanelite.tugas1sqlite.NasabahModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.NasabahColumns.NAMA;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.NasabahColumns.NOREK;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.NasabahColumns.TABUNGAN;
import static com.kontrakanelite.tugas1sqlite.dbHelper.DatabaseContract.TABLE_NASABAH;

public class NasabahHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public NasabahHelper(Context context) {
        this.context = context;
    }

    public NasabahHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }


    public ArrayList<NasabahModel> getAllData() {
        Cursor cursor = database.query(TABLE_NASABAH, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        ArrayList<NasabahModel> arrayList = new ArrayList<>();
        NasabahModel nasabahModel;
        if (cursor.getCount() > 0) {
            do {
                nasabahModel = new NasabahModel();
                nasabahModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                nasabahModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAMA)));
                nasabahModel.setNoRek(cursor.getString(cursor.getColumnIndexOrThrow(NOREK)));
                nasabahModel.setTabungan(cursor.getInt(cursor.getColumnIndexOrThrow(TABUNGAN)));
                arrayList.add(nasabahModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(NasabahModel nasabahModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAMA, nasabahModel.getName());
        initialValues.put(NOREK, nasabahModel.getNoRek());
        initialValues.put(TABUNGAN, nasabahModel.getTabungan());
        return database.insert(TABLE_NASABAH, null, initialValues);
    }


    public int update(NasabahModel nasabahModel) {
        ContentValues args = new ContentValues();
        args.put(NAMA, nasabahModel.getName());
        args.put(NOREK, nasabahModel.getNoRek());
        args.put(TABUNGAN, nasabahModel.getTabungan());
        return database.update(TABLE_NASABAH, args, _ID + "= '" + nasabahModel.getId() + "'", null);
    }


    public int delete(int id) {
        return database.delete(TABLE_NASABAH, _ID + " = '" + id + "'", null);
    }
}
