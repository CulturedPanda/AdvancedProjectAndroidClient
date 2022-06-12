package com.example.advancedprojectandroidclient.api;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.daos.MessageDao;
import com.example.advancedprojectandroidclient.entities.Message;
import com.example.advancedprojectandroidclient.repositories.RefreshTokenRepository;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageApi {

    private final Retrofit retrofit;
    private final IMessageApi messageApi;
    private final MutableLiveData<List<Message>> messages;
    private final MessageDao messageDao;

    public MessageApi(MutableLiveData<List<Message>> messages, MessageDao messageDao) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        this.messageApi = retrofit.create(IMessageApi.class);
        this.messages = messages;
        this.messageDao = messageDao;
    }

    public void getAll(String id){
        Call<List<Message>> call = messageApi.getMessages(id, "Bearer " + RefreshTokenRepository.accessToken);
        call.enqueue(new retrofit2.Callback<List<Message>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Message>> call, retrofit2.Response<List<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Making sure the date is parsed correctly
                    for (Message message : response.body()) {
                        message.setActualTime(Message.parseActualTime(message.getCreated()));
                        message.setCreated(Message.parseDate(message.getCreated()));
                        message.setWith(id);
                    }
                    new Thread(() -> {
                        messageDao.deleteAll(id);
                        for (Message message : response.body()) {
                            message.setWith(id);
                            messageDao.insert(message);
                        }
                    }).start();
                }
                messages.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                messages.postValue(messageDao.getAllMessages(id));
            }
        });
    }

    public void addMessage(String id,Message message){
        message.setCreated(null);
        Call<Message> call = messageApi.addMessage(id, "Bearer " + RefreshTokenRepository.accessToken,
                message);
        call.enqueue(new retrofit2.Callback<Message>() {

            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
