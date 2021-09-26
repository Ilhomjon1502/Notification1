package com.ilhomjon.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
//import android.app.RemoteInput  //bu xato androidx ni ihidan olishi kerak
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.ilhomjon.notification.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    //link: https://startandroid.ru/ru/uroki/vse-uroki-spiskom/509-android-notifications-osnovy.html

    lateinit var binding:ActivityMainBinding
    var CHANNEL_ID = "1"
    var notificationId = 1

    var max = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNotification.setOnClickListener {

//            var intent = Intent(this, MainActivity::class.java)

            var intent = Intent(this, MyService::class.java)
            intent.action = "ACTION_REPLY"
            intent.putExtra("EXTRA_ITEM_ID", notificationId)

//            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT) //activityni ochib berish uchun

            val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                val remoteInput = RemoteInput.Builder("EXTRA_TEXT_REPLY")
                .setLabel("type message")
                .build()

            val action = NotificationCompat.Action.Builder(
                android.R.drawable.ic_menu_send,
                "Reply", pendingIntent
            ).addRemoteInput(remoteInput)
                .build()


            var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("textTitle")
                .setContentText("textContent")
                    //setContextText ishlashi uchun setStyle ishlatmaslik kerak
//                .setStyle(
//                    NotificationCompat.BigTextStyle()
//                        .bigText("ko'pgina yozuv")
//                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent) //notification ni bosganda ilovani ochib berishi
                .setAutoCancel(true )
//                .setProgress(max, 0, true)
//                .addAction(android.R.drawable.ic_delete, "Delete", pendingIntent)
                .addAction(action)
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = getString(R.string.app_name)
                val descriptionText = getString(R.string.app_name)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                notificationManager.createNotificationChannel(channel)
            }


            notificationManager.notify(notificationId, builder.build())


//            //for progress
//            Thread(object : Runnable{
//                override fun run() {
//                    TimeUnit.SECONDS.sleep(1)
//                    var progress = 0
//
//                    while (progress < max){
//                        try {
//                            TimeUnit.SECONDS.sleep(1)
//
//                        }catch (e:Exception){
//                            e.printStackTrace()
//                        }
//                        progress += 10
//                        builder.setProgress(max, progress, false)
//                            .setContentText("$progress")
//                        notificationManager.notify(notificationId, builder.build())
//                    }
//
//                    builder.setProgress(0, 10, false)
//                        .setContentText("Completed")
//                    notificationManager.notify(notificationId, builder.build())
//                }
//            }).start()
        }

        binding.btnCancel.setOnClickListener {
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(notificationId)
        }
    }




    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}