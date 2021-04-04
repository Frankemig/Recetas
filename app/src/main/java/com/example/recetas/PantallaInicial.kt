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
import kotlinx.android.synthetic.main.pantalla_inicial.*

class PantallaInicial:Fragment() {

lateinit var binding: PantallaInicialBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PantallaInicialBinding.inflate(inflater)
        MainActivity.binding.navigationBottom.isVisible = false

        binding.logoFucap.animate().apply {
            duration=3000
            alpha(.5f)
            scaleXBy(.5f)
            scaleYBy(.5f)
            rotationYBy(360f)
            translationYBy(200f)
        }.withEndAction {
            binding.logoFucap.animate().apply {
                duration=3000
                alpha(1f)
                scaleXBy(-.5f)
                scaleYBy(-.5f)
                rotationYBy(360f)
                translationYBy(-200f)
            }
        }.start()

binding.btnIngreso.setOnClickListener {
    findNavController().navigate(R.id.action_pantallaInicial_to_recetasCocinaList)
    MainActivity.binding.navigationBottom.isVisible = true
}
        binding.noRegistrado.setOnClickListener {
            findNavController().navigate(R.id.action_pantallaInicial_to_registrarse)
        }

        return binding.root
    }

}