package com.github.enteraname74

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.github.enteraname74.model.notification.MusikNotification
import com.github.enteraname74.remotedatasource.remoteDataSourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startKoin {
            androidContext(applicationContext)
            modules(appModule, remoteDataSourceModule)
        }
    }

    /**
    * Create the channel used by the Notification.
    */
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            MusikNotification.MUSIC_NOTIFICATION_CHANNEL_ID,
            "Music playback notification",
            NotificationManager.IMPORTANCE_LOW
        )
        channel.description = "Used to control the music playback."

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}