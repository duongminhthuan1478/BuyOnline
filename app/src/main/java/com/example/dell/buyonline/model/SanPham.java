package com.example.dell.buyonline.model;

import java.io.Serializable;

/**
 * Created by Dell on 07-Mar-18.
 */

public class SanPham implements Serializable{
    private int mID;
    private String mTenSanPham;
    private Integer mGiaSanPham;
    private String mHinhAnhSanPham;
    private String mMotaSanPham;
    private int mIdLoaiSanPham;

    public SanPham(int ID, String tenSanPham, Integer giaSanPham, String hinhAnhSanPham,
            String motaSanPham, int idSanPham) {

        mID = ID;
        mTenSanPham = tenSanPham;
        mGiaSanPham = giaSanPham;
        mHinhAnhSanPham = hinhAnhSanPham;
        mMotaSanPham = motaSanPham;
        mIdLoaiSanPham = idSanPham;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getTenSanPham() {
        return mTenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        mTenSanPham = tenSanPham;
    }

    public Integer getGiaSanPham() {
        return mGiaSanPham;
    }

    public void setGiaSanPham(Integer giaSanPham) {
        mGiaSanPham = giaSanPham;
    }

    public String getHinhAnhSanPham() {
        return mHinhAnhSanPham;
    }

    public void setHinhAnhSanPham(String hinhAnhSanPham) {
        mHinhAnhSanPham = hinhAnhSanPham;
    }

    public String getMotaSanPham() {
        return mMotaSanPham;
    }

    public void setMotaSanPham(String motaSanPham) {
        mMotaSanPham = motaSanPham;
    }

    public int getIdLoaiSanPham() {
        return mIdLoaiSanPham;
    }

    public void setIdLoaiSanPham(int idLoaiSanPham) {
        mIdLoaiSanPham = idLoaiSanPham;
    }


}
