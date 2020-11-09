package com.example.crudsqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table estudiantes (cedula_est integer primary key, nombre_est text, edad_est integer, id_curso integer)");
        db.execSQL("create table cursos (codigo_cu integer primary key AUTOINCREMENT, nombre_cu text, creditos_cu integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists estudiantes");
        db.execSQL("drop table if exists cursos");
        db.execSQL("create table estudiantes (cedula_est integer primary key, nombre_est text, edad_est integer, id_curso integer)");
        db.execSQL("create table cursos (codigo_cu integer primary key AUTOINCREMENT, nombre_cu text, creditos_cu integer)");
    }

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
