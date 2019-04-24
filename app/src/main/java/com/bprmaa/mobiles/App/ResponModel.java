package com.bprmaa.mobiles.App;

import java.util.List;

public class ResponModel {

    String pesan, message;
    List<DataModel> result, promo, lelang, news, list, errors, slider, dana, kredit;
    int kode;

    List<DataModel> cabang, komisaris, direksi, kas, pusat, social;
    List<DataModel> visi;

    public List<DataModel> getMisi() {
        return misi;
    }

    public void setMisi(List<DataModel> misi) {
        this.misi = misi;
    }

    List<DataModel> misi;

    public List<DataModel> getVisi() {
        return visi;
    }

    public String sejarah;

    public String updated_at;

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getSejarah() {
        return sejarah;
    }

    public void setSejarah(String sejarah) {
        this.sejarah = sejarah;
    }

    public void setVisi(List<DataModel> visi) {
        this.visi = visi;
    }

    public List<DataModel> getSocial() {
        return social;
    }

    public void setSocial(List<DataModel> social) {
        this.social = social;
    }

    public List<DataModel> getPusat() {
        return pusat;
    }

    public void setPusat(List<DataModel> pusat) {
        this.pusat = pusat;
    }

    public List<DataModel> getKas() {
        return kas;
    }

    public void setKas(List<DataModel> kas) {
        this.kas = kas;
    }

    public List<DataModel> getKomisaris() {
        return komisaris;
    }

    public void setKomisaris(List<DataModel> komisaris) {
        this.komisaris = komisaris;
    }

    public List<DataModel> getDireksi() {
        return direksi;
    }

    public void setDireksi(List<DataModel> direksi) {
        this.direksi = direksi;
    }

    public List<DataModel> getCabang() {
        return cabang;
    }

    public void setCabang(List<DataModel> cabang) {
        this.cabang = cabang;
    }

    public List<DataModel> getDana() {
        return dana;
    }

    public void setDana(List<DataModel> dana) {
        this.dana = dana;
    }

    public List<DataModel> getKredit() {
        return kredit;
    }

    public void setKredit(List<DataModel> kredit) {
        this.kredit = kredit;
    }

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public List<DataModel> getSlider() {
        return slider;
    }

    public void setSlider(List<DataModel> slider) {
        this.slider = slider;
    }

    public List<DataModel> getNews() {
        return news;
    }

    public void setNews(List<DataModel> news) {
        this.news = news;
    }

    public List<DataModel> getErrors() {
        return errors;
    }

    public void setErrors(List<DataModel> errors) {
        this.errors = errors;
    }

    public List<DataModel> getList() {
        return list;
    }

    public void setList(List<DataModel> list) {
        this.list = list;
    }

    public List<DataModel> getLelang() {
        return lelang;
    }

    public void setLelang(List<DataModel> lelang) {
        this.lelang = lelang;
    }

    public List<DataModel> getPromo() {
        return promo;
    }

    public void setPromo(List<DataModel> promo) {
        this.promo = promo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataModel> getResult() {
        return result;
    }

    public void setResult(List<DataModel> result) {
        this.result = result;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}


