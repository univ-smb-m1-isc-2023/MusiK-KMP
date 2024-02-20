package com.github.enteraname74

import com.github.enteraname74.composable.WelcomeComposable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.enteraname74.screens.MainScreen
import com.github.enteraname74.viewmodel.MainScreenViewModelImpl
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Example of injection of a ViewModel with koin.
            val mainActivityViewModel = koinViewModel<MainScreenViewModelImpl>()
            MainScreen(
                viewModel = mainActivityViewModel
            )
        }
    }
}