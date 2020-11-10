package com.example.crudsqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table empleados (id_empleado integer primary key, nombre_empleado text,correo_empleado text, id_empresa integer)");
        db.execSQL("create table empresas (id_empresa integer primary key AUTOINCREMENT, nombre_empresa text, direccion_empresa text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists empresas");
        db.execSQL("drop table if exists empleados");
        db.execSQL("create table empleados (id_empleado integer primary key, nombre_empleado text,correo_empleado text, id_empresa integer)");
        db.execSQL("create table empresas (id_empresa integer primary key AUTOINCREMENT, nombre_empresa text, direccion_empresa text)");
    }

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
