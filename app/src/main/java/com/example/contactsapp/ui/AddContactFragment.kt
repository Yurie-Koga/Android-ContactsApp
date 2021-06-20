package com.example.contactsapp.ui

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
import com.example.contactsapp.databinding.FragmentAddContactBinding
import com.example.contactsapp.viewmodels.AddContactViewModel
import com.example.contactsapp.viewmodels.AddContactViewModelFactory
import com.google.android.material.snackbar.Snackbar

/**
 * [Fragment] for adding a new contact entry.
 */
class AddContactFragment : Fragment() {

    private var _binding: FragmentAddContactBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddContactBinding.inflate(inflater, container, false)

        /** Bind View and ViewModel **/
        val application = requireNotNull(this.activity).application
        val viewModelFactory = AddContactViewModelFactory(application)
        val addContactViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddContactViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.addContactViewModel = addContactViewModel

        /** Observer for Cancel Button onClick() **/
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
                // save data
                addContactViewModel.onSaveClick(binding.editTextName.text.toString(), binding.editTextPhone.text.toString())

                resetUI(false)
                Snackbar.make(it, "Saved!", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Set up default UI **/
        resetUI(true)

        /** ConstraintLayout  **/
        binding.constraintlayoutWall.setOnClickListener {
            it?.let {
                // hide keyboard
                setKeyboard(false)
            }
        }

        /** Methods for text changes on ContactName EditText **/
        binding.editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let { binding.buttonSave.isEnabled = isSaveButtonEnabled() }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        /** Methods for text changes on Phone EditText **/
        binding.editTextPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let { binding.buttonSave.isEnabled = isSaveButtonEnabled() }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /** Reset UI **/
    private fun resetUI(showKeyboard: Boolean = false) {
        binding.editTextName.text = null
        binding.editTextPhone.text = null
        binding.buttonSave.isEnabled = false
        binding.editTextName.requestFocus()

        setKeyboard(showKeyboard, binding.editTextName)
    }
    private fun setKeyboard(show: Boolean = false, setTo: View? = null) {
        val imm = view?.let { ContextCompat.getSystemService(it.context, InputMethodManager::class.java) }
        if (show && setTo != null) {
            imm?.showSoftInput(setTo, InputMethodManager.SHOW_IMPLICIT)
        } else {
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }


    /** Check if Save button can be enabled **/
    private fun isSaveButtonEnabled() : Boolean {
        val name = binding.editTextName.text.toString()
        val phone = binding.editTextPhone.text.toString()
        if (!Regex("(?i)^\\s*[a-z\\d]+\\s+[a-z\\d]+").containsMatchIn(name)) { return false }
        if (!Regex("^\\s*\\d{10}\\s*$").matches(phone)) { return false }

        return true
    }
}