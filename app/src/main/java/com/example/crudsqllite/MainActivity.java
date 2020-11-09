package com.example.crudsqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et1, et2, et3, et4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText) findViewById(R.id.editText); //cedula
        et2 = (EditText) findViewById(R.id.editText2); //nombre
        et3 = (EditText) findViewById(R.id.editText3); //colegio
        et4 = (EditText) findViewById(R.id.editText4); //salon
    }

    public void insertar(View v) {
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();
        String cedula = et1.getText().toString();
        String nombre = et2.getText().toString();
        String colegio = et3.getText().toString();
        String salon = et4.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("cedula_vt", cedula);
        registro.put("nombre_vt", nombre);
        registro.put("colegio_vt", colegio);
        registro.put("salon_vt", salon);
        bd.insert("votantes", null, registro);
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        Toast.makeText(this, "se cargaron los datos de la persona", Toast.LENGTH_SHORT).show();
    }

    public void consultar(View v) {
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();
        String cedula = et1.getText().toString();
        Cursor fila = bd.rawQuery("select nombre_vt, colegio_vt, salon_vt from votantes where cedula_vt =" +cedula,null);
        if(fila.moveToFirst()){
            et2.setText(fila.getString(0));
            et3.setText(fila.getString(1));
            et4.setText(fila.getString(2));
        } else {
            Toast.makeText(this, "No existe una persona con dicha cedula ", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void actualizar(View v) {
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();
        String cedula = et1.getText().toString();
        String nombre = et2.getText().toString();
        String colegio = et3.getText().toString();
        String salon = et4.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("nombre_vt", nombre);
        registro.put("colegio_vt", colegio);
        registro.put("salon_vt", salon);
        bd.insert("votantes", null, registro);
        int cant = bd.update("votantes", registro, "cedula_vt="+cedula,null);
        bd.close();
        if( cant == 1){
            Toast.makeText(this, "se modificaron los datos de la persona", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "no existe una persona con dicha cedula", Toast.LENGTH_SHORT).show();
        }
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");

    }

    public void eliminar(View v) {
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();
        String cedula = et1.getText().toString();
        int cant = bd.delete("votantes","cedula_vt = "+cedula, null);
        if(cant == 1){
            Toast.makeText(this, "se eliminó correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "no existe una persona con dicha cedula", Toast.LENGTH_SHORT).show();
        }
    }

}