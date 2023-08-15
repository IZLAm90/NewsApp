package com.example.newsapp.helper

import android.app.Dialog
import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.newsapp.R

fun ImageView.loadImage(image: String) {
    Glide.with(this.context).load(image).into(this)
}

fun Context.showMessageDialog(string: String, errorTypes: ErrorTypes) {
    val dialog = Dialog(this)
    dialog.setContentView(R.layout.dialog_message)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    dialog.window?.setDimAmount(.2f)
    kotlin.runCatching { dialog.show() }.onFailure { }
    kotlin.runCatching {
        dialog.findViewById<TextView>(R.id.tv_message).set(string)
        dialog.findViewById<ImageView>(R.id.imageView)
            .setImageResource(
                when (errorTypes) {
                    ErrorTypes.NETWORK -> R.drawable.baseline_signal_wifi_connected_no_internet_4_black_24dp
                    ErrorTypes.BLOCKED -> R.drawable.baseline_signal_wifi_connected_no_internet_4_black_24dp
                    ErrorTypes.SERVER_ERROR -> R.drawable.baseline_signal_wifi_connected_no_internet_4_black_24dp
                    ErrorTypes.MESSAGE -> R.drawable.baseline_signal_wifi_connected_no_internet_4_black_24dp
                }
            )
    }
}
fun TextView.set(string: String?) {
    this.setText(string.toString().replace("null", ""))
}
fun EditText.set(string: String?) {
    this.setText(string?.replace("null", ""))
}
fun EditText.isEditTextValid(): Boolean {
    if (this.text.toString().trim().isEmpty()) {
        this.error = this.context.resources.getString(R.string.reqiured)
        return false
    }
    return true
}