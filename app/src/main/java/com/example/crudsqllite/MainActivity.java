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

import com.example.crudsqllite.entidad.Curso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText et1, et2, et3, et4, et5, et6;
    TextView tv9;
    Spinner sp;
    ListView lv;
    List<String> cursos= new ArrayList<>();
    List<Integer> cursosId= new ArrayList<>();
    List<Curso> cursosList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText) findViewById(R.id.editText); //cedula
        et2 = (EditText) findViewById(R.id.editText2); //nombre
        et3 = (EditText) findViewById(R.id.editText3); //edad

        et4 = (EditText) findViewById(R.id.editText4); // nombre del curso
        et5 = (EditText) findViewById(R.id.editText5); // creditos del curso
        et6 = (EditText) findViewById(R.id.editText6); // codigo del curso

        tv9 = (TextView) findViewById(R.id.textView9); // curso
        sp= (Spinner)findViewById(R.id.spinner);
        lv = (ListView)findViewById(R.id.listView);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listarCursos());
        sp.setAdapter(adapter);

    }
    public ArrayAdapter<String> adapter(List<String> cursos){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cursos);
       return adapter;
    }
    public ArrayAdapter<String> adapterList(List<String> estudiantes){
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, estudiantes );
        return adap;
    }
    public List<String> listarEstudiantesCurso(int cursoId, SQLiteDatabase bd){

        List<String> listaEstu = new ArrayList<>();
        Cursor fil = bd.rawQuery("select cedula_est, nombre_est from estudiantes where id_curso="+ cursoId,null);
        while (fil.moveToNext()){
            String res = fil.getInt(0)+" "+fil.getString(1);
            listaEstu.add(res);
        }
        return listaEstu;
    }

    public void consultarEstPorCurso(View v){
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();
        int cursoId =  Integer.parseInt(et6.getText().toString());

        lv.setAdapter(adapterList(listarEstudiantesCurso(cursoId,bd)));

    }

    public List<String> listarCursos(){


        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();
        List<String> cursosSpinner= new ArrayList<>();
        Cursor fila = bd.rawQuery("select codigo_cu ,nombre_cu, creditos_cu from cursos ", null);

        while(fila.moveToNext()){
            Curso curso = new Curso();
            curso.setCodigoCu(fila.getInt(0));
            curso.setNombreCu(fila.getString(1));
            curso.setCreditos(fila.getInt(2));
            cursosList.add(curso);
            cursosId.add(fila.getInt(0));
            cursos.add(fila.getString(1));
            cursosSpinner.add(fila.getString(1));
        }
        if(cursosSpinner.isEmpty()){

        }
        return cursosSpinner;



    }

    public void insertar(View v) {
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();

        String nombreCurso  = (String) sp.getSelectedItem().toString();

        String cedula = et1.getText().toString();
        String nombre = et2.getText().toString();
        String edad = et3.getText().toString();

        int idCurso = getCodigoCurso(bd,nombreCurso);
        ContentValues registro = new ContentValues();
        registro.put("cedula_est", cedula);
        registro.put("nombre_est", nombre);
        registro.put("edad_est", edad);
        registro.put("id_curso", idCurso);
        bd.insert("estudiantes", null, registro);
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        tv9.setText("");
        Toast.makeText(this, "se cargaron los datos del estudiante", Toast.LENGTH_SHORT).show();
    }
    public int getCodigoCurso(SQLiteDatabase bd, String nombre){
        int codigo=0;
        for(int i= 0; i<cursosList.size();i++){
            if(cursosList.get(i).getNombreCu().equals(nombre)){
                codigo = cursosList.get(i).getCodigoCu();
                break;
            }
        }

        return codigo;
    }
    public void insertarCurso(View v) {
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();

        String nombre = et4.getText().toString();
        String credito = et5.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("nombre_cu", nombre);
        registro.put("creditos_cu", credito);

        bd.insert("cursos", null, registro);

        sp.setAdapter(adapter(listarCursos()));

        bd.close();

        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");
        et6.setText("");

        Toast.makeText(this, "se cargaron los datos del curso", Toast.LENGTH_SHORT).show();
    }

    public String listaCursosEstudiante(){
        return "";
    }
    public String nombreCurso(SQLiteDatabase bd, Integer codigo){
        String nombreCurso="";
        Cursor fila = bd.rawQuery("select nombre_cu from cursos where codigo_cu ="+ codigo, null);
        if(fila.moveToFirst()){
            nombreCurso = fila.getString(0);
        }
        return nombreCurso+"\n"+codigo;
    }

    public void consultar(View v) {
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();
        String cedula = et1.getText().toString();
        Cursor fila = bd.rawQuery("select nombre_est, edad_est, id_curso from estudiantes where cedula_est =" +cedula,null);
        if(fila.moveToFirst()){
            et2.setText(fila.getString(0));
            et3.setText(fila.getString(1));
            if(nombreCurso(bd, fila.getInt(2)).length() == 0){
                Toast.makeText(this, "Eror con el curso ", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println(fila.getInt(2));
                tv9.setText(nombreCurso(bd,fila.getInt(2)));
            }
        } else {
            Toast.makeText(this, "No existe un estudiante con dicha cedula ", Toast.LENGTH_SHORT).show();
        }

        bd.close();
        listarCursos();
    }
    public void consultarCurso(View v) {
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();
        String id = et6.getText().toString();
        Cursor fila = bd.rawQuery("select nombre_cu, creditos_cu from cursos where codigo_cu =" +id,null);
        if(fila.moveToFirst()){
            et4.setText(fila.getString(0));
            et5.setText(fila.getString(1));

        } else {
            Toast.makeText(this, "No existe un curso con ese id ", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void actualizar(View v) {
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();
        String cedula = et1.getText().toString();
        String nombre = et2.getText().toString();
        String edad = et3.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("nombre_est", nombre);
        registro.put("edad_est", edad);
        bd.insert("estudiantes", null, registro);
        int cant = bd.update("estudiantes", registro, "estudiante_st="+cedula,null);
        bd.close();
        if( cant == 1){
            Toast.makeText(this, "se modificaron los datos del estudiante", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "no existe un estudiante con esa cedula", Toast.LENGTH_SHORT).show();
        }
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");

    }
    public void actualizarCurso(View v) {
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();
        String codigo = et6.getText().toString();
        String nombre = et4.getText().toString();
        String creditos = et5.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("nombre_cu", nombre);
        registro.put("creditos_cu", creditos);
        bd.insert("cursos", null, registro);
        int cant = bd.update("cursos", registro, "codigo_cu="+codigo,null);
        bd.close();
        if( cant == 1){
            sp.setAdapter(adapter(listarCursos()));
            Toast.makeText(this, "se modificaron los datos del curso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "no existe un curso con ese id", Toast.LENGTH_SHORT).show();
        }
        et4.setText("");
        et5.setText("");
        et6.setText("");

    }

    public void eliminar(View v) {
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();
        String cedula = et1.getText().toString();
        int cant = bd.delete("estudiantes","cedula_est = "+cedula, null);
        if(cant == 1){
            Toast.makeText(this, "se eliminó correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "no existe un estudiante con dicha cedula", Toast.LENGTH_SHORT).show();
        }

        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");
        et6.setText("");
    }
    public void eliminarCurso(View v) {
        AdminSQLiteOpenHelper   admin = new AdminSQLiteOpenHelper(this,"administracion",null, 1);
        SQLiteDatabase  bd = admin.getWritableDatabase();
        String id = et6.getText().toString();
        int cant = bd.delete("cursos","codigo_cu = " + id, null);
        if(cant == 1){
            sp.setAdapter(adapter(listarCursos()));
            Toast.makeText(this, "se eliminó correctamente", Toast.LENGTH_SHORT).show();
            et1.setText("");
            et2.setText("");
            et3.setText("");
            et4.setText("");
            et5.setText("");
            et6.setText("");
        } else {
            et1.setText("");
            et2.setText("");
            et3.setText("");
            et4.setText("");
            et5.setText("");
            et6.setText("");
            Toast.makeText(this, "no existe un curso con dicho codigo", Toast.LENGTH_SHORT).show();
        }
    }

}