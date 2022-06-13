package com.example.courierservicetest.ai.CarsFragment.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper

import com.example.courierservicetest.R
import com.example.courierservicetest.databinding.CarItemLayoutBinding
import com.example.courierservicetest.models.Car
import java.io.File

class CarsAdapter(val carItemClicks: CarItemClicks) : RecyclerView.Adapter<CarViewHolder>() {

    var carItems: List<Car> = mutableListOf()
    private val viewBinderHelper: ViewBinderHelper = ViewBinderHelper().also {
        it.setOpenOnlyOne(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding =
            CarItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        viewBinderHelper.bind(holder.getSwipeLayout(), carItems[position].primaryKey.toString());
        holder.bind(carItems[position], carItemClicks)
    }

    override fun getItemCount(): Int {
        return carItems.size
    }
}


class CarViewHolder(private val binding: CarItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun getSwipeLayout(): SwipeRevealLayout {
        return binding.swipeRefreshLayout
    }

    fun bind(car: Car, carItemClicks: CarItemClicks) {
        binding.carItemBrand.text = car.brand
        binding.carItemModel.text = car.model
        val carImageFile = File(car.imageUri)
        val carImageFileUri = Uri.fromFile(carImageFile)

        Glide
            .with(binding.root)
            .load(carImageFileUri)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .centerCrop()
            .placeholder(R.drawable.ic_car_placeholder)
            .into(binding.carItemImage)

        binding.carItemImage.setOnClickListener {
            if (car.imageUri.isNotEmpty()) {
                carItemClicks.onImageClickListener(car.imageUri)
            }
        }

        binding.itemCarEdit.setOnClickListener {
            carItemClicks.onEditClickListener(car)
        }

        binding.itemCarDelete.setOnClickListener {
            carItemClicks.onDeleteClickListener(car)
        }

    }
}