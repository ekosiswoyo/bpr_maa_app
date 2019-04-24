package com.bprmaa.mobiles.App;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModelArray {
    @SerializedName("kode")
    @Expose
    private Integer kode;
    @SerializedName("pesan")
    @Expose
    private String pesan;
    @SerializedName("result")
    @Expose
    private ResponModel result;

    public Integer getKode() {
        return kode;
    }

    public void setKode(Integer kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public ResponModel getResult() {
        return result;
    }

    public void setResult(ResponModel result) {
        this.result = result;
    }
}
