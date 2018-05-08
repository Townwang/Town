package com.townwang.town.base.baseImpl

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.townwang.town.base.BasePresenter
import com.townwang.town.base.BaseView
import com.townwang.town.mvp.widget.LoadingView

/**
 * @author Town
 * @created at 2018/5/7 14:56
 * @Last Modified by: Town
 * @Last Modified time: 2018/5/7 14:56
 * @Remarks Activity基类
 */
abstract class BaseActivity<P : BasePresenter> : AppCompatActivity(), BaseView {
    //定义一个逻辑文件
    lateinit var presenter: P
    //定义一个上下文
    lateinit var context: Context
    //定义一个弹框
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        presenter = initPresenter()
    }

    override fun onDestroy() {
        presenter.detach()//在presenter中解绑释放view
        super.onDestroy()
    }

    override fun showLoading(msg: String) {
        if (dialog == null) {
            dialog = LoadingView.showLoading(context, msg)
        }
    }

    override fun dissLoading() {
        if (dialog != null) {
            LoadingView.closeDialog(dialog)
            dialog = null
        }
    }

    /**
     * 在子类中初始化对应的presenter
     *
     */
    abstract fun initPresenter(): P
}