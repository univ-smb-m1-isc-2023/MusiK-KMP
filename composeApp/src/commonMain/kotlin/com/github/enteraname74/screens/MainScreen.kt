package com.github.enteraname74.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import com.github.enteraname74.Constants
import com.github.enteraname74.composable.MusicRow
import com.github.enteraname74.composable.StateView
import com.github.enteraname74.composable.search.SearchMusics
import com.github.enteraname74.composable.search.SearchView
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.event.MainScreenEvent
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.strings.appStrings
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.FetchingState
import com.github.enteraname74.type.PlayerScreenSheetStates
import com.github.enteraname74.type.SearchScreenSheetStates
import com.github.enteraname74.viewmodel.MainScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    playbackController: PlaybackController,
    playerScreenSwipeableState: SwipeableState<PlayerScreenSheetStates>
) {
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.handler.state.collectAsState()

    val searchScreenSwipeableState = rememberSwipeableState(
        initialValue = SearchScreenSheetStates.COLLAPSED,
        animationSpec = tween(Constants.AnimationDuration.normal)
    )

    LaunchedEffect(key1 = null) {
        viewModel.handler.onEvent(MainScreenEvent.FetchMusics)
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MusikColorTheme.colorScheme.primary)
    ) {
        val constraintsScope = this
        val maxHeight = with(LocalDensity.current) {
            constraintsScope.maxHeight.toPx()
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
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
            when (state.allMusicsState) {
                is FetchingState.Error -> StateView(message = (state.allMusicsState as FetchingState.Error).message)
                is FetchingState.Loading -> StateView(message = (state.allMusicsState as FetchingState.Loading).message)
                is FetchingState.Success -> AllMusicsView(
                    musics = (state.allMusicsState as FetchingState.Success<List<Music>>).data,
                    onClick = {
                        coroutineScope.launch {
                            playbackController.setPlayerLists((state.allMusicsState as FetchingState.Success<List<Music>>).data)
                            playbackController.setAndPlayMusic(it)
                            playerScreenSwipeableState.animateTo(
                                targetValue = PlayerScreenSheetStates.EXPANDED,
                                anim = tween(Constants.AnimationDuration.normal)
                            )
                        }
                    }
                )
            }
        }

        SearchView(
            maxHeight = maxHeight,
            searchScreenSwipeableState = searchScreenSwipeableState,
            playerScreenSwipeableState = playerScreenSwipeableState,
            placeholder = appStrings.allMusicsPlaceholder
        ) { searchText, focusManager ->
            SearchMusics(
                playerScreenSwipeableState = playerScreenSwipeableState,
                searchText = searchText,
                musics = if (state.allMusicsState is FetchingState.Success<List<Music>>)
                    (state.allMusicsState as FetchingState.Success<List<Music>>).data
                else emptyList(),
                playbackController = playbackController,
                focusManager = focusManager
            )
        }
    }


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

@Composable
private fun AllMusicsView(
    musics: List<Music>,
    onClick: (Music) -> Unit
) {

    if (musics.isEmpty()) {
        StateView(
            message = appStrings.noMusicsFound
        )
    } else {
        LazyColumn {
            items(musics, key = {
                it.id
            }) { music ->
                MusicRow(
                    music = music,
                    onClick = {
                        onClick(it)
                    }
                )
            }
        }
    }
}