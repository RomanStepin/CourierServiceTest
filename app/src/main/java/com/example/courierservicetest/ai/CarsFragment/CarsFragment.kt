package com.example.courierservicetest.ai.CarsFragment

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courierservicetest.App
import com.example.courierservicetest.R
import com.example.courierservicetest.ai.CarsFragment.adapter.CarItemClicks
import com.example.courierservicetest.ai.CarsFragment.adapter.CarsAdapter
import com.example.courierservicetest.ai.CarsFragment.adapter.CarsDiffUtilCallback
import com.example.courierservicetest.ai.CarsFragment.dialog.FullImageDialogFragment
import com.example.courierservicetest.ai.CarsViewModelFactory
import com.example.courierservicetest.databinding.FragmentCarsBinding
import com.example.courierservicetest.deleteFileWithPath
import com.example.courierservicetest.models.Car
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.widget.textChanges

class CarsFragment : Fragment() {

    private lateinit var binding: FragmentCarsBinding
    private lateinit var viewModel: CarsViewModel
    private val fullImageDialogFragment = FullImageDialogFragment()
    private val viewModelFactory: CarsViewModelFactory =
        App.carsViewModelFactoryComponent.getCarsViewModelFactory()

    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController

    private val adapter =
        CarsAdapter(object : CarItemClicks {
            override fun onImageClickListener(imageUri: String) {
                fullImageDialogFragment.uri = imageUri
                fullImageDialogFragment.show(requireActivity().supportFragmentManager, "FULL_IMAGE")
            }

            override fun onEditClickListener(car: Car) {
                val bundle = Bundle().apply { putParcelable("CAR", car) }
                navController.navigate(R.id.action_carsFragment_to_editCarsFragment, bundle)
            }

            override fun onDeleteClickListener(car: Car) {
                lifecycleScope.launch {
                    val carFilePath = car.imageUri
                    viewModel.deleteCar(car)
                    deleteFileWithPath(carFilePath)
                    updateCarsRecyclerView()
                }
            }
        })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[CarsViewModel::class.java]

        initRecyclerView()
        initButtonsListener()
        initFragmentResultListener()
        initCarsSearch()

        updateCarsRecyclerView()
    }

    private fun initButtonsListener() {
        binding.addCarButton.setOnClickListener {
            navController.navigate(R.id.action_carsFragment_to_editCarsFragment, Bundle.EMPTY)
        }
    }

    private fun initFragmentResultListener() {
        setFragmentResultListener("EDIT_CAR") { requestKey, resultBundle ->
            if (requestKey == "EDIT_CAR") {
                updateCarsRecyclerView()
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun initCarsSearch() {
        binding.carsSearch.textChanges()
            .debounce(250)
            .onEach {
                updateCarsRecyclerView()
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

    private fun initRecyclerView() {
        binding.carsRecyclerView.adapter = this.adapter
        binding.carsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updateCarsRecyclerView() {
        lifecycleScope.launch {
            val newCarsList = viewModel.getCars(binding.carsSearch.text.toString())
            val callback = CarsDiffUtilCallback(adapter.carItems, newCarsList)
            val result = DiffUtil.calculateDiff(callback)
            val recyclerViewState: Parcelable? =
                binding.carsRecyclerView.layoutManager?.onSaveInstanceState()
            adapter.carItems = newCarsList
            result.dispatchUpdatesTo(adapter)
            binding.carsRecyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
            binding.carsListPlaseholder.visibility =
                if (newCarsList.isEmpty()) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }
        }
    }
}