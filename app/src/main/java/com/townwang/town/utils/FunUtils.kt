package com.townwang.town.utils

import android.content.Context
import android.content.pm.PackageManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * @author Town
 * @created at 2018/4/2 10:34
 * @Last Modified by: Town
 * @Last Modified time: 2018/4/2 10:34
 * @Remarks 方法封装类
 */


object  FunUtils {
    /***************************************内部变量**********************************************/
    private var MIUI8orLater: Boolean? = null
    /**
     * 点击初始时间戳
     */
    private var lastClickTime: Long = 0

    /******************************************封装方法********************************************/
    /**
     * 防止多次点击
     * @return
     */
    val isFastDoubleClick: Boolean get() {
            val time = System.currentTimeMillis()
            if (time - lastClickTime in 1..499) {
                return true
            }
            lastClickTime = time
            return false
        }


        /**
         * @return
         */
        val isMIUI8orLater: Boolean get() {
                if (MIUI8orLater != null) return MIUI8orLater!!
                val propName = "ro.miui.ui.version.name"
                var line: String? = null
                var input: BufferedReader? = null
                try {
                    val p = Runtime.getRuntime().exec("getprop " + propName)
                    input = BufferedReader(InputStreamReader(p.inputStream), 1024)
                    line = input.readLine()
                    input.close()
                } catch (ex: IOException) {
                    MIUI8orLater = false
                } finally {
                    if (input != null) {
                        try {
                            input.close()
                        } catch (ignored: IOException) {
                        }

                    }
                }
                    try {
                        val num = Integer.parseInt(line?.replace("[\\D]".toRegex(), ""))
                        MIUI8orLater = num >= 8
                    } catch (e: Exception) {
                        MIUI8orLater = false
                    }
                return MIUI8orLater!!
            }

        /**
         * 获取版本号名称
         *
         * @param context 上下文
         * @return
         */
        fun getVerName(context: Context): String {
            var verName = ""
            try {
                verName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return verName
        }


}
