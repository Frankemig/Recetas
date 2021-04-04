package com.example.recetas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.recetas.cocina.RecetasCocinaList
import com.example.recetas.cocteleria.RecetasCocteleriaList
import com.example.recetas.databinding.ActivityMainBinding
import com.example.recetas.postres.RecetasPostresList

class MainActivity : AppCompatActivity() {
companion object{
    lateinit var binding: ActivityMainBinding

}

    val cocina = RecetasCocinaList()
    val cocteleria = RecetasCocteleriaList()
    val postres = RecetasPostresList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.navigationBottom.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.menu_cocina) {
                showSelectedFragment(cocina)
            } else if (it.itemId == R.id.menu_postres) {
                showSelectedFragment(postres)
            } else if (it.itemId == R.id.menu_cocteleria) {
                showSelectedFragment(cocteleria)
            }
            return@setOnNavigationItemSelectedListener true
        }

        setContentView(binding.root)
    }

    fun showSelectedFragment(fragment: Fragment) {
        if (fragment != null)

            supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
    }

}