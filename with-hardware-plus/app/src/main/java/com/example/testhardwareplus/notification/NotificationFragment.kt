package com.example.testhardwareplus.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.testhardwareplus.MainActivity
import com.example.testhardwareplus.R

/*
 * Notes:
 *
 * - Starting in Android 8.0 Oreo (API level 26), all notifications must be assigned to a channel
 *      -The user interface refers to channels as "categories."
 *      -One app can have multiple notification channels. One for each type
 *
 * - The notification is shown by an internal app in the OS (The Notification Manager)
 * - Use pending intents to navigate to the app when the user clicks on the notification
 *      - A pending intent allows another app to execute a piece of code from my app
 */

// https://developer.android.com/training/notify-user/build-notification

// a notification pops up and the user clicks it to open up the app

class NotificationFragment : Fragment() {

    private lateinit var notifyBtn: Button

    val CHANNEL_ID = "channelID"   // the value doesn't really matter
    val CHANNEL_NAME = "channelName"
    val NOTIFICATION_ID = 0     // to differentiate between different notifications

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        notifyBtn = view.findViewById(R.id.notify_btn)

        // use Intent to travel to the activity from the notification
        // the second parameter is the activity that will be opened
        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(requireContext()).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        createNotificationChannel()
        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle("Test notification")
            .setContentText("This is the text of the test notification")
            .setSmallIcon(R.drawable.ic_buildings_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)  // user clicks the notification and opens the activity
            .build()

        val notificationManager = NotificationManagerCompat.from(requireContext())

        notifyBtn.setOnClickListener {
            // will display the notification
            notificationManager.notify(NOTIFICATION_ID, notification)
        }

        return view
    }

    fun createNotificationChannel() {
        // check if the version of android is Oreo or greater
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.RED
                enableLights(true)
            }
            val notificationManager = requireActivity()
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}
