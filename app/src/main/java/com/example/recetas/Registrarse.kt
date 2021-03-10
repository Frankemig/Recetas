package com.example.recetas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recetas.databinding.RegistrarseBinding

class Registrarse:Fragment() {
    lateinit var binding: RegistrarseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegistrarseBinding.inflate(inflater)



        return binding.root
    }
}