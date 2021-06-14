package com.example.contactsapp.util


import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.database.Contact

/**
 * Format to display Contacts data as HTML
 */
fun formatContacts(contacts: List<Contact>, resources: Resources) : Spanned {
    val sb = StringBuilder()
    sb.apply {
        append("<h3>HERE IS YOUR CONTACT DATA</h3>")
        contacts.forEach {
            append("<br>")
            append("<b>ID:</b>")
            append("\t${it.contactId.toString()}")
            append("<br>")
            append("<b>Name:</b>")
            append("\t${it.nameFirst} ${it.nameLast}")
            append("<br>")
            append("<b>Phone:</b>")
            append("\t${it.phone}")
            append("<br>")
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)