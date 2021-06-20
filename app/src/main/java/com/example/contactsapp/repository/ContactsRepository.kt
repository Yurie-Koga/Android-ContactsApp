package com.example.contactsapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.contactsapp.database.Contact
import com.example.contactsapp.database.ContactDatabase
import com.example.contactsapp.database.asDomainModel
import com.example.contactsapp.domain.ContactProperty
import com.example.contactsapp.network.ContactApi
import com.example.contactsapp.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Repository to manage database access and fetching data from network for ViewModels.
 */
class ContactsRepository(database: ContactDatabase) {

    private val dao = database.contactDatabaseDao

    /** Insert a single contact data to database **/
    suspend fun insert(contact: Contact) = dao.insert(contact)

    /** List of the contacts from database **/
    fun getAllContactProperty(): LiveData<List<ContactProperty>> =
        Transformations.map(dao.getAllContactsOrderByName()) { it.asDomainModel() }

    /** A single contact data from database **/
    fun getContactProperty(contactKey: Long): LiveData<ContactProperty> =
        Transformations.map(dao.getContactWithId(contactKey)) { it.asDomainModel() }

    /** Clear database **/
    suspend fun clear() = dao.clear()


    /** Fetch data from network and store to database **/
    suspend fun refreshContacts(lengthOfResults: Int) {
        withContext(Dispatchers.IO) {
            Timber.i("refreshContacts() is called.")
            // fetch data from network
            val contactList = ContactApi.retrofitService.getContactList(lengthOfResults)
            // map the network data to database
            dao.insertAll(contactList.asDatabaseModel())
            Timber.i("Successfully data is fetched from network and stored to database.")
        }
    }
}