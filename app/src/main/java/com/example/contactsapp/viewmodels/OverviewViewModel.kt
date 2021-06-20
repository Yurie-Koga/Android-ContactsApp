package com.example.contactsapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.contactsapp.database.ContactDatabase
import com.example.contactsapp.repository.ContactsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    /** Repository for data access **/
    private val contactsRepository = ContactsRepository(ContactDatabase.getInstance(application))
    val contactList = contactsRepository.getAllContactProperty()


    /** For NetworkError Event **/
    private var _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError


    /** For NetworkError Display **/
    private var _isNetworkErrorShown = MutableLiveData(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
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

    fun onAddContactNavigated() {
        _navigateToAddContact.value = null
    }


    /** FAB **/
    fun onAddContactClicked() {
        _navigateToAddContact.value = true
    }


    /** Methods for Option menus **/
    fun refreshDataFromRepository(lengthOfResults: Int) {
        // reset flags each time when Refresh menu selected
        _eventNetworkError.value = false
        _isNetworkErrorShown.value = false

        viewModelScope.launch {
            try {
                // clear Database
                contactsRepository.clear()
                // refresh data
                contactsRepository.refreshContacts(lengthOfResults)
                Timber.i("Success : data has been fetched from Network and stored to Database")
            } catch (e: Exception) {
                if (contactList.value.isNullOrEmpty()) { _eventNetworkError.value = true }
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