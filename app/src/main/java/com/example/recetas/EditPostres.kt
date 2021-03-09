package com.example.recetas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.cocina_edit.*

class EditPostres : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cocina_edit)

        val key = intent.getStringExtra("key")
        val database = Firebase.database
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val myRef = database.getReference("Postres").child(key)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val recetas:Recetas? = dataSnapshot.getValue(Recetas::class.java)
                if (recetas != null) {
                    et_nameEdit.text = Editable.Factory.getInstance().newEditable(recetas.name)
                    et_preparacionEdit.text = Editable.Factory.getInstance().newEditable(recetas.description)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        saveButtonAddCocina.setOnClickListener { v ->

            val name : String = et_nameEdit.text.toString()
            val description: String = et_preparacionEdit.text.toString()

            myRef.child("name").setValue(name)
            //myRef.child("date").setValue(date)
            myRef.child("description").setValue(description)
            //myRef.child("url").setValue(url)

            finish()
        }
    }

}