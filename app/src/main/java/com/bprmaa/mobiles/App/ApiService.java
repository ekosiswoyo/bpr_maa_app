package com.bprmaa.mobiles.App;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "X-Requested-With: XMLHTTPRequest"})
    @POST("api/login")
//    @POST("dev_file/api/bpr/login.php")
    Call<ResponseBody> loginReq(@Field("username") String username,
                                @Field("fcm_token") String fcm_token,
                                @Field("password") String password);

    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "X-Requested-With: XMLHTTPRequest"})
    @POST("api/forgot")
    Call<ResponseBody> lupaPassword(@Field("email") String email);


    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "X-Requested-With: XMLHTTPRequest"})
    @POST("api/register")
//    @POST("dev_file/api/bpr/register.php")
    Call<ResponseBody> sendDataReg(@Field("name") String name,
                                   @Field("email") String email,
                                   @Field("phone") String phone,
                                   @Field("password") String password);

    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "X-Requested-With: XMLHTTPRequest"})
    @POST("api/user/update")
//    @POST("dev_file/api/bpr/register.php")
    Call<ResponseBody> update(@Query("token") String api,
                              @Field("name") String name,
                              @Field("email") String email,
                              @Field("phone") String phone,
                              @Field("alamat") String alamat,
                              @Field("tgl_lahir") String tgl,
                              @Field("jenis_kelamin") String jenis);


    @Multipart
    @Headers({
            "Accept: application/json",
            "X-Requested-With: XMLHTTPRequest"})
    @POST("api/career")
//    @POST("dev_file/api/bpr/submitCareer.php")
    Call<ResponseBody> sendDataCareer(@Query("token") String api,
                                      @Part("vacancy_id") RequestBody id,
                                      @Part("name") RequestBody name,
                                      @Part("email") RequestBody email,
                                      @Part("phone") RequestBody phone,
                                      @Part("description") RequestBody description,
                                      @Part MultipartBody.Part file);

    @Multipart
    @POST("api/user/update")
    Call<ResponseBody> gantiFotoProfil(@Query("token") String api,
                                       @Part("email") RequestBody bank,
                                       @Part MultipartBody.Part file);

    @Multipart
    @Headers({
            "Accept: application/json",
            "X-Requested-With: XMLHTTPRequest"})
    @POST("api/credit")
//    @POST("dev_file/api/bpr/submitCareer.php")
    Call<ResponseBody> sendDataCredit(@Query("token") String api,
                                      @Part("name") RequestBody name,
                                      @Part("email") RequestBody email,
                                      @Part("phone") RequestBody phone,
                                      @Part("address") RequestBody address,
                                      @Part MultipartBody.Part file);

    @Multipart
    @Headers({
            "Accept: application/json",
            "X-Requested-With: XMLHTTPRequest"})
    @POST("api/tabungan")
//    @POST("dev_file/api/bpr/submitCareer.php")
    Call<ResponseBody> sendDataTabungan(@Query("token") String api,
                                        @Part("name") RequestBody name,
                                        @Part("email") RequestBody email,
                                        @Part("phone") RequestBody phone,
                                        @Part("address") RequestBody address,
                                        @Part MultipartBody.Part file);

    @GET("api/product")
    Call<ResponModel> getProduct(@Query("token") String api);

    @GET("api/news")
    Call<ResponModel> getListNews(@Query("token") String api);

    @GET("api/valas")
    Call<ResponModel> getListKurs(@Query("token") String api);

    @GET("api/commodity")
    Call<ResponModel> getListCommodity(@Query("token") String api);

    @GET("api/layanan")
    Call<ResponModel> getListLayanan(@Query("token") String api);

    @GET("api/promo")
    Call<ResponModel> getListPromo(@Query("token") String api);

    @GET("api/lelang")
    Call<ResponModel> getListLelang(@Query("token") String api);

    @GET("api/vacancy")
    Call<ResponModel> getListCareer(@Query("token") String api);

    @GET("api/dashboard")
    Call<ResponModel> getListDashboard();

    @GET("api/history/credit")
    Call<ResponModel> getHistoryKredit(@Query("token") String api);

    @GET("api/history/tabungan")
    Call<ResponModel> getHistoryTabungan(@Query("token") String api);

    @GET("api/about")
    Call<ResponseBody> getAbout();

//    @GET("dev_file/api/bpr/read.php")
    @GET("api/contacts")
    Call<DataModelArray> getListKantor();

    @GET("api/corporates")
    Call<DataModelArray> getListManagemen();

    @GET("api/about")
    Call<DataModelArray> getVisi();

    @GET("")
    Call<ResponseBody> getUrl(@Url String url);

    @FormUrlEncoded
    @POST("api/otp")
    Call<ResponseBody> cek_tlp(
            @Field("phone") String phone,
            @Field("fcm_token") String fcm_token
    );

}
