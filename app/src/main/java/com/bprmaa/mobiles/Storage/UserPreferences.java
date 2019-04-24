package com.bprmaa.mobiles.Storage;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

    public static final String BPR = "bpr";
    public static final String KEY_USER_TEREGISTER = "user", KEY_PASS_TEREGISTER ="pass", KEY_USER_TOKEN = "token";
    public static final String KEY_USER_NAME = "name",KEY_USER_EMAIL = "email", KEY_USER_PHONE = "phone";
    public static final String KEY_USERNAME_SEDANG_LOGIN = "Username_logged_in";
    public static final String KEY_STATUS_SEDANG_LOGIN = "Status_logged_in";

    SharedPreferences sp;
    SharedPreferences.Editor editor;

//    public static SharedPreferences getSharedPreference(Context context){
//        return PreferenceManager.getDefaultSharedPreferences(context);
//    }

    public UserPreferences(Context context){
        sp = context.getSharedPreferences(BPR, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void setRegisteredUser(String token, String name, String email, String phone){

        editor.putString(KEY_USER_TOKEN, token);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_PHONE, phone);
        editor.apply();
    }

    public String getRegisteredUser( String user) {
        switch (user) {
            case "token":
                return sp.getString(KEY_USER_TOKEN, "Default Token");
            case "name":
                return sp.getString(KEY_USER_NAME, "Default name");
            case "email":
                return sp.getString(KEY_USER_EMAIL, "Default Email");
            case "phone":
                return sp.getString(KEY_USER_PHONE, "Default phone");
            default:
                return sp.getString(KEY_USER_TEREGISTER, "Default");
        }
    }
//
//    public static void setLoggedInUser(Context context, String username){
//        SharedPreferences.Editor editor = getSharedPreference(context).edit();
//        editor.putString(KEY_USERNAME_SEDANG_LOGIN, username);
//        editor.apply();
//    }
//
//    public static String getLoggedInUser(Context context){
//        return getSharedPreference(context).getString(KEY_USERNAME_SEDANG_LOGIN,"");
//    }
//
//    public static void setLoggedInStatus(Context context, boolean status){
//        SharedPreferences.Editor editor = getSharedPreference(context).edit();
//        editor.putBoolean(KEY_STATUS_SEDANG_LOGIN,status);
//        editor.apply();
//    }
//
//    public static boolean getLoggedInStatus(Context context){
//        return getSharedPreference(context).getBoolean(KEY_STATUS_SEDANG_LOGIN,false);
//    }
//
//    public static void clearLoggedInUser (Context context){
//        SharedPreferences.Editor editor = getSharedPreference(context).edit();
//        editor.remove(KEY_USERNAME_SEDANG_LOGIN);
//        editor.remove(KEY_STATUS_SEDANG_LOGIN);
//        editor.remove(KEY_USER_TEREGISTER);
//        editor.remove(KEY_USER_TOKEN);
//        editor.remove(KEY_USER_NAME);
//        editor.remove(KEY_USER_EMAIL);
//        editor.remove(KEY_USER_PHONE);
//        editor.apply();
//    }

}
