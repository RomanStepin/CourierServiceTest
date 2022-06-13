package com.example.courierservicetest.ai.EditCarsFragment

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.courierservicetest.App
import com.example.courierservicetest.R
import com.example.courierservicetest.data.GetContentForSavedImageUri
import com.example.courierservicetest.databinding.FragmentEditCarBinding
import com.example.courierservicetest.models.Car
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

class EditCarFragment : Fragment() {
    private var initialCar: Car? = null
    private lateinit var binding: FragmentEditCarBinding
    private lateinit var viewModel: EditCarViewModel
    private val viewModelFactory = App.carsViewModelFactoryComponent.getCarsViewModelFactory()

    private val selectImageResultLaunchers =
        registerForActivityResult(GetContentForSavedImageUri()) { selectedImageUri: Uri? ->
            if (selectedImageUri != null) {
                lifecycleScope.launch {
                    viewModel.changeSelectedImageUri(selectedImageUri)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[EditCarViewModel::class.java]
        initialCar = arguments?.getParcelable("CAR")
        initialCar?.let {
            binding.brandEditText.setText(it.brand)
            binding.modelEditText.setText(it.model)
            val carImageFile = File(it.imageUri)
            val carImageFileUri = Uri.fromFile(carImageFile)
            displayImage(carImageFileUri)
        }

        initButtonsListener()
        initObservables()
    }

    private fun initButtonsListener() {
        binding.selectImageButton.setOnClickListener {
            selectImageResultLaunchers.launch("image/*")
        }

        binding.saveButton.setOnClickListener {
            lifecycleScope.launch {
                saveCar()
                popBackStack()
            }
        }
    }

    private fun initObservables() {
        lifecycleScope.launch {
            viewModel.selectedImageUriFlow.collectLatest { uri ->
                displayImage(uri)
            }
        }
    }

    private fun displayImage(uri: Uri) {
        Glide
            .with(requireContext())
            .load(uri).centerCrop()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.ic_add_car_placeholder)
            .into(binding.selectImageButton)
    }

    private suspend fun saveCar() {
        val carModel = binding.modelEditText.text.toString()
        val carBrand = binding.brandEditText.text.toString()

        if (initialCar != null) {
            viewModel.saveEditableCar(initialCar!!, carModel, carBrand)
        } else {
            viewModel.saveNewCar(carModel, carBrand)
        }
    }

    private fun popBackStack() {
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setFragmentResult("EDIT_CAR", Bundle.EMPTY)
        navController.navigate(R.id.action_editCarsFragment_to_carsFragment)
    }
}