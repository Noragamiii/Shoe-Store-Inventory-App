package com.udacity.shoestore.ui.shoedetail

import android.app.AlertDialog
import android.os.Bundle
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
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding

class ShoeDetailFragment : Fragment() {

    private lateinit var binding: FragmentShoeDetailBinding
    private lateinit var viewModel: UIViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_detail,
            container,
            false
        )

        //init ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(UIViewModel::class.java)
        binding.detailShoeDetailViewModel = viewModel
        binding.shoe = viewModel.shoe

        binding.setLifecycleOwner(this)

        viewModel.eventSaveShoeDetailPress.observe(viewLifecycleOwner, Observer{
            if (it) {
                saveShoeDetail()
                viewModel.shoeDetailComplete()
            }
        })

        viewModel.eventCancelShoeDetailPress.observe(viewLifecycleOwner, Observer{
            if (it) {
                cancelShoeDetail()
                viewModel.cancelShoeDetailComplete()
            }
        })

        viewModel.eventFailNameShoeDetail.observe(viewLifecycleOwner, Observer{
            if (it) {
                val message = "Please fill name of shoe"
                showMess(message)
                viewModel.saveFailNameShoeComplete()
            }
        })

        viewModel.eventFailNameCompany.observe(viewLifecycleOwner, Observer{
                if (it) {
                    val message = "Please fill company of shoe"
                    showMess(message)
                    viewModel.saveFailNameCompanyComplete()
                }
        })

        viewModel.eventFailSizeShoeDetail.observe(viewLifecycleOwner, Observer{
            if (it) {
                val message = "Please fill size of shoe"
                showMess(message)
                viewModel.saveFailSizeShoeComplete()
            }
        })

        viewModel.eventSizeViewShoeDetailPress.observe(viewLifecycleOwner, Observer{ view ->
            view?.let {
                clearSizeShoeDetail()
                setSizeSelect(view)
            }
        })

        viewModel.eventPictureShoeDetailPress.observe(viewLifecycleOwner, Observer{ nameModelShoe ->
                nameModelShoe?.let {
                    changePictureShoePress(nameModelShoe)
                }
            })

        changePictureShoePress("model_0")
        (activity as AppCompatActivity).supportActionBar?.show()

        return binding.root
    }

    // Clear shoe
    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearShoeList()
    }

    private fun changePictureShoePress(nameModelShoe: String) {
        when (nameModelShoe) {
            "model_0" -> binding.imageshoe.setImageResource(R.drawable.nike_1)
            "model_1" -> binding.imageshoe.setImageResource(R.drawable.nike_2)
            "model_2" -> binding.imageshoe.setImageResource(R.drawable.adidas_1)
            "model_3" -> binding.imageshoe.setImageResource(R.drawable.adidas_2)
            "model_4" -> binding.imageshoe.setImageResource(R.drawable.lining_1)
            "model_5" -> binding.imageshoe.setImageResource(R.drawable.conan_shoe)
        }
    }

    private fun saveShoeDetail() {
        findNavController().popBackStack()
    }

    private fun cancelShoeDetail() {
        findNavController().popBackStack()
    }

    private fun showMess(message: String) {
        val builder = AlertDialog.Builder(context)

        with(builder)
        {
            setTitle("Error")
            setMessage(message)
            setPositiveButton("OK", null)
            show()
        }
    }

    private fun clearSizeShoeDetail() {
        binding.Buttonone.setBackgroundResource(R.drawable.rounded_circle_1)
        binding.Buttontwo.setBackgroundResource(R.drawable.rounded_circle_1)
        binding.Buttonthree.setBackgroundResource(R.drawable.rounded_circle_1)
        binding.Buttonfour.setBackgroundResource(R.drawable.rounded_circle_1)
    }

    private fun setSizeSelect(view: View) {
        view.setBackgroundResource(R.drawable.rounded_circle)
    }
}