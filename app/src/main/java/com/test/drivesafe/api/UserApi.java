package com.test.drivesafe.api;

import com.test.drivesafe.models.LoginReq;
import com.test.drivesafe.models.LoginRes;
import com.test.drivesafe.models.TokenReq;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApi {
    @POST("user/login")
    Call<LoginRes> login(@Body LoginReq req);

    @POST("user/token")
    Call<ResponseBody> updateToken(@Body TokenReq req, @Header("Authorization") String token);
}
