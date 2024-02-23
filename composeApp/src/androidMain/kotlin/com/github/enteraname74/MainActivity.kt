package com.github.enteraname74

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.core.content.ContextCompat
import com.github.enteraname74.model.PlaybackControllerImpl
import com.github.enteraname74.screens.MainScreen
import com.github.enteraname74.screens.PlayerSwipeableScreen
import com.github.enteraname74.theme.ColorThemeManager
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.PlayerScreenSheetStates
import com.github.enteraname74.ui.theme.MusikTheme
import com.github.enteraname74.viewmodel.MainScreenViewModelImpl
import com.github.enteraname74.viewmodel.PlayerScreenViewModelImpl
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    private val colorThemeManager: ColorThemeManager by inject<ColorThemeManager>()
    private val playbackController: PlaybackControllerImpl by inject<PlaybackControllerImpl>()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MusikColorTheme.colorScheme = colorThemeManager.getColorTheme()

            var isPostNotificationGranted by rememberSaveable {
                mutableStateOf(false)
            }

            isPostNotificationGranted = checkIfPostNotificationGranted()

            val postNotificationLauncher = permissionLauncher { isGranted ->
                isPostNotificationGranted = isGranted
            }

            if (!isPostNotificationGranted) {
                SideEffect {
                    checkAndAskMissingPermissions(
                        isPostNotificationGranted = isPostNotificationGranted,
                        postNotificationLauncher = postNotificationLauncher
                    )
                }
            }

            // Example of injection of a ViewModel with koin.
            val mainScreenViewModel = koinViewModel<MainScreenViewModelImpl>()
            val playerScreenViewModel = koinViewModel<PlayerScreenViewModelImpl>()
            playbackController.playerViewModel = playerScreenViewModel

            MusikTheme {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MusikColorTheme.colorScheme.primary)
                ) {
                    val constraintsScope = this
                    val maxHeight = with(LocalDensity.current) {
                        constraintsScope.maxHeight.toPx()
                    }

                    val playerScreenSwipeableState = rememberSwipeableState(
                        initialValue = PlayerScreenSheetStates.COLLAPSED,
                        animationSpec = tween(Constants.AnimationDuration.normal)
                    )

                    MainScreen(
                        viewModel = mainScreenViewModel,
                        playbackController = playbackController,
                        playerScreenSwipeableState = playerScreenSwipeableState
                    )

                    PlayerSwipeableScreen(
                        maxHeight = maxHeight,
                        swipeableState = playerScreenSwipeableState,
                        playerScreenViewModel = playerScreenViewModel,
                        playbackController = playbackController
                    )
                }
            }
        }
    }

    /**
     * Check the state of the post notification permission.
     * If the device is below Android 13, the post notification is not necessary.
     */
    @Composable
    private fun checkIfPostNotificationGranted(): Boolean {
        val context = LocalContext.current
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    /**
     * Build a permission launcher.
     */
    @Composable
    private fun permissionLauncher(
        onResult: (Boolean) -> Unit
    ): ManagedActivityResultLauncher<String, Boolean> {
        return rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            onResult(isGranted)
        }
    }
    /**
     * Check and ask for missing permissions.
     */
    private fun checkAndAskMissingPermissions(
        isPostNotificationGranted: Boolean,
        postNotificationLauncher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        if (!isPostNotificationGranted && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)) {
            postNotificationLauncher.launch(POST_NOTIFICATIONS)
        }
    }
}