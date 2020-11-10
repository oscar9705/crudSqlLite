package com.example.crudsqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class empresa extends AppCompatActivity {
    EditText et1, et2, et3;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);

        et1 = (EditText)findViewById(R.id.editText13); // id empresa
        et2 = (EditText)findViewById(R.id.editText14); // nombre empresa
        et3 = (EditText)findViewById(R.id.editText15); // direccion empresa

        lv = (ListView) findViewById(R.id.listView); // lista de empresas
    }

    public SQLiteDatabase bd(){
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"ejercicio",null, 1);
        return admin.getWritableDatabase();
    }

    public ArrayAdapter<String> adapterList(List<String> empresas){
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, empresas );
        return adap;
    }
    public List<String> listarEmpresas(SQLiteDatabase bd){

        List<String> listaEmp = new ArrayList<>();
        Cursor fil = bd.rawQuery("select * from empresas",null);
        while (fil.moveToNext()){
            String res = fil.getInt(0)+" "+fil.getString(1);
            listaEmp.add(res);
        }
        return listaEmp;
    }
    public void insertar(View v) {

        String id = et1.getText().toString();
        String nombre = et2.getText().toString();
        String direccion = et3.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("id_empresa", id);
        registro.put("nombre_empresa", nombre);
        registro.put("direccion_empresa", direccion);
        bd().insert("empresas", null, registro);
        bd().close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        Toast.makeText(this, "se cargaron los datos de la empresa", Toast.LENGTH_SHORT).show();
    }
    public void consultar(View v) {
        String id = et1.getText().toString();
        Cursor fila = bd().rawQuery("select nombre_empresa, direccion_empresa from empresas where id_empresa =" +id,null);
        if(fila.moveToFirst()){
            et2.setText(fila.getString(0));
            et3.setText(fila.getString(1));

        } else {
            Toast.makeText(this, "No existe una empresa con ese id ", Toast.LENGTH_SHORT).show();
        }
        bd().close();
    }
    public void actualizar(View v) {

        String id = et1.getText().toString();
        String nombre = et2.getText().toString();
        String direccion = et3.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("nombre_empresa", nombre);
        registro.put("direccion_empresa", direccion);
        bd().insert("empresas", null, registro);
        int cant = bd().update("empresas", registro, "id_empresa="+id,null);
        bd().close();
        if( cant == 1){
            Toast.makeText(this, "se modificaron los datos de la empresa", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "no existe una empresa con ese id", Toast.LENGTH_SHORT).show();
        }
        et1.setText("");
        et2.setText("");
        et3.setText("");

    }

}