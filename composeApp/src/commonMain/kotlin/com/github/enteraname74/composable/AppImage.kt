package com.github.enteraname74.composable


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.github.enteraname74.Drawables
import com.github.enteraname74.MusikContext
import com.github.enteraname74.theme.MusikColorTheme

@Composable
fun AppImage(
    bitmap : ImageBitmap?,
    size : Dp,
    modifier: Modifier = Modifier,
    roundedPercent : Int = 10,
    tint: Color = MusikColorTheme.colorScheme.onSecondary
) {
    val modifierBase = Modifier
        .size(size)
        .clip(RoundedCornerShape(percent = roundedPercent))
        .then(modifier)

    if (bitmap != null) {
        Image(
            modifier = modifierBase,
            bitmap = bitmap,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            modifier = modifierBase,
            painter = MusikContext.appPainterResource(Drawables.appIcon),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(tint)
        )
    }
}