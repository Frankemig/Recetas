package com.example.recetas

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.cocina_add.*
import java.io.ByteArrayOutputStream
import java.util.*

class AddCocteleria : AppCompatActivity() {

    private lateinit var myRef: DatabaseReference
    private lateinit var downloadUri: Uri

    private val database = Firebase.database
    private lateinit var storageReference: StorageReference
    var thumb_bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cocina_add)

        myRef = database.getReference("Cocteleria")

        val name=et_addNameCocina.text
        val description=et_preparacionAddCocina.text

        storageReference = FirebaseStorage.getInstance().reference.child("imagenes")

        urlImage.setOnClickListener {
            CropImage.startPickImageActivity(this)
        }

        saveButtonAddCocina.setOnClickListener { v ->
            val recetas = Recetas(name.toString(), "",description.toString(), downloadUri.toString())
            myRef.child(myRef.push().key.toString()).setValue(recetas)
            finish()
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
                    storageReference.child("Cocteleria").child(resultUri.lastPathSegment!!)
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

                Picasso.get().load(resultUri).into(imageAddCocina)

            }
        }
    }
}