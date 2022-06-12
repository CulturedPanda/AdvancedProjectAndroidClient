package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The API for handling contacts.
 */
public interface IContactsApi {

    /**
     * Get the list of contacts
     *
     * @param token The JWT bearer token.
     * @return The list of contacts.
     */
    @GET("Contacts")
    Call<List<Contact>> getContacts(@Header("Authorization") String token);

    /***
     * Adds a new contact to the user by their username.
     * @param contact The contact to add.
     * @param token The JWT bearer token.
     * @param local whether the contact is local or not.
     * @return The contact added.
     */
    @POST("Contacts")
    Call<Contact> createContactByUsername(@Body Contact contact, @Header("Authorization") String token,
                                          @Query("local") boolean local);

    /***
     * Checks if a user is already the current user's contact by their username
     * @param user The user to check.
     * @param token The JWT bearer token.
     * @return Whether the user is already the current user's contact.
     */
    @GET("Contacts/alreadyContact/{user}")
    Call<Boolean> isAlreadyContactByUsername(@Path("user") String user, @Header("Authorization") String token);

    /**
     * Checks if a user exists by their username.
     *
     * @param username The username to check.
     * @param token    The JWT bearer token.
     * @return Whether the user exists.
     */
    @GET("RegisteredUsers/doesUserExistByUsername/{username}")
    Call<Boolean> doesUserExistByUsername(@Path("username") String username, @Header("Authorization") String token);

    /**
     * Adds a contact by their email
     *
     * @param contact The contact to add.
     * @param token   The JWT bearer token.
     * @param local   whether the contact is local or not.
     * @return The contact added.
     */
    @POST("Contacts/byEmail")
    Call<Contact> createContactByEmail(@Body Contact contact, @Header("Authorization") String token,
                                       @Query("local") boolean local);

    /**
     * Checks if a user is already the current user's contact by their email
     *
     * @param email The email to check.
     * @param token The JWT bearer token.
     * @return Whether the user is already the current user's contact.
     */
    @GET("Contacts/byEmail/{email}")
    Call<Boolean> isAlreadyContactByEmail(@Path("email") String email, @Header("Authorization") String token);

    /**
     * Checks if a user exists by their email.
     *
     * @param email The email to check.
     * @param token The JWT bearer token.
     * @return Whether the user exists.
     */
    @GET("RegisteredUsers/doesUserExistByEmail/{email}")
    Call<Boolean> doesUserExistByEmail(@Path("email") String email, @Header("Authorization") String token);

    /**
     * Adds a contact by their phone number
     *
     * @param contact The contact to add.
     * @param token   The JWT bearer token.
     * @param local   whether the contact is local or not.
     * @return The contact added.
     */
    @POST("Contacts/byPhone")
    Call<Contact> createContactByPhone(@Body Contact contact, @Header("Authorization") String token,
                                       @Query("local") boolean local);

    /**
     * Checks if a user is already the current user's contact by their phone number
     *
     * @param phoneNum The phone number to check.
     * @param token    The JWT bearer token.
     * @return Whether the user is already the current user's contact.
     */
    @GET("Contacts/alreadyContact/{phone}")
    Call<Boolean> isAlreadyContactByPhone(@Path("phone") String phoneNum, @Header("Authorization") String token);

    /**
     * Checks if a user exists by their phone number.
     *
     * @param phoneNum The phone number to check.
     * @param token    The JWT bearer token.
     * @return Whether the user exists.
     */
    @GET("RegisteredUsers/doesUserExistByPhone/{phone}")
    Call<Boolean> doesUserExistByPhone(@Path("phone") String phoneNum, @Header("Authorization") String token);
}
