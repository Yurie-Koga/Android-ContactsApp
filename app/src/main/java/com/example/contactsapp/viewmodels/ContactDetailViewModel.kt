package com.example.contactsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contactsapp.database.ContactDatabase
import com.example.contactsapp.repository.ContactsRepository


class ContactDetailViewModel(
    contactKey: Long = 0L,
    application: Application
) : AndroidViewModel(application) {

    /** Repository for data access **/
    private val contactsRepository = ContactsRepository(ContactDatabase.getInstance(application))
    val contact = contactsRepository.getContactProperty(contactKey)


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