package com.bprmaa.mobiles.Helper;

import android.util.Log;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Helper {

    public Helper() {

    }

    public String convertRupiah(Integer x) {

        Locale localeID = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(localeID);
        String hasilConvert = format.format((double) x);

        return hasilConvert;

    }

    public String convertDateFormat(String date, String frmtlama) {

        String hasil= "";

//        final String formatLama = "yyyy-MM-dd hh:mm:s";
        final String formatBaru = "dd MMMM yyyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(frmtlama);
        try {
            Date dd = dateFormat.parse(date);
            dateFormat.applyPattern(formatBaru);
            hasil = dateFormat.format(dd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return hasil;

    }

    public String convertDateTime(String date, String frmtlama) {

        String hasil= "";

//        final String formatLama = "yyyy-MM-dd hh:mm:s";
        final String formatBaru = "dd MMMM yyyy, hh:mm";

        SimpleDateFormat dateFormat = new SimpleDateFormat(frmtlama);
        try {
            Date dd = dateFormat.parse(date);
            dateFormat.applyPattern(formatBaru);
            hasil = dateFormat.format(dd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return hasil;

    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

//        String date_after = formateDateFromstring("dd-MM-yyyy", "dd MMMM yyyy", date_before);

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            Log.d("Respon", "ParseException - dateFormat");
        }

        return outputDate;
    }

}
