package com.bprmaa.mobiles.Storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    public static final String SP = "pinshope";
    public static final String TOKEN = "token", NAME = "ga", EMAIL = "ha", PHONE="aha", FOTO = "aea", ALAMAT = "ah", JENIS_KELAMIN ="jenis", TGL_LAHIR ="tgl", PASSWORD="passa";
    public static final String PERTAMA_MASUK = "pertama";
    public static final String SUDAH_LOGIN = "sudah_login";
    public static final String ID_LOWONGAN = "id", NAME_LOWONGAN = "Default Nama", LOKASI = "Defualt Lokasi" , JENIS = "Defualt Jenis" , KUALIFIKASI = "Defualt Kualifikasi" , FASILITAS = "Defualt Fasilitas" ;
    public static final String ID_P = "id",NAME_P = "nama",EMBEDED = "embeded", SHORT_DESC_P = "desc", IMAGE_P = "image", TIME_P= " time";
    public static final String NAME_K = "name", BELI_K = "beli";

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public SharedPref(Context context) {
        sp = context.getSharedPreferences(SP, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void savePrefString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getPrefString(String key, String value){
        return sp.getString(key, value);
    }

    public void setProductKurs(String name, String beli){
        editor.putString(NAME_K, name);
        editor.putString(BELI_K, beli);
        editor.apply();
    }

    public String getProductKurs(String Kurs){

        switch (Kurs) {
            case "name":
                return sp.getString(NAME_K, "Name");
            default:
                return sp.getString(SHORT_DESC_P, "Beli");
        }
    }

    public void setProductInfo(String id, String name, String embeded, String short_desc, String image){
        editor.putString(ID_P, id);
        editor.putString(NAME_P, name);
        editor.putString(EMBEDED, embeded);
        editor.putString(SHORT_DESC_P, short_desc);
        editor.putString(IMAGE_P, image);
        editor.apply();
    }

    public String getProductInfo(String product){

        switch (product) {
            case "id":
                return sp.getString(ID_P, "id");
            case "name":
                return sp.getString(NAME_P, "Lowongan");
            case "embeded":
                return sp.getString(EMBEDED, "null");
            case "desc":
                return sp.getString(SHORT_DESC_P, "Lowongan");
            case "image":
                return sp.getString(IMAGE_P, "Image");
            case "time":
                return sp.getString(TIME_P, "11-09-2018");
            default:
                return sp.getString(TIME_P, "11-09-2018");
        }
    }

    public void setLowonganInfo(String id, String name, String lokasi, String jenis, String kualifikasi, String fasilitas){
        editor.putString(ID_LOWONGAN, id);
        editor.putString(NAME_LOWONGAN, name);
        editor.putString(LOKASI, lokasi);
        editor.putString(JENIS, jenis);
        editor.putString(KUALIFIKASI, kualifikasi);
        editor.putString(FASILITAS, fasilitas);
//        editor.putString(EXPIRED, expired);
        editor.apply();
    }

    public String getLowonganInfo(String lowongan){
        switch (lowongan) {
            case "id":
                return sp.getString(ID_LOWONGAN, "ID");
            case "name":
                return sp.getString(NAME_LOWONGAN, " Name");
            case "lokasi":
                return sp.getString(LOKASI, " Lokasi ");
            case "jenis":
                return sp.getString(JENIS, " Jenis ");
            case "kualifikasi":
                return sp.getString(KUALIFIKASI, " Kualifikasi ");
            default:
                return sp.getString(FASILITAS, " Fasilitas ");
//            case "expired":
//                return sp.getString(EXPIRED, " Expired");
//            default:
//                return sp.getString(EXPIRED, " Expired");
        }
    }

    public void setUserInfo(String token, String name, String email, String password, String phone ,String foto, String alamat, String jenis, String tgl_lahir) {
        editor.putString(TOKEN, token);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.putString(PHONE, phone);
        editor.putString(FOTO, foto);
        editor.putString(ALAMAT, alamat);
        editor.putString(JENIS_KELAMIN, jenis);
        editor.putString(TGL_LAHIR, tgl_lahir);
        editor.apply();
    }

    public String getUserInfo(String user) {
        switch (user) {
            case "token":
                return sp.getString(TOKEN, "token");
            case "name":
                return sp.getString(NAME, "name");
            case "email":
                return sp.getString(EMAIL, "email");
            case "phone":
                return sp.getString(PHONE, "phone");
            case "foto":
                return sp.getString(FOTO, "foto");
            case "alamat":
                return sp.getString(ALAMAT, "Set Alamat");
            case "jenis_kelamin":
                return sp.getString(JENIS_KELAMIN, "Set Jenis Kelamin");
            case "tgl_lahir":
                return sp.getString(TGL_LAHIR, "tgl lahir");
            case "password":
                return sp.getString(PASSWORD, "tgl lahir");
            default:
                return sp.getString(EMAIL, "user");
        }
    }

    public void savePrefBoolean(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void clearAll() {
        editor.clear();
        editor.apply();
    }

    public Boolean getSudahLogin() {
        return sp.getBoolean(SUDAH_LOGIN, false);
    }

    public Boolean getPertamaMasuk() {
        return sp.getBoolean(PERTAMA_MASUK, true);
    }
}

