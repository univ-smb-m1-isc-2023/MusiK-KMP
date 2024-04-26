package com.github.enteraname74.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.darkrockstudios.libraries.mpfilepicker.MultipleFilePicker
import com.github.enteraname74.domain.model.File
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.PlayerScreenSheetStates

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UploadFabComposable(
    modifier: Modifier = Modifier,
    playerScreenSwipeableState: SwipeableState<PlayerScreenSheetStates>,
    uploadFile: (File) -> Unit
) {
    val context = LocalContext.current

    var showFilePicker by remember { mutableStateOf(false) }

    val fileType = listOf("m4a", "mp4", "mp3", "aac", "wav", "ogg")
    MultipleFilePicker(show = showFilePicker, fileExtensions = fileType) { selection ->
        showFilePicker = false

        selection?.let { files ->
            files.forEach { file ->
                try {
                    val f = getFile(
                        context,
                        file.platformFile.toString(),
                        file.path.substringAfterLast('/')
                    )

                    uploadFile(f)
                } catch (_: Exception) {
                }
            }
        }
    }
    println("THERE")
    FloatingActionButton(
        onClick = { showFilePicker = true },
        containerColor = MusikColorTheme.colorScheme.onSecondary,
        modifier = modifier.padding(bottom = if (playerScreenSwipeableState.currentValue != PlayerScreenSheetStates.COLLAPSED) 65.dp else 0.dp)
    ) {
        Icon(Icons.Rounded.Add, "Upload button.")
    }
}
