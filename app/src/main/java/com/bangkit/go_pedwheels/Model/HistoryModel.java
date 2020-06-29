package com.bangkit.go_pedwheels.Model;

public class HistoryModel {

    private String mIdsewa;
    private String mTanggal;
    private String mRiwayat;
    private String mTotal;
    private int mImageResourceId;
    private static final int NO_IMAGE_PROVIDED = -1;
    /** By Wibawa Bangkit on Tahun 2020
     *  Penyewaan Otoped  Wheels Berdasarkan Metode TOPSIS
     */
    public HistoryModel(String idSewa, String tanggal, String riwayat, String total, int imageResourceId) {
        mIdsewa = idSewa;
        mTanggal = tanggal;
        mRiwayat = riwayat;
        mTotal = total;
        mImageResourceId = imageResourceId;
    }

    public String getIdsewa() {
        return mIdsewa;
    }

    public String getTanggal() {
        return mTanggal;
    }

    public String getRiwayat() {
        return mRiwayat;
    }

    public String getTotal() {
        return mTotal;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

}