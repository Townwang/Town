package com.townwang.town.daemon

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder

import com.townwang.town.R

/**
 * Description:   方法2：前台进程
 */

class KeepLiveService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        //API 18以下，直接发送Notification并将其置为前台
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            startForeground(NOTIFICATION_ID, Notification())
        } else {
            //API 18以上，发送Notification并将其置为前台后，启动InnerService
            val builder = Notification.Builder(this)
            builder.setSmallIcon(R.mipmap.ic_launcher)
            startForeground(NOTIFICATION_ID, builder.build())
            startService(Intent(this, InnerService::class.java))
        }
    }

    @SuppressLint("Registered")
    inner class InnerService : Service() {
        override fun onBind(intent: Intent): IBinder? {
            return null
        }

        override fun onCreate() {
            super.onCreate()
            //发送与KeepLiveService中ID相同的Notification，然后将其取消并取消自己的前台显示
            val builder = Notification.Builder(this)
            builder.setSmallIcon(R.mipmap.ic_launcher)
            startForeground(NOTIFICATION_ID, builder.build())
            Handler().postDelayed({
                stopForeground(true)
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.cancel(NOTIFICATION_ID)
                stopSelf()
            }, 100)

        }
    }

    companion object {

        const val NOTIFICATION_ID = 0x11
    }
}