package com.droidafricana.moveery.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.droidafricana.moveery.R
import com.droidafricana.moveery.utils.Constants.MOVIE_REFRESH_NOTIFICATION_CHANNEL_ID
import com.droidafricana.moveery.utils.Constants.MOVIE_REFRESH_NOTIFICATION_ID

/**A helper for the notifications*/
object NotificationUtils {

    /**Send put the notification to the user
     * @param context is context
     * @param message is the actual message for the notification
     * */
    fun sendNotification(context: Context, message: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                MOVIE_REFRESH_NOTIFICATION_CHANNEL_ID,
                context.getString(R.string.main_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(mChannel)
        }

        //Use builder pattern to create the notification
        val notificationBuilder =
            NotificationCompat.Builder(context, MOVIE_REFRESH_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.launcher)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        }

        notificationManager.notify(MOVIE_REFRESH_NOTIFICATION_ID, notificationBuilder.build())
    }
}