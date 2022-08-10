package com.example.personalnotebook.model

import java.io.Serializable

data class NoteEntity(
    var id: String? = "",
    var title: String? = ""
) : Serializable