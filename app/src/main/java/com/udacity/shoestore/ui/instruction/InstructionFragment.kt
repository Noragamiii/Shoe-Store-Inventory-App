package com.udacity.shoestore.ui.instruction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.UIViewModel
import com.udacity.shoestore.databinding.FragmentInstructionBinding
import com.udacity.shoestore.databinding.FragmentWelcomeScreenBinding
import com.udacity.shoestore.ui.login.LoginFragmentDirections

class InstructionFragment : Fragment() {

    private lateinit var binding: FragmentInstructionBinding
    private lateinit var viewModel: UIViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_instruction,
            container,
            false
        )

        //init ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(UIViewModel::class.java)
        binding.instructionScreen = viewModel

        binding.setLifecycleOwner(this)

        viewModel.eventInstruction.observe(viewLifecycleOwner, Observer {
            if(it) {
                gotoShoeListScreen()
                viewModel.goToShoeListComplete()
            }
        })

        (activity as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    fun gotoShoeListScreen() {
        findNavController().navigate(InstructionFragmentDirections.actionInstructionToShoelist())
    }


}