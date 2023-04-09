package com.udacity.shoestore

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class UIViewModel() : ViewModel() {

    var shoe = Shoe(
        "",
        0,
        "",
        "",
        0
    )

    // Login Screen ---
    private val _eventLogin = MutableLiveData(false)
    val eventLogin: LiveData<Boolean>
        get() = _eventLogin
    // --------

    // Welcome Screen ---
    private val _eventWelcome = MutableLiveData(false)
    val eventWelcome: LiveData<Boolean>
        get() = _eventWelcome
    // --------

    // Instructions ---
    private val _eventInstruction = MutableLiveData(false)
    val eventInstruction: LiveData<Boolean>
        get() = _eventInstruction
    // --------

    //Shoes List ---
    private var _shoesList = MutableLiveData<MutableList<Shoe>>(mutableListOf())
    val shoesList: LiveData<MutableList<Shoe>>
        get() = _shoesList

    // Press Add + in Shoes List Screen
    private val _eventAddShoeListPress = MutableLiveData(false)
    val eventAddShoeListPress: LiveData<Boolean>
        get() = _eventAddShoeListPress
    // --------

    //Shoe Detail ---
    // Press Save Button
    private val _eventSaveShoeDetailPress = MutableLiveData(false)
    val eventSaveShoeDetailPress: LiveData<Boolean>
        get() = _eventSaveShoeDetailPress

    // Press Cancel Button
    private val _eventCancelShoeDetailPress = MutableLiveData(false)
    val eventCancelShoeDetailPress: LiveData<Boolean>
        get() = _eventCancelShoeDetailPress

    // Press shoe picture
    private val _eventPictureShoeDetailPress = MutableLiveData<String>()
    val eventPictureShoeDetailPress: LiveData<String>
        get() = _eventPictureShoeDetailPress

    // Press Size Shoe
    private val _eventSizeViewShoeDetailPress = MutableLiveData<View>()
    val eventSizeViewShoeDetailPress: LiveData<View>
        get() = _eventSizeViewShoeDetailPress

    // Missing name shoe
    private val _eventFailNameShoeDetail = MutableLiveData(false)
    val eventFailNameShoeDetail: LiveData<Boolean>
        get() = _eventFailNameShoeDetail

    // Missing size shoe
    private val _eventFailSizeShoeDetail = MutableLiveData(false)
    val eventFailSizeShoeDetail: LiveData<Boolean>
        get() = _eventFailSizeShoeDetail

    // Missing name company
    private val _eventFailNameCompany = MutableLiveData(false)
    val eventFailNameCompany: LiveData<Boolean>
        get() = _eventFailNameCompany

    // --------

    // Action when press button in login screen
    fun gotoWelcomeScreen() {
        _eventLogin.value = true
    }

    // After login
    fun gotoWelcomeComplete() {
        _eventLogin.value = false
    }

    // Instruction Screen
    fun gotoInstructionScreen() {
        _eventWelcome.value = true
    }

    // After login
    fun gotoInstructionComplete() {
        _eventWelcome.value = false
    }

    // Shoe List Screen
    fun goToShoeListScreen() {
        _eventInstruction.value = true
    }

    // After login
    fun goToShoeListComplete() {
        _eventInstruction.value = false
    }

    //Shoes List Screen
    fun goToShoeDetailStart() {
        _eventAddShoeListPress.value = true
    }

    fun goToShoeDetailStartComplete() {
        _eventAddShoeListPress.value = false
    }

    // Shoe detail Screen
    // Check name, company, description is filled in
    fun saveShoeDetail() {
        when {
            shoe.company.trim().isEmpty() -> {
                _eventFailNameShoeDetail.value = true
            }
            shoe.name.trim().isEmpty() -> {
                _eventFailNameCompany.value = true
            }
            shoe.size <= 0.9 -> {
                _eventFailSizeShoeDetail.value = true
            }
            else -> {
                saveNewShoe()
                _eventSaveShoeDetailPress.value = true
            }
        }
    }

    fun shoeDetailComplete() {
        _eventSaveShoeDetailPress.value = false
    }

    fun saveFailNameShoeComplete() {
        _eventFailNameShoeDetail.value = false
    }

    fun saveFailNameCompanyComplete() {
        _eventFailNameCompany.value = false
    }

    fun saveFailSizeShoeComplete() {
        _eventFailSizeShoeDetail.value = false
    }

    // Cancel Button
    fun cancelShoeDetailPress() {
        _eventCancelShoeDetailPress.value = true
    }

    fun cancelShoeDetailComplete() {
        _eventCancelShoeDetailPress.value = false
    }

    // Chang shoe picture when press view shoe
    fun changeShoePicture() {
        shoe.modelShoe++
        if (shoe.modelShoe >= shoe.modelsAvailable.size) {
            shoe.modelShoe = 0
        }
        _eventPictureShoeDetailPress.value = shoe.modelsAvailable[shoe.modelShoe]
    }

    private fun saveNewShoe() {
        val list = _shoesList.value
        list?.let {
            it.add(shoe)
            _shoesList.setValue(list)
        }
    }

    fun setSize(view: View, size: Int) {
        _eventSizeViewShoeDetailPress.value = view
        shoe.size = size
    }

    fun clearShoeList() {
        shoe = Shoe(
            "",
            0,
            "",
            "",
            0
        )
        _eventPictureShoeDetailPress.value = shoe.modelsAvailable[shoe.modelShoe]
    }

}