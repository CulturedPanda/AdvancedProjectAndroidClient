package com.example.advancedprojectandroidclient.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.daos.AppDB;
import com.example.advancedprojectandroidclient.daos.MessageDao;
import com.example.advancedprojectandroidclient.entities.Message;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MessagesRepository {

    private MessageDao messageDao;
    // private ContactApi contactApi;
    private MessageData messages;

    public void setWith(String with) {
        this.with = with;
    }

    private String with;

    public MessagesRepository() {
        AppDB db = Room.databaseBuilder(
                MyApplication.context,
                AppDB.class,
                "app.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        messageDao = db.messageDao();
        messages = new MessageData();
        // contactApi = new ContactApi(contacts, contactDao);
    }

    public void insert(Message message) {
        messageDao.insert(message);
        messages.setValue(messageDao.getAllMessages(with));
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
        if (!Objects.equals(with, this.with)) {
            this.with = with;
            messages.setValue(messageDao.getAllMessages(with));
        }
        return messages;
    }
}
