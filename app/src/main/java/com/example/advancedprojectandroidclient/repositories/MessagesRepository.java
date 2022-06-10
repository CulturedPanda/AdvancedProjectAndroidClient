package com.example.advancedprojectandroidclient.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.api.MessageApi;
import com.example.advancedprojectandroidclient.daos.MessageDao;
import com.example.advancedprojectandroidclient.entities.Message;

import java.util.LinkedList;
import java.util.List;

public class MessagesRepository {

    private final MessageDao messageDao;
    private final MessageApi messageApi;
    private final MessageData messages;

    public void setWith(String with) {
        this.with = with;
    }

    private String with;

    public MessagesRepository() {
        messageDao = MyApplication.appDB.messageDao();
        messages = new MessageData();
        messageApi = new MessageApi(messages, messageDao);
    }

    public void insert(Message message) {
        messageDao.insert(message);
        messages.postValue(messageDao.getAllMessages(with));
        messageApi.addMessage(with, message);
    }

    public void getAll(){
        messageApi.getAll(with);
    }

    class MessageData extends MutableLiveData<List<Message>>{

        public MessageData() {
            super();
            LinkedList<Message> dummyData = new LinkedList<>();
            setValue(dummyData);
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                List<Message> messageList = messageDao.getAllMessages(with);
                messages.postValue(messageList);
            }).start();
        }
    }

    public LiveData<List<Message>> getAllMessages(String with){
        new Thread(() ->{
            messages.postValue(messageDao.getAllMessages(with));
        }).start();
        return messages;
    }
}
