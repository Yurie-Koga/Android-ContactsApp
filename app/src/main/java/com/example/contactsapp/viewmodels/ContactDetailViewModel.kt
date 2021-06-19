package com.example.contactsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.database.Contact
import com.example.contactsapp.database.ContactDatabase
import com.example.contactsapp.database.ContactDatabaseDao
import com.example.contactsapp.domain.ContactProperty
import com.example.contactsapp.repository.ContactsRepository
import kotlinx.coroutines.launch

class ContactDetailViewModel(
    private val contactKey: Long = 0L,
    val database: ContactDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    /** Repository for data access **/
    private val contactsRepository = ContactsRepository(ContactDatabase.getInstance(application))
    val contact = contactsRepository.getContactProperty(contactKey)

//    fun getContact() = contact    // made 'contact' public, so no need to declare getter

    init {
    }

    /** For Navigation **/
    private val _navigateToOverview = MutableLiveData<Boolean?>()

    val navigateToOverview: LiveData<Boolean?>
        get() = _navigateToOverview

    fun doneNavigating() {
        _navigateToOverview.value = null
    }

    fun onClose() {
        _navigateToOverview.value = true
    }
}