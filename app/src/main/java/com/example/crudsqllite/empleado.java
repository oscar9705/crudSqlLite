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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crudsqllite.entidad.Empresa;

import java.util.ArrayList;
import java.util.List;

public class empleado extends AppCompatActivity {
    EditText et1, et2, et3;
    TextView tv;
    Spinner sp;
    ListView lv;
    List<Empresa> empresaList = new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado);

        et1 =(EditText)findViewById(R.id.editText20); // id empleado
        et2 =(EditText)findViewById(R.id.editText21); // nombre empleado
        et3 =(EditText)findViewById(R.id.editText22); // correo empleado

        tv= (TextView)findViewById(R.id.textView19); // donde el nombre de la empresa

        sp = (Spinner)findViewById(R.id.spinner2);
        if(!listarEmpresas(bd()).isEmpty()){
            sp.setAdapter(adapterSpinner(listarEmpresas(bd())));

        } else {
            Toast.makeText(this, "Por favor registre una empresa primero", Toast.LENGTH_SHORT).show();
        }
        lv = (ListView)findViewById(R.id.listView2);
    }
    public SQLiteDatabase bd(){
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"ejercicio",null, 1);
        return admin.getWritableDatabase();
    }
    public int getCodigoEmpresa(String nombre){
        int codigo=0;
        for(int i = 0; i< empresaList.size(); i++){
            if(empresaList.get(i).getNombreEmpresa().equals(nombre)){
                codigo = empresaList.get(i).getIdEmpresa();
                break;
            }
        }

        return codigo;
    }
    public String getNombreEmpresa(String codigo){
        String nombre ="";
        Cursor fil = bd().rawQuery("select nombre_empresa from empresas where id_empresa="+codigo,null);
        if(fil.moveToFirst()){
            nombre = fil.getString(0);
        }
        return nombre;
    }
    public List<String> listarEmpresas(SQLiteDatabase bd){

        List<String> listaEmp = new ArrayList<>();
        Cursor fil = bd.rawQuery("select id_empresa,nombre_empresa, direccion_empresa from empresas",null);
        while (fil.moveToNext()){
            Empresa emp = new Empresa();
            emp.setIdEmpresa(fil.getInt(0));
            emp.setNombreEmpresa(fil.getString(1));
            emp.setDireccionEmpresa(fil.getString(2));
            empresaList.add(emp);
            String res = fil.getString(1);
            listaEmp.add(res);
        }
        return listaEmp;
    }
    public List<String> listarEmpresasLv(SQLiteDatabase bd){

        List<String> listaEmp = new ArrayList<>();
        Cursor fil = bd.rawQuery("select id_empresa,nombre_empresa, direccion_empresa from empresas",null);
        while (fil.moveToNext()){
            Empresa emp = new Empresa();
            emp.setIdEmpresa(fil.getInt(0));
            emp.setNombreEmpresa(fil.getString(1));
            emp.setDireccionEmpresa(fil.getString(2));
            empresaList.add(emp);
            String res = fil.getString(0)+" "+fil.getString(1);
            listaEmp.add(res);
        }
        return listaEmp;
    }
    public ArrayAdapter<String> adapterSpinner(List<String> empresas){
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, empresas );
        return adap;
    }
    public ArrayAdapter<String> adapterList(List<String> empresas){
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, empresas );
        return adap;
    }
    public void insertar(View v){
        String nombreEmpresa  = (String) sp.getSelectedItem().toString();
        String id = et1.getText().toString();
        String nombre = et2.getText().toString();
        String correo = et3.getText().toString();
        int idEmpresa = getCodigoEmpresa(nombreEmpresa);

        ContentValues registro = new ContentValues();
        registro.put("id_empleado", id);
        registro.put("nombre_empleado", nombre);
        registro.put("correo_empleado", correo);
        registro.put("id_empresa", idEmpresa );
        bd().insert("empleados", null, registro);
        bd().close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        tv.setText("");
        Toast.makeText(this, "se cargaron los datos del empleado", Toast.LENGTH_SHORT).show();
    }
    public void consultar(View v) {
        String id = et1.getText().toString();
        Cursor fila = bd().rawQuery("select nombre_empleado, correo_empleado, id_empresa from empleados where id_empleado ="+id,null);
        if(fila.moveToFirst()){
            et2.setText(fila.getString(0));
            et3.setText(fila.getString(1));
            String idEmp= fila.getString(2);
            tv.setText(getNombreEmpresa(idEmp));

        } else {
            Toast.makeText(this, "No existe un empleado con ese id ", Toast.LENGTH_SHORT).show();
        }
        bd().close();
    }

    public void actualizar(View v) {

        String nombreEmpresa  = sp.getSelectedItem().toString();
        String id = et1.getText().toString();
        String nombre = et2.getText().toString();
        String correo = et3.getText().toString();
        int idEmpresa = getCodigoEmpresa(nombreEmpresa);

        ContentValues registro = new ContentValues();
        registro.put("id_empleado", id);
        registro.put("nombre_empleado", nombre);
        registro.put("correo_empleado", correo);
        registro.put("id_empresa", idEmpresa );
        int cant = bd().update("empleados", registro, "id_empleado="+id,null);
        bd().close();
        if( cant == 1){
            Toast.makeText(this, "se modificaron los datos del empleado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "no existe un empleado con ese id", Toast.LENGTH_SHORT).show();
        }
        et1.setText("");
        et2.setText("");
        et3.setText("");
        tv.setText("");

    }
    public void listar(View v){
        List<String> empList  = listarEmpresasLv(bd());
        if(empList.isEmpty()){
            Toast.makeText(this, "no hay empleados guardados", Toast.LENGTH_SHORT).show();
        } else {
            lv.setAdapter(adapterList(empList));

        }

    }
}