package com.example.crudsqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    public Intent redireccion(Class<?> cls){
        return  new Intent(this,cls);
    }

    public void redirigirEmpresas(View v){
        startActivity(redireccion(empresa.class));
    }
    public void redirigirEmpleados(View v){
        startActivity(redireccion(empleado.class));
    }
    public void redirigirListado(View v){
        startActivity(redireccion(lista.class));
    }
}