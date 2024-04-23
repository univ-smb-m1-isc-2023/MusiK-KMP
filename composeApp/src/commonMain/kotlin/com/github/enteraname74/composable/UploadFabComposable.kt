package com.github.enteraname74.composable

import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.darkrockstudios.libraries.mpfilepicker.MultipleFilePicker
import com.github.enteraname74.domain.model.File
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.PlayerScreenSheetStates
import kotlin.math.max
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UploadFabComposable(
    modifier: Modifier = Modifier,
    playerScreenSwipeableState: SwipeableState<PlayerScreenSheetStates>,
    uploadFile: (File) -> Unit
) {
    val context = LocalContext.current

    var showFilePicker by remember { mutableStateOf(false) }

    val fileType = listOf("mp3", "mp4", "m4a", "aac", "wav")
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

    FloatingActionButton(
        onClick = { showFilePicker = true },
        containerColor = MusikColorTheme.colorScheme.onSecondary,
        modifier = modifier.padding(
            bottom = 25.dp,
            end = 25.dp,
        ).offset {
            IntOffset(
                x = 0,
                y = max(playerScreenSwipeableState.offset.value.roundToInt() - 200, 0)
            )
        }
    ) {
        Icon(Icons.Rounded.Add, "Upload button.")
    }
}
