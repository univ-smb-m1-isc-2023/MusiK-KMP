package com.github.enteraname74.composable

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.net.toUri
import com.github.enteraname74.domain.model.File
import java.io.FileNotFoundException

@SuppressLint("Recycle")
actual fun getFile(context: Context, path: String, name: String): File {
    println("path: $path")

    val assetDescriptor = context.contentResolver.openAssetFileDescriptor(path.toUri(), "r")
    val length = assetDescriptor?.length ?: throw FileNotFoundException()

    val inputStream =
        context.contentResolver.openInputStream(path.toUri()) ?: throw FileNotFoundException()

    val contentType = context.contentResolver.getType(path.toUri()) ?: throw FileNotFoundException()

    return File(
        name = name,
        length = length,
        inputStream = inputStream,
        contentType = contentType
    )
}



