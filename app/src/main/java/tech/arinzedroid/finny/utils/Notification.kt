package tech.arinzedroid.finny.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import tech.arinzedroid.finny.R

class Notification(private val context: Context) {

    private val chanelId = "channelID"; private val notifyId = 21

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Finny Channel"
            val descriptionText = "This channel is used to send notifications on finny app"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(chanelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }else{

        }
    }

    fun createNotification(title: String,text: String){
        createNotificationChannel()

        val mBuilder = NotificationCompat.Builder(context, chanelId)
                .setSmallIcon(R.mipmap.finny_icon)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(text))
                .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)){
            notify(notifyId,mBuilder.build())
        }
    }
}