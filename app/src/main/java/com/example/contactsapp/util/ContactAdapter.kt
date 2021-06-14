package com.example.contactsapp.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.R
import com.example.contactsapp.database.Contact

/** ContactAdapter for all the Contact data **/
class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    var data = listOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val priorItem = if (position > 0) data[position - 1] else null
        holder.bind(item, priorItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerLetter: TextView = itemView.findViewById(R.id.text_header_letter)
        val contactName: TextView = itemView.findViewById(R.id.text_contact_name)
        val contactPhone: TextView = itemView.findViewById(R.id.text_contact_phone)

        fun bind(item: Contact, priorItem: Contact?) {
            val res = itemView.context.resources
            /** Temporary use ID as section letter  **/
//            val curLetter = item.nameFirst.substring(0, 1)
            val curLetter = item.contactId.toString().substring(0, 1)
            val fullName = "${item.contactId}: ${item.nameFirst} ${item.nameLast}"

            headerLetter.text = curLetter
            contactName.text = fullName
            contactPhone.text = item.phone

            /** Section in the Contact list **/
            headerLetter.visibility = View.VISIBLE
            priorItem?.let {
                /** Temporary use ID as section letter  **/
                if (curLetter == it.contactId.toString().substring(0, 1)) {
//                if (curLetter == it.nameFirst.substring(0, 1)) {
                    // if the first letter is the same as the prior contact name, hide header text view
                    headerLetter.visibility = View.GONE
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item_contact, parent, false)
                return ViewHolder(view)
            }
        }
    }
}

/** ContactAdapter for Contact ID **/
/*
class ContactAdapter : RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        /** For one line TextView **/
        holder.textView.text = item.contactId.toString()
//        holder.textView.text_contact_id.text = item.contactId.toString()  // access by id : works

        /** Tried two lines TextView **/
//        val contactName = "${item.nameFirst} ${item.nameLast}"
//        holder.textView.text_contact_name.text = contactName
//        holder.textView.text_contact_phone.text = item.phone
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        /** For one line TextView **/
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView

        /** Tried two lines TextView **/
//        val view = layoutInflater.inflate(R.layout.list_item_contact, parent, false) as TextView
        return TextItemViewHolder(view)
    }
}
 */