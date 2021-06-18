package com.example.contactsapp.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import com.example.contactsapp.R
import com.example.contactsapp.database.ContactDatabase
import com.example.contactsapp.databinding.FragmentAddContactBinding
import com.example.contactsapp.viewmodels.AddContactViewModel
import com.example.contactsapp.viewmodels.AddContactViewModelFactory
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddContactFragment : Fragment() {

    private var _binding: FragmentAddContactBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddContactBinding.inflate(inflater, container, false)

        /** Bind Database and ViewModel **/
        val application = requireNotNull(this.activity).application
        val dataSource = ContactDatabase.getInstance(application).contactDatabaseDao
        val viewModelFactory = AddContactViewModelFactory(dataSource, application)
        val addContactViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddContactViewModel::class.java)

        binding.setLifecycleOwner(this)
        binding.addContactViewModel = addContactViewModel

        /** Observer for Navigation **/
        addContactViewModel.navigateToOverview.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    AddContactFragmentDirections.actionAddContactFragmentToOverviewFragment()
                )
                addContactViewModel.doneNavigating()
            }
        })

        /** Save Button **/
        binding.buttonSave.setOnClickListener {
            it?.let {
                val imm = ContextCompat.getSystemService(it.context, InputMethodManager::class.java)
                imm?.hideSoftInputFromWindow(it.windowToken, 0)
                addContactViewModel.onSaveClick(binding.editTextName.text.toString(), binding.editTextPhone.text.toString())
                Snackbar.make(it, "Saved!", Snackbar.LENGTH_SHORT).setAction("Action", null).show()

                resetUI()
            }
        }

        return binding.root
    }

    private fun resetUI() {
        binding.editTextName.text = null
        binding.editTextPhone.text = null
        binding.editTextName.requestFocus()
        binding.buttonSave.isEnabled = false
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Set up default UI **/
        resetUI()


        /** ConstraintLayout  **/
        binding.constraintlayoutWall.setOnClickListener {
            it?.let {
                // hide keyboard
                val imm = ContextCompat.getSystemService(it.context, InputMethodManager::class.java)
                imm?.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }

        /** ContactName EditText **/
        binding.editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.buttonSave.isEnabled = false
                s?.let {
                    if (Regex("(?i)^\\s*[a-z\\d]+\\s+[a-z\\d]+").containsMatchIn(it)) {
                        binding.buttonSave.isEnabled = true
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })


        /** Cancel Button **/
        binding.buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_AddContactFragment_to_OverviewFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}