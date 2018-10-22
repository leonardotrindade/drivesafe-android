package com.test.drivesafe.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.drivesafe.models.LoginReq;
import com.test.drivesafe.models.LoginRes;
import com.test.drivesafe.models.TokenReq;
import com.test.drivesafe.utils.Constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiInvoker {
    private static ApiInvoker mInstance;
    private UserApi mUserApi;

    private ApiInvoker() {
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mUserApi = retrofit.create(UserApi.class);
    }

    public static ApiInvoker getInstance() {
        if (mInstance == null) {
            mInstance = new ApiInvoker();
        }

        return mInstance;
    }

    public Call<LoginRes> LoginCall(LoginReq req) {
        return mUserApi.login(req);
    }

    public Call<ResponseBody> UpdateTokenCall(TokenReq req, String token) {
        return mUserApi.updateToken(req, token);
    }
}
