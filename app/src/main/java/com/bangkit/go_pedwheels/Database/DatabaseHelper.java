package com.bangkit.go_pedwheels.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    //nama database
    public static final String DATABASE_NAME = "db_apkgoped.db";
    // nama tabel dan kolom pada user
    public static final String TABLE_USER = "tb_user";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_NAME = "name";
    // nama tabel dan kolom pada sewa
    public static  final String TABLE_SEWA = "tb_sewa";
    public static final String COL_ID_SEWA = "ID_Sewa";
    public static final String COL_JARAK = "Jarak";
    public static final String COL_USIA = "Usia";
    public static final String COL_TANGGAL = "tanggal";
    public static final String COL_WAKTU = "Waktu";
    public static final String COL_BERAT = "Berat";
    //table Harga
    public static final String TABLE_HARGA = "tb_harga";
    public static final String COL_HARGA_TOTAL = "harga_total";
    public static final String COL_HARGA = "harga";


    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys=ON");
        //Tabel  User
        db.execSQL("create table " + TABLE_USER + " (" + COL_USERNAME + " TEXT PRIMARY KEY, " + COL_PASSWORD +
                " TEXT, " + COL_NAME + " TEXT)");
        //Tabel Sewa
        db.execSQL("create table " + TABLE_SEWA + " (" + COL_ID_SEWA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USIA + " TEXT, " + COL_BERAT + " TEXT" + ", " + COL_TANGGAL + " TEXT, " + COL_WAKTU + " TEXT, "
                + COL_JARAK + " TEXT)");
        //Tabel Harga
        db.execSQL("create table " + TABLE_HARGA + " (" + COL_USERNAME + " TEXT, " + COL_ID_SEWA + " INTEGER, " +
                "" + COL_HARGA + " TEXT, " + COL_HARGA_TOTAL +
                " TEXT, FOREIGN KEY(" + COL_USERNAME + ") REFERENCES " + TABLE_USER
                + ", FOREIGN KEY(" + COL_ID_SEWA + ") REFERENCES " + TABLE_SEWA + ")");
        //sample record tabel user
        db.execSQL("insert into " + TABLE_USER + " values ('Rifqi@gmail.com','aaa','Owner'),('admin','aaa','admin');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public boolean Register(String username, String password, String name) throws SQLException {

        @SuppressLint("Recycle") Cursor mCursor = db.rawQuery("INSERT INTO " + TABLE_USER + "(" + COL_USERNAME + ", " + COL_PASSWORD + ", " + COL_NAME + ") VALUES (?,?,?)", new String[]{username, password, name});
        if (mCursor != null) {
            return mCursor.getCount() > 0;
        }
        return false;
    }


    public boolean checkUserAsAdmin(String username, String password){
        String email = "admin";
        String passwords = "aaa";
        return username.equals(email) && password.equals(passwords);
    }

    public boolean Login(String username, String password) throws SQLException {
        @SuppressLint("Recycle") Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {
            return mCursor.getCount() > 0;
        }
        return false;
    }

}