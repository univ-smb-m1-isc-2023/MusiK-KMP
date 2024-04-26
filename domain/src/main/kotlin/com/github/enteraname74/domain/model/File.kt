package com.github.enteraname74.domain.model

import java.io.InputStream

data class File(
    val name: String,
    val length: Long,
    val inputStream: InputStream,
    val contentType: String,
)
