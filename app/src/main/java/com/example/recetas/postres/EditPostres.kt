package com.example.recetas.postres

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.example.recetas.R
import com.example.recetas.Recetas
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.cocina_edit.*
import java.io.ByteArrayOutputStream
import java.util.*

class EditPostres : AppCompatActivity() {

    private lateinit var storageReference: StorageReference
    var thumb_bitmap: Bitmap? = null
    private lateinit var downloadUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cocina_edit)

        val key = intent.getStringExtra("key")
        val database = Firebase.database
        storageReference = FirebaseStorage.getInstance().reference.child("imagenes")

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val myRef = database.getReference("Postres").child(key)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val recetas: Recetas? = dataSnapshot.getValue(Recetas::class.java)
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
            val url : String = downloadUri.toString()

            myRef.child("name").setValue(name)
            //myRef.child("date").setValue(date)
            myRef.child("description").setValue(description)
            myRef.child("url").setValue(url)

            finish()
        }
        btn_imagenEdit.setOnClickListener {
            CropImage.startPickImageActivity(this)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val imageUri: Uri = CropImage.getPickImageResultUri(this, data)

            CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setRequestedSize(480, 640)
                .setAspectRatio(1, 2).start(this)
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result: CropImage.ActivityResult = CropImage.getActivityResult(data)

            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.uri
                val byteArrayOutputStream = ByteArrayOutputStream()
                thumb_bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)
                val thum_byte = byteArrayOutputStream.toByteArray()

                val filePath: StorageReference =
                    storageReference.child("Postres").child(resultUri.lastPathSegment!!)
                val uploadTask: UploadTask = filePath.putBytes(thum_byte)
                val uriTask: Task<Uri> =
                    uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        if (!it.isSuccessful) {
                            throw Objects.requireNonNull(it.exception)!!
                        }
                        return@Continuation filePath.downloadUrl
                    }).addOnCompleteListener(OnCompleteListener {
                        downloadUri = it.result!!
                    })

                filePath.putFile(resultUri).addOnSuccessListener {
                    Toast.makeText(this, "Foto Subida Exitosamente...", Toast.LENGTH_LONG).show()
                }

                Picasso.get().load(resultUri).into(ImageEdit)

            }
        }
    }

}