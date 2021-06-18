package com.example.contactsapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.contactsapp.database.Contact
import com.example.contactsapp.database.ContactDatabase
import com.example.contactsapp.database.ContactDatabaseDao
import com.example.contactsapp.domain.ContactProperty
import com.example.contactsapp.repository.ContactsRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

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



    /** Repository to fetch data from Network and store to Database **/
    private val contactsRepository = ContactsRepository(ContactDatabase.getInstance(application))
    val contactList = contactsRepository.contacts


    /** For Initialization **/
    init {
        // Default data: set within ContactsRepository to ContactProperty as LiveData fro this fragment
    }


    /** Navigation for ContactDetail Fragment with ContactId **/
    private val _navigateToContactDetail = MutableLiveData<Long>()

    val navigateToContactDetail: LiveData<Long>
        get() = _navigateToContactDetail

    fun onContactClicked(id: Long) {
        _navigateToContactDetail.value = id
    }

    fun onContactDetailNavigated() {
        _navigateToContactDetail.value = null
    }

    /** Navigation for AddContact Fragment **/
    private val _navigateToAddContact = MutableLiveData<Boolean>()

    val navigateToAddContact: LiveData<Boolean>
        get() = _navigateToAddContact

    fun onAddContactClicked() {
        _navigateToAddContact.value = true
    }

    fun onAddContactNavigated() {
        _navigateToAddContact.value = null
        // refresh Kotlin object: will be updated automatically
//        viewModelScope.launch {
//            contactsRepository.refreshContactProperty()
//        }
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
            val newContact = Contact(nameFirst = "FirstName", nameLast = "LastName", phone = "123 456-7890")
            insert(newContact)
        }
    }


    /** Methods for Option menus **/
    fun refreshDataFromRepository(lengthOfResults: Int) {
        viewModelScope.launch {
            try {
                // clear Database
                clear()
                // refresh data
                contactsRepository.refreshContacts(lengthOfResults)
                Timber.i("Success : data has been fetched from Network and stored to Database")
            } catch (e: Exception) {
                Timber.i("Failure : ${e.message}")
            }
        }
    }

    fun clearContacts() {
       viewModelScope.launch {
           clear()
       }
    }
}