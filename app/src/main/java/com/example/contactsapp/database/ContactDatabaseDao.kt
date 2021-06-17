package com.example.contactsapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDatabaseDao {
    @Insert
    suspend fun insert(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(contacts: List<Contact>)

    @Update
    suspend fun update(contact: Contact)

    @Query("SELECT * FROM contact_table WHERE contactId = :key")
    suspend fun get(key: Long): Contact?

    @Query("DELETE FROM contact_table")
    suspend fun clear()

    @Query("SELECT * FROM contact_table ORDER BY contactId DESC LIMIT 1")
    suspend fun getLatestContact(): Contact?

    @Query("SELECT * FROM contact_table ORDER BY contactId")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact_table WHERE contactId = :key")
    fun getContactWithId(key: Long): LiveData<Contact>
}
