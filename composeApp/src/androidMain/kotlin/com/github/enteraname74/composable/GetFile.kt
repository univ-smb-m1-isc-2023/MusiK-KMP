package com.github.enteraname74.composable

import android.annotation.SuppressLint
import android.content.Context
import android.provider.OpenableColumns
import androidx.core.net.toUri
import com.github.enteraname74.domain.model.File
import java.io.FileNotFoundException

@SuppressLint("Recycle")
actual fun getFile(context: Context, path: String, name: String): File {
    println("path: $path")
    val uri = path.toUri()

    val assetDescriptor = context.contentResolver.openAssetFileDescriptor(uri, "r")
    val length = assetDescriptor?.length ?: throw FileNotFoundException()

    val inputStream =
        context.contentResolver.openInputStream(uri) ?: throw FileNotFoundException()

    val cursor = context.contentResolver.query(uri, null, null, null, null)

    var finalName = name

    cursor?.let { c ->
        val index = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        c.moveToFirst()
        finalName = c.getString(index)
        println("Got final name : $finalName")
        c.close()
    }

    val contentType = context.contentResolver.getType(path.toUri()) ?: throw FileNotFoundException()

    return File(
        name = finalName,
        length = length,
        inputStream = inputStream,
        contentType = contentType
    )
}



