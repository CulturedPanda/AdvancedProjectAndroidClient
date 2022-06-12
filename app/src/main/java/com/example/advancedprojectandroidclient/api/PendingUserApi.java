package com.example.advancedprojectandroidclient.api;

import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.entities.PendingUser;
import com.example.advancedprojectandroidclient.entities.RefreshToken;
import com.example.advancedprojectandroidclient.repositories.RefreshTokenRepository;

import java.io.IOException;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The implementation of the API for handling pending users.
 */
public class PendingUserApi {

    private final Retrofit retrofit;
    private final IPendingUserApi IPendingUserApi;
    RefreshTokenRepository refreshTokenRepository;

    public PendingUserApi() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        this.IPendingUserApi = retrofit.create(IPendingUserApi.class);
        refreshTokenRepository = MyApplication.refreshTokenRepository;
    }

    /**
     * Checks if a pending user exists by attribute X.
     * Unlike a certain other method that was copy pasted 3 times in ContactsApi, I was actually
     * writing this in an hour where my brain was not shut down, therefore, there was no stupid copy pasting.
     *
     * @param X              the attribute to check
     * @param attributeValue the attributeValue to check
     * @param result         the MutableLiveData to put the result in
     */
    public void doesPendingUserExistByX(String X, String attributeValue, MutableLiveData<Boolean> result) {
        Call<Boolean> call = IPendingUserApi.doesPendingUserExistByX(X, attributeValue);
        call.enqueue(new retrofit2.Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                if (response.isSuccessful()) {
                    result.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                result.postValue(false);
            }
        });
    }

    /**
     * Signs up the user.
     *
     * @param pendingUser the pending user to sign up
     * @param result      the MutableLiveData to put the result in
     */
    public void signUpUser(PendingUser pendingUser, MutableLiveData<Boolean> result) {
        Call<Void> call = IPendingUserApi.createPendingUser(pendingUser);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    result.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                result.postValue(true);
            }
        });
    }

    /**
     * Renews a pending user's verification code.
     *
     * @param username the username of the pending user
     */
    public void renewCode(String username) {
        Call<Void> call = IPendingUserApi.renewCode(username);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("renewCode success");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("renewCode failure");
            }
        });
    }

    /**
     * Verifies a pending user's code.
     *
     * @param username     the username of the pending user
     * @param code         the code to verify
     * @param isSuccessful the MutableLiveData to put the result in
     */
    public void verifyCode(String username, String code, MutableLiveData<Boolean> isSuccessful) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url(MyApplication.context.getString(R.string.base_url) + "PendingUsers/" + username + "?verificationCode=" + code)
                .get()
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                isSuccessful.postValue(false);
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    RefreshTokenRepository.accessToken = response.body().string();
                    isSuccessful.postValue(true);
                } else {
                    isSuccessful.postValue(false);
                }
            }
        });
    }

    /**
     * Finishes the sign up process and turns the pending user into a registered user, as well as gets
     * their refresh token.
     *
     * @param success
     */
    public void finishSignUp(MutableLiveData<Boolean> success) {
        // Using OkHttp instead of retrofit because of malformed JSON errors in the return value when using retrofit.
        OkHttpClient client = new OkHttpClient.Builder().build();

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "{}");

        Request request = new Request.Builder()
                .url(MyApplication.context.getString(R.string.base_url) + "RegisteredUsers/signUp")
                .post(body)
                .addHeader("Authorization", "Bearer " + RefreshTokenRepository.accessToken)
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                System.out.println("finishSignUp failure");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                // Save the new refresh token on success.
                if (response.isSuccessful() && response.body() != null) {
                    MyApplication.refreshTokenRepository.deleteRefreshToken();
                    MyApplication.refreshTokenRepository.setRefreshToken(new RefreshToken(response.body().string()));
                    success.postValue(true);
                } else {
                    System.out.println("finishSignUp failure");
                    success.postValue(false);
                }
            }
        });
    }

    /**
     * Sets the pending user's firebase token on signup finish.
     *
     * @param username the username of the pending user
     * @param token    the token to set
     */
    public void setPhoneToken(String username, String token) {
        Call<Void> call = IPendingUserApi.setPhoneToken(username, token);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("setPhoneToken success");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("setPhoneToken failure");
            }
        });
    }
}
