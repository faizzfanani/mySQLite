package com.kontrakanelite.tugas1sqlite;

public class NasabahModel {
    private int id;
    private String name;
    private String noRek;
    private int tabungan;

    public NasabahModel(){}
    public NasabahModel(String name, String noRek, int tabungan){
        this.name = name;
        this.noRek = noRek;
        this.tabungan = tabungan;
    }
    public NasabahModel(int id, String name, String noRek, int tabungan){
        this.id = id;
        this.name = name;
        this.noRek = noRek;
        this.tabungan = tabungan;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNoRek(String noRek) {
        this.noRek = noRek;
    }

    public String getNoRek() {
        return noRek;
    }

    public void setTabungan(int tabungan) {
        this.tabungan = tabungan;
    }

    public int getTabungan() {
        return tabungan;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}


