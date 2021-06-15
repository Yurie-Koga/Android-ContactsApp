package com.example.contactsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contactsapp.database.Contact
import com.example.contactsapp.database.ContactDatabaseDao

class ContactDetailViewModel(
    private val contactKey: Long = 0L,
    val database: ContactDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val contact: LiveData<Contact>

    fun getContact() = contact

    init {
        contact = database.getContactWithId(contactKey)
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