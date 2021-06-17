package com.example.contactsapp.network

import com.example.contactsapp.domain.ContactProperty
import com.example.contactsapp.domain.Name
//import com.example.contactsapp.domain.Result
import com.squareup.moshi.JsonClass

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 *
 * @see domain package for
 */

/** Multiple records, only name ---------------------------------------------------- start -----**/
@JsonClass(generateAdapter = true)
data class NetworkContactContainer(
    val results: List<NetworkResult>
)

//@JsonClass(generateAdapter = true)
//data class NetworkContact(
//    val results: List<NetworkResult>
//)

@JsonClass(generateAdapter = true)
data class NetworkResult(
    val name: NetworkName
)

@JsonClass(generateAdapter = true)
data class NetworkName(
    val title: String,
    val first: String,
    val last: String
)

fun NetworkContactContainer.asDomainModel(): List<ContactProperty> {
    return results.map {
        ContactProperty(
            name = Name(
                it.name.title,
                it.name.first,
                it.name.last
            )
        )
    }
}
/** Multiple records, only name ---------------------------------------------------- end -----**/

//data class NetworkContact(
//    val gender: String,
//    val name_title: String,
//    val name_first: String,
//    val name_last: String,
//    val location_street_number: String,
//    val location_street_name: String,
//    val location_city: String,
//    val location_state: String,
//    val location_country: String,
//    val location_postcode: String,
//    val location_coordinates_latitude: String,
//    val location_coordinates_longitude: String,
//    val location_timezone_offset: String,
//    val location_timezone_description: String,
//    val email: String,
//    val phone: String
//)

/** Convert Network results to Kotlin objects **/
//fun NetworkContactContainer.asDomainModel(): List<ContactProperty> {
//    return contacts.map {
//        ContactProperty(
//            results = it.results
//        )
//    }
//}

//fun NetworkContactContainer.asDomainModel(): List<ContactProperty> {
//    return contacts.map {
//        ContactProperty(
//            gender = it.gender,
//            name_title = it.name_title,
//            name_first = it.name_first,
//            name_last = it.name_last,
//            location_street_number = it.location_street_number,
//            location_street_name = it.location_street_name,
//            location_city = it.location_city,
//            location_state = it.location_state,
//            location_country = it.location_country,
//            location_postcode = it.location_postcode,
//            location_coordinates_latitude = it.location_coordinates_latitude,
//            location_coordinates_longitude = it.location_coordinates_longitude,
//            location_timezone_offset = it.location_timezone_offset,
//            location_timezone_description = it.location_timezone_description,
//            email = it.email,
//            phone = it.phone
//        )
//    }
//}