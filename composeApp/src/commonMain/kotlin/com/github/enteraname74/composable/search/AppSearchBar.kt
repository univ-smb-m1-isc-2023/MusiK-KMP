package com.github.enteraname74.composable.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.github.enteraname74.theme.MusikColorTheme

@Composable
fun AppSearchBar(
    searchText: String,
    placeholder: String,
    updateTextMethod: (String) -> Unit,
    focusManager: FocusManager
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = searchText,
        onValueChange = updateTextMethod,
        placeholder = {
            Text(
                text = placeholder,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingIcon = {
            if (searchText.isNotBlank()) {
                Icon(
                    imageVector = Icons.Rounded.Cancel,
                    contentDescription = "",
                    tint = MusikColorTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .clickable {
                            updateTextMethod("")
                        }
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "",
                    tint = MusikColorTheme.colorScheme.onPrimary,
                )
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(percent = 50),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MusikColorTheme.colorScheme.secondary,
            focusedTextColor = MusikColorTheme.colorScheme.onSecondary,
            cursorColor = MusikColorTheme.colorScheme.onSecondary,
            focusedIndicatorColor = Color.Transparent,
            focusedLabelColor = MusikColorTheme.colorScheme.onSecondary,
            unfocusedTextColor = MusikColorTheme.colorScheme.onSecondary,
            focusedContainerColor = MusikColorTheme.colorScheme.secondary,
            selectionColors = TextSelectionColors(
                handleColor = MusikColorTheme.colorScheme.onSecondary,
                backgroundColor = MusikColorTheme.colorScheme.secondary
            ),
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedLabelColor = MusikColorTheme.colorScheme.onSecondary,
            focusedPlaceholderColor = MusikColorTheme.colorScheme.onSecondary,
            unfocusedPlaceholderColor = MusikColorTheme.colorScheme.onSecondary
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
}