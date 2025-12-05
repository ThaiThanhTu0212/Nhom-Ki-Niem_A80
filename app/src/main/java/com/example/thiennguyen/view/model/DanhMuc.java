package com.example.thiennguyen.view.model;

public class DanhMuc {
    private int idDm;
    private String tenDm;

    public DanhMuc() {
    }

    public DanhMuc(int idDm, String tenDm) {
        this.idDm = idDm;
        this.tenDm = tenDm;
    }

    public int getIdDm() {
        return idDm;
    }

    public void setIdDm(int idDm) {
        this.idDm = idDm;
    }

    public String getTenDm() {
        return tenDm;
    }

    public void setTenDm(String tenDm) {
        this.tenDm = tenDm;
    }
}
