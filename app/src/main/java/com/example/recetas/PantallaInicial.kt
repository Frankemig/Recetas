package com.example.recetas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recetas.databinding.PantallaInicialBinding
import kotlinx.android.synthetic.main.cocina_add.*

class PantallaInicial:Fragment() {

lateinit var binding: PantallaInicialBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PantallaInicialBinding.inflate(inflater)

binding.btnIngreso.setOnClickListener {
    findNavController().navigate(R.id.action_pantallaInicial_to_recetasCocinaList)
}
        binding.noRegistrado.setOnClickListener {
            findNavController().navigate(R.id.action_pantallaInicial_to_registrarse)
        }

        return binding.root
    }
}