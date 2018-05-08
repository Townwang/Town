package com.townwang.town.base.baseImpl

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import com.townwang.town.base.BasePresenter
import com.townwang.town.base.BaseView
import com.townwang.town.mvp.widget.LoadingView

/**
 * @author Town
 * @created at 2018/5/7 15:20
 * @Last Modified by: Town
 * @Last Modified time: 2018/5/7 15:20
 * @Remarks Fragment基类
 */
abstract class BaseFragment<P:BasePresenter>: Fragment(),BaseView{
    open lateinit var presenter: P
    private var isViewCreate = false//view是否创建
    private var isViewVisible = false//view是否可见
    private var isFirst = true//是否第一次加载
    private var dialog: Dialog? = null//加载框

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = initPresenter()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewCreate = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isViewVisible = isVisibleToUser
        if (isVisibleToUser && isViewCreate) {
            visibleToUser()
        }
    }

    @SuppressLint("NewApi")
    override fun showLoading(msg: String) {
        dialog = LoadingView.showLoading(context,msg)!!
    }

    override fun dissLoading() {
        LoadingView.closeDialog(dialog)
    }

    /**
     * 懒加载
     * 让用户可见
     * 第一次加载
     */
    open fun firstLoad() {

    }

    /**
     * 懒加载
     * 让用户可见
     */
    private fun visibleToUser() {
        if (isFirst) {
            firstLoad()
            isFirst = false
        }
    }
    override fun onResume() {
        super.onResume()
        if (isViewVisible) {
            visibleToUser()
        }
    }

    override fun onDestroyView() {
        presenter.detach()
        isViewCreate = false
        super.onDestroyView()
    }
    /**
     * 在子类中初始化对应的presenter
     *
     */
    abstract fun initPresenter(): P
}

