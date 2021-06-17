package com.example.contactsapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.contactsapp.database.ContactDatabase
import com.example.contactsapp.database.asDomainModel
import com.example.contactsapp.domain.ContactProperty
import com.example.contactsapp.network.ContactApi
import com.example.contactsapp.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching devbyte videos from the network and storing them on disk
 */
class ContactsRepository(private val database: ContactDatabase) {

    val contacts: LiveData<List<ContactProperty>> =
        Transformations.map(database.contactDatabaseDao.getAllContacts()) {
            it.asDomainModel()
        }


    suspend fun refreshContacts() {
        withContext(Dispatchers.IO) {
            // fetch data from network
            val contactList = ContactApi.retrofitService.getContactList()
            // map the network data to database
            database.contactDatabaseDao.insertAll(contactList.asDatabaseModel())
        }
    }
}