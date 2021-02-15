package com.mycodeflow.utils

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.mycodeflow.data.MovieListItem
import com.mycodeflow.ui.MainActivity
import com.mycodeflow.ui.R


class MovieUpdateNotification(val context: Context, val movie: MovieListItem) {

    private val notificationManagerCompat by lazy {
        NotificationManagerCompat.from(context)
    }

    private val largeNotificationIcon by lazy {
        BitmapFactory.decodeResource(context.resources, R.drawable.movie_large_notif_icon)
    }

    private val moviePendingIntent by lazy {
        val contentUri = "com.mycodeflow://detail/movie/${movie.id}".toUri()
        //intent indicating the place for redirecting user to a fragment detail
        val openDetailIntent = Intent(context, MainActivity::class.java)
                .setAction(Intent.ACTION_VIEW)
                .setData(contentUri)
        PendingIntent.getActivity(context, 1, openDetailIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun sendNotification(){
        registerNotificationChannel()
        val notification = createNotification()
        notificationManagerCompat.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notification)
    }

    private fun registerNotificationChannel(){
        val channel = NotificationChannelCompat
            .Builder(CHANNEL_ID, IMPORTANCE_DEFAULT)
            .setName(context.getString(R.string.channel_name))
            .setDescription(context.getString(R.string.channel_description_text))
            .build()
        //registering the created channel in the system
        notificationManagerCompat.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getString(R.string.new_movie_notif_title))
                .setContentText("${movie.title} is available for watching. Enjoy!")
                .setSmallIcon(R.drawable.star_icon_on)
                .setLargeIcon(largeNotificationIcon)
                .setContentIntent(moviePendingIntent)
                .build()
    }

    companion object {
        const val CHANNEL_ID = "movie_update_channel"
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_TAG = "new_movie_tag"
        const val IMPORTANCE_DEFAULT = NotificationManagerCompat.IMPORTANCE_DEFAULT
    }
}
