package com.example.contactsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.database.Contact
import com.example.contactsapp.database.ContactDatabaseDao
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class AddContactViewModel(
    val database: ContactDatabaseDao,
    application: Application
): AndroidViewModel(application) {

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


    /** Methods for Database **/
    private suspend fun insert(contact: Contact) {
        database.insert(contact)
    }


    /** Save Button **/
    fun onSaveClick(fullName: String, phone: String) {
        var items = fullName.split(Regex("\\s+")).toMutableList()
        if (items.isNotEmpty()) {
            // convert fullname to each element
//            items.map { word -> word.replaceFirstChar { it.uppercase() } }
            items.map { word -> word.uppercase() }
            val firstName = items.removeAt(0)
            val lastName = items.joinToString(" ")
            val newContact = Contact(nameFirst = firstName, nameLast = lastName, phone = phone)

            // insert to database
            viewModelScope.launch {
                insert(newContact)
            }
        }
    }

}