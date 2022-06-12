package com.example.advancedprojectandroidclient.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.entities.Message;
import com.example.advancedprojectandroidclient.repositories.MessagesRepository;

import java.util.List;
import java.util.Objects;

/**
 * A view model for connecting the messages repository with the view.
 */
public class MessagesViewModel extends ViewModel {

    private LiveData<List<Message>> messages;
    private final MessagesRepository messagesRepository;

    /**
     * Sets who the current conversation is with.
     * @param with who the current conversation is with.
     */
    public void setWith(String with) {
        this.with = with;
        messagesRepository.setWith(with);
    }

    private String with;

    /**
     * Constructor
     */
    public MessagesViewModel() {
        this.messagesRepository = MyApplication.messagesRepository;
    }

    /**
     * Gets the messages from the repository or locally if they are up to date.
     * @param with who the current conversation is with.
     * @return the messages with the user.
     */
    public LiveData<List<Message>> getMessages(String with) {
        if (!Objects.equals(with, this.with) || messages == null) {
            this.with = with;
            messagesRepository.setWith(with);
            messages = messagesRepository.getAllMessages(with);
        }
        return messages;
    }

    /**
     * Gets the messages from the server via the repository.
     */
    public void getLiveAll(){
        messagesRepository.getAll();
    }

    /**
     * Adds a new message to a conversation.
     * @param message
     */
    public void insert(Message message) {
        new Thread(()->{messagesRepository.insert(message);}).start();
    }

    /**
     * Deletes all messages in the local database.
     */
    public void deleteAll(){
        new Thread(messagesRepository::deleteAll).start();
    }
}
