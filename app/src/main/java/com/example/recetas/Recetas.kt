package com.example.recetas
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Recetas(
    val name: String? = null,
    val date: String? = null,
    val description: String? = null,
    val url: String? = null,
    val SubidoPor:String?=null,
    val pdf:String?=null,
    val video:String?=null,
    val word:String?=null,
    @Exclude val key: String? = null) {
}