package com.example.contactsapp.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.databinding.ListItemContactBinding
import com.example.contactsapp.domain.ContactProperty
import java.lang.Exception

/**
 * Adapter for RecyclerView with Kotlin Object ----------------------------------------------------------------------
 */
class ContactAdapter(val clickListener: ContactListener) :
    ListAdapter<ContactProperty, ContactAdapter.ViewHolder>(ContactDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val priorItem: ContactProperty? = try { getItem(position - 1) } catch (e: Exception) { null }
        holder.bind(item, priorItem, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ContactProperty,
            priorItem: ContactProperty?,
            clickListener: ContactListener
        ) {
            binding.domaincontact = item
            binding.executePendingBindings()
            binding.clickListener = clickListener

            val curLetter = item.name.first.substring(0, 1)

            /** Section in the Contact list **/
            binding.textHeaderLetter.visibility = View.VISIBLE
            priorItem?.let {
                if (curLetter == it.name.first.substring(0, 1)) {
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

/** Observers for Data changes **/
class ContactDiffCallback: DiffUtil.ItemCallback<ContactProperty>() {
    override fun areItemsTheSame(oldItem: ContactProperty, newItem: ContactProperty): Boolean {
        // issue: when a new entry is added to top with the same header letter, header section of the post item isn't hidden
//        return oldItem.id == newItem.id
        return false
    }

    override fun areContentsTheSame(oldItem: ContactProperty, newItem: ContactProperty): Boolean {
        return oldItem == newItem
    }
}

/** Click Listener for RecyclerView **/
class ContactListener(val clickListener: (ContactProperty) -> Unit) {
    fun onClick(contact: ContactProperty) = clickListener(contact)
}
