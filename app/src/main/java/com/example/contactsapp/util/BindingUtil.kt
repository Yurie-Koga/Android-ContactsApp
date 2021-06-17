package com.example.contactsapp.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.contactsapp.database.Contact
import com.example.contactsapp.domain.ContactProperty

/**
 * BindingAdapter for Kotlin Object ----------------------------------------------------------
 */
@BindingAdapter("contactSection")
fun TextView.setSection(item: ContactProperty?) {
    item?.let {
        text = item.name.first.toString().substring(0, 1)
    }
}

@BindingAdapter("fullName")
fun TextView.setFullName(item: ContactProperty?) {
    item?.let {
        val fullName = "${item.name.first} ${item.name.last}"
        text = fullName
    }
}

@BindingAdapter("phoneNumber")
fun TextView.setPhoneNumberFormatted(item: ContactProperty?) {
    item?.let {
        // Format to '(123)-456-7890'
        text = item.phone.replaceFirst(Regex("\\(?(\\d{3})\\)?[\\s-]?(\\d{3})[\\s-]?(\\d+)"), "($1)-$2-$3");
    }
}

/**
 * BindingAdapter for Database ----------------------------------------------------------
 */
@BindingAdapter("contactSection")
fun TextView.setSection(item: Contact?) {
    item?.let {
        text = item.contactId.toString().substring(0, 1)
    }
}

@BindingAdapter("fullName")
fun TextView.setFullName(item: Contact?) {
    item?.let {
        val fullName = "${item.contactId}: ${item.nameFirst} ${item.nameLast}"
        text = fullName
    }
}

@BindingAdapter("phoneNumber")
fun TextView.setPhoneNumberFormatted(item: Contact?) {
    item?.let {
        // Format to '123-456-7890'
        text = item.phone.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
    }
}

