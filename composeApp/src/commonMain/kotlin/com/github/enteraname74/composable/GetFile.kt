package com.github.enteraname74.composable

import android.content.Context
import com.github.enteraname74.domain.model.File

expect fun getFile(context: Context, path: String): File