package com.example.crudsqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table votantes (cedula_vt integer primary key, nombre_vt text, colegio_vt text, salon_vt integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists votantes");
        db.execSQL("create table votantes (cedula_vt integer primary key, nombre_vt text, colegio_vt text, salon_vt integer)");
    }

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
