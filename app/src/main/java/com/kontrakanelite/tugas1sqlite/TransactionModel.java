package com.kontrakanelite.tugas1sqlite;

public class TransactionModel {
    private int id;
    private String noRek;
    private int nominal;
    private String keterangan;

    public TransactionModel(){}
    public TransactionModel(String noRek, int nominal, String keterangan){
        this.noRek = noRek;
        this.nominal = nominal;
        this.keterangan = keterangan;
    }
    public TransactionModel(int id, String noRek, int nominal, String keterangan){
        this.id = id;
        this.noRek = noRek;
        this.nominal = nominal;
        this.keterangan = keterangan;
    }

    public void setNoRek(String noRek) {
        this.noRek = noRek;
    }

    public String getNoRek() {
        return noRek;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}


