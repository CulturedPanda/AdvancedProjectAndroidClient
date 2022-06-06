package com.example.advancedprojectandroidclient.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.advancedprojectandroidclient.entities.Message;
import com.example.advancedprojectandroidclient.repositories.MessagesRepository;

import java.util.List;

public class MessagesViewModel extends ViewModel {

    private LiveData<List<Message>> messages;
    private MessagesRepository messagesRepository;

    public MessagesViewModel() {
        this.messagesRepository = new MessagesRepository();
        messages = messagesRepository.getAllMessages();
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }
}
