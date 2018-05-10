package com.townwang.town.daemon

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context

import java.lang.ref.WeakReference

/**
 * Description:   screen屏幕管理者
 */
class ScreenManager private constructor(private val mContext: Context) {

    private var mActivityWref: WeakReference<Activity>? = null

    fun setActivity(pActivity: Activity) {
        mActivityWref = WeakReference(pActivity)
    }

    fun startActivity() {
        // 开启一个像素的SinglePixelActivity
        SinglePixelActivity.actionToSinglePixelActivity(mContext)
    }

    fun finishActivity() {
        //结束掉SinglePixelActivity
        if (mActivityWref != null) {
            val activity = mActivityWref!!.get()
            activity?.finish()
        }
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var gDefualt: ScreenManager? = null

        fun getInstance(pContext: Context): ScreenManager {
            if (gDefualt == null) {
                gDefualt = ScreenManager(pContext.applicationContext)
            }
            return gDefualt as ScreenManager
        }
    }

}
