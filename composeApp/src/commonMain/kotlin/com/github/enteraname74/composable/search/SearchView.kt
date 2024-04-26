package com.github.enteraname74.composable.search

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.IntOffset
import com.github.enteraname74.Constants
import com.github.enteraname74.composable.MusikBackHandler
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.PlayerScreenSheetStates
import com.github.enteraname74.type.SearchScreenSheetStates
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchView(
    maxHeight: Float,
    searchScreenSwipeableState: SwipeableState<SearchScreenSheetStates>,
    playerScreenSwipeableState: SwipeableState<PlayerScreenSheetStates>,
    placeholder: String,
    searchResult: @Composable (String, FocusManager) -> Unit

) {
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    MusikBackHandler(
        searchScreenSwipeableState.currentValue == SearchScreenSheetStates.EXPANDED
                && playerScreenSwipeableState.currentValue != PlayerScreenSheetStates.EXPANDED
    ) {
        coroutineScope.launch {
            searchScreenSwipeableState.animateTo(
                SearchScreenSheetStates.COLLAPSED,
                tween(Constants.AnimationDuration.normal)
            )
        }
    }

    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    if (searchScreenSwipeableState.currentValue == SearchScreenSheetStates.COLLAPSED) {
        searchText = ""
    }

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    x = 0,
                    y = max(searchScreenSwipeableState.offset.value.roundToInt(),0)
                )
            }
            .swipeable(
                state = searchScreenSwipeableState,
                orientation = Orientation.Vertical,
                anchors = mapOf(
                    maxHeight to SearchScreenSheetStates.COLLAPSED,
                    0f to SearchScreenSheetStates.EXPANDED
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MusikColorTheme.colorScheme.primary)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .padding(Constants.Spacing.medium)
        ) {

            AppSearchBar(
                searchText = searchText,
                placeholder = placeholder,
                updateTextMethod = {
                    searchText = it
                },
                focusManager = focusManager,
            )

            if (searchText.isNotBlank()) {
                searchResult(searchText, focusManager)
            }
        }
    }
}