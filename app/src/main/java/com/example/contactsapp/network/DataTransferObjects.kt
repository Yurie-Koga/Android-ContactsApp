package com.example.contactsapp.network

import com.example.contactsapp.database.Contact
import com.example.contactsapp.domain.*
import com.squareup.moshi.JsonClass

/**
 * Convert network response to JSON class.
 * Convert that data to Kotlin Object and Database Object.
 *
 * @see domain for objects that are used in this app
 * @see database for objects that are mapped to the database
 */

/** Top layer of JSON Response : "{ "results": [...] }" **/
@JsonClass(generateAdapter = true)
data class NetworkContactContainer(
    val results: List<NetworkResult>
)

/** Equivalent to the data inside 'results: []' in the JSON Response **/
@JsonClass(generateAdapter = true)
data class NetworkResult(
    val gender: String,
    val name: NetworkName,
    val location: NetworkLocation,
    val email: String,
    val phone: String,
    val picture: NetworkPicture
)

@JsonClass(generateAdapter = true)
data class NetworkName(
    val title: String,
    val first: String,
    val last: String
)

@JsonClass(generateAdapter = true)
data class NetworkLocation(
    val street: NetworkStreet,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Int
)

@JsonClass(generateAdapter = true)
data class NetworkStreet(
    val number: Int,
    val name: String
)

@JsonClass(generateAdapter = true)
data class NetworkPicture(
    val large: String
)


/** Convert Network result to Kotlin Object **/
fun NetworkContactContainer.asDomainModel(): List<ContactProperty> {
    return results.map {
        ContactProperty(
            gender = it.gender,
            name = Name(
                it.name.first,
                it.name.last
            ),
            location = Location(
                Street(it.location.street.number, it.location.street.name),
                it.location.city,
                it.location.state,
                it.location.country,
                it.location.postcode
            ),
            email = it.email,
            phone = it.phone,
            picture = Picture(it.picture.large)
        )
    }
}

/** Convert Network result to Database Object **/
fun NetworkContactContainer.asDatabaseModel(): List<Contact> {
    return results.map {
        Contact(
            gender = it.gender,
            nameFirst = it.name.first,
            nameLast = it.name.last,
            streetNumber = it.location.street.number,
            streetName = it.location.street.name,
            city = it.location.city,
            state = it.location.state,
            country = it.location.country,
            postcode = it.location.postcode,
            email = it.email,
            phone = it.phone,
            pictureLarge = it.picture.large
        )
    }
}
