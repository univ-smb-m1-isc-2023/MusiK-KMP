package com.github.enteraname74.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.theme.MusikColorTheme
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


@Composable
fun PlaylistRow(
    title: String,
    musics: List<Music>,
    onClick: () -> Unit
) {
    val myId = "inlineContent"
    val musicCountText = buildAnnotatedString {
        append(musics.size.toString())

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
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.large),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (musics.isNotEmpty() && musics[0].albumArtworkUrl.isNotEmpty()) {
            KamelImage(
                modifier = Modifier.size(75.dp),
                resource = asyncPainterResource(data = musics[0].albumArtworkUrl),
                contentDescription = null
            )
        } else {
            AppImage(bitmap = null, size = 75.dp)
        }

        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
            Text(
                title,
                color = MusikColorTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )

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

@Composable
fun PlaylistRow(
    title: String,
    total: Int,
    artworkUrl: String,
    onClick: () -> Unit
) {
    val myId = "inlineContent"
    val musicCountText = buildAnnotatedString {
        append(total.toString())

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
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.large),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (artworkUrl.isNotBlank()) {
            KamelImage(
                modifier = Modifier.size(75.dp),
                resource = asyncPainterResource(data = artworkUrl),
                contentDescription = null
            )
        } else {
            AppImage(bitmap = null, size = 75.dp)
        }

        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
            Text(
                title,
                color = MusikColorTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )

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
