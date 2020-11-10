package com.example.crudsqllite.entidad;

public class Curso {
    private Integer codigoCu;
    private String nombreCu;
    private Integer creditos;

    public Curso() {
        // constructor
    }


    public Curso(Integer codigoCu, String nombreCu, Integer creditos) {
        this.codigoCu = codigoCu;
        this.nombreCu = nombreCu;
        this.creditos = creditos;
    }

    public Integer getCodigoCu() {
        return codigoCu;
    }

    public void setCodigoCu(Integer codigoCu) {
        this.codigoCu = codigoCu;
    }

    public String getNombreCu() {
        return nombreCu;
    }

    public void setNombreCu(String nombreCu) {
        this.nombreCu = nombreCu;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }
}
