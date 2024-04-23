package com.github.enteraname74.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.enteraname74.Constants
import com.github.enteraname74.theme.MusikColorTheme

@Composable
fun Playlist(name: String, musicCount: Int) {
    val myId = "inlineContent"
    val musicCountText = buildAnnotatedString {
        append(musicCount.toString())

        appendInlineContent(myId, "[icon]")
    }

    val inlineContent = mapOf(
        Pair(
            myId,
            InlineTextContent(
                Placeholder(
                    width = 12.sp,
                    height = 12.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Icon(Icons.Rounded.MusicNote, "", tint = MusikColorTheme.colorScheme.onPrimary)
            }
        )
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.large),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppImage(bitmap = null, size = 75.dp)

        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
            Text(name, color = MusikColorTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)

            Row {
                Text(
                    text = musicCountText,
                    inlineContent = inlineContent,
                    color = MusikColorTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
