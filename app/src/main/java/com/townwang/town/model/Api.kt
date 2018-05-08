package com.townwang.town.model

import com.townwang.town.retroft.BaseApiImpl

/**
 * @author Town
 * @created at 2018/3/5 11:20
 * @Last Modified by: Town
 * @Last Modified time: 2018/3/5 11:20
 * @Remarks 网络访问
 */
class Api(baseUrl: String) : BaseApiImpl(baseUrl) {
    companion object {
        private val api = Api(RetrofitService.BASE_URL)
         fun getInstance(): RetrofitService? {
            return api.getRetrofit()?.create(RetrofitService::class.java)
        }
    }
}
