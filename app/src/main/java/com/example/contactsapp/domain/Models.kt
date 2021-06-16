package com.example.contactsapp.domain

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects that parse or prepare network calls
 */


/**
 * {
"results": [
{
"gender": "male",
"name": {
"title": "Mr",
"first": "Vincent",
"last": "Gagné"
},
"location": {
"street": {
"number": 2316,
"name": "Balmoral St"
},
"city": "Flatrock",
"state": "New Brunswick",
"country": "Canada",
"postcode": "Y8I 2P3",
"coordinates": {
"latitude": "-66.9071",
"longitude": "-19.3852"
},
"timezone": {
"offset": "+11:00",
"description": "Magadan, Solomon Islands, New Caledonia"
}
},
"email": "vincent.gagne@example.com",
"phone": "862-473-9434"
}
]
}
 */
/*
data class ContactProperty(
    val gender: String,
    val name_title: String,
    val name_first: String,
    val name_last: String,
    val location_street_number: String,
    val location_street_name: String,
    val location_city: String,
    val location_state: String,
    val location_country: String,
    val location_postcode: String,
    val location_coordinates_latitude: String,
    val location_coordinates_longitude: String,
    val location_timezone_offset: String,
    val location_timezone_description: String,
    val email: String,
    val phone: String
) {
    // Add transformation here as needed:
// E.g. length
//    val shortDescription: String
//        get() = description.smartTruncate(200)
}
*/

/** Multiple records, only name ---------------------------------------------------- start -----**/
//{
//    "results": [
//        {
//            "name": {
//                "title": "Mr",
//                "first": "Bob",
//                "last": "Daniels"
//            }
//        }
//    ]
//}
data class ContactProperty(
    val results: List<Result>
)

data class Result(
    val name: Name
)

data class Name(
    val title: String,
    val first: String,
    val last: String
)

/** Multiple records, only name ---------------------------------------------------- end -----**/


