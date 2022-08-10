package com.example.personalnotebook.mapper

import com.example.personalnotebook.model.Note
import com.example.personalnotebook.model.NoteEntity

fun Note.toEntity(id: String?): NoteEntity {
    return NoteEntity(
        id = id,
        title = title
    )
}

fun NoteEntity.toModel(): Note {
    return Note(
        id = id,
        title = title
    )
}