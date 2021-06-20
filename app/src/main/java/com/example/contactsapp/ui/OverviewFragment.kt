package com.example.contactsapp.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.contactsapp.R
import com.example.contactsapp.databinding.FragmentOverviewBinding
import com.example.contactsapp.util.ContactAdapter
import com.example.contactsapp.util.ContactListener
import com.example.contactsapp.viewmodels.OverviewViewModel
import com.example.contactsapp.viewmodels.OverviewViewModelFactory

/**
 * [Fragment] for the default destination in the navigation.
 * Display the contact list and navigate to ContactDetail Fragment by tapping items.
 * By tapping fab, navigates to AddContact Fragment.
 */
class OverviewFragment : Fragment() {

    private lateinit var overviewViewModel: OverviewViewModel

    private var _binding: FragmentOverviewBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        /** Bind View and ViewModel **/
        val application = requireNotNull(this.activity).application
        val viewModelFactory = OverviewViewModelFactory(application)
        overviewViewModel =
            ViewModelProvider(this, viewModelFactory).get(OverviewViewModel::class.java)
        binding.lifecycleOwner = this
        binding.overviewViewModel = overviewViewModel

        /** Bind Kotlin Object and RecyclerView **/
        val adapter = ContactAdapter(ContactListener { contactProperty ->
            overviewViewModel.onContactClicked(contactProperty.id)
        })
        binding.contactList.adapter = adapter
        overviewViewModel.contactList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })


        /** Observer for Navigation to ContactDetail Fragment on RecyclerView item click **/
        overviewViewModel.navigateToContactDetail.observe(viewLifecycleOwner, Observer { contactId ->
            contactId?.let {
                this.findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToContactDetailFragment(contactId)
                )
                overviewViewModel.onContactDetailNavigated()
            }
        })


        /** Observer for Navigation to AddContact Fragment **/
        overviewViewModel.navigateToAddContact.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToAddContactFragment()
                )
                overviewViewModel.onAddContactNavigated()
            }
        })

        /** Observer for NetworkError **/
        overviewViewModel.eventNetworkError.observe(viewLifecycleOwner, Observer { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        /** Display Option menu on this fragment **/
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** NetworkError Event **/
    private fun onNetworkError() {
        if (!overviewViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            overviewViewModel.onNetworkErrorShown()
        }
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
        when (item.itemId) {
            R.id.action_refresh_small -> overviewViewModel.refreshDataFromRepository(5)
            R.id.action_refresh_medium -> overviewViewModel.refreshDataFromRepository(50)
            R.id.action_refresh_large -> overviewViewModel.refreshDataFromRepository(100)
            R.id.action_clear -> overviewViewModel.clearContacts()
            else -> return true
        }

        return true
    }
}
