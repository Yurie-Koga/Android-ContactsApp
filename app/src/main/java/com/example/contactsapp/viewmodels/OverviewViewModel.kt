package com.example.contactsapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.contactsapp.database.Contact
import com.example.contactsapp.database.ContactDatabaseDao
import com.example.contactsapp.domain.ContactProperty
import com.example.contactsapp.network.ContactApi
import com.example.contactsapp.network.asDomainModel
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * ViewModel for OverviewFragment.
 */
class OverviewViewModel(
    val database: ContactDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    /** Convert Contacts data to Spanned for displaying **/
    //import com.example.contactsapp.util.formatContacts
//    val contacts = database.getAllContacts()
//    val contactsString = Transformations.map(contacts) { contacts ->
//        formatContacts(contacts, application.resources)
//    }


    /** domain Models for [list_item_contact.xml] **/
    private var _contactList = MutableLiveData<List<ContactProperty>>()
    val contactList: LiveData<List<ContactProperty>>
        get() = _contactList


    /** For Initialization **/
    init {
        refreshDataFromNetwork()
    }

    /** For a API call **/
    private fun refreshDataFromNetwork() {
        viewModelScope.launch {
            try {
                val contactList = ContactApi.retrofitService.getContactList()
                _contactList.postValue(contactList.asDomainModel())
                Timber.i("Success response: ${contactList}")
            } catch (e: Exception) {
                Timber.i("Failure response: ${e.message}")
            }
        }
    }


    /** Navigation for ContactDetail Fragment with Databasae **/
    private val _navigateToContactDetail = MutableLiveData<Long>()

    val navigateToContactDetail: LiveData<Long>
        get() = _navigateToContactDetail

    fun onContactClicked(id: Long) {
        _navigateToContactDetail.value = id
    }

    fun onContactDetailNavigated() {
        _navigateToContactDetail.value = null
    }



    /** Methods for Database **/
    private suspend fun getLatestContactFromDatabase(): Contact? {
        var contact = database.getLatestContact()
        if (contact?.nameFirst != null) {
            // contact info is already registered
            contact = null
        }
        return contact
    }

    private suspend fun insert(contact: Contact) {
        database.insert(contact)
    }

    private suspend fun clear() {
        database.clear()
    }


    /** Methods for Button press **/
    fun onAddNewContact() {
        viewModelScope.launch {
            val newContact = Contact()
            insert(newContact)
        }
    }

    /** Methods for Option menus **/
    fun refreshContacts() {
        //TODO("Restore data and update View")

        viewModelScope.launch {
            // Clear Database
            clear()
            /** Temporal : Insert 5 entries **/
            repeat(5) {
                val newContact = Contact()
                insert(newContact)
            }
        }
    }

    fun clearContacts() {
       viewModelScope.launch {
           clear()
       }
    }
}