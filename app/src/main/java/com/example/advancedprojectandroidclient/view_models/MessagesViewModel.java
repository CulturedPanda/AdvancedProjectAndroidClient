package com.example.advancedprojectandroidclient.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.advancedprojectandroidclient.entities.Message;
import com.example.advancedprojectandroidclient.repositories.MessagesRepository;

import java.util.List;
import java.util.Objects;

public class MessagesViewModel extends ViewModel {

    private LiveData<List<Message>> messages;
    private final MessagesRepository messagesRepository;

    public void setWith(String with) {
        this.with = with;
        messagesRepository.setWith(with);
    }

    private String with;

    public MessagesViewModel() {
        this.messagesRepository = new MessagesRepository();
    }

    public LiveData<List<Message>> getMessages(String with) {
        if (!Objects.equals(with, this.with) || messages == null) {
            this.with = with;
            messagesRepository.setWith(with);
            messages = messagesRepository.getAllMessages(with);
        }
        return messages;
    }

    public void getLiveAll(){
        messagesRepository.getAll();
    }

    public void insert(Message message) {
        new Thread(()->{messagesRepository.insert(message);}).start();
    }
}
