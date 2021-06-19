package com.example.contactsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.database.Contact
import com.example.contactsapp.database.ContactDatabase
import com.example.contactsapp.database.ContactDatabaseDao
import com.example.contactsapp.repository.ContactsRepository
import kotlinx.coroutines.launch
import java.util.*

class AddContactViewModel(
    val database: ContactDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    /** Repository for data access **/
    private val contactsRepository = ContactsRepository(ContactDatabase.getInstance(application))


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


    /** Save Button **/
    fun onSaveClick(fullName: String, phone: String) {
        val names = fullName.trim().split(Regex("\\s+")).toMutableList()
        if (names.isNotEmpty()) {
            // capitalize
            val capitalizedNames = names.map { word ->
                word.substring(0, 1).uppercase(Locale.getDefault())+
                        word.substring(1).lowercase(Locale.getDefault())
            }.toMutableList()

            val firstName = capitalizedNames.removeAt(0)
            val lastName = capitalizedNames.joinToString(" ")
            val newContact = Contact(nameFirst = firstName, nameLast = lastName, phone = phone.trim())

            // insert to database
            viewModelScope.launch {
                contactsRepository.insert(newContact)
            }
        }
    }
}