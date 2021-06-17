package com.example.contactsapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.contactsapp.domain.ContactProperty
import com.example.contactsapp.domain.Name

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var contactId: Long = 0L,

    @ColumnInfo(name = "name_first")
    var nameFirst: String = "nameF",

    @ColumnInfo(name = "name_last")
    var nameLast: String = "nameL",

    @ColumnInfo(name = "phone_string")
    var phone: String = "123-456-7890"
)

/** Convert Database object to Kotlin object **/
fun List<Contact>.asDomainModel(): List<ContactProperty> {
    return map {
        ContactProperty(
            id = it.contactId,
            name = Name(
                title = "",
                first = it.nameFirst,
                last = it.nameLast
            )
        )
    }
}