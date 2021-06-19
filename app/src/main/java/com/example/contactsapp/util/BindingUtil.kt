package com.example.contactsapp.util

import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * BindingAdapter for Contact Data in [list_item_contact.xml], [fragment_contact_detail.xml] ----------------------------------------------------------
 */

/** Section Letter (Header of Contact List) **/
@BindingAdapter("sectionLetter")
fun TextView.setSection(firstName: String?) {
    firstName?.let {
        text = it.substring(0, 1)
    }
}

/** Full Name **/
@BindingAdapter("firstName", "lastName")
fun TextView.setFullName(firstName: String?, lastName: String?) {
    var fullName = StringBuilder()
    firstName?.let { fullName.append(firstName).append(" ") }
    lastName?.let { fullName.append(lastName) }
    text = fullName
}

/** Address **/
@BindingAdapter("streetNumber", "streetName", "city", "state", "country", "postcode")
fun TextView.setAddress(streetNum: Int?, streetName: String?, city: String?, state: String?, country: String?, postcode: Int?) {
    var address = StringBuilder()
    if (streetNum != null && streetNum != 0) { address.append(streetNum.toString()).append(" ") }
    streetName?.let { address.append(streetName).append(" ") }
    city?.let { address.append(city).append(" ") }
    state?.let { address.append(state).append(" ") }
    country?.let { address.append(country).append(" ") }
    if (postcode != null && postcode != 0) { address.append(postcode.toString()) }
    text = address
}

/** PhoneNumber **/
@BindingAdapter("phoneNumber")
fun TextView.setPhoneNumberFormatted(phone: String?) {
    phone?.let {
        // Format to '(123)-456-7890'
        text = it.replaceFirst(Regex("\\(?(\\d{3})\\)?[\\s-]?(\\d{3})[\\s-]?(\\d+)"), "($1)-$2-$3");
    }
}

