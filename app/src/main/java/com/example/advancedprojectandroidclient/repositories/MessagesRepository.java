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

public class MessagesRepository {

    private MessageDao messageDao;
    // private ContactApi contactApi;
    private MessageData messages;
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

    public LiveData<List<Message>> getAllMessages(){
        return messages;
    }
}
