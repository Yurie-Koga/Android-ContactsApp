package com.example.contactsapp.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.database.Contact
import com.example.contactsapp.databinding.ListItemContactBinding

/** ContactAdapter for all the Contact data **/
class ContactAdapter(val clickListener: ContactListener) : ListAdapter<Contact, ContactAdapter.ViewHolder>(ContactDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val priorItem = if (position > 0) getItem(position - 1) else null
        holder.bind(item, priorItem, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemContactBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contact, priorItem: Contact?, clickListener: ContactListener) {
            binding.contact = item
            binding.executePendingBindings()
            binding.clickListener = clickListener

            /** Temporary use ID as section letter  **/
//            val curLetter = item.nameFirst.substring(0, 1)
            val curLetter = item.contactId.toString().substring(0, 1)

            /** Section in the Contact list **/
            binding.textHeaderLetter.visibility = View.VISIBLE
            priorItem?.let {
                /** Temporary use ID as section letter  **/
                if (curLetter == it.contactId.toString().substring(0, 1)) {
//                if (curLetter == it.nameFirst.substring(0, 1)) {
                    // if the first letter is the same as the prior contact name, hide header text view
                    binding.textHeaderLetter.visibility = View.GONE
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemContactBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ContactDiffCallback: DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}

/** Click Listener for RecyclerView **/
class ContactListener(val clickListener: (contactId: Long) -> Unit) {
    fun onClick(contact: Contact) = clickListener(contact.contactId)
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