package com.example.advancedprojectandroidclient.api;

import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.entities.AccessToken;
import com.example.advancedprojectandroidclient.entities.RefreshToken;
import com.example.advancedprojectandroidclient.entities.User;
import com.example.advancedprojectandroidclient.repositories.RefreshTokenRepository;

import java.io.IOException;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisteredUserApi {

    private final Retrofit retrofit;
    private final IRegisteredUserApi IRegisteredUserApi;
    RefreshTokenRepository refreshTokenRepository;

    public RegisteredUserApi() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        this.IRegisteredUserApi = retrofit.create(IRegisteredUserApi.class);
        refreshTokenRepository = MyApplication.refreshTokenRepository;
    }

    public RegisteredUserApi(RefreshTokenRepository refreshTokenRepository) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        this.IRegisteredUserApi = retrofit.create(IRegisteredUserApi.class);
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void loginUser(User user, MutableLiveData<Boolean> loggedIn) {

        Call<AccessToken> call = IRegisteredUserApi.logIn(user);
        call.enqueue(new retrofit2.Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, retrofit2.Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    RefreshTokenRepository.accessToken = response.body().getAccessToken();
                    RefreshToken refreshToken = new RefreshToken(response.body().getRefreshToken());
                    new Thread(() -> {
                        refreshTokenRepository.deleteRefreshToken();
                        refreshTokenRepository.setRefreshToken(refreshToken);
                    }).start();
                    loggedIn.postValue(true);
                } else {
                    loggedIn.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                loggedIn.postValue(false);
            }
        });
    }

    public void changeDescription(String newDescription) {
        Call<Void> call = IRegisteredUserApi.editDescription(newDescription, "Bearer " + RefreshTokenRepository.accessToken);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Description changed");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Description not changed");
            }
        });
    }

    public void changeNickname(String newNickname) {
        Call<Void> call = IRegisteredUserApi.editNickName(newNickname, "Bearer " + RefreshTokenRepository.accessToken);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Description changed");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Description not changed");
            }
        });
    }

    public void changeServer(String newServer) {
        Call<Void> call = IRegisteredUserApi.changeServer(newServer, "Bearer " + RefreshTokenRepository.accessToken);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Description changed");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Description not changed");
            }
        });
    }

    public void getDescription(MutableLiveData<String> description, String username) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url(MyApplication.context.getString(R.string.base_url) + "RegisteredUsers/getDescription/" + username)
                .get()
                .addHeader("Authorization", "Bearer " + RefreshTokenRepository.accessToken)
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                description.postValue("");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    description.postValue(response.body().string());
                } else {
                    description.postValue("");
                }
            }
        });
    }

    public void getNickname(MutableLiveData<String> nickname, String username) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url(MyApplication.context.getString(R.string.base_url) + "RegisteredUsers/getNickName/" + username)
                .get()
                .addHeader("Authorization", "Bearer " + RefreshTokenRepository.accessToken)
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                nickname.postValue("");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    nickname.postValue(response.body().string());
                } else {
                    nickname.postValue("");
                }
            }
        });
    }

    public void getServer(MutableLiveData<String> server, String username) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url(MyApplication.context.getString(R.string.base_url) + "RegisteredUsers/getServer/" + username)
                .get()
                .addHeader("Authorization", "Bearer " + RefreshTokenRepository.accessToken)
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                server.postValue("");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    server.postValue(response.body().string());
                } else {
                    server.postValue("");
                }
            }
        });
    }

    public void setPhoneToken(String phoneToken) {
        Call<Void> call = IRegisteredUserApi.setPhoneToken("Bearer " + RefreshTokenRepository.accessToken, phoneToken);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Phone token set");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Phone token not set");
            }
        });
    }

    public void logOut() {
        Call<Void> call = IRegisteredUserApi.logOut("Bearer " + RefreshTokenRepository.accessToken);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Logged out");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Logout failed");
            }
        });
    }

    public void verifySecretQuestion(String username, String question, String answer, MutableLiveData<Boolean> success) {
        Call<Boolean> call = IRegisteredUserApi.verifySecretQuestion(username, question, answer);
        call.enqueue(new retrofit2.Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                if (response.isSuccessful()) {
                    success.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                success.postValue(false);
            }
        });
    }

    public void renewCode(String username) {
        Call<Void> call = IRegisteredUserApi.renewVerificationCode(username);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Code renewed");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Code not renewed");
            }
        });
    }

    public void verifyCode(String username, String code, MutableLiveData<Boolean> success) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url(MyApplication.context.getString(R.string.base_url) + "RegisteredUsers/verifyCode/" + username
                        + "?verificationCode=" + code)
                .get()
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                success.postValue(false);
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    RefreshTokenRepository.accessToken = response.body().string();
                    success.postValue(true);
                } else {
                    success.postValue(false);
                }
            }
        });
    }

    public void resetPassword(String password, MutableLiveData<Boolean> success) {
        User user = new User("abcde", password);
        Call<Void> call = IRegisteredUserApi.resetPassword("Bearer " + RefreshTokenRepository.accessToken, user);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    success.postValue(true);
                } else {
                    success.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                success.postValue(false);
            }
        });
    }

    public void logInEmail(User user, MutableLiveData<Boolean> loggedIn) {
        Call<AccessToken> call = IRegisteredUserApi.logInByEmail(user);
        call.enqueue(new retrofit2.Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, retrofit2.Response<AccessToken> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RefreshTokenRepository.accessToken = response.body().getAccessToken();
                    RefreshToken refreshToken = new RefreshToken(response.body().getRefreshToken());
                    new Thread(() -> {
                        refreshTokenRepository.deleteRefreshToken();
                        refreshTokenRepository.setRefreshToken(refreshToken);
                    }).start();
                    MyApplication.username = response.body().getUsername();
                    loggedIn.postValue(true);
                } else {
                    loggedIn.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                loggedIn.postValue(false);
            }
        });
    }
}
