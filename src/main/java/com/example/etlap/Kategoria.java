package com.example.etlap;

public class Kategoria {
    private int id;
    private String nev;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public Kategoria(int id, String nev) {
        this.id = id;
        this.nev = nev;
    }
}
