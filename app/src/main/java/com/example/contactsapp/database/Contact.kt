package com.example.contactsapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var contactId: Long = 0L,

    @ColumnInfo(name = "name_first")
    var nameFirst: String = "",

    @ColumnInfo(name = "name_last")
    var nameLast: String = "",

    @ColumnInfo(name = "phone_string")
    var phone: String = ""
)
