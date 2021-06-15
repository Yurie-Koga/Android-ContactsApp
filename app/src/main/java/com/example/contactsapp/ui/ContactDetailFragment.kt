package com.example.contactsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.contactsapp.database.ContactDatabase
import com.example.contactsapp.databinding.FragmentContactDetailBinding
import com.example.contactsapp.viewmodels.ContactDetailViewModel
import com.example.contactsapp.viewmodels.ContactDetailViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [ContactDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactDetailFragment : Fragment() {

    private lateinit var contactDetailViewModel: ContactDetailViewModel

    private var _binding: FragmentContactDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)

        /** Bind Database and ViewModel **/
        val application = requireNotNull(this.activity).application
        val arguments = ContactDetailFragmentArgs.fromBundle(arguments)
        val dataSource = ContactDatabase.getInstance(application).contactDatabaseDao
        val viewModelFactory = ContactDetailViewModelFactory(arguments.contactKey, dataSource, application)
        contactDetailViewModel =
            ViewModelProvider(this, viewModelFactory).get(ContactDetailViewModel::class.java)

        binding.setLifecycleOwner(this)
        binding.contactDetailViewModel = contactDetailViewModel

        /** Observer for Navigation **/
        contactDetailViewModel.navigateToOverview.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    ContactDetailFragmentDirections.actionContactDetailFragmentToOverviewFragment()
                )
                contactDetailViewModel.doneNavigating()
            }
        })


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}