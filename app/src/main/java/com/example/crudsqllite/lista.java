package com.example.crudsqllite;

import androidx.appcompat.app.AppCompatActivity;

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

public class lista extends AppCompatActivity {
    EditText et1;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        et1 =(EditText)findViewById(R.id.editText30);
        lv =(ListView)findViewById(R.id.listView3);


    }
    public SQLiteDatabase bd(){
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"ejercicio",null, 1);
        return admin.getWritableDatabase();
    }
    public ArrayAdapter<String> adapterList(List<String> empleados){
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, empleados );
        return adap;
    }
    public List<String> listarEmpleados(String codigo){

        List<String> listaEmp = new ArrayList<>();
        Cursor fil = bd().rawQuery("select id_empleado, nombre_empleado from empleados where id_empresa ="+ codigo,null);
        Cursor fila = bd().rawQuery("select nombre_empresa from empresas where id_empresa ="+ codigo,null);
        while (fil.moveToNext()){
            String res = fil.getInt(0)+" "+fil.getString(1);
            if(fila.moveToFirst()){
                res+=" "+fila.getString(0);
            }
            listaEmp.add(res);
        }
        return listaEmp;
    }
    public void listarEmpleados(View v){
        String idEmpresa = et1.getText().toString();
        if(!listarEmpleados(idEmpresa).isEmpty()){
            lv.setAdapter(adapterList(listarEmpleados(idEmpresa)));
        } else{
            Toast.makeText(this, "no hay empleados registrados en esa empresa", Toast.LENGTH_SHORT).show();
        }
    }
}