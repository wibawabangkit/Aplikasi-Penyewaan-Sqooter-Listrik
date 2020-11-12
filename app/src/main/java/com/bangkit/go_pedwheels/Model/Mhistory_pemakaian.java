package com.bangkit.go_pedwheels.Model;

public class Mhistory_pemakaian {

    private String mIdsewa;
    private String mTanggalpakai;
    private String mRiwayatpakai;
    private String mHasil;
    private int mImageResourceId;
    private static final int NO_IMAGE_PROVIDED = -1;

    public Mhistory_pemakaian(String idSewa, String tanggalpakai, String riwayatpakai, String hasil, int imageResourceId) {
        mIdsewa = idSewa;
        mTanggalpakai = tanggalpakai;
        mRiwayatpakai = riwayatpakai;
        mHasil = hasil;
        mImageResourceId = imageResourceId;
    }



    public String getIdsewa() {
        return mIdsewa;
    }

    public String getTanggalpakai() {
        return mTanggalpakai;
    }

    public String getRiwayatpakai() {
        return mRiwayatpakai;
    }

    public String getHasil() {
        return mHasil;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

}