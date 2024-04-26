package com.github.enteraname74.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.github.enteraname74.Constants
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.strings.appStrings

@Composable
fun AllMusicView(
    musics: List<Music>,
    onClick: (Music) -> Unit,
    onLongClick: (Music) -> Unit
) {
    if (musics.isEmpty()) {
        StateView(
            message = appStrings.noMusicsFound
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(Constants.Spacing.large),
            verticalArrangement = Arrangement.spacedBy(Constants.Spacing.large)
        ) {
            items(musics, key = {
                it.id
            }) { m ->
                MusicRow(
                    music = m,
                    onClick = {
                        onClick(it)
                    },
                    onLongClick = {
                        onLongClick(it)
                    }
                )
            }
        }
    }
}
