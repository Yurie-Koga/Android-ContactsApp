package com.example.contactsapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.contactsapp.R
import com.example.contactsapp.database.ContactDatabase
import com.example.contactsapp.databinding.FragmentOverviewBinding
import com.example.contactsapp.util.ContactAdapter
import com.example.contactsapp.util.ContactListener
import com.example.contactsapp.viewmodels.OverviewViewModel
import com.example.contactsapp.viewmodels.OverviewViewModelFactory
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OverviewFragment : Fragment() {

    private lateinit var overviewViewModel: OverviewViewModel

    private var _binding: FragmentOverviewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        /** Bind Database and ViewModel **/
        val application = requireNotNull(this.activity).application
        val dataSource = ContactDatabase.getInstance(application).contactDatabaseDao
        val viewModelFactory = OverviewViewModelFactory(dataSource, application)
        overviewViewModel =
            ViewModelProvider(this, viewModelFactory).get(OverviewViewModel::class.java)

        binding.lifecycleOwner = this
        binding.overviewViewModel = overviewViewModel

        /** Bind Kotlin Object and RecyclerView **/
        val adapter = ContactAdapter(ContactListener { contactProperty ->
            Snackbar.make(requireView(), "key passed: ${contactProperty.name.first}", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            overviewViewModel.onContactClicked(contactProperty.id)
        })
        binding.contactList.adapter = adapter

        overviewViewModel.contactList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        /** Bind Database and RecyclerView **/
//        val adapter = ContactAdapter(ContactListener { contactId ->
////            Toast.makeText(context, "key passed: ${contactId}", Toast.LENGTH_SHORT).show()
//            Snackbar.make(requireView(), "key passed: ${contactId}", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//            overviewViewModel.onContactClicked(contactId)
//        })
//        binding.contactList.adapter = adapter
//
//        overviewViewModel.contacts.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.submitList(it)
//            }
//        })

        /** Observer for Navigation **/
        overviewViewModel.navigateToContactDetail.observe(viewLifecycleOwner, Observer { contact ->
            contact?.let {
                this.findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToContactDetailFragment(contact)
                )
                overviewViewModel.onContactDetailNavigated()
            }
        })


        /** Display Option menu on this fragment **/
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** FAB Button **/
        binding.fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_OverviewFragment_to_AddContactFragment)
//            view.findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToContactDetailFragment(1))
        }

//        /** Contacts List : Will update for RecyclerView **/
//        binding.contactList.setOnClickListener { view ->
//            findNavController().navigate(R.id.action_OverviewFragment_to_ContactDetailFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Update ViewModel when data action is selected
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(context, "Option clicked", Toast.LENGTH_SHORT).show()
        when (item.itemId) {
            R.id.action_refresh -> overviewViewModel.refreshDataFromRepository()
            R.id.action_clear -> overviewViewModel.clearContacts()
            else -> return true
        }

        return true
    }
}
