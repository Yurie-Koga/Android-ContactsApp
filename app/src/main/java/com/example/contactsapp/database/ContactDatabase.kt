package com.example.contactsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * version history
 * [1] : original
 * [2] : added = "picture_large"
 */
@Database(entities = [Contact::class], version = 2, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {

    abstract val contactDatabaseDao: ContactDatabaseDao

    companion object {
        /** Database instance: singleton **/
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        "contact_list_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}