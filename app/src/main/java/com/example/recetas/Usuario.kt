package com.example.recetas

data class Usuario(
        val Nombre: String = "",
        val Contraseña: String ="",
        val Estatus: String = "",
        val Mail: String = "",
        val Registrado_Por: String? = ""
)
