package com.example.personalnotebook.extensions

import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.example.personalnotebook.R

fun EditText.changeVisibleText(): Boolean {
    if(this.inputType == InputType.TYPE_CLASS_TEXT) {
        inputType = 129
        return false
    } else {
        inputType = InputType.TYPE_CLASS_TEXT
        return true
    }
}

fun ImageView.changeIconColorForVisibleText(context: Context, result: Boolean) {
    if(result) {
        this.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_visible_text_on_edit_text_focused))
    } else {
        this.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_visible_text_on_edit_text))
    }
}