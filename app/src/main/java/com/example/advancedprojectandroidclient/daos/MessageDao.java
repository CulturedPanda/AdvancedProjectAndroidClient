package com.example.advancedprojectandroidclient.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.advancedprojectandroidclient.entities.Message;

import java.util.List;

/**
 * Data Access Object for the Message table.
 */
@Dao
public interface MessageDao {


    @Query("SELECT * FROM message WHERE message.`with` = :with order by actualTime desc")
    List<Message> getAllMessages(String with);

    @Insert
    void insert(Message message);

    @Query("DELETE FROM message WHERE message.`with` = :with")
    void deleteAll(String with);

    @Query("DELETE FROM message")
    void deleteTable();
}
