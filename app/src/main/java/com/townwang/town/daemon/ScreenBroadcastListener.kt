package com.townwang.town.daemon

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

/**
 * Description:   监听 系统锁屏的广播监听器
 */

class ScreenBroadcastListener(context: Context) {
    private val mContext: Context = context.applicationContext

    private val mScreenReceiver: ScreenBroadcastReceiver

    private var mListener: ScreenStateListener? = null

    init {
        mScreenReceiver = ScreenBroadcastReceiver()
    }

    interface ScreenStateListener {

        fun onScreenOn()

        fun onScreenOff()
    }

    /**
     * screen状态广播接收者
     */
    inner class ScreenBroadcastReceiver : BroadcastReceiver() {
        private var action: String? = null

        override fun onReceive(context: Context, intent: Intent) {
            action = intent.action
            if (Intent.ACTION_SCREEN_ON == action) { // 开屏
                mListener!!.onScreenOn()
            } else if (Intent.ACTION_SCREEN_OFF == action) { // 锁屏
                mListener!!.onScreenOff()
            }
        }
    }

    fun registerListener(listener: ScreenStateListener) {
        mListener = listener
        registerListener()
    }

    private fun registerListener() {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        mContext.registerReceiver(mScreenReceiver, filter)
    }
}
