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
import timber.log.Timber

/**
 * Repository for fetching devbyte videos from the network and storing them on disk
 */
class ContactsRepository(private val database: ContactDatabase) {

    /** Read database and set to kotlin object as LiveData for OverviewFragment as a default data **/
    val contacts: LiveData<List<ContactProperty>> =
        Transformations.map(database.contactDatabaseDao.getAllContactsOrderByName()) {
            it.asDomainModel()
        }


    suspend fun refreshContacts(lengthOfResults: Int) {
        withContext(Dispatchers.IO) {
            Timber.i("refreshContacts() is called.")
            // fetch data from network
            val contactList = ContactApi.retrofitService.getContactList(lengthOfResults)
            // map the network data to database
            database.contactDatabaseDao.insertAll(contactList.asDatabaseModel())
            Timber.i("Successfully data is fetched from network and stored to database.")
        }
    }
}