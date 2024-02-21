package com.github.enteraname74.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.enteraname74.Constants
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.theme.MusikColorTheme

/**
 * Represent a row with information about of a Music.
 */
@Composable
fun MusicRow(
    music: Music,
    onClick: (Music) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(music) }
            .padding(Constants.Spacing.medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .height(55.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = music.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MusikColorTheme.colorScheme.onPrimary
                )
                Text(
                    text = "${music.artist} | ${music.album}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MusikColorTheme.colorScheme.onPrimary
                )

            }
        }
    }
}