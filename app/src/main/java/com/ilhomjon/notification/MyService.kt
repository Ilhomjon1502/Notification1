package com.ilhomjon.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.RemoteInput
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat

class MyService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == "ACTION_REPLY"){
            val resultsFromIntent = RemoteInput.getResultsFromIntent(intent)
            if (resultsFromIntent != null){
                val str = resultsFromIntent.getCharSequence("EXTRA_TEXT_REPLY")
                Toast.makeText(this, "$str", Toast.LENGTH_SHORT).show()
            }
            val notificationId = intent.getIntExtra("EXTRA_ITEM_ID",0)
            val builder = NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Bu content title")
                .setContentText("Bu content text")
            val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId, builder.build())
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {

        return null
    }
}