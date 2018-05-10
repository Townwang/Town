package com.townwang.town.daemon

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder

/**
 * Description:   方法1：把MainActivity的业务逻辑放到Service中，使得进程更加轻量
 */
class LiveService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // 屏幕管理者的单例
        val screenManager = ScreenManager.getInstance(this)
        // 屏幕广播监听器
        val listener = ScreenBroadcastListener(this)
        listener.registerListener(object : ScreenBroadcastListener.ScreenStateListener {
            override fun onScreenOn() {
                // 屏幕打开时候，关闭一个像素的Activity
                screenManager.finishActivity()
            }

            override fun onScreenOff() {
                // 屏幕锁屏的时候，开启一个像素的SinglePixelActivity
                screenManager.startActivity()
            }
        })
        return Service.START_REDELIVER_INTENT
    }

    companion object {

        fun toLiveService(pContext: Context) {
            val intent = Intent(pContext, LiveService::class.java)
            pContext.startService(intent)
        }
    }
}