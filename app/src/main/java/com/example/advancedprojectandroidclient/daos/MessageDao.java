package com.example.advancedprojectandroidclient.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.advancedprojectandroidclient.entities.Message;

import java.util.List;

@Dao
public interface MessageDao {


    @Query("SELECT * FROM message WHERE message.`with` = :with order by created asc")
    public List<Message> getAllMessages(String with);

    @Insert
    public void insert(Message message);
}
