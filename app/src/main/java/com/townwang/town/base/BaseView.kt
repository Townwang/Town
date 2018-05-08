package com.townwang.town.base

/**
 * @author Town
 * @created at 2018/5/7 14:49
 * @Last Modified by: Town
 * @Last Modified time: 2018/5/7 14:49
 * @Remarks 基类接口
 */
interface BaseView {
    /**
     * 弹出加载中
     */
    fun showLoading(msg:String)

    /**
     * 隐藏加载中
     */
    fun dissLoading()
}