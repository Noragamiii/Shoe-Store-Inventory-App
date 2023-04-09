package com.udacity.shoestore.ui.shoelist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.UIViewModel
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.databinding.FragmentShoeModelBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.ui.login.LoginFragmentDirections

class ShoeListFragment : Fragment() {
    private lateinit var binding: FragmentShoeListBinding
    private lateinit var viewModel: UIViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBackPressedConfiguration()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_list,
            container,
            false
        )

        //init ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(UIViewModel::class.java)
        binding.listShoe = viewModel

        binding.setLifecycleOwner(this)

        viewModel.eventAddShoeListPress.observe(viewLifecycleOwner, Observer {
            if(it) {
                gotoShoeDetailScreen()
                viewModel.goToShoeDetailStartComplete()
            }
        })

        viewModel.shoesList.observe(viewLifecycleOwner, Observer { listShoes ->
            initShoeList(listShoes)
        })

        // Action Bar
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        return binding.root
    }

    fun gotoShoeDetailScreen() {
        findNavController().navigate(ShoeListFragmentDirections.actionShoelistToShoedetail())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(ShoeListFragmentDirections.actionShoelistToLoginfragment())
        return super.onOptionsItemSelected(item)
    }

    private fun setBackPressedConfiguration() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val i = Intent()
                i.action = Intent.ACTION_MAIN
                i.addCategory(Intent.CATEGORY_HOME)
                startActivity(i)
            }
        })
    }

    private fun initShoeList(listShoes: MutableList<Shoe>) {
        val parentLayout = binding.shoelist

        var index = 0
        while (index < listShoes.size) {
            val shoeBinding: FragmentShoeModelBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_shoe_model,
                parentLayout,
                false
            )

            val shoe = listShoes[index]
            shoeBinding.nameshoe.text = shoe.name
            shoeBinding.companyshoe.text = shoe.company
            shoeBinding.sizeshoe.text = shoe.size.toString()
            when (shoe.modelsAvailable[shoe.modelShoe]) {
                "model_0" -> shoeBinding.shoe.setImageResource(R.drawable.nike_1)
                "model_1" -> shoeBinding.shoe.setImageResource(R.drawable.nike_2)
                "model_2" -> shoeBinding.shoe.setImageResource(R.drawable.adidas_1)
                "model_3" -> shoeBinding.shoe.setImageResource(R.drawable.adidas_2)
                "model_4" -> shoeBinding.shoe.setImageResource(R.drawable.lining_1)
                "model_5" -> shoeBinding.shoe.setImageResource(R.drawable.conan_shoe)
            }
            parentLayout.addView(shoeBinding.root)

            index++
        }
    }
}

