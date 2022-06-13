package com.example.courierservicetest.ai.CarsFragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.courierservicetest.R
import com.example.courierservicetest.databinding.ImageFullScreenLayoutBinding

class FullImageDialogFragment() : DialogFragment() {
    lateinit var binding: ImageFullScreenLayoutBinding
    var uri: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ImageFullScreenLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide
            .with(requireContext())
            .load(uri)
            .into(binding.fullScreenPhotoView)
    }

}