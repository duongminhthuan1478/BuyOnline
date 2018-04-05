package com.example.dell.buyonline.model;

/**
 * Created by Dell on 03-Mar-18.
 */

public class LoaiSanPham {
    public int mID;
    public String mTenLoaiSp;
    public String mHinhAnhLoaiSp;

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getTenLoaiSp() {
        return mTenLoaiSp;
    }

    public void setTenLoaiSp(String tenLoaiSp) {
        mTenLoaiSp = tenLoaiSp;
    }

    public String getHinhAnhLoaiSp() {
        return mHinhAnhLoaiSp;
    }

    public void setHinhAnhLoaiSp(String hinhAnhLoaiSp) {
        mHinhAnhLoaiSp = hinhAnhLoaiSp;
    }

    public LoaiSanPham(int ID, String tenLoaiSp, String hinhAnhLoaiSp) {

        mID = ID;
        mTenLoaiSp = tenLoaiSp;
        mHinhAnhLoaiSp = hinhAnhLoaiSp;
    }
}
