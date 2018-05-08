package com.townwang.town.retroft


import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * @author Town
 * @created at 2018/3/8 12:00
 * @Last Modified by: Town
 * @Last Modified time: 2018/3/8 12:00
 * @Remarks  Retrofit 顶层接口
 */
interface BaseApi {
    /**
     * 构建Retrofit
     */
      fun getRetrofit():Retrofit?
    /**
     * 日志拦截器
     */
    fun getLoggerInterceptorBody(): HttpLoggingInterceptor
}
