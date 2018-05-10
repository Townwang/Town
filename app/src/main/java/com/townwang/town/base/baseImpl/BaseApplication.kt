package com.townwang.town.base.baseImpl
import android.app.Application
import com.townwang.town.daemon.LiveService

/**
 * @author Town
 * @created at 2018/3/15 11:53
 * @Last Modified by: Town
 * @Last Modified time: 2018/3/15 11:53
 * @Remarks 全局Application
 */

class BaseApplication : Application() {
    //伴生对象
    companion object {
        /**
         * 全局Application
         */
        private var app: BaseApplication? = null
        fun instance() = app!!
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        LiveService.toLiveService(this)
    }
}
