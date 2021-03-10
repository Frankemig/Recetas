package com.example.recetas.postres

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.recetas.R
import com.example.recetas.Recetas
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.receta_detail.*

class DetailPostres : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receta_detail)

        val key = intent.getStringExtra("key")
        val database = Firebase.database
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val myRef = database.getReference("Postres").child(key!!)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val recetas: Recetas? = dataSnapshot.getValue(Recetas::class.java)
                if (recetas != null) {
                    tv_name_receta_full.text = recetas.name.toString()
                    descriptionPreparacion.text = recetas.description.toString()
                    images(recetas.url.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })


    }

    private  fun images(url: String){
        Picasso.get().load(url).placeholder(R.drawable.ic_idea_comodin).centerCrop().resize(480,640)
            .into(posterImgeView)

        Picasso.get().load(url).placeholder(R.drawable.ic_idea_comodin).centerCrop().resize(480,640)
            .into(backgroundImageView)


    }
}