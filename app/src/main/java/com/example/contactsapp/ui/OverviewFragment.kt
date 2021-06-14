package com.example.contactsapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.contactsapp.R
import com.example.contactsapp.database.ContactDatabase
import com.example.contactsapp.databinding.FragmentOverviewBinding
import com.example.contactsapp.util.ContactAdapter
import com.example.contactsapp.viewmodels.OverviewViewModel
import com.example.contactsapp.viewmodels.OverviewViewModelFactory

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

        binding.setLifecycleOwner(this)
        binding.overviewViewModel = overviewViewModel

        /** Bind Database and RecyclerView **/
        val adapter = ContactAdapter()
        binding.contactList.adapter = adapter

        overviewViewModel.contacts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        /** Display Option menu on this fragment **/
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
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
            R.id.action_refresh -> overviewViewModel.refreshContacts()
            R.id.action_clear -> overviewViewModel.clearContacts()
            else -> return true
        }

        return true
    }
}
