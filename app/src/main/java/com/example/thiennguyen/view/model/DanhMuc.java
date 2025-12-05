package com.example.thiennguyen.view.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DanhMuc implements Parcelable {
    private int idDm;
    private String tenDm;

    public DanhMuc() {
    }

    public DanhMuc(int idDm, String tenDm) {
        this.idDm = idDm;
        this.tenDm = tenDm;
    }

    protected DanhMuc(Parcel in) {
        idDm = in.readInt();
        tenDm = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idDm);
        dest.writeString(tenDm);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DanhMuc> CREATOR = new Creator<DanhMuc>() {
        @Override
        public DanhMuc createFromParcel(Parcel in) {
            return new DanhMuc(in);
        }

        @Override
        public DanhMuc[] newArray(int size) {
            return new DanhMuc[size];
        }
    };

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
