package com.example.recetas.cocina

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recetas.databinding.PdfViewBinding

class PDFViewCocina : Fragment() {
    lateinit var binding: PdfViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PdfViewBinding.inflate(inflater)



        return binding.root
    }
}