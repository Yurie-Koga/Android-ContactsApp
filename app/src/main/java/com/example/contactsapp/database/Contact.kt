package com.example.contactsapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.contactsapp.domain.ContactProperty
import com.example.contactsapp.domain.Location
import com.example.contactsapp.domain.Name
import com.example.contactsapp.domain.Street

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var contactId: Long = 0L,

    @ColumnInfo(name = "gender")
    var gender: String = "",

    @ColumnInfo(name = "name_first")
    var nameFirst: String = "",

    @ColumnInfo(name = "name_last")
    var nameLast: String = "",

    @ColumnInfo(name = "street_number")
    var streetNumber: Int = 0,

    @ColumnInfo(name = "street_name")
    var streetName: String = "",

    @ColumnInfo(name = "city")
    var city: String = "",

    @ColumnInfo(name = "state")
    var state: String = "",

    @ColumnInfo(name = "country")
    var country: String = "",

    @ColumnInfo(name = "postcode")
    var postcode: Int = 0,

    @ColumnInfo(name = "email")
    var email: String = "",

    @ColumnInfo(name = "phone_string")
    var phone: String = ""
)

/** Convert Database object to Kotlin object **/
fun List<Contact>.asDomainModel(): List<ContactProperty> {
    return map {
        it.asDomainModel()
    }
}

fun Contact.asDomainModel(): ContactProperty {
    return ContactProperty(
            id = contactId,
            gender = gender,
            name = Name(
                first = nameFirst,
                last = nameLast
            ),
            location = Location(
                Street(streetNumber, streetName),
                city,
                state,
                country,
                postcode
            ),
            email = email,
            phone = phone
        )
}