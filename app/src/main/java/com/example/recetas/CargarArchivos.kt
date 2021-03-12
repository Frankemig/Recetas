package com.example.recetas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.example.recetas.databinding.CargaArchivoBinding

class CargarArchivos:Fragment() {

    private lateinit var binding: CargaArchivoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CargaArchivoBinding.inflate(inflater)





        return binding.root

    }
}