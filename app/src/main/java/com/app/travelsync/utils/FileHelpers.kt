package com.app.travelsync.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID


private fun imageDir(context: Context): File =
    File(context.filesDir, "images").apply { mkdirs() }


fun saveBitmapInternal(
    context: Context,
    bitmap: Bitmap,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 90
): Uri {
    val file = File(imageDir(context), "${UUID.randomUUID()}.jpg")
    FileOutputStream(file).use { bitmap.compress(format, quality, it) }
    return file.toUri()
}


fun copyUriInternal(
    context: Context,
    source: Uri
): Uri {
    val file = File(imageDir(context), "${UUID.randomUUID()}.jpg")
    context.contentResolver.openInputStream(source).use { input: InputStream? ->
        FileOutputStream(file).use { output ->
            input?.copyTo(output)
        }
    }
    return file.toUri()
}