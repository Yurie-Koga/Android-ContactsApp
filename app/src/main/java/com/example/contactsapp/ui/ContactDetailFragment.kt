package com.example.contactsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.contactsapp.databinding.FragmentContactDetailBinding
import com.example.contactsapp.viewmodels.ContactDetailViewModel
import com.example.contactsapp.viewmodels.ContactDetailViewModelFactory


/**
 * [Fragment] for displaying the contact details.
 */
class ContactDetailFragment : Fragment() {

    private lateinit var contactDetailViewModel: ContactDetailViewModel

    private var _binding: FragmentContactDetailBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)

        /** Bind View and ViewModel **/
        val application = requireNotNull(this.activity).application
        val arguments = ContactDetailFragmentArgs.fromBundle(arguments)
        val viewModelFactory = ContactDetailViewModelFactory(arguments.contactKey, application)
        contactDetailViewModel =
            ViewModelProvider(this, viewModelFactory).get(ContactDetailViewModel::class.java)

        binding.setLifecycleOwner(this)
        binding.contactDetailViewModel = contactDetailViewModel

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}