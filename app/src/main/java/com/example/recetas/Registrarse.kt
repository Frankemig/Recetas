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
        binding.logoFucapRegistro.animate().apply {
            duration=3000
            alpha(.5f)
            scaleXBy(.5f)
            scaleYBy(.5f)
            rotationYBy(360f)
            translationYBy(200f)
        }.withEndAction {
            binding.logoFucapRegistro.animate().apply {
                duration=3000
                alpha(1f)
                scaleXBy(-.5f)
                scaleYBy(-.5f)
                rotationYBy(360f)
                translationYBy(-200f)
            }
        }


        return binding.root
    }
}