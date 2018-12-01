package com.example.dell.buyonline.model;

public class GioHang {
    private int mId;
    private String mTenSanPham;
    private int mGiaSanPham;
    private String mImageSanPham;
    private int mSoLuongSanPham;

    public GioHang(int idSanPham, String tenSanPham, int giaSanPham, String imageSanPham,
            int soLuongSanPham) {
        mId = idSanPham;
        mTenSanPham = tenSanPham;
        mGiaSanPham = giaSanPham;
        mImageSanPham = imageSanPham;
        mSoLuongSanPham = soLuongSanPham;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTenSanPham() {
        return mTenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        mTenSanPham = tenSanPham;
    }

    public int getGiaSanPham() {
        return mGiaSanPham;
    }

    public void setGiaSanPham(int giaSanPham) {
        mGiaSanPham = giaSanPham;
    }

    public String getImageSanPham() {
        return mImageSanPham;
    }

    public void setImageSanPham(String imageSanPham) {
        mImageSanPham = imageSanPham;
    }

    public int getSoLuongSanPham() {
        return mSoLuongSanPham;
    }

    public void setSoLuongSanPham(int soLuongSanPham) {
        mSoLuongSanPham = soLuongSanPham;
    }
}
