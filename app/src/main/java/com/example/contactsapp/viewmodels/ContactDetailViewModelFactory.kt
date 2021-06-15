package com.example.contactsapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.database.ContactDatabaseDao

class ContactDetailViewModelFactory(
    private val contactKey: Long,
    private val dataSource: ContactDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactDetailViewModel::class.java)) {
            return ContactDetailViewModel(contactKey, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}