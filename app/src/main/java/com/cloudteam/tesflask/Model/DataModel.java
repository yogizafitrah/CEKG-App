package com.cloudteam.tesflask.Model;

public class DataModel {
    private int id;
    private String nama , jk,alamat, tglrekam;
    private double tspt ,bpm,irr,irrlokal;
    private String hr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTgl() {
        return tglrekam;
    }

    public void setTgl(String tgl) {
        this.tglrekam = tgl;
    }

    public String getHr() {
        return hr;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public double getTspt() {
        return tspt;
    }

    public void setTspt(double tspt) {
        this.tspt = tspt;
    }

    public double getBpm() {
        return bpm;
    }

    public void setBpm(double bpm) {
        this.bpm = bpm;
    }

    public double getIrr() {
        return irr;
    }

    public void setIrr(double irr) {
        this.irr = irr;
    }

    public double getIrrlokal() {
        return irrlokal;
    }

    public void setIrrlokal(double irrlokal) {
        this.irrlokal = irrlokal;
    }
}
