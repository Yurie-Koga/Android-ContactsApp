package com.example.contactsapp

import android.app.Application
import timber.log.Timber

/**
 * Declare at global level
 *
 * Make sure to add [android:name=".ContactsApplication"] to [AndroidManifest.xml]
 */
class ContactsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        /** Create an instance for Timber logging **/
        Timber.plant(Timber.DebugTree())
    }
}