package com.example.contactsapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.contactsapp.database.Contact
import com.example.contactsapp.database.ContactDatabaseDao
import com.example.contactsapp.domain.ContactProperty
import com.example.contactsapp.network.ContactApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

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
    //import com.example.contactsapp.util.formatContacts
//    val contactsString = Transformations.map(contacts) { contacts ->
//        formatContacts(contacts, application.resources)
//    }

    /** For Initialization **/
    init {
        // Data from Room Database
        initializeLatestContact()

        // Data from a API Response
        getContactProperties()
    }

    private fun initializeLatestContact() {
        viewModelScope.launch {
            latestContact.value = getLatestContactFromDatabase()
        }
    }


    /** For a API call **/
    private var _response: String = ""
    val response: String
        get() = _response
    private fun getContactProperties() {
        ContactApi.retrofitService.getProperties().enqueue(
            object : Callback<ContactProperty> {
                override fun onResponse(call: Call<ContactProperty>, response: Response<ContactProperty>) {
                    _response = response.body().toString()
                    Timber.i("_response onResponse: ${_response}")
                }

                override fun onFailure(call: Call<ContactProperty>, t: Throwable) {
                    _response = "Failure: " + t.message
                    Timber.i("_response onFailure: ${_response}")
                }
            })
    }



    /** Navigation for ContactDetail Fragment **/
    private val _navigateToContactDetail = MutableLiveData<Long>()

    val navigateToContactDetail: LiveData<Long>
        get() = _navigateToContactDetail

    fun onContactClicked(id: Long) {
        _navigateToContactDetail.value = id
    }

    fun onContactDetailNavigated() {
        _navigateToContactDetail.value = null
    }

//    fun doneNavigating() {
//        _navigateToContactDetail.value = null
//    }
//
//    fun onClose() {
//        //TODO("Will delete this function or change to send clicked item")
//        _navigateToContactDetail.value = latestContact.value
//    }


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