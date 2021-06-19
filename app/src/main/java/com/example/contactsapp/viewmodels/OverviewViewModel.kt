package com.example.contactsapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.contactsapp.database.ContactDatabase
import com.example.contactsapp.database.ContactDatabaseDao
import com.example.contactsapp.repository.ContactsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

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



    /** Repository for data access **/
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
    }


    /** Methods for Option menus **/
    fun refreshDataFromRepository(lengthOfResults: Int) {
        viewModelScope.launch {
            try {
                // clear Database
                contactsRepository.clear()
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
           contactsRepository.clear()
       }
    }
}