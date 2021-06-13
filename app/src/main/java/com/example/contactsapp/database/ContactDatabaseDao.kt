package com.example.contactsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDatabaseDao {
    @Insert
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Query("SELECT * FROM contact_table WHERE contactId == :key")
    fun get(key: Long): Contact?

    @Query("DELETE FROM contact_table")
    fun clear()

    @Query("SELECT * FROM contact_table ORDER BY contactId DESC LIMIT 1")
    fun getLatestContact(): Contact?

    @Query("SELECT * FROM contact_table ORDER BY contactId")
    fun getAllContacts(): LiveData<List<Contact>>
}
