package com.example.personalnotebook.model

import java.io.Serializable

data class Note(
    var id: String? = "",
    var title: String? = "",
    var description: String? = "",
    val date: String? = ""
) : Serializable
