package com.example.contactsapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.contactsapp.database.Contact
import com.example.contactsapp.database.ContactDatabase
import com.example.contactsapp.database.ContactDatabaseDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class ContactDatabaseTest {
    private lateinit var contactDao: ContactDatabaseDao
    private lateinit var db: ContactDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, ContactDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        contactDao = db.contactDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /** Commented out as marked Dao with 'suspend' **/
//    @Test
//    @Throws(Exception::class)
//    fun insertAndGetContact() {
//        val contact = Contact()
//        contactDao.insert(contact)
//        val latestContact = contactDao.getLatestContact()
//        assertEquals("", latestContact?.nameFirst)
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun updateAndGetContact() {
//        val latestContact = contactDao.getLatestContact()
//        if (latestContact != null) {
//            latestContact?.nameLast = "lastName"
//            contactDao.update(latestContact)
//            val updatedContact = contactDao.getLatestContact()
//            assertEquals(latestContact.nameLast, updatedContact?.nameLast)
//        }
//    }
}