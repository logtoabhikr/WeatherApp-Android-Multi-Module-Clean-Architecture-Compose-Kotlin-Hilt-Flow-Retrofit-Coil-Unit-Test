package com.lbg.domain.utils

import android.content.Context
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun String.parseDateTo(input: String, output: String): String {
    val inputFormat = SimpleDateFormat(input, Locale.getDefault())
    val outputFormat = SimpleDateFormat(output, Locale.getDefault())
    val date: Date? = try {
        inputFormat.parse(this)
    } catch (e: ParseException) {
        return this
    }
    return outputFormat.format(date!!)
}