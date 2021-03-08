package com.example.recetas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.recetas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

/*        binding.navigationBottom.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.menu_cocina -> showSelectedFragment(cocina)
                R.id.menu_cocteleria -> showSelectedFragment(cocteleria)
                R.id.menu_postres -> showSelectedFragment(postres)
            }
        }*/

        setContentView(binding.root)
    }

    private fun showSelectedFragment(fragment: Fragment) {
        if (fragment != null)

            supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
    }

}