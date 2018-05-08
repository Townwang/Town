package com.townwang.town.model

import com.townwang.town.base.baseImpl.BaseBean
import com.townwang.town.mvp.bean.TestBean

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * @author Town
 * @created at 2018/3/5 11:20
 * @Last Modified by: Town
 * @Last Modified time: 2018/3/5 11:20
 * @Remarks 接口示例
 */
interface RetrofitService  {
    //伴生对象 提供顶级Url
    companion object {
        //服务器接口
        const val BASE_URL = "https://townwang.com/"
    }
    /**
     *   GET测试示例
     *  {id} Url参数拼接 {@path("id") 名字:类型 }
     *  {map}body参数
     *  返回BaseBean参数解析后数据
     */
    @GET("test/{id}")
    fun testGET(@Path("id") id: Int, @QueryMap map: Map<String, Any>): Observable<BaseBean<*>>


    /**
     * POST测试示例
     *  {id} Url参数拼接 {@path("id") 名字:类型 }
     *  {map} body参数
     *  返回BaseBean参数解析后数据
     */
    @POST("test/{id}")
    fun testPOST(@Path("id") id: Int, @QueryMap map: Map<String, Any>): Observable<BaseBean<*>>

    /**
     * 测试类调用
     */
    @GET("app/json/test-t.json")
    fun testTown():Observable<BaseBean<TestBean>>

}