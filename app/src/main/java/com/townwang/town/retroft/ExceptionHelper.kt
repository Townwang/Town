package com.townwang.town.retroft

import android.net.ParseException
import com.google.gson.JsonParseException
import com.townwang.town.utils.Config
import com.townwang.town.utils.Log
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author Town
 * @created at 2018/3/8 12:00
 * @Last Modified by: Town
 * @Last Modified time: 2018/3/8 12:00
 * @Remarks TODO 异常抛出帮助类
 */
object ExceptionHelper {

    fun handleException(e: Throwable): String {
        e.printStackTrace()
        var error: String
        var testError: String? = null
        if (e is SocketTimeoutException) {//网络超时
            error = "网络连接异常"
            testError = error
        } else if (e is ConnectException) { //均视为网络错误
            error = "网络连接异常"
            testError = error
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {   //均视为解析错误
            error = "数据解析异常"
        } else if (e is ApiException) {//服务器返回的错误信息
            error = e.cause?.message!!

            when (error) {
                Config.失败 -> {
                    error = "请求失败"
                    testError = error
                }
                Config.失败 -> error = "Token过期"
            }

        } else if (e is UnknownHostException) {
            error = "网络连接异常"
            testError = error
        } else if (e is IllegalArgumentException) {
            error = "下载文件已存在"
        } else if (e is NullPointerException) {
            error = "空指针异常报错"
        } else {//未知错误

            error = "未知错误"
        }
        if (testError != null) {
          TODO("弹出错误原因")
        }
        Log.e("错误信息: $error")
        return error
    }

}