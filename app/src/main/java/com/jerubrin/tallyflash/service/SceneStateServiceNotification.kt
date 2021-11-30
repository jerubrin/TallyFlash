package com.jerubrin.tallyflash.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import com.jerubrin.tallyflash.MainActivity
import com.jerubrin.tallyflash.R
import com.jerubrin.tallyflash.domain.usecase.ReadSharedPrefMainUseCase
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.SceneState
import javax.inject.Inject

class SceneStateServiceNotification @Inject constructor(
    private val readSharedPrefMainUseCase: ReadSharedPrefMainUseCase,
    private var notificationBuilder: Notification.Builder
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(service: Service, intent: Intent) {
        intent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent =
            PendingIntent.getActivity(service, 0, intent, 0)
        
        notificationBuilder = setNotificationText(
            title = service.getText(R.string.app_name).toString(),
            text = service.getText(R.string.notification_message).toString()
        )
            .setContentIntent(pendingIntent)
        
        service.startForeground(SceneStateService.ONGOING_NOTIFICATION_ID, notificationBuilder.build())
    }
    
    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(context: Context, channelId: String, channelName: String) {
        val channel = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
    }
    
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationText(title: String, text: String): Notification.Builder =
        notificationBuilder
            .setContentTitle(title)
            .setContentText(text)
    
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateNotificationText(context: Context, title: String, text: String) {
        val notificationManager =
            context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        notificationBuilder = setNotificationText(title, text)
        notificationManager.notify(
            SceneStateService.ONGOING_NOTIFICATION_ID,
            notificationBuilder.build()
        )
    }
    
    @RequiresApi(Build.VERSION_CODES.O)
    fun changeNotification(context: Context, currentScene: Scene, sceneState : SceneState) {
        val settingsData = readSharedPrefMainUseCase.execute(Unit)
        when(sceneState) {
            SceneState.ACTIVE ->
                updateNotificationText(
                    context,
                    title = settingsData.activeText,
                    text = currentScene.shortTitle
                )
            SceneState.PREVIEW ->
                updateNotificationText(
                    context,
                    title = settingsData.previewText,
                    text = currentScene.shortTitle
                )
            SceneState.OFF ->
                updateNotificationText(
                    context,
                    title = settingsData.offText,
                    text = currentScene.shortTitle
                )
            SceneState.ERROR ->
                updateNotificationText(
                    context,
                    title = context.getString(R.string.error),
                    text = currentScene.shortTitle
                )
        }
    }
}