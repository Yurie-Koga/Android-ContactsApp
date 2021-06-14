package com.example.contactsapp.viewmodels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.database.Contact
import com.example.contactsapp.database.ContactDatabaseDao
import com.example.contactsapp.util.formatContacts
import kotlinx.coroutines.launch

/**
 * ViewModel for OverviewFragment.
 */
class OverviewViewModel(
    val database: ContactDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var latestContact = MutableLiveData<Contact?>()

    val contacts = database.getAllContacts()

    /** Convert Contacts data to Spanned for displaying **/
    val contactsString = Transformations.map(contacts) { contacts ->
        formatContacts(contacts, application.resources)
    }

    init {
        initializeLatestContact()
    }

    private fun initializeLatestContact() {
        viewModelScope.launch {
            latestContact.value = getLatestContactFromDatabase()
        }
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
            latestContact.value = getLatestContactFromDatabase()
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
            latestContact.value = getLatestContactFromDatabase()
        }
    }

    fun clearContacts() {
       viewModelScope.launch {
           clear()
           latestContact.value = null
       }
    }
}