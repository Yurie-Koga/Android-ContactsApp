package com.example.contactsapp.domain

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app.
 * These are the objects that should be displayed on screen, or manipulated by the app.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects that parse or prepare network calls
 */

// Equivalent to the data inside 'results: []' in the JSON Response
data class ContactProperty(
    val id: Long = 0L,
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val phone: String,
    val picture: Picture
)

data class Name(
    val first: String,
    val last: String
)

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Int
)

data class Street(
    val number: Int,
    val name: String
)

data class Picture(
    val large: String
)