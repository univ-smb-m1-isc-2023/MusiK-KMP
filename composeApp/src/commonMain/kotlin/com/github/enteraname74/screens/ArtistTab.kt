package com.github.enteraname74.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.github.enteraname74.composable.PlaylistView
import com.github.enteraname74.domain.model.Artist
import com.github.enteraname74.viewmodel.PlayerScreenModel

class ArtistTab(
    private val artist: Artist
) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Album Tab"

            return remember {
                TabOptions(
                    index = 4u,
                    title = title,
                    icon = null
                )
            }
        }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val playerScreenModel = getScreenModel<PlayerScreenModel>()
        val navigator = LocalTabNavigator.current

        PlaylistView(
            title = artist.name,
            musics = artist.musics,
            playerSwipeableState = playerScreenModel.playerScreenSwipeableState,
            onBack = { navigator.current = ArtistsTab }
        )
    }
}