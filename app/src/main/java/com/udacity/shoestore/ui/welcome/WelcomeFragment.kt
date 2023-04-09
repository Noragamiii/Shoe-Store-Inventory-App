package com.udacity.shoestore.ui.welcome

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.UIViewModel
import com.udacity.shoestore.databinding.FragmentWelcomeScreenBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeScreenBinding
    private lateinit var viewModel: UIViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_welcome_screen,
            container,
            false
        )

        //init ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(UIViewModel::class.java)
        binding.welcomeScreen = viewModel

        binding.setLifecycleOwner(this)

        viewModel.eventWelcome.observe(viewLifecycleOwner, Observer {
            if(it) {
                gotoInstructionScreen()
                viewModel.gotoInstructionComplete()
            }
        })

        (activity as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    fun gotoInstructionScreen() {
        findNavController().navigate(WelcomeFragmentDirections.actionWelcomeToInstruction())
    }
}