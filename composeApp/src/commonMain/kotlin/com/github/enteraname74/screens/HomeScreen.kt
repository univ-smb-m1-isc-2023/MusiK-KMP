package com.github.enteraname74.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.github.enteraname74.Constants
import com.github.enteraname74.composable.search.SearchMusics
import com.github.enteraname74.composable.search.SearchView
import com.github.enteraname74.di.injectElement
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.event.MainScreenEvent
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.model.PlaybackControllerImpl
import com.github.enteraname74.strings.appStrings
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.FetchingState
import com.github.enteraname74.type.SearchScreenSheetStates
import com.github.enteraname74.viewmodel.HomeScreenModel
import com.github.enteraname74.viewmodel.PlayerScreenModel
import kotlinx.coroutines.launch

class HomeScreen : Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()

        val screenModel = getScreenModel<HomeScreenModel>()

        val playerScreenModel = getScreenModel<PlayerScreenModel>()
        val playbackController = injectElement<PlaybackController>()

        playbackController.playerViewModel = playerScreenModel

        val state by screenModel.state.collectAsState()

        val searchScreenSwipeableState = rememberSwipeableState(
            initialValue = SearchScreenSheetStates.COLLAPSED,
            animationSpec = tween(Constants.AnimationDuration.normal)
        )

        LaunchedEffect(key1 = null) {
            screenModel.onEvent(MainScreenEvent.FetchMusics)
            screenModel.onEvent(MainScreenEvent.FetchPlaylists)
            screenModel.onEvent(MainScreenEvent.FetchAlbums)
            screenModel.onEvent(MainScreenEvent.FetchArtists)
        }

        TabNavigator(MusicTab) {
            Scaffold(
                containerColor = MusikColorTheme.colorScheme.primary,
                content = { padding ->
                    BoxWithConstraints(Modifier.padding(padding)) {
                        val constraintsScope = this
                        val maxHeight = with(LocalDensity.current) {
                            constraintsScope.maxHeight.toPx()
                        }

                        Column {
                            MainMenuHeaderComposable(
                                settingsAction = {},
                                searchAction = {
                                    coroutineScope.launch {
                                        searchScreenSwipeableState.animateTo(
                                            targetValue = SearchScreenSheetStates.EXPANDED,
                                            anim = tween(Constants.AnimationDuration.normal)
                                        )
                                    }
                                }
                            )

                            CurrentTab()
                        }

                        SearchView(
                            maxHeight = maxHeight,
                            searchScreenSwipeableState = searchScreenSwipeableState,
                            playerScreenSwipeableState = playerScreenModel.playerScreenSwipeableState,
                            placeholder = appStrings.allMusicsPlaceholder
                        ) { searchText, focusManager ->
                            SearchMusics(
                                playerScreenSwipeableState = playerScreenModel.playerScreenSwipeableState,
                                searchText = searchText,
                                musics = if (state.allMusicsState is FetchingState.Success<List<Music>>) {
                                    (state.allMusicsState as FetchingState.Success<List<Music>>).data
                                } else emptyList(),
                                focusManager = focusManager
                            )
                        }

                        PlayerSwipeableScreen(
                            maxHeight = maxHeight,
                            playerScreenModel = playerScreenModel
                        )
                    }
                },
                bottomBar = {
                    BottomNavigation(
                        contentColor = MusikColorTheme.colorScheme.secondary
                    ) {
                        TabNavigationItem(MusicTab)
                        TabNavigationItem(PlaylistsTab)
                        TabNavigationItem(AlbumsTab)
                        TabNavigationItem(ArtistsTab)
                    }
                }
            )
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    val icon: Painter =
        (tab.options.icon ?: Icon(Icons.Rounded.Error, contentDescription = "Error")) as Painter

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = icon, contentDescription = icon.toString()) }
    )
}

/**
 * Header bar for the main screen.
 */
@Composable
private fun MainMenuHeaderComposable(
    settingsAction: () -> Unit,
    searchAction: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Constants.Spacing.medium,
                end = Constants.Spacing.medium,
                top = Constants.Spacing.small,
                bottom = Constants.Spacing.small
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(Constants.ImageSize.medium)
                .clickable {
                    settingsAction()
                },
            imageVector = Icons.Rounded.Settings,
            contentDescription = "",
            tint = MusikColorTheme.colorScheme.onPrimary
        )
        Text(
            text = appStrings.appName,
            color = MusikColorTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Icon(
            modifier = Modifier
                .size(Constants.ImageSize.medium)
                .clickable {
                    searchAction()
                },
            imageVector = Icons.Rounded.Search,
            contentDescription = "",
            tint = MusikColorTheme.colorScheme.onPrimary
        )
    }
}
