package com.example.contactsapp.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.contactsapp.database.Contact



@BindingAdapter("contactSection")
fun TextView.setSection(item: Contact) {
    text = item.contactId.toString().substring(0, 1)
}

@BindingAdapter("fullName")
fun TextView.setFullName(item: Contact) {
    val fullName = "${item.contactId}: ${item.nameFirst} ${item.nameLast}"
    text = fullName
}

@BindingAdapter("phoneNumber")
fun TextView.setPhoneNumberFormatted(item: Contact) {
    // Format to '123-456-7890'
    text = item.phone.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
}

