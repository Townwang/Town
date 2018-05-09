package com.townwang.town.mvp.presenter

import com.townwang.town.base.baseImpl.BaseBean
import com.townwang.town.base.baseImpl.BasePresenterImpl
import com.townwang.town.model.Api
import com.townwang.town.mvp.bean.TestBean
import com.townwang.town.mvp.contact.TestContact
import com.townwang.town.retroft.ApiException
import com.townwang.town.retroft.ExceptionHelper
import com.townwang.town.utils.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers


/**
 * @author Town
 * @created at 2018/5/7 12:03
 * @Last Modified by: Town
 * @Last Modified time: 2018/5/7 12:03
 * @Remarks 测试逻辑
 */

class TestPresenter(view: TestContact.view) : BasePresenterImpl<TestContact.view>(view), TestContact.presenter {
    /**
     * 得到俺Github上的Json
     */
    override fun getViewText() {
        Api.getInstance().testTown()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    addDisposable(it)
                    view?.showLoading("加载中")
                }.map(Function<BaseBean<TestBean>, TestBean> {
                    if (it.code == Config.成功) {
                        return@Function it.data
                    } else {
                        throw ApiException(it.msg)
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //成功
                    view?.dissLoading()
                    view?.setTest("姓名:${it.name}\n 博客:${it.blog} \n 微信:${it.wechat} \n 邮箱:${it.email}")
                }, {
                    //失败 报错
                    view?.dissLoading()
                    ExceptionHelper.handleException(it)
                })
    }

}

