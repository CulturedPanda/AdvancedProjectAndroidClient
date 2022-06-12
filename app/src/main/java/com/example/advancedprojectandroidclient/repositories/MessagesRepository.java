package com.example.advancedprojectandroidclient.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.api.MessageApi;
import com.example.advancedprojectandroidclient.daos.MessageDao;
import com.example.advancedprojectandroidclient.entities.Message;

import java.util.LinkedList;
import java.util.List;

/**
 * A repository for the messages table.
 * Manages the database operations for the messages table as well as contacting the server for messages.
 */
public class MessagesRepository {

    private final MessageDao messageDao;
    private final MessageApi messageApi;
    private final MessageData messages;

    /**
     * Setter for who the current conversation is with.
     *
     * @param with who the current conversation is with.
     */
    public void setWith(String with) {
        this.with = with;
    }

    /**
     * Getter for who the current conversation is with.
     *
     * @return who the current conversation is with.
     */
    public String getWith() {
        return with;
    }

    private String with;

    /**
     * Constructor
     */
    public MessagesRepository() {
        messageDao = MyApplication.appDB.messageDao();
        messages = new MessageData();
        messageApi = new MessageApi(messages, messageDao);
    }

    /**
     * Adds a new message to a conversation.
     *
     * @param message
     */
    public synchronized void insert(Message message) {
        // Updates the local database and the current message list.
        messageDao.insert(message);
        messages.postValue(messageDao.getAllMessages(with));
        // Updates the server.
        messageApi.addMessage(with, message);
    }

    /**
     * Gets all messages for a conversation from the server.
     */
    public synchronized void getAll() {
        messageApi.getAll(with);
    }

    /**
     * Deletes all messages stored in the local database.
     */
    public synchronized void deleteAll() {
        messageDao.deleteTable();
    }

    /**
     * Extension of MutableLiveData that stores a list of messages.
     * Loads up the list of messages from the local database once active.
     */
    class MessageData extends MutableLiveData<List<Message>> {

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

    /**
     * Getter for the messages list, as well as updates the list.
     *
     * @param with who the current conversation is with.
     * @return the messages list
     */
    public synchronized LiveData<List<Message>> getAllMessages(String with) {
        new Thread(() -> {
            messages.postValue(messageDao.getAllMessages(with));
        }).start();
        return messages;
    }
}
